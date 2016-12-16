package com.serious.budgeat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.serious.budgeat.R;

public class CouponActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receit);

        String token = getIntent().getStringExtra("SESSION_TOKEN");

        TextView textViewToken = (TextView)findViewById(R.id.token);
        textViewToken.setText(token);

    }
}
