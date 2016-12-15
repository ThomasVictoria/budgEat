package com.serious.budgeat.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.serious.budgeat.Activity.MainActivity;
import com.serious.budgeat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ReductionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_reduction, container, false);

        String id = this.getArguments().getString("id");
        String email = this.getArguments().getString("mail");

        https://budgeat.stan.sh/user/

        AndroidNetworking.get("https://budgeat.stan.sh/user/"+email)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.get("id").toString();

                            getSchoolOrders(id, view);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {

                    }
                });

        return view;
    }

    private void getSchoolOrders(String id, final View view){
        AndroidNetworking.get("https://budgeat.stan.sh/schools/"+id+"/count")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Integer orders = Integer.valueOf(response.get("achats").toString());

                            TextView title = (TextView)view.findViewById(R.id.reductionTitle);
                            TextView subtitle = (TextView)view.findViewById(R.id.reductionSubtitle);

                            title.setText("-"+orders.toString()+"%");
                            subtitle.setText("Soit "+ orders.toString()+" commandes");

                            ((MainActivity)getActivity()).setReduc(orders);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {

                    }
                });
    }
}
