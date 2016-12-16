package com.serious.budgeat.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.serious.budgeat.Activity.CouponActivity;
import com.serious.budgeat.Activity.InscriptionActivity;
import com.serious.budgeat.Activity.MainActivity;
import com.serious.budgeat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderFragment extends Fragment {

    Integer coupon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ButterKnife.bind(getActivity());
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        String id = this.getArguments().getString("id");

        final TextView textViewCompo = (TextView)view.findViewById(R.id.compo);
        final TextView textViewCalcul = (TextView)view.findViewById(R.id.calcul);
        final TextView textViewTotal = (TextView)view.findViewById(R.id.total);
        final Button couponButton = (Button)view.findViewById(R.id.couponButton);

        final Integer reduc = ((MainActivity)getActivity()).getReduc();
        final Integer price = ((MainActivity)getActivity()).getPrice();

        final Integer reducString = 1 - (reduc / 100);
        final Integer finalPrice = price * reducString;

        AndroidNetworking.get("http://budgeat.stan.sh/users/1/orders")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        for(Integer i = 0; i < response.length();i++){
                            try {
                                Integer payed = Integer.valueOf(response.getJSONObject(i.toString()).get("is_payed").toString());
                                if(payed == 0){

                                    coupon = (Integer) response.getJSONObject(i.toString()).get("token");

                                    textViewCompo.setText(
                                            response.getJSONObject(i.toString()).get("bread").toString() + " + " +
                                            response.getJSONObject(i.toString()).get("meat").toString() + " + " +
                                            response.getJSONObject(i.toString()).get("cheese").toString() + " + " +
                                            response.getJSONObject(i.toString()).get("legume").toString()
                                    );

                                    textViewCalcul.setText("6 - " + reducString.toString());
                                    textViewTotal.setText(finalPrice + " €");

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onError(ANError error) {

                    }
                });

        return view;
    }

    @OnClick(R.id.couponButton)
    void goToCoupon(){
        Intent intent = new Intent(getActivity(), CouponActivity.class);
        intent.putExtra("SESSION_TOKEN", coupon.toString());
        startActivity(intent);
    }

}
