package com.serious.budgeat.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


import com.androidnetworking.AndroidNetworking;
import com.serious.budgeat.JsonFactory.JsonParserFactory;
import com.serious.budgeat.R;
import com.serious.budgeat.Utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class InscriptionActivity extends AppCompatActivity {
    private Integer school_id;

    static private final String screenName = "Inscription";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.setParserFactory(new JsonParserFactory());

        AndroidNetworking.get("https://budgeat.stan.sh/schools/")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(final JSONArray response) {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        try {

                            final ArrayList<String> ecoles = new ArrayList<String>();

                            for(int i=0; i<response.length();i++){

                                ecoles.add("");
                                ecoles.add(response.getJSONObject(i).getString("ecole"));
                            }

                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, ecoles);
                            Spinner spinner = (Spinner) findViewById(R.id.spinner1);
                            spinner.setAdapter(adapter);

                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

                                    if(position != 0) {
                                        Integer index = position - 1;

                                        try {
                                            school_id = Integer.valueOf(response.getJSONObject(index).getString("id"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        school_id = 0;
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Pas de connexion", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(InscriptionActivity.this, LandingActivity.class);
                        startActivity(intent);
                    }
                });
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

        EditText name = (EditText) findViewById(R.id.name);
        final EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        // Verification mot de passe
        if (password.getText().toString().length() > 6) {
            if(Integer.valueOf(school_id) != 0) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", email.getText().toString());
                    jsonObject.put("name", name.getText().toString());
                    jsonObject.put("password", password.getText().toString());
                    jsonObject.put("ecole_id", school_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Creation de l'utilisateur et passage a l'activité
                AndroidNetworking.post("https://budgeat.stan.sh/user/new")
                        .addJSONObjectBody(jsonObject)
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    Boolean status = Boolean.valueOf(String.valueOf(response.get("success")));
                                    if (status) {
                                        Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
                                        intent.putExtra("SESSION_EMAIL", String.valueOf(email));
                                        intent.putExtra("SESSION_ID", (String) response.get("id"));
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Creation du compte impossible", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError error) {
                                Log.d("jkyqgou yez", error.toString());
                                Toast.makeText(getApplicationContext(), "Erreur Reseaux", Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
                Toast.makeText(getApplicationContext(), "Veuillez selectionner une école", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Le mot de passe doit faire plus de 6 characteres", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Inscription Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
}
