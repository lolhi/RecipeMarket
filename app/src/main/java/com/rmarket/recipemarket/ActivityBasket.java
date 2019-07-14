package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityBasket extends AppCompatActivity {
    Context mContext;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        back  =findViewById(R.id.basket_back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              finish();
            }
        });
    }
}
