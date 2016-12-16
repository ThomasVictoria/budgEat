package com.serious.budgeat.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.serious.budgeat.Model.Order;
import com.serious.budgeat.R;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends AppCompatActivity {

    private String session_email;
    private String session_id;
    private Order order;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        id = preferences.getString("user_id", "");
        session_email = preferences.getString("user_email", "");

        if (extras != null) {
            order = (new Gson()).fromJson(extras.getString("SESSION_ORDER"), Order.class);
        }
    }

    @OnClick(R.id.sendPayment)
    void sendPayment(){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        TextView cardNumber = (TextView)findViewById(R.id.cardNumber);
        TextView month = (TextView) findViewById(R.id.peremptionMonth);
        TextView year = (TextView) findViewById(R.id.peremptionYear);
        TextView cryptogramme = (TextView) findViewById(R.id.cryptogramme);
        Integer valueMonth = Integer.parseInt( month.getText().toString());
        Integer valueYears = Integer.parseInt( year.getText().toString());

    }

    private void stripeInit(TextView cardNumber, Integer valueMonth, Integer valueYears, TextView cryptogramme){
        Card card = new Card(cardNumber.getText().toString(), valueMonth, valueYears, cryptogramme.getText().toString());

        Log.d("Stripe", "DEBUT");

        Stripe stripe = null;
        try {
            stripe = new Stripe("pk_test_Z7Jygw96IdsAouhSoJbMTDsG");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            Log.d("Stripe", "EXEPTION");
        }

        if (!card.validateCard()) {
            Log.d("", "CARD PAS OK");
        }

        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {

                        sendRequest(token);
                        Log.d("Stripe", "TOKEN OK");

                    }
                    public void onError(Exception error) {
                        // Show localized error message
                        Log.d("", "STRIPE PAS OK");
                        Log.e("MYAPP", "exception", error);
                    }
                }
        );
    }

    private void sendRequest(Token token){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("stripeToken", token.getId().toString());
            jsonObject.put("lastname", session_email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("STRIPE", "REQUETTE DEBUT");
        AndroidNetworking.post("https://budgeat.stan.sh/la/route/de/payment")
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // la reponse de stann
                        Log.d("Stripe", "RESPONSE STAN");
                        try {
                            if(response.get("success").toString()=="") {
                                sendOrder();
                                Log.d("Stripe", "WIN");
                            }
                        } catch (JSONException e) {
                            Log.d("Stripe", "STAN BUG");
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {

                    }
                });

    }

    private void sendOrder(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("has_charcut", order.getMeat().toString());
            jsonObject.put("has_legume", order.getVegetable().toString());
            jsonObject.put("bread_type", order.getBread().toString());
            jsonObject.put("has_cheese", order.getCheese().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://budgeat.stan.sh/user/"+ id +"/order")
                .addJSONObjectBody(jsonObject)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // la reponse de stann
                        try {
                            if(response.get("success").toString() =="") {

                                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                                intent.putExtra("SESSION_ORDER", (new Gson()).toJson(order));
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {

                    }
                });
    }

}
