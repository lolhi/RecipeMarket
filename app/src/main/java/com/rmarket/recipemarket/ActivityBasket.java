package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityBasket extends AppCompatActivity {
    Context mContext;
    RecyclerView basket_recycle;
    LinearLayout basket_full,basket_emty;
    BasketRecyclerAdapter adapter;
    ArrayList<Basket_Item> BasketItem = new ArrayList<>();
    ImageView back;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        BasketItem.add(new Basket_Item("라니네",
                "맛있는 보우짱",
                2500,
                18000,
                1,
                R.drawable.straw));

        BasketItem.add(new Basket_Item("기조네 자두농장",
                "싱싱한 자두",
                2500,
                17000,
                2,
                R.drawable.honey));

        BasketItem.add(new Basket_Item("라니네",
                "맛있는 보우짱",
                2500,
                18000,
                1,
                R.drawable.straw));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        mContext = this;

        basket_full = findViewById(R.id.basket_full);
        basket_emty = findViewById(R.id.basket_emty);
        basket_emty.setVisibility(View.GONE);

        adapter = new BasketRecyclerAdapter(mContext,BasketItem);

        basket_recycle = findViewById(R.id.basket_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        basket_recycle.setHasFixedSize(true);
        basket_recycle.setLayoutManager(layoutManager);
        basket_recycle.setAdapter(adapter);
        back = findViewById(R.id.basket_back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              finish();
            }
        });
    }
}
