package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShoppingDetail extends AppCompatActivity {
    Context mContext;
    ImageView prepare,goguma,yangba,pumpkin;
    ImageView back,basket;
    ShopItem buffer;
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_ranina);
        prepare = findViewById(R.id.ranina_onion);
        goguma = findViewById(R.id.ranina_corn);
        yangba = findViewById(R.id.ranina_honey);
        pumpkin = findViewById(R.id.ranina_potato);

        buffer = new ShopItem("라니네 농수산물", "제주도에서 온 미니밤호박 5kg", 2500, 26000, 0, R.drawable.myhobak, R.drawable.bowjjang);

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
                intent.putExtra("Item",buffer);
                mContext.startActivity(intent);
            }
        });

        prepare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "만생종 저장 양파 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        pumpkin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ActivityShopItem.class);
                intent.putExtra("Item",buffer);
                mContext.startActivity(intent);
            }
        });

        yangba.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "잡화꿀 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        goguma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "고랭지 백찰 옥수수 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
