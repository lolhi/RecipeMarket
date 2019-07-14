package com.rmarket.recipemarket;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityAcoount extends AppCompatActivity {
    Context mContext;
    ImageView back;
    LinearLayout account_exit;  //계정탈퇴
    LinearLayout account_logout;    //로그아웃
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acoount);
        mContext = this;

        account_logout = findViewById(R.id.account_logout); // 로그아웃 클릭이벤트 및 아이디 연결
        account_logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        account_exit = findViewById(R.id.account_exit); // 계정탈퇴 클릭이벤트 및 아이디 연결
        account_exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "계정탈퇴 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        back = findViewById(R.id.account_back);//엑티비티 탈퇴
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}