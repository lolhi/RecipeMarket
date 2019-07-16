package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.rmarket.recipemarket.R.drawable.nonselect_bar;

public class SearchRayout extends AppCompatActivity {
    EditText search_edit;
    ImageView search_button;//오른쪽 상단 서치버튼
    ImageView imgnation[];
    ImageView imgfood[];
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search_button = (ImageView) findViewById(R.id.search_button);
        search_edit = (EditText) findViewById(R.id.search_edit);
        imgnation = new ImageView[]{findViewById(R.id.nation0), findViewById(R.id.nation1),findViewById(R.id.nation2),findViewById(R.id.nation3),findViewById(R.id.nation4),findViewById(R.id.nation5)};
        imgfood = new ImageView[]{findViewById(R.id.cook0), findViewById(R.id.cook1),findViewById(R.id.cook2),findViewById(R.id.cook3),findViewById(R.id.cook4),findViewById(R.id.cook5),findViewById(R.id.cook6),findViewById(R.id.cook7)};

        final ArrayList<SearchCategoryItem> arrList = new ArrayList<>();
        arrList.add(new SearchCategoryItem("한국", "한식", R.drawable.nation_korea));
        arrList.add(new SearchCategoryItem("중국", R.drawable.nation_panda));
        arrList.add(new SearchCategoryItem("일본", R.drawable.nation_japan));
        arrList.add(new SearchCategoryItem("서양", R.drawable.nation_west));
        arrList.add(new SearchCategoryItem("이탈리아", R.drawable.nation_italia));
        arrList.add(new SearchCategoryItem("퓨전", R.drawable.nation_fusion));

        final ArrayList<SearchCategoryItem> arrList2 = new ArrayList<>();
        arrList2.add(new SearchCategoryItem("밥", "", R.drawable.rice));
        arrList2.add(new SearchCategoryItem("볶음", "", R.drawable.bukum));
        arrList2.add(new SearchCategoryItem("밑반찬", "밑반찬/김치", R.drawable.level_hight));
        arrList2.add(new SearchCategoryItem("튀김", "튀김/커틀릿", R.drawable.level_hight));
        arrList2.add(new SearchCategoryItem("찌개", "찌개/전골/스튜", R.drawable.chigga));
        arrList2.add(new SearchCategoryItem("면류", "만두/면류", R.drawable.nuddle));
        arrList2.add(new SearchCategoryItem("구이", "", R.drawable.level_hight));
        arrList2.add(new SearchCategoryItem("국", "", R.drawable.level_hight));

