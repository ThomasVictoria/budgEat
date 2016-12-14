package com.serious.budgeat.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.serious.budgeat.JsonFactory.JsonParserFactory;
import com.serious.budgeat.Model.School;
import com.serious.budgeat.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class InscriptionActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.setParserFactory(new JsonParserFactory());

        // #### TEMPORAIRE
//        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        // ####


        AndroidNetworking.get("https://budgeat.stan.sh/schools/")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        try {

                            ArrayList<String> ecoles = new ArrayList<String>();

                            for(int i=0; i<response.length();i++){

                                ecoles.add(response.getJSONObject(i).getString("ecole"));
                            }

                            Spinner spinner = (Spinner) findViewById(R.id.spinner1);

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, ecoles);

                            spinner.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("TAG_LOCATIONS", error.toString());
                    }
                });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @OnClick(R.id.vaporisation)
    void inscription() {
//       ##### Temporaire
        Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
        startActivity(intent);
//       #####

        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        EditText verifyPassord = (EditText) findViewById(R.id.verifyPassword);
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
