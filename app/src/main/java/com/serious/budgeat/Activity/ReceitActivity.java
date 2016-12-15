package com.serious.budgeat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.serious.budgeat.R;
import com.serious.budgeat.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceitActivity extends AppCompatActivity {

    private String screenName = "Receit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receit);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            session_email = extras.getString("SESSION_EMAIL");
            session_id = extras.getString("SESSION_ID");
        }

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