        ListView listview_popular = (ListView) findViewById(R.id.search_list_popular);
        SearchLayoutHttpConn2 conn = new SearchLayoutHttpConn2(SearchRayout.this,"GetPopular",listview_popular);
        conn.execute();

        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onSuccess(MeV2Response result) {
                ListView listview_resent = (ListView) findViewById(R.id.search_list_recent);
                JSONObject jsonObject = new JSONObject();
                try{
                    jsonObject.put("ID", result.getId());
                }catch (JSONException e) {
                    e.printStackTrace();
                }

                SearchLayoutHttpConn connPost = new SearchLayoutHttpConn(SearchRayout.this, "GetRecentSearch",new AppCompatDialog(SearchRayout.this),jsonObject, listview_resent);
                connPost.execute();
            }
        });

        final TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        tabHost1.setup();

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("분류별");
        tabHost1.addTab(ts1);

        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("인기검색어");
        tabHost1.addTab(ts2);


        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"content3")
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("최근검색");
        tabHost1.addTab(ts3);



        imgnation[0].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {
                intent.putExtra("Category", "NATION_NM");
                intent.putExtra("SearchString",arrList.get(0).getRealName());
                   mContext.startActivity(intent);
            }
        });

        imgnation[1].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {
                intent.putExtra("Category", "NATION_NM");
                intent.putExtra("SearchString",arrList.get(1).getCategoryName());
                  mContext.startActivity(intent);
            }
        });
        imgnation[2].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {
                intent.putExtra("Category", "NATION_NM");
                intent.putExtra("SearchString",arrList.get(2).getCategoryName());
                mContext.startActivity(intent);
            }
        });
        imgnation[3].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {
                intent.putExtra("Category", "NATION_NM");
                intent.putExtra("SearchString",arrList.get(3).getCategoryName());
                mContext.startActivity(intent);
            }
        });
        imgnation[4].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {
                intent.putExtra("Category", "NATION_NM");
                intent.putExtra("SearchString",arrList.get(4).getCategoryName());
                mContext.startActivity(intent);
            }
        });
        imgnation[5].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {
                intent.putExtra("Category", "NATION_NM");
                intent.putExtra("SearchString",arrList.get(5).getCategoryName());
                mContext.startActivity(intent);
            }
        });


        imgfood[0].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {

                intent.putExtra("SearchString",arrList2.get(0).getCategoryName());
                intent.putExtra("Category", "TY_NM");
                mContext.startActivity(intent);
            }
        });

        imgfood[1].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {

                intent.putExtra("SearchString",arrList2.get(1).getCategoryName());
                intent.putExtra("Category", "TY_NM");
                mContext.startActivity(intent);
            }
        });

        imgfood[2].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {

                intent.putExtra("SearchString",arrList2.get(2).getRealName());
                intent.putExtra("Category", "TY_NM");
                mContext.startActivity(intent);
            }
        });

        imgfood[3].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {

                intent.putExtra("SearchString",arrList2.get(3).getRealName());
                intent.putExtra("Category", "TY_NM");
                mContext.startActivity(intent);
            }
        });

        imgfood[4].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {

                intent.putExtra("SearchString",arrList2.get(4).getRealName());
                intent.putExtra("Category", "TY_NM");
                mContext.startActivity(intent);
            }
        });

        imgfood[5].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {

                intent.putExtra("SearchString",arrList2.get(5).getRealName());
                intent.putExtra("Category", "TY_NM");
                mContext.startActivity(intent);
            }
        });
        imgfood[6].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {

                intent.putExtra("SearchString",arrList2.get(6).getCategoryName());
                intent.putExtra("Category", "TY_NM");
                mContext.startActivity(intent);
            }
        });
        imgfood[7].setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {

                intent.putExtra("SearchString",arrList2.get(7).getCategoryName());
                intent.putExtra("Category", "TY_NM");
                mContext.startActivity(intent);
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() { // 이미지 버튼 이벤트 정의
            @Override

            public void onClick(View v) { //클릭 했을경우
                if(search_edit.getText().toString().equals("")) {
                    Toast.makeText(mContext, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {

                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        Log.e("OnSuccess :: ", "success");
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("ID", result.getId());
                            jsonObject.put("SEARCH_STRING", search_edit.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        HttpConnection connPost = new HttpConnection(SearchRayout.this,"AddResentSearch", jsonObject);
                        connPost.execute();
                    }
                });

                Intent intent = new Intent(mContext, Search_Detail_Layout.class);
                intent.putExtra("Category", "");
                intent.putExtra("SearchString", search_edit.getText().toString());
                mContext.startActivity(intent);
            }
        });

        search_edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(search_edit.getText().toString().equals("")) {
                        search_edit.setText("");
                        Toast.makeText(mContext, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    UserManagement.getInstance().me(new MeV2ResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {

                        }

                        @Override
                        public void onSuccess(MeV2Response result) {
                            Log.e("OnSuccess :: ", "success");
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("ID", result.getId());
                                jsonObject.put("SEARCH_STRING", search_edit.getText().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            HttpConnection connPost = new HttpConnection(SearchRayout.this,"AddResentSearch", jsonObject);
                            connPost.execute();
                        }
                    });

                    Intent intent = new Intent(mContext, Search_Detail_Layout.class);
                    intent.putExtra("Category", "");
                    intent.putExtra("SearchString", search_edit.getText().toString());
                    mContext.startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }



}
