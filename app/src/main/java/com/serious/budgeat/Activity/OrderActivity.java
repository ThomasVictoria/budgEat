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
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        Order order = new Order();
        if(order.getBread() == null) {
            generateView("breads");
        } else if(order.getMeat() == null) {

        } else if(order.getCheese() == null) {

        } else if(order.getVegetable() == null) {

        }

    }

    private void generateView(String type){
        AndroidNetworking.get("http://budgeat.stan.sh/"+type)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray breads = response.getJSONArray("breads");

                            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.itemsContainer);

                            for(int i = 0; i<breads.length(); i++) {
                                ImageButton btn = new ImageButton(getBaseContext());
                                btn.setId(Integer.valueOf((String) breads.getJSONObject(i).get("bread_id")));

                                Integer imageId = R.drawable.yolo;

                                btn.setImageResource(imageId);
                                Integer width = findViewById(R.id.activity_order).getWidth();

                                Integer btnWidth = width / breads.length();
                                btn.setMinimumWidth(btnWidth);
                                btn.setMinimumHeight(btnWidth);
                                linearLayout.addView(btn);

                                btn.setOnClickListener(new View.OnClickListener()   {
                                    public void onClick(View v)  {

                                    }
                                });

                                Log.d("Button", "genrate "+imageId.toString());
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
