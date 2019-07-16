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
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityComment extends AppCompatActivity {
    Context mContext;
    RecyclerView comment_recycle;
    ActivityCommentAdapter mAdapter;
    ImageView comment_btn; //코멘트달기 -> 이렇게 생긴 버튼
    EditText comment_edit; //edittext 설정
    RecommendItem recommendItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        recommendItem = (RecommendItem) intent.getSerializableExtra("RecommandItem");
        mContext = this;

        comment_btn = findViewById(R.id.comment_button);
        comment_edit= findViewById(R.id.comment_edit);

        comment_recycle = findViewById(R.id.comment_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        comment_recycle.setHasFixedSize(true);
        comment_recycle.setLayoutManager(layoutManager);

        ActivityCommentHttpConn httpConn = new ActivityCommentHttpConn(ActivityComment.this,"GetComment/" + recommendItem.getId(), comment_recycle, new AppCompatDialog(ActivityComment.this));
        httpConn.execute();

        //댓글등록 버튼 클릭이벤트 구현...
        comment_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

                String getTime = sdf.format(date);
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Toast.makeText(mContext, "로그인 하지 않아 댓글을 달 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("RECIPE_ID",recommendItem.getId());
                            jsonObject.put("WRITER", result.getNickname());
                            jsonObject.put("PROFILE_IMG", result.getProfileImagePath());
                            jsonObject.put("TIME", getTime);
                            jsonObject.put("COMM", comment_edit.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        HttpConnection connPost = new HttpConnection(mContext,"AddComment", jsonObject);
                        connPost.execute();
                    }
                });
            }
        });

    }
}
