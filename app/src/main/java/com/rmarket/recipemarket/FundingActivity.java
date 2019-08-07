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
    ImageView fundingImage,fundingSearch;
    LinearLayout fundingBtn;
    ProgressBar progressBar;
    int percent;
    TextView fundingDday, fundingPercent,fundingGoal,fundingPrecent,fundingUser;
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding);

        fundingImage = findViewById(R.id.fundingImage); //펀딩이미지
        fundingDday = findViewById(R.id.fundingDday);   //펀딩디데이
        fundingPercent = findViewById(R.id.fundingPercent); //펀딩 퍼센트
        fundingGoal = findViewById(R.id.fundingGoal);
        fundingPrecent = findViewById(R.id.fundingPresent);
        fundingUser = findViewById(R.id.fundingUser);
        fundingBtn = findViewById(R.id.fundingBtn);
        fundingSearch = findViewById(R.id.fundingSearch);
        progressBar = findViewById(R.id.fundingProgress);

        percent = 51;
        progressBar.setProgress(percent);
        fundingBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        fundingSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchRayout.class);
                mContext.startActivity(intent);
            }
        });
    }
}




