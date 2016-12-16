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
    final Integer TurnOver = 1;

    public Integer getPrice() { return price; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String email = preferences.getString("user_email", "");
        String id = preferences.getString("user_id", "");

        Bundle extras = getIntent().getExtras();

        hasOrdered(id, email);

        ButterKnife.bind(this);
    }

    private void generateFragment(String id, String email, Boolean ordered) {

        Calendar c = Calendar.getInstance();
        Integer hour = c.get(Calendar.HOUR_OF_DAY);

        if (hour < TurnOver){
            if (ordered) {
                getOrderFragment(email);
                Log.d("TEST", "COMANDE MATIN");
            } else {
                Log.d("TEST", "QUE DALLE");
                getNothingFragment();
            }
        } else {
            if (ordered) {
                Log.d("TEST", "COMANDE APRES MIDI");
                getReductionView(id, email);
                getOrderFragment(email);
            } else {
                Log.d("TEST", "VA COMMANDER");
                getReductionView(id, email);
                goToOrderFragment();
            }
        }
//   getReductionView(id, email);
//    getNothingFragment();
//    getOrderFragment(email);
//      goToOrderFragment();

    }

    public void hasOrdered(final String id, final String email){
        Log.d("IDDDDDDD", id);
        AndroidNetworking.get("http://budgeat.stan.sh/users/"+id+"/orders")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String test = String.valueOf(response.get("success"));
                            Log.d("ORDER","VIDE");
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
        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        goOrderFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().add(R.id.bottomFragment, goOrderFragment).commit();
    }

    public void getOrderFragment(String email){

        OrderFragment orderFragment = new OrderFragment();
        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        orderFragment.setArguments(getIntent().getExtras());

        Bundle bundle = new Bundle();

        bundle.putString("email", email);

        orderFragment.setArguments(bundle);
        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction().add(R.id.bottomFragment, orderFragment).commit();
    }

    public void getNothingFragment(){

        NothingFragmentTop nothingFragmentTop = new NothingFragmentTop();
        NothingFragmentBottom nothingFragmentBottom = new NothingFragmentBottom();
        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        nothingFragmentTop.setArguments(getIntent().getExtras());
        nothingFragmentBottom.setArguments(getIntent().getExtras());
        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction().add(R.id.topFragment, nothingFragmentTop).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.bottomFragment, nothingFragmentBottom).commit();
    }

    public void getReductionView(String id, String email){

        ReductionFragment reductionFragment = new ReductionFragment();
        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        reductionFragment.setArguments(getIntent().getExtras());

        Bundle bundle = new Bundle();

        bundle.putString("id", id);
        bundle.putString("mail", email);

        reductionFragment.setArguments(bundle);

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction().add(R.id.topFragment, reductionFragment).commit();

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
