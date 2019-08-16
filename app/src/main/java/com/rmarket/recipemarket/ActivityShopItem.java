package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ActivityShopItem extends AppCompatActivity {
    Context mContext;
    TextView shopItemTitle1,shopItemTitle2,shopItemCost,shopItemDeliveryCost;
    ImageView back,basket,shopItemImage,shopItemDetail;
    CardView ShopItemBtn;

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

        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_item);
        Intent intent = getIntent();
        ShopItem buffer = (ShopItem) intent.getSerializableExtra("Item");

        shopItemTitle1 = findViewById(R.id.shopItem_title1);
        shopItemTitle2 = findViewById(R.id.shopItem_title2);
        shopItemCost = findViewById(R.id.shopItem_Cost);
        shopItemDeliveryCost = findViewById(R.id.shopItem_DeliveryCost);
        shopItemImage = findViewById(R.id.shopItem_Image);
        shopItemDetail = findViewById(R.id.shopItem_Detail);

        ShopItemBtn = findViewById(R.id.shopItem_Btn);
        shopItemTitle1.setText(""+buffer.getProductName());
        shopItemTitle2.setText(""+buffer.getProductName());
        shopItemCost.setText(""+buffer.getProductCost());
        GlideApp.with(mContext).load(buffer.getProductImage()).into(shopItemImage);
        GlideApp.with(mContext).load(buffer.getProductDetail()).into(shopItemDetail);

        ShopItemBtn = findViewById(R.id.shopItem_Btn);
        ShopItemBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "결제하기", Toast.LENGTH_SHORT).show();
            }
        });

        basket = findViewById(R.id.shopItem_basket);
        basket.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityBasket.class);
                mContext.startActivity(intent);
            }
        });


        back  =findViewById(R.id.shopItem_back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}