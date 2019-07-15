package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShoppingDetail extends AppCompatActivity {
    Context mContext;
    ImageView onion,corn,honey,potato;
    ImageView back,basket;
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_ranina);
        onion = findViewById(R.id.ranina_onion);
        corn = findViewById(R.id.ranina_corn);
        honey = findViewById(R.id.ranina_honey);
        potato = findViewById(R.id.ranina_potato);

        back = findViewById(R.id.ranina_back);
        basket = findViewById(R.id.ranina_basket);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        basket.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityBasket.class);
                mContext.startActivity(intent);
            }
        });

        onion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "만생종 저장 양파 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        potato.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "춘천 소양강 감자 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        honey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "잡화꿀 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        corn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "고랭지 백찰 옥수수 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
