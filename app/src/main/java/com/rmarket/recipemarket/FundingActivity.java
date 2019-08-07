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
    TextView fundingDday, fundingPercent,fundingGoal,fundingPrecent,fundingUser;
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding);

        fundingImage = findViewById(R.id.fundingImage);
        fundingDday = findViewById(R.id.fundingDday);
        fundingPercent = findViewById(R.id.fundingPercent);
        fundingGoal = findViewById(R.id.fundingGoal);
        fundingPrecent = findViewById(R.id.fundingPresent);
        fundingUser = findViewById(R.id.fundingUser);
        fundingBtn = findViewById(R.id.fundingBtn);
        fundingSearch = findViewById(R.id.fundingSearch);
        progressBar = findViewById(R.id.fundingProgress);
        progressBar.setProgress(51);
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




