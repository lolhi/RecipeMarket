package com.example.tablemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

////////d여기당
public class RecipeActivity_detail extends AppCompatActivity {

    ImageView scrap_image, comment_image;
    TextView title;
    View view;
    Context mcontext;
    RecyclerView detail_recycle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mcontext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        final String search_text; //getIntent롤 넒겨온 title 값
        Intent intent;
        intent = getIntent();
        search_text = intent.getStringExtra("recipeTitle");

        scrap_image = findViewById(R.id.srcap);
        comment_image = findViewById(R.id.comment);

        detail_recycle = findViewById(R.id.recommend_detail_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext);
        detail_recycle.setHasFixedSize(true);
        detail_recycle.setLayoutManager(layoutManager);
        detail_recycle.setAdapter(new Recipe_Detail_Recle_Adapter(mcontext, search_text));
        detail_recycle.setItemAnimator(new DefaultItemAnimator());

        scrap_image.setOnClickListener(new View.OnClickListener() { // 이미지 버튼 이벤트 정의
            @Override
            public void onClick(View v) { //클릭 했을경우
//                Toast.makeText(getApplicationContext(), search_text + " : Scrap", Toast.LENGTH_LONG).show();
                Toast.makeText(mcontext, "아직 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        comment_image.setOnClickListener(new View.OnClickListener() { // 이미지 버튼 이벤트 정의
            @Override
            public void onClick(View v) { //클릭 했을경우
//                Toast.makeText(getApplicationContext(), search_text+" : Comment", Toast.LENGTH_LONG).show();
                Toast.makeText(mcontext, "아직 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
