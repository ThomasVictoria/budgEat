package com.serious.budgeat.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;
import com.google.gson.Gson;
import com.serious.budgeat.Model.Order;
import com.serious.budgeat.R;
import com.serious.budgeat.Utils;
import com.serious.budgeat.Activity.ContainerHolderSingleton;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String screenName = "Main";
    private String session_email;
    private String session_id;
    private Order order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String email = preferences.getString("user_email", "");

        Log.d("email pref", email);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            session_email = extras.getString("SESSION_EMAIL");
            session_id = extras.getString("SESSION_ID");
            order = (new Gson()).fromJson(extras.getString("SESSION_ORDER"), Order.class);
        }

        ButterKnife.bind(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Calendar c = Calendar.getInstance();
        Integer hour = c.get(Calendar.HOUR_OF_DAY);

        if (hour > 14) {
            goToOrderHome();
        } else {
            goToReceit();
        }
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            session_email = extras.getString("email");
            session_id = extras.getString("id");
        }
        Utils.pushOpenScreenEvent(this, screenName);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utils.pushCloseScreenEvent(this, screenName);
    }

    void goToOrderHome() {
        Intent intent = new Intent(MainActivity.this, OrderHomeActivity.class);
        intent.putExtra("SESSION_EMAIL", session_email);
        intent.putExtra("SESSION_ID", session_id);
        startActivity(intent);
    }

    @OnClick(R.id.orderPageButton)
    void goToOrder() {
        Intent intent = new Intent(MainActivity.this, OrderActivity.class);
        startActivity(intent);
    }

    void goToReceit() {
        Intent intent = new Intent(MainActivity.this, ReceitActivity.class);
        intent.putExtra("SESSION_EMAIL", session_email);
        intent.putExtra("SESSION_ID", session_id);
        startActivity(intent);
    }
}
