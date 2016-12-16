package com.serious.budgeat.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.serious.budgeat.Fragment.GoOrderFragment;
import com.serious.budgeat.Fragment.NothingFragmentBottom;
import com.serious.budgeat.Fragment.NothingFragmentTop;
import com.serious.budgeat.Fragment.OrderFragment;
import com.serious.budgeat.Fragment.ReductionFragment;
import com.serious.budgeat.Model.Order;
import com.serious.budgeat.R;
import com.serious.budgeat.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String screenName = "Main";
    private Order order;
    final Integer price = 6;
    final Integer TurnOver = 14;

    public Integer getPrice() { return price; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String email = preferences.getString("user_email", "");
        String id = preferences.getString("user_id", "");

        hasOrdered(id, email);

        ButterKnife.bind(this);
    }

    private void generateFragment(String id, String email, Boolean ordered) {

        Calendar c = Calendar.getInstance();
        Integer hour = c.get(Calendar.HOUR_OF_DAY);

        if (hour < TurnOver){
            if (ordered) {
                getReductionView(id, email);
                getOrderFragment(email);

            } else {

                getNothingFragment();
            }
        } else {
            if (ordered) {

                getReductionView(id, email);
                getOrderFragment(email);
            } else {

                getReductionView(id, email);
                goToOrderFragment();
            }
        }
    }

    public void hasOrdered(final String id, final String email){

        AndroidNetworking.get("http://budgeat.stan.sh/users/"+id+"/orders")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String test = String.valueOf(response.get("success"));
                            generateFragment(id, email, false);
                        } catch (JSONException e) {
                            for(Integer i = 0; i < response.length();i++){

                                try {
                                    Integer payed = Integer.valueOf(response.getJSONArray("orders").getJSONObject(i).get("is_payed").toString());
                                    if(payed == 0){
                                        generateFragment(id, email, true);
                                    } else {
                                        generateFragment(id, email, false);
                                    }

                                } catch (JSONException ex) {
                                    ex.printStackTrace();

                                }
                            }
                        }
                    }
                    @Override
                    public void onError(ANError error) {

                    }
                });

    }

    public void goToOrderFragment(){
        GoOrderFragment goOrderFragment = new GoOrderFragment();
        goOrderFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().replace(R.id.bottomFragment, goOrderFragment).commit();
    }

    public void getOrderFragment(String email){

        OrderFragment orderFragment = new OrderFragment();
        orderFragment.setArguments(getIntent().getExtras());

        Bundle bundle = new Bundle();

        bundle.putString("email", email);

        orderFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.bottomFragment, orderFragment).commit();
    }

    public void getNothingFragment(){

        NothingFragmentTop nothingFragmentTop = new NothingFragmentTop();
        NothingFragmentBottom nothingFragmentBottom = new NothingFragmentBottom();

        nothingFragmentTop.setArguments(getIntent().getExtras());
        nothingFragmentBottom.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().replace(R.id.topFragment, nothingFragmentTop).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.bottomFragment, nothingFragmentBottom).commit();
    }

    public void getReductionView(String id, String email){

        ReductionFragment reductionFragment = new ReductionFragment();

        reductionFragment.setArguments(getIntent().getExtras());

        Bundle bundle = new Bundle();

        bundle.putString("id", id);
        bundle.putString("mail", email);

        reductionFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.topFragment, reductionFragment).commit();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Utils.pushCloseScreenEvent(this, screenName);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.pushOpenScreenEvent(this, screenName);
    }

}
