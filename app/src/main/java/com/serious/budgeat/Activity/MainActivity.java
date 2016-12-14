package com.serious.budgeat.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;
import com.serious.budgeat.R;
import com.serious.budgeat.Utils;
import com.serious.budgeat.Activity.ContainerHolderSingleton;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    private static final String screenName = "Main";
    private static Integer hour = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);

        if (hour > 14) {
            goToOrder();
        } else {
            goToReceit();
        }

        Log.d("current hod", String.valueOf(hour));

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
        Intent intent = new Intent(MainActivity.this, OrderActivity.class);
        startActivity(intent);
    }

    void goToReceit() {
        Intent intent = new Intent(MainActivity.this, ReceitActivity.class);
        startActivity(intent);
    }
}
