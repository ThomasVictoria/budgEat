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


        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(LandingActivity.this, OnBoardingActivity.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
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
