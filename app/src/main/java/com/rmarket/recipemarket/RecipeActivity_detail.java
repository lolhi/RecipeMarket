package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import org.json.JSONException;
import org.json.JSONObject;

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
        RecipeActivity_detailHttpConn httpConn = new RecipeActivity_detailHttpConn(this,"GetMaterial/" + recommendItem.getId(), detail_recycle,recommendItem,new ArrayList<Materialitem>(), new ArrayList<ProcessItem>(), new AppCompatDialog(this));
        httpConn.execute();

        scrap_image.setOnClickListener(new View.OnClickListener() { // 이미지 버튼 이벤트 정의
            @Override
            public void onClick(View v) { //클릭 했을경우
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        // 로그인 x
                        Log.e("onSessionClosed ::", errorResult.toString());
                        Toast.makeText(mcontext, "로그인 하지 않았습니다. 로그인 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        // 로그인 o
                        Log.e("onSuccess ::", "login");
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("ID",result.getId());
                            jsonObject.put("RECIPE_ID", recommendItem.getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        HttpConnection connPost = new HttpConnection(RecipeActivity_detail.this, "AddClipping",jsonObject);
                        connPost.execute();
                    }
                });
            }
        });

        comment_image.setOnClickListener(new View.OnClickListener() { // 이미지 버튼 이벤트 정의

            @Override
            public void onClick(View v) { //클릭 했을경우
//
                Intent intent = new Intent(mcontext, ActivityComment.class);
                intent.putExtra("RecommandItem", recommendItem);
                mcontext.startActivity(intent);

            }
        });
    }
}
