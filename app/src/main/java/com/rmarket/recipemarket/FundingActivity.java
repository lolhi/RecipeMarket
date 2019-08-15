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

public class FundingActivity extends AppCompatActivity{
    Context mContext;
    ImageView fundingImage,fundingSubImage,fundingDetail;
    LinearLayout fundingBtn;
    ProgressBar progressBar;
    int percent;
    TextView fundingDday, fundingName,fundingPercent,fundingGoal,fundingPrecent,fundingUser,fundingSubTitle,fundingTitle;
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding);

        Intent intent = getIntent();
        FundingItem item = (FundingItem) intent.getSerializableExtra("fundingItem");

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

        fundingName.setText(item.getsFundingName());
        fundingSubImage.setImageResource(item.getiFundingMark());
        fundingImage.setImageResource(item.getiProductImg());
        fundingGoal.setText(item.getsGoalAmount());
        fundingPrecent.setText(""+item.getsPresentAmount());
        fundingPercent.setText(""+item.getiFundingPercent());
        fundingSubTitle.setText(item.getsProductSubName());
        fundingTitle.setText(item.getsProductName());

        GlideApp.with(this).load(item.getiFundingDetail()).into(fundingDetail);

        fundingBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}




