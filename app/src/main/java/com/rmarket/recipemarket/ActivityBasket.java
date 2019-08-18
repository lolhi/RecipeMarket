package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
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
    LinearLayout basket_full,basket_emty,basket_Btn;
    BasketRecyclerAdapter adapter;
    ArrayList<Basket_Item> BasketItem = new ArrayList<>();
    ImageView back, ivBasketEmpty;

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
                ActivityBasketHttpConn httpConn = new ActivityBasketHttpConn(mContext,"GetBasket", new AppCompatDialog(mContext), basket_recycle, jsonObject, GlideApp.with(mContext), basket_emty);
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
                Intent intent = new Intent(mContext, ActivityPayment.class);
                mContext.startActivity(intent);
            }
        });

    }
}
