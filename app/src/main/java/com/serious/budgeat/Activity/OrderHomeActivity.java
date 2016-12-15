package com.serious.budgeat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.serious.budgeat.R;
import com.serious.budgeat.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderHomeActivity extends AppCompatActivity {
    private static final String screenName = "OrderHome";
    private String session_email;
    private String session_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            session_email = extras.getString("SESSION_EMAIL");
            session_id = extras.getString("SESSION_ID");
        }

        Log.d("zedrftg", session_id);

       // AndroidNetworking.get("https://budgeat.stan.sh/users/"+ String.valueOf(session_id) +"/orders")
        AndroidNetworking.get("https://budgeat.stan.sh/users/1/orders")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (true) {

                            } else {

                            }
                            for (int i = 0; i<response.length(); i++) {
                                String id = response.getJSONObject(0).get("id").toString();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.d("leiqygfqer", error.toString());
                        Toast.makeText(getApplicationContext(), "Erreur reseaux", Toast.LENGTH_LONG).show();
                    }
                });

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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

    @OnClick(R.id.orderPageButton)
    void goToOrder(){
        Intent intent = new Intent(OrderHomeActivity.this, OrderActivity.class);
        intent.putExtra("SESSION_EMAIL", session_email);
        intent.putExtra("SESSION_ID", session_id);
        startActivity(intent);
    }
}
