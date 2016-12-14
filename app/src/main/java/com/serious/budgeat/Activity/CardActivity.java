package com.serious.budgeat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.serious.budgeat.R;

public class CardActivity extends AppCompatActivity {

    static private final String screenName = "Card";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
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
