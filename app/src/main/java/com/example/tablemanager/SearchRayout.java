package com.example.tablemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchRayout extends AppCompatActivity {
    EditText search_edit;
        ImageView search_button;//오른쪽 상단 서치버튼
    static final String[] LIST_MENU_POPULAR = {"감자", "양파", "사랑아","그리운","내사랑아"} ; //두번째tap 배열
    static final String[] LIST_MENU_RECENT = {"아이야","그놈의","정들먹","이며","한건하려"} ; // 세번째tap 배열
    Context mContext;
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            mContext = this;
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            search_button=(ImageView) findViewById(R.id.search_btn);

            RecyclerView recyclerView = findViewById(R.id.search_list_category);
            RecyclerView.LayoutManager mLayoutManager;
            recyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);

            final ArrayList<SearchCategoryItem> arrList = new ArrayList<>();
            arrList.add(new SearchCategoryItem("한국","한식",R.drawable.mando));
            arrList.add(new SearchCategoryItem("중국",R.drawable.mando));
            arrList.add(new SearchCategoryItem("일본",R.drawable.mando));
            arrList.add(new SearchCategoryItem("서양",R.drawable.mando));
            arrList.add(new SearchCategoryItem("동남아시아",R.drawable.mando));

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrList);
            recyclerView.setAdapter(adapter);

            ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU_RECENT) ;
            ListView listview_resent = (ListView) findViewById(R.id.search_list_recent) ;
            listview_resent.setAdapter(adapter2) ;


            //인기검색 리스트
            ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU_POPULAR) ;
            ListView listview_popular = (ListView) findViewById(R.id.search_list_popular) ;
            listview_popular.setAdapter(adapter1) ;



            TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1) ;
            tabHost1.setup() ;

            // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
            TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1") ;
            ts1.setContent(R.id.content1) ;
            ts1.setIndicator("요리별") ;
            tabHost1.addTab(ts1) ;

            TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2") ;
            ts2.setContent(R.id.content2) ;
            ts2.setIndicator("인기검색어") ;
            tabHost1.addTab(ts2) ;


            // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"content3")
            TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3") ;
            ts3.setContent(R.id.content3) ;
            ts3.setIndicator("최근검색") ;
            tabHost1.addTab(ts3) ;

            ItemClickSupport.addTo(recyclerView,R.id.search_list_category).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Intent intent = new Intent(mContext, Search_Detail_Layout.class);
                    if(arrList.get(position).getRealName().equals(""))
                        intent.putExtra("SearchString",arrList.get(position).getCategoryName());
                    else
                        intent.putExtra("SearchString",arrList.get(position).getRealName());
                    mContext.startActivity(intent);
                }
            });

            listview_popular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                // 코드 계속 ...

                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id) {

                    // get TextView's Text.
                    String strText = (String) parent.getItemAtPosition(position) ;

                    // TODO : use strText
                }
            }) ;

            listview_resent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                // 코드 계속 ...

                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id) {

                    // get TextView's Text.
                    String temp = "위치값" + position;
                    Intent intent = new Intent(mContext, Search_Detail_Layout.class);
                    intent.putExtra("SearchString",temp);
                    mContext.startActivity(intent);
                    // TODO : use strText
                }
            }) ;



            search_button.setOnClickListener(new View.OnClickListener() { // 이미지 버튼 이벤트 정의
                @Override

                public void onClick(View v) { //클릭 했을경우

                    search_edit.getText();
                    Intent intent = new Intent(mContext, Search_Detail_Layout.class);
                    intent.putExtra("editText", search_edit.getText().toString());
                    mContext.startActivity(intent);

                }

            });
        }

}
