package com.serious.budgeat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.serious.budgeat.R;
import com.serious.budgeat.Utils;

import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity {

    static private final String screenName = "Order";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        /* ICI TOUT PLEIN DE FRAGMENT QUI S'EMBOITES */


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

    // Proto
    /*@OnClick(R.id.addOrderButton)
    void addOrder() {

    }

    @OnClick(R.id.orderButton)
    void validOrder() {

    }*/

    void goToCard(){
        Intent intent = new Intent(OrderActivity.this, CardActivity.class);
        startActivity(intent);
    }

}
