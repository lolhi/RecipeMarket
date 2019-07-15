package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class shopping_detail2 extends AppCompatActivity {
    Context mContext;
    ImageView jadoo,apple,prefare1,prefare2;
    ImageView back,basket;
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_gizo);
        jadoo = findViewById(R.id.gizo_jadoo);
        apple = findViewById(R.id.gizo_apple);
        prefare1 = findViewById(R.id.gizo_prefare1);
        prefare2 = findViewById(R.id.gizo_prefare2);

        back = findViewById(R.id.gizo_back);
        basket = findViewById(R.id.gizo_basket);

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

        jadoo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "김천 자두 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        apple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "김천 사과 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        prefare1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "상품 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        prefare2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "상품 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
