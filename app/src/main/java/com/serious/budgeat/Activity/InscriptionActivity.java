package com.serious.budgeat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.serious.budgeat.JsonFactory.JsonParserFactory;
import com.serious.budgeat.R;
import com.serious.budgeat.Utils;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class InscriptionActivity extends AppCompatActivity {

    static private final String screenName = "Inscription";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.setParserFactory(new JsonParserFactory());

        // #### TEMPORAIRE
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        // ####


      /*  AndroidNetworking.get("https://budgeat.stan.sh/schools/")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        try {
                            Log.d("TAG_LOCATIONS", response.getJSONObject(0).getString("firstName"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        Log.d("TAG_LOCATIONS", error.toString());
                    }
                });*/
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

    @OnClick(R.id.vaporisation)
    void inscription() {
//       ##### Temporaire
        Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
        startActivity(intent);
//       #####

        EditText email = (EditText)findViewById(R.id.email);
        EditText password = (EditText)findViewById(R.id.password);
        EditText verifyPassord = (EditText)findViewById(R.id.verifyPassword);
        // Dropdown pour les ecoles
        /* EditText ecole = (EditText)findViewById(R.id.); */

        // Future verification mot de passe
        /*if(password.getText() == verifyPassord.getText()) {*/

        // Creation de l'utilisateur et passage a l'activité
/*            AndroidNetworking.post("https://budgeat.stan.sh/user/new")
                    .addBodyParameter("name", "Captain Yolo")
                    .addBodyParameter("email", "captain@yolo")
                    .addBodyParameter("password", "captainleplusbeau")
                    .addBodyParameter("ecole_id", "1")
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onError(ANError error) {

                        }
                    });*/


       /* } else {
            Toast.makeText(getApplicationContext(), "Password non Identique", Toast.LENGTH_LONG).show();
        }
*/
    }

}
