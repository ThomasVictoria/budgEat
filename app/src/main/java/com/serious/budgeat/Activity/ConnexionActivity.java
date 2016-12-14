package com.serious.budgeat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.serious.budgeat.JsonFactory.JsonParserFactory;
import com.serious.budgeat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.serious.budgeat.R;
import com.serious.budgeat.Utils;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConnexionActivity extends AppCompatActivity {

    static private final String screenName = "Connexion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.setParserFactory(new JsonParserFactory());

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
        final TextView email = (TextView)findViewById(R.id.email);
        TextView pass = (TextView)findViewById(R.id.password);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email.getText().toString());
            jsonObject.put("password", pass.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(pass != null && email != null) {

            // Connexion de l'utilisateur
            AndroidNetworking.post("https://budgeat.stan.sh/user/auth")
                .addJSONObjectBody(jsonObject)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.length() == 1){
                            Toast.makeText(getApplicationContext(), "Mauvais identifiants", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
                            intent.putExtra("SESSION_ID", String.valueOf(email));
                            startActivity(intent);
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        Log.d("leiqygfqer",error.toString());
                        Toast.makeText(getApplicationContext(), "Erreur reseaux", Toast.LENGTH_LONG).show();
                    }
                });
        } else {
            Toast.makeText(getApplicationContext(), "Champs manquant", Toast.LENGTH_LONG).show();
        }
    }
}
