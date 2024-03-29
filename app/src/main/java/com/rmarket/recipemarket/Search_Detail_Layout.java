package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import java.util.ArrayList;

public class Search_Detail_Layout extends AppCompatActivity {
    ImageView back_arrow;
    TextView search_top;
    Context mContext;
    GridView grid;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail);
        mContext = this;
        String search_text; //검색 변수 저장하는 스트링 자료형
        String cateroty;
        back_arrow = (ImageView) findViewById(R.id.search_detail_back_button);
        final ArrayList<RecommendItem> arrList = new ArrayList<>();

        //상단 텍스트 및 검색 텍스트 받아오기
        Intent intent;
        intent = getIntent();
        search_text = intent.getStringExtra("SearchString");
        cateroty = intent.getStringExtra("Category");
        search_top = findViewById(R.id.search_food_inf);
        search_top.setText(search_text);

        grid = (GridView) findViewById(R.id.grid);
        RelativeLayout re = (RelativeLayout) findViewById(R.id.search_fail_re);
        TextView tvSearchFail = (TextView) findViewById(R.id.tv_search_fail);
        Search_Detail_LayoutHttpConn httpconn;
        if(cateroty.equals(""))
            httpconn= new Search_Detail_LayoutHttpConn(mContext, "SearchRecipe/" + search_text, new AppCompatDialog(mContext), grid, re, tvSearchFail, GlideApp.with(this));
        else
            httpconn = new Search_Detail_LayoutHttpConn(mContext, "SearchRecipe/" + cateroty + "/" + search_text, new AppCompatDialog(mContext), grid, re,tvSearchFail, GlideApp.with(this));
        httpconn.execute();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(mContext, "현재 서비스 준비중입니다.", Toast.LENGTH_SHORT).show();
                // TODO : use strText
            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() { // 이미지 버튼 이벤트 정의
            @Override
            public void onClick(View v) { //클릭 했을경우
                finish();
            }
        });
    }
}
