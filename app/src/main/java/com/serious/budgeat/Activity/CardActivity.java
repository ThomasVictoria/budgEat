package com.serious.budgeat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.google.gson.Gson;
import com.serious.budgeat.Model.Order;
import com.serious.budgeat.R;

import com.serious.budgeat.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class CardActivity extends AppCompatActivity {

    static private final String screenName = "Card";
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            order = (new Gson()).fromJson(extras.getString("SESSION_ORDER"), Order.class);
        }

        TextView bread = (TextView)findViewById(R.id.breadTextView);
        bread.setText(order.getBreadName()+" + ");

        TextView meat = (TextView)findViewById(R.id.meatTextView);
        meat.setText(order.getMeatName()+" + ");

        TextView vegetable = (TextView)findViewById(R.id.vegetableTextView);
        vegetable.setText(order.getVegetablesName()+" + ");

        TextView cheese = (TextView)findViewById(R.id.cheeseTextView);
        cheese.setText(order.getCheeseName());
    }

    @OnClick(R.id.buttonPayment)
    void gotoPayment(){
        Intent intent = new Intent(CardActivity.this, PaymentActivity.class);

        intent.putExtra("SESSION_ORDER", (new Gson()).toJson(order));
        startActivity(intent);
    }

    @OnClick(R.id.buttonReOrder)
    void reorder(){
        Intent intent = new Intent(CardActivity.this, OrderActivity.class);
        startActivity(intent);
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
}
