package com.rmarket.recipemarket;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityPaymentComplete extends AppCompatActivity {

    private ImageView ivGoShop, ivBtn;
    private TextView tvOrder, tvPayment, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_complete);

        ivGoShop = (ImageView)findViewById(R.id.iv_goshop);
        ivBtn = (ImageView)findViewById(R.id.iv_btn);

        tvOrder = (TextView)findViewById(R.id.tv_order);
        tvPayment = (TextView)findViewById(R.id.tv_payment);
        tvAddress = (TextView)findViewById(R.id.tv_address);

        ivGoShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityPaymentComplete.this, "서비스 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
