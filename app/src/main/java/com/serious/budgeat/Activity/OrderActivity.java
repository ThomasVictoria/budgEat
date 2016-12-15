package com.serious.budgeat.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import butterknife.OnClick;

import static android.R.interpolator.linear;

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
        if(order.getBread() == null) {
            generateView("bread");
        } else if(order.getMeat() == null) {
            generateView("meat");
        } else if(order.getCheese() == null) {
            generateView("cheese");
        } else if(order.getVegetable() == null) {
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

                            for(int i = 0; i<item.length(); i++) {
                                ImageButton btn = new ImageButton(getBaseContext());

                                final String name = String.valueOf((String) item.getJSONObject(i).get(type));

                                btn.setId(Integer.valueOf((String) item.getJSONObject(i).get(type+"_id")));

                                Log.d("NAME", name);
                                Log.d("ID", item.getJSONObject(i).get(type+"_id").toString());

                                Integer imageId = R.drawable.comte;

                                btn.setImageResource(imageId);
                                Integer width = findViewById(R.id.activity_order).getWidth();

                                Integer btnWidth = width / item.length();
                                btn.setMinimumWidth(btnWidth);
                                btn.setMinimumHeight(btnWidth);
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

}
