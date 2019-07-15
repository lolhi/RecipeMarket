package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

////////d여기당
public class RecipeActivity_detail extends AppCompatActivity {

    ImageView scrap_image, comment_image;
    View view;
    Context mcontext;
    RecyclerView detail_recycle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mcontext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent;
        intent = getIntent();
        RecommendItem recommendItem = (RecommendItem)intent.getSerializableExtra("RecommandItem");

        scrap_image = findViewById(R.id.srcap);
        comment_image = findViewById(R.id.comment);

        detail_recycle = findViewById(R.id.recommend_detail_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext);
        detail_recycle.setHasFixedSize(true);
        detail_recycle.setLayoutManager(layoutManager);
        RecipeActivity_detailHttpConn httpConn = new RecipeActivity_detailHttpConn(this,"GetMaterial/" + recommendItem.getId(), detail_recycle,recommendItem,new ArrayList<Materialitem>(), new AppCompatDialog(this));
        httpConn.execute();

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
//
                Intent intent = new Intent(mcontext, ActivityComment.class);
                mcontext.startActivity(intent);

            }
        });
    }
}
