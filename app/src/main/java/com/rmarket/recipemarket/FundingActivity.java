package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class FundingActivity extends AppCompatActivity{
    Context mContext;
    ImageView fundingImage,fundingSubImage,fundingDetail,fundingDownDraw;
    CardView fundingBtn;
    LinearLayout fundingFinal,fundingGoPayment;
    ProgressBar progressBar;
    int percent;
    int countStatus = 1;
    TextView fundingDday, fundingName,fundingPercent,fundingGoal,fundingPrecent,fundingUser,fundingSubTitle,fundingTitle,fundingTitle3,fundingMinus,fundingPlus,fundingTotalCost,fundingCountStatus;
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding);

        Intent intent = getIntent();
        FundingItem item = (FundingItem) intent.getSerializableExtra("fundingItem");

        fundingGoPayment = findViewById(R.id.fundingGoPayment);
        fundingTitle3 = findViewById(R.id.funding_title3);
        fundingMinus = findViewById(R.id.funding_Minus);
        fundingPlus = findViewById(R.id.fundingPlus);
        fundingCountStatus = findViewById(R.id.fundingCountStatus);
        fundingTotalCost = findViewById(R.id.fundingTotalCost);
        fundingFinal = findViewById(R.id.fundingFinal);
        fundingDownDraw = findViewById(R.id.fundingDownDraw);


        fundingImage = findViewById(R.id.fundingImage); //펀딩이미지
        fundingDday = findViewById(R.id.fundingDday);   //펀딩디데이
        fundingPercent = findViewById(R.id.fundingPercent); //펀딩 퍼센트
        fundingGoal = findViewById(R.id.fundingGoal);
        fundingPrecent = findViewById(R.id.fundingPresent);
        fundingUser = findViewById(R.id.fundingUser);
        fundingBtn = findViewById(R.id.fundingBtn);
        progressBar = findViewById(R.id.fundingProgress);
        fundingSubTitle = findViewById(R.id.fundingSubTitle);
        fundingName = findViewById(R.id.fundingName);
        fundingSubImage = findViewById(R.id.fundingCat);
        fundingTitle = findViewById(R.id.fundingTitle);
        fundingDetail = findViewById(R.id.fundingDetail);
        progressBar.setProgress(item.getiFundingPercent());

        fundingTitle3.setText(item.getsProductName());
        fundingCountStatus.setText(""+countStatus);
        fundingTotalCost.setText(""+(item.getiProductCost()*countStatus));


        fundingName.setText(item.getsFundingName());
        fundingSubImage.setImageResource(item.getiFundingMark());
        fundingImage.setImageResource(item.getiProductImg());
        fundingGoal.setText(item.getsGoalAmount());
        fundingPrecent.setText(""+item.getsPresentAmount());
        fundingPercent.setText(""+item.getiFundingPercent());
        fundingSubTitle.setText(item.getsProductSubName());
        fundingTitle.setText(item.getsProductName());

        GlideApp.with(this).load(item.getiFundingDetail()).into(fundingDetail);

        fundingPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                countStatus++;
                fundingCountStatus.setText(""+countStatus);
                fundingTotalCost.setText(""+(item.getiProductCost()*countStatus));
            }
        });

        fundingMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(countStatus == 1) {
                    Toast.makeText(mContext, "더이상 수량을 줄일 수 없어요.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    countStatus--;
                    fundingCountStatus.setText(""+countStatus);
                    fundingTotalCost.setText(""+(item.getiProductCost()*countStatus));
                }
            }
        });

        fundingDownDraw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fundingFinal.setVisibility(View.GONE);
                fundingBtn.setVisibility(View.VISIBLE);
            }
        });

        fundingBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fundingBtn.setVisibility(View.GONE);
                fundingFinal.setVisibility(View.VISIBLE);


            }
        });

        fundingGoPayment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "결제하기 창으로 이동", Toast.LENGTH_SHORT).show();

            }
        });
            fundingFinal.setVisibility(View.GONE);

    }
}




