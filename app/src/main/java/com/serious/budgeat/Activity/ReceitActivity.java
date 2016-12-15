package com.serious.budgeat.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.serious.budgeat.Model.Order;
import com.serious.budgeat.R;
import com.serious.budgeat.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceitActivity extends AppCompatActivity {

    private String screenName = "Receit";

    private String session_email;
    private Order order;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receit);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        id = preferences.getString("user_id", "");
        session_email = preferences.getString("user_email", "");
    }

    @OnClick(R.id.beginOrder)
    void goToOrder(){

        Intent intent = new Intent(ReceitActivity.this, OrderActivity.class);
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
