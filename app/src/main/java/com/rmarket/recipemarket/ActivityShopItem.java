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

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityShopItem extends AppCompatActivity {
    Context mContext;
    TextView shopItemTitle1,shopItemTitle2,shopItemTitle3,shopItemCost,shopItemDeliveryCost,shopItemMinus,shopItemPlus,shopItemCountStatus,shopItemTotalCost;
    ImageView back,basket,shopItemImage,shopItemDetail,shopItemDownDraw,shopItemGoPayment,shopItemGobasket;
    CardView ShopItemBtn;
    LinearLayout shopItemFinal;
    int countStatus = 1;

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

        shopItemGobasket = findViewById(R.id.shopItem_goBasket);
        shopItemGoPayment = findViewById(R.id.shopItem_goPayment);
        shopItemTotalCost = findViewById(R.id.shopItem_TotalCost);
        shopItemTotalCost.setText(""+(buffer.getProductCost()*countStatus));
        shopItemPlus = findViewById(R.id.shopItem_Plus);
        shopItemMinus = findViewById(R.id.shopItem_Minus);
        shopItemCountStatus = findViewById(R.id.shopItem_CountStatus);
        shopItemCountStatus.setText(""+countStatus);
        shopItemTitle1 = findViewById(R.id.shopItem_title1);
        shopItemTitle2 = findViewById(R.id.shopItem_title2);
        shopItemTitle3 = findViewById(R.id.shopItem_title3);
        shopItemCost = findViewById(R.id.shopItem_Cost);
        shopItemDeliveryCost = findViewById(R.id.shopItem_DeliveryCost);
        shopItemImage = findViewById(R.id.shopItem_Image);
        shopItemDetail = findViewById(R.id.shopItem_Detail);

        ShopItemBtn = findViewById(R.id.shopItem_Btn);
        shopItemTitle1.setText(""+buffer.getProductName());
        shopItemTitle2.setText(""+buffer.getProductName());
        shopItemTitle3.setText(""+buffer.getProductName());
        shopItemCost.setText(""+buffer.getProductCost());
        GlideApp.with(mContext).load(buffer.getProductImage()).into(shopItemImage);
        GlideApp.with(mContext).load(buffer.getProductDetail()).into(shopItemDetail);
        shopItemFinal = findViewById(R.id.shopItem_Final);


        shopItemPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                countStatus++;
                shopItemCountStatus.setText(""+countStatus);
                shopItemTotalCost.setText(""+(buffer.getProductCost()*countStatus));
            }
        });

        shopItemMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(countStatus == 1) {
                    Toast.makeText(mContext, "더이상 수량을 줄일 수 없어요.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    countStatus--;
                    shopItemCountStatus.setText(""+countStatus);
                    shopItemTotalCost.setText(""+(buffer.getProductCost()*countStatus));
                }
            }
        });

        shopItemDownDraw = findViewById(R.id.shopItem_DownDraw);
        shopItemDownDraw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               shopItemFinal.setVisibility(View.GONE);
               ShopItemBtn.setVisibility(View.VISIBLE);
            }
        });

        ShopItemBtn = findViewById(R.id.shopItem_Btn);
        ShopItemBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShopItemBtn.setVisibility(View.GONE);
                shopItemFinal.setVisibility(View.VISIBLE);


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

        shopItemGobasket.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Toast.makeText(mContext, "로그인 되지 않았습니다. 로그인 후 다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("id", result.getId());
                            jsonObject.put("product_name", buffer.getProductName());
                            jsonObject.put("quantity", Integer.parseInt(shopItemCountStatus.getText().toString()));
                            jsonObject.put("total_amount", Integer.parseInt(shopItemTotalCost.getText().toString()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        HttpConnection httpConn = new HttpConnection(ActivityShopItem.this, "AddBasket", jsonObject);
                        httpConn.execute();
                    }
                });
            }
        });

        shopItemGoPayment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        shopItemFinal.setVisibility(View.GONE);
    }
}