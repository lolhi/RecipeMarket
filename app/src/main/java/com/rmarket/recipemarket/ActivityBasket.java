package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.airbnb.lottie.network.FileExtension.JSON;

public class ActivityBasket extends AppCompatActivity {
    Context mContext;
    RecyclerView basket_recycle;
    LinearLayout basket_full, basket_Btn;
    BasketRecyclerAdapter adapter;
    ArrayList<Basket_Item> BasketItem = new ArrayList<>();
    ImageView back;
    RelativeLayout basket_emty;
    CardView cvBuy;
    ActivityBasketHttpConn httpConn;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        mContext = this;

        basket_full = findViewById(R.id.basket_full);
        basket_emty = findViewById(R.id.basket_emty);
        basket_emty.setVisibility(View.GONE);
        basket_Btn = findViewById(R.id.basketBtn);
        basket_recycle = findViewById(R.id.basket_recycle);
        back = findViewById(R.id.basket_back);
        cvBuy = findViewById(R.id.cv_buy);

        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onSuccess(MeV2Response result) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", result.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                httpConn = new ActivityBasketHttpConn(mContext,"GetBasket", new AppCompatDialog(mContext), basket_recycle, jsonObject, GlideApp.with(mContext), basket_emty, cvBuy);
                httpConn.execute();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              finish();
            }
        });

        basket_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int cnt = 0;
                int iFirstIdx = -1;
                boolean[] bCheckBox = httpConn.getbCheckbox();
                ArrayList<Basket_Item> BasketItemArrList = httpConn.getBasketItemArrList();
                String sProductName;
                int iProductPrice = 0;
                int iDeliveryCost = 0;
                int iQuantity = 0;

                for(int i = 0; i < bCheckBox.length; i++){
                    if(bCheckBox[i]) {
                        if(iFirstIdx == -1)
                            iFirstIdx = i;
                        cnt++;
                        iProductPrice += BasketItemArrList.get(i).getProductCost() * BasketItemArrList.get(i).getProductCount();
                        iDeliveryCost += BasketItemArrList.get(i).getDeliverCost();
                        iQuantity += BasketItemArrList.get(i).getProductCount();
                    }
                }

                if(cnt == 0){
                    Toast.makeText(mContext, "선택된 아이템이 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(cnt == 1){
                    sProductName = BasketItemArrList.get(iFirstIdx).getProductName();
                }
                else{
                    sProductName = BasketItemArrList.get(iFirstIdx).getProductName() + " 외 " + (cnt - 1) +"건";
                }

                PaymentItem mPaymentItem = new PaymentItem(sProductName, iProductPrice, iDeliveryCost, iQuantity);
                Intent intent = new Intent(mContext, ActivityPayment.class);
                intent.putExtra("paymentitem", mPaymentItem);
                mContext.startActivity(intent);
            }
        });

    }
}
