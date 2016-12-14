package com.serious.budgeat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.serious.budgeat.R;

import com.serious.budgeat.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class CardActivity extends AppCompatActivity {

    static private final String screenName = "Card";
    private String session_email;
    private String session_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            session_email = extras.getString("SESSION_EMAIL");
            session_id = extras.getString("SESSION_ID");
        }


    }

    @OnClick(R.id.buttonReOrder)
    void reorder(){
        Intent intent = new Intent(CardActivity.this, OrderActivity.class);
        intent.putExtra("SESSION_EMAIL", session_email);
        intent.putExtra("SESSION_ID", session_id);
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
