package com.serious.budgeat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.serious.budgeat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LandingActivity extends AppCompatActivity {
    static private final String screenName = "Landing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
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

    @OnClick(R.id.buttonInscription)
    void goToInscription() {
        Intent intent = new Intent(LandingActivity.this, InscriptionActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonConnexion)
    void goToConnexion(){
        Intent intent = new Intent(LandingActivity.this, ConnexionActivity.class);
        startActivity(intent);
    }
}
