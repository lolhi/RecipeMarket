package com.example.tablemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Search_Detail_Layout extends AppCompatActivity {
        ImageView back_arrow;
        TextView search_top;
        Context mContext;
///GridView Adapter Use Data

    GridView grid;
    String[] title = {
            "straw",
            "mando",
            "tomato",
            "podong",


    } ;
    int[] imageId = {
            R.drawable.straw,
            R.drawable.mando,
            R.drawable.tomato,
            R.drawable.podong,

    };


    String[] subtitle = {
            "디저트",
            "밥",
            "국",
            "양식",


    } ;

    String[] time = {
            "30분",
            "20분",
            "10분",
            "1시간",


    } ;


    int[] level = {
            R.drawable.ic_dashboard_black_24dp,
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_notifications_black_24dp,
            R.drawable.ic_notifications_black_24dp,

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail);
        mContext = this;


        String search_text; //검색 변수 저장하는 스트링 자료형

        back_arrow = (ImageView)findViewById(R.id.search_detail_back_button);


        //상단 텍스트 및 검색 텍스트 받아오기
        Intent intent;
        intent = getIntent();
        search_text  = intent.getStringExtra("editText");
        search_top = findViewById(R.id.search_food_inf);
        search_top.setText(search_text);


        //GridView Adapter 구성및 OnClick method 구성
        CustomGrid adapter = new CustomGrid(Search_Detail_Layout.this, title, imageId,level,subtitle,time);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Search_Detail_Layout.this, "You Clicked at " +title[+ position], Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, RecipeActivity_detail.class);
                intent.putExtra("recipeTitle",title[+ position]);
                mContext.startActivity(intent);
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
