package com.serious.budgeat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.serious.budgeat.JsonFactory.JsonParserFactory;
import com.serious.budgeat.R;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConnexionActivity extends AppCompatActivity {

    static private final String screenName = "Connexion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        ButterKnife.bind(this);
        /*AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.setParserFactory(new JsonParserFactory());*/

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

    @OnClick(R.id.laucnchConnexion)
    void connexion(){
        TextView email = (TextView)findViewById(R.id.email);
        TextView pass = (TextView)findViewById(R.id.password);

        // #### Temporaire
        Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
        startActivity(intent);
        // ####

        // Connexion de l'utilisateur
        /*AndroidNetworking.post("https://budgeat.stan.sh/user/new")
                .addBodyParameter("emaim", (String) email.getText())
                .addBodyParameter("pass", (String) pass.getText())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });
*/
    }
}
