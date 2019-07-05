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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Search_Detail_Layout extends AppCompatActivity {
    ImageView back_arrow;
    TextView search_top;
    Context mContext;
    GridView grid;
    JSONArray jsonArr;
    String jsonData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail);
        mContext = this;
        String search_text; //검색 변수 저장하는 스트링 자료형
        back_arrow = (ImageView)findViewById(R.id.search_detail_back_button);
        final ArrayList<RecommendItem> arrList = new ArrayList<>();

        //상단 텍스트 및 검색 텍스트 받아오기
        Intent intent;
        intent = getIntent();
        search_text  = intent.getStringExtra("SearchString");
        search_top = findViewById(R.id.search_food_inf);
        search_top.setText(search_text);

        HttpConnection httpconn = new HttpConnection(mContext, UrlClass.Url + "SearchRecipe/" + search_text);
        try {
            jsonData = httpconn.execute().get();
            jsonArr = new JSONArray(jsonData);
            for(int i = 0; i < jsonArr.length(); i++){
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                arrList.add(new RecommendItem(jsonObj.getString("RECIPE_NM_KO"),
                        jsonObj.getString("TY_NM"),
                        jsonObj.getString("COOKING_TIME"),
                        jsonObj.getString("IMG_URL"),
                        jsonObj.getString("LEVEL_NM")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //GridView Adapter 구성및 OnClick method 구성
        CustomGrid adapter = new CustomGrid(Search_Detail_Layout.this, arrList);
        grid = (GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Search_Detail_Layout.this, "You Clicked at " + arrList.get(+ position).getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, RecipeActivity_detail.class);
                intent.putExtra("recipeTitle",arrList.get(+ position).getTitle());
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
