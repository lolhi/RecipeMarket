package com.example.tablemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
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
    GridView grid1,grid2;
        ImageView search_button;//오른쪽 상단 서치버튼
    static final String[] LIST_MENU_POPULAR = {"감자", "양파", "사랑아","그리운","내사랑아"} ; //두번째tap 배열
    static final String[] LIST_MENU_RECENT = {"아이야","그놈의","정들먹","이며","한건하려"} ; // 세번째tap 배열
    Context mContext;
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            mContext = this;
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            search_button=(ImageView) findViewById(R.id.search_button);



            final ArrayList<SearchCategoryItem> arrList = new ArrayList<>();
            arrList.add(new SearchCategoryItem("한국","한식",R.drawable.nationkorea));
            arrList.add(new SearchCategoryItem("중국",R.drawable.nationchina));
            arrList.add(new SearchCategoryItem("일본",R.drawable.nationjapan));
            arrList.add(new SearchCategoryItem("서양",R.drawable.nationwest));
            arrList.add(new SearchCategoryItem("이탈리아",R.drawable.nationitalia));
            arrList.add(new SearchCategoryItem("퓨전",R.drawable.nationfusion));
            arrList.add(new SearchCategoryItem("동남아시아",R.drawable.nationdongnam));
            arrList.add(new SearchCategoryItem("",R.color.colorWhite));




            CustomGridsubject adapter = new CustomGridsubject(mContext, arrList);
            grid1 = (GridView)findViewById(R.id.grid_subject);
            grid1.setAdapter(adapter);


            final ArrayList<SearchCategoryItem> arrList2 = new ArrayList<>();
            arrList2.add(new SearchCategoryItem("밥","밥",R.drawable.rice));
            arrList2.add(new SearchCategoryItem("볶음","볶음",R.drawable.bukum));
            arrList2.add(new SearchCategoryItem("밑반찬","밑반찬/김치",R.drawable.mitbanchan));
            arrList2.add(new SearchCategoryItem("튀김","튀김/커틀릿",R.drawable.fried));
            arrList2.add(new SearchCategoryItem("찌개","찌개/전골/스튜",R.drawable.chigga));
            arrList2.add(new SearchCategoryItem("면류","만두/면류",R.drawable.nuddle));
            arrList2.add(new SearchCategoryItem("구이","구이",R.drawable.guii));
            arrList2.add(new SearchCategoryItem("국","국",R.drawable.kuk));
            CustomGridsubject2 adapter_2 = new CustomGridsubject2(mContext, arrList2);
            grid2 = (GridView)findViewById(R.id.grid_cook);
            grid2.setAdapter(adapter_2);



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
            ts1.setIndicator("분류별") ;
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
//
////            ItemClickSupport.addTo(grid,R.id.grid_subject).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//////                @Override
//////                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//////                    Intent intent = new Intent(mContext, Search_Detail_Layout.class);
//                    if(arrList.get(position).getRealName().equals(""))
//                        intent.putExtra("SearchString",arrList.get(position).getCategoryName());
//                    else
//                        intent.putExtra("SearchString",arrList.get(position).getRealName());
//                    mContext.startActivity(intent);
//////                }
//////            });

            grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent intent = new Intent(mContext,  Search_Detail_Layout.class);
                    if(arrList.get(position).getRealName().equals(""))
                        intent.putExtra("SearchString",arrList.get(position).getCategoryName());
                    else
                        intent.putExtra("SearchString",arrList.get(position).getRealName());
                    mContext.startActivity(intent);

                    // TODO : use strText
                }
            });

            grid2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent intent = new Intent(mContext, Search_Detail_Layout.class);
                    if(arrList2.get(position).getRealName().equals(""))
                        intent.putExtra("SearchString",arrList2.get(position).getCategoryName());
                    else
                        intent.putExtra("SearchString",arrList2.get(position).getRealName());
                    mContext.startActivity(intent);

                    // TODO : use strText
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
