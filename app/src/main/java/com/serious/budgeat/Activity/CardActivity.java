package com.serious.budgeat.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.vision.text.Text;
import com.google.gson.Gson;
import com.serious.budgeat.Model.Order;
import com.serious.budgeat.R;

import com.serious.budgeat.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class CardActivity extends AppCompatActivity {

    static private final String screenName = "Card";
    private Order order;
    final private Double price = 6.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            order = (new Gson()).fromJson(extras.getString("SESSION_ORDER"), Order.class);
        }



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String email = preferences.getString("user_email", "");

        AndroidNetworking.get("https://budgeat.stan.sh/user/"+email)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String id = response.get("id").toString();
                            String ecole = response.get("ecole_id").toString();

                            getSchoolOrders(id, ecole);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {

                    }
                });

        TextView bread = (TextView)findViewById(R.id.sandwichComposition);
        bread.setText(order.getBreadName() +" + "+ order.getMeatName() +" + "+ order.getVegetablesName()
                +" + "+ order.getCheeseName());
    }

    private void getSchoolOrders(final String id, String ecole){

        AndroidNetworking.get("https://budgeat.stan.sh/schools/"+ecole+"/count")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Double orders = Double.valueOf(response.get("achats").toString());
                            TextView textViewreduction = (TextView)findViewById(R.id.reduction);
                            TextView textViewPrice = (TextView)findViewById(R.id.price);

                            final Double reducString = Double.valueOf(price * 1 * (orders / 100));
                            final Double finalPrice = price - reducString;

                            textViewreduction.setText(String.valueOf(orders.intValue())+"%");
                            textViewPrice.setText("Soit un prix de "+finalPrice+"€");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {

                    }
                });
    }

    @OnClick(R.id.buttonPayment)
    void gotoPayment(){
        Intent intent = new Intent(CardActivity.this, PaymentActivity.class);

        intent.putExtra("SESSION_ORDER", (new Gson()).toJson(order));
        startActivity(intent);
    }

    @OnClick(R.id.buttonReOrder)
    void reorder(){
        Intent intent = new Intent(CardActivity.this, OrderActivity.class);
        startActivity(intent);
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
