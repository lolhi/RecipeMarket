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
import androidx.appcompat.app.AppCompatDialog;

import java.util.ArrayList;

public class ShoppingDetail extends AppCompatActivity {
    Context mContext;
    ImageView prepare,goguma,yangba,pumpkin;
    ImageView back,basket;
    private ArrayList<ShopItem> ShoppingItemArrList = new ArrayList<>();
    private ShoppingDetailHttpConn httpConn;

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GlideApp.get(this).clearMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        GlideApp.get(this).trimMemory(level);
    }

    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_ranina);
        httpConn = new ShoppingDetailHttpConn(mContext,"GetShoppingItem",new AppCompatDialog(mContext));
        httpConn.execute();

        prepare = findViewById(R.id.ranina_onion);
        goguma = findViewById(R.id.ranina_corn);
        yangba = findViewById(R.id.ranina_honey);
        pumpkin = findViewById(R.id.ranina_potato);
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

        prepare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "상품 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        pumpkin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ShoppingItemArrList.size() == 0)
                    ShoppingItemArrList = httpConn.getShoppingItemArrList();
                Intent intent = new Intent(mContext, ActivityShopItem.class);
                intent.putExtra("Item", ShoppingItemArrList.get(0));
                mContext.startActivity(intent);
            }
        });

        yangba.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ShoppingItemArrList.size() == 0)
                    ShoppingItemArrList = httpConn.getShoppingItemArrList();
                Intent intent = new Intent(mContext, ActivityShopItem.class);
                intent.putExtra("Item", ShoppingItemArrList.get(2));
                mContext.startActivity(intent);
            }
        });

        goguma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ShoppingItemArrList.size() == 0)
                    ShoppingItemArrList = httpConn.getShoppingItemArrList();
                Intent intent = new Intent(mContext, ActivityShopItem.class);
                intent.putExtra("Item", ShoppingItemArrList.get(1));
                mContext.startActivity(intent);
            }
        });
    }
}
