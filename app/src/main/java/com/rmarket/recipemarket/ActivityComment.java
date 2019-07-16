package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityComment extends AppCompatActivity {
    Context mContext;
    RecyclerView comment_recycle;
    ActivityCommentAdapter mAdapter;
    ImageView comment_btn; //코멘트달기 -> 이렇게 생긴 버튼
    EditText comment_edit; //edittext 설정
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mContext = this;

        comment_btn = findViewById(R.id.comment_button);
        comment_edit= findViewById(R.id.comment_edit);

        final ArrayList<Commetn_Item> arrList = new ArrayList<>();

        arrList.add(new Commetn_Item(R.drawable.profile_raninae, "장용주","아존나힘들다","2019-07/13 13:11" ));
        arrList.add(new Commetn_Item(R.drawable.profile_raninae, "이정우","아\n존\n나\n힘\n들\n다","2019-07/13 13:11" ));

        comment_recycle = findViewById(R.id.comment_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        comment_recycle.setHasFixedSize(true);
        comment_recycle.setLayoutManager(layoutManager);

        mAdapter = new ActivityCommentAdapter(arrList);
        comment_recycle.setAdapter(mAdapter);


        //댓글등록 버튼 클릭이벤트 구현...
        comment_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext, "버튼이 입력되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
