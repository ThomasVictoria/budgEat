package com.serious.budgeat.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.serious.budgeat.Model.Order;
import com.serious.budgeat.R;
import com.serious.budgeat.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity {

    static private final String screenName = "Order";
    public Order order = new Order();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        controller();
    }

    private void controller() {
        setContentView(R.layout.activity_order);
        ImageView image = (ImageView)findViewById(R.id.sandwich);
        if(order.getBread() == null) {
            generateView("bread");
        } else if(order.getMeat() == null) {
            image.setImageResource(getResources().getIdentifier("sandwich_2", "drawable", getApplicationContext().getPackageName()));
            generateView("meat");
        } else if(order.getCheese() == null) {
            image.setImageResource(getResources().getIdentifier("sandwich_3", "drawable", getApplicationContext().getPackageName()));
            generateView("cheese");
        } else if(order.getVegetable() == null) {
            image.setImageResource(getResources().getIdentifier("sandwich_4", "drawable", getApplicationContext().getPackageName()));
            generateView("legume");
        } else {
            Intent intent = new Intent(OrderActivity.this, CardActivity.class);

            intent.putExtra("SESSION_ORDER", (new Gson()).toJson(order));
            startActivity(intent);
        }
    }

    private void generateView(final String type){
        AndroidNetworking.get("http://budgeat.stan.sh/"+type+"s")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray item = response.getJSONArray(type+"s");

                            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.itemsContainer);
                            LinearLayout linearLayoutText = (LinearLayout)findViewById(R.id.itemsTextContainer);

                            for(int i = 0; i<item.length(); i++) {
                                ImageButton btn = new ImageButton(getBaseContext());
                                TextView textView = new TextView(getBaseContext());

                                final String name = String.valueOf((String) item.getJSONObject(i).get(type));
                                final Integer id = Integer.valueOf((String) item.getJSONObject(i).get(type+"_id"));

                                btn.setId(id);
                                Integer imageId = getResources().getIdentifier(type +"_"+ id.toString(),
                                        "drawable", getApplicationContext().getPackageName());


                                btn.setImageResource(imageId);
                                textView.setText(name);

                                Integer width = findViewById(R.id.activity_order).getWidth();

                                Integer btnWidth = width / item.length();

                                textView.setMinimumWidth(btnWidth-1);
                                textView.setMinimumHeight(btnWidth-1);
                                textView.setGravity(Gravity.CENTER);
                                textView.setTextColor(getResources().getColor(R.color.colorText));
                                linearLayoutText.addView(textView);

                                btn.setBackgroundColor(Color.TRANSPARENT);
                                btn.setMinimumWidth(btnWidth-1);
                                btn.setMinimumHeight(btnWidth-1);
                                linearLayout.addView(btn);

                                btn.setOnClickListener(new View.OnClickListener()   {
                                    public void onClick(View v)  {
                                        Log.d("Button Log", String.valueOf(v.getId()));
                                        if(type == "bread"){
                                            order.setBread(v.getId());
                                            order.setBreadName(name);
                                        } else if (type == "meat") {
                                            order.setMeat(v.getId());
                                            order.setMeatName(name);
                                        } else if (type == "cheese") {
                                            order.setCheese(v.getId());
                                            order.setCheeseName(name);
                                        } else if (type == "legume") {
                                            order.setVegetable(v.getId());
                                            order.setVegetablesName(name);
                                        }
                                        controller();
                                    }
                                });

                                Log.d("Button", "genrate "+ order.getBread());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getApplicationContext(), "Erreur reseaux", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.pushOpenScreenEvent(this, screenName);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utils.pushCloseScreenEvent(this, screenName);
    }

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
