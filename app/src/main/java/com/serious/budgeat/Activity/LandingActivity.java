package com.serious.budgeat.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.serious.budgeat.R;
import com.serious.budgeat.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LandingActivity extends AppCompatActivity {
    static private final String screenName = "Landing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
                if (isFirstStart) {
                    Intent i = new Intent(LandingActivity.this, OnBoardingActivity.class);
                    startActivity(i);
                    SharedPreferences.Editor e = getPrefs.edit();
                    e.putBoolean("firstStart", false);
                    e.apply();
                }
            }
        });
        t.start();

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
