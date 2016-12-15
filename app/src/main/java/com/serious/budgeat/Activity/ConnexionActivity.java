package com.serious.budgeat.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @OnClick(R.id.buttonConnexion)
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
                    public void onResponse(final JSONObject response) {
                        if (response.length() == 1){
                            Toast.makeText(getApplicationContext(), "Mauvais identifiants", Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                final String id = (String) response.get("id");
                                final String email = (String) response.get("email");
                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        SharedPreferences getPrefs = PreferenceManager
                                                .getDefaultSharedPreferences(getBaseContext());
                                        SharedPreferences.Editor e = getPrefs.edit();
                                        e.putString("user_email", email);
                                        e.putString("user_id", id);
                                        e.apply();
                                    }
                                });
                                t.start();
                                Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.d("leiqygfqer", error.toString());
                        Toast.makeText(getApplicationContext(), "Erreur reseaux", Toast.LENGTH_LONG).show();
                    }
                });
        } else {
            Toast.makeText(getApplicationContext(), "Champs manquant", Toast.LENGTH_LONG).show();
        }
    }
}
