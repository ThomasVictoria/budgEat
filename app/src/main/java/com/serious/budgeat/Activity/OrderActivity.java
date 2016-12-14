package com.serious.budgeat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.serious.budgeat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        /* ICI TOUT PLEIN DE FRAGMENT QUI S'EMBOITES */


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
