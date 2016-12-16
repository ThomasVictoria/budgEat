package com.serious.budgeat.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.serious.budgeat.Fragment.NothingFragmentBottom;
import com.serious.budgeat.Fragment.NothingFragmentTop;
import com.serious.budgeat.Fragment.OrderFragment;
import com.serious.budgeat.Fragment.ReductionFragment;
import com.serious.budgeat.Model.Order;
import com.serious.budgeat.R;
import com.serious.budgeat.Utils;

import java.util.Calendar;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String screenName = "Main";
    private Order order;
    final Integer price = 6;

    public Integer getPrice() { return price; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String email = preferences.getString("user_email", "");
        String id = preferences.getString("user_id", "");

        Bundle extras = getIntent().getExtras();

        generateFragment(id, email);

        ButterKnife.bind(this);
    }


    private void generateFragment(String id, String email){
//        getReductionView(id, email);
//        getNothingFragment();
        getOrderFragment(email);
    }

    public void getOrderFragment(String email){
        Log.d("EXECUTE", "qreilubqouyregbqle");
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
        Calendar c = Calendar.getInstance();
        Integer hour = c.get(Calendar.HOUR_OF_DAY);

        Utils.pushOpenScreenEvent(this, screenName);
    }

}
