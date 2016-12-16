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

import static android.R.attr.id;
import static android.R.attr.order;

public class OrderFragment extends Fragment {

    Integer coupon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_order, container, false);
        String email = this.getArguments().getString("email");

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
                            Double orders = Double.valueOf(response.get("achats").toString());
                            getReduction(orders, view);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {

                    }
                });
    }

    private void getReduction(Double reduc, View view){
        final TextView textViewCompo = (TextView)view.findViewById(R.id.compo);
        final TextView textViewCalcul = (TextView)view.findViewById(R.id.calcul);
        final TextView textViewTotal = (TextView)view.findViewById(R.id.total);
        final Button couponButton = (Button)view.findViewById(R.id.couponButton);
        ;
        final Integer price = ((MainActivity)getActivity()).getPrice();

        final Double reducString = Double.valueOf(price * 1 * (reduc / 100));
        final Double finalPrice = price - reducString;


        AndroidNetworking.get("http://budgeat.stan.sh/users/1/orders")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Integer i = response.getJSONArray("orders").length() - 1;

                            Integer payed = Integer.valueOf(response.getJSONArray("orders").getJSONObject(i).get("is_payed").toString());

                            coupon = Integer.valueOf(response.getJSONArray("orders").getJSONObject(i).get("token").toString());

                            textViewCompo.setText(
                                    response.getJSONArray("orders").getJSONObject(i).get("bread").toString() + " + " +
                                            response.getJSONArray("orders").getJSONObject(i).get("meat").toString() + " + " +
                                            response.getJSONArray("orders").getJSONObject(i).get("cheese").toString() + " + " +
                                            response.getJSONArray("orders").getJSONObject(i).get("legume").toString()
                            );

                            textViewCalcul.setText("6.00 € - " + reducString.toString() + " €");
                            textViewTotal.setText(finalPrice + " €");

                            couponButton.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), CouponActivity.class);
                                    intent.putExtra("SESSION_TOKEN", coupon.toString());
                                    startActivity(intent);

                                }
                            });

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
