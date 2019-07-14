package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import java.util.ArrayList;

import static com.rmarket.recipemarket.R.drawable.nonselect_bar;

public class SearchRayout extends AppCompatActivity {
    static final String[] LIST_MENU_POPULAR = {"감자", "양파", "버섯", "카레", "오징어"}; //두번째tap 배열
    static final String[] LIST_MENU_RECENT = {"감자", "양파", "버섯", "카레", "오징어"}; // 세번째tap 배열
    EditText search_edit;
    GridView grid1, grid2;
    ImageView search_button;//오른쪽 상단 서치버튼
    ImageView img_italy;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search_button = (ImageView) findViewById(R.id.search_button);
        search_edit = (EditText) findViewById(R.id.search_edit);
        img_italy = findViewById(R.id.nation4);

        final ArrayList<SearchCategoryItem> arrList = new ArrayList<>();
        arrList.add(new SearchCategoryItem("한국", "한식", R.drawable.nation_korea));
        arrList.add(new SearchCategoryItem("중국", R.drawable.nation_panda));
        arrList.add(new SearchCategoryItem("일본", R.drawable.nation_japan));
        arrList.add(new SearchCategoryItem("서양", R.drawable.nation_west));
        arrList.add(new SearchCategoryItem("이탈리아", R.drawable.nation_italia));
        arrList.add(new SearchCategoryItem("퓨전", R.drawable.nation_fusion));
//        arrList.add(new SearchCategoryItem("동남아시아", R.drawable.nation_east));
//        arrList.add(new SearchCategoryItem("", R.color.colorWhite));


//        CustomGridsubject adapter = new CustomGridsubject(mContext, arrList);
//        grid1 = (GridView) findViewById(R.id.grid_subject);
//        grid1.setAdapter(adapter);


        final ArrayList<SearchCategoryItem> arrList2 = new ArrayList<>();
        arrList2.add(new SearchCategoryItem("밥", "", R.drawable.rice));
        arrList2.add(new SearchCategoryItem("볶음", "", R.drawable.bukum));
        arrList2.add(new SearchCategoryItem("밑반찬", "밑반찬/김치", R.drawable.mitbanchan));
        arrList2.add(new SearchCategoryItem("튀김", "튀김/커틀릿", R.drawable.fried));
        arrList2.add(new SearchCategoryItem("찌개", "찌개/전골/스튜", R.drawable.chigga));
        arrList2.add(new SearchCategoryItem("면류", "만두/면류", R.drawable.nuddle));
        arrList2.add(new SearchCategoryItem("구이", "", R.drawable.guii));
        arrList2.add(new SearchCategoryItem("국", "", R.drawable.kuk));
//        CustomGridsubject2 adapter_2 = new CustomGridsubject2(mContext, arrList2);
//        grid2 = (GridView) findViewById(R.id.grid_cook);
//        grid2.setAdapter(adapter_2);


        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU_RECENT);
        ListView listview_resent = (ListView) findViewById(R.id.search_list_recent);
        listview_resent.setAdapter(adapter2);


        //인기검색 리스트
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU_POPULAR);
        ListView listview_popular = (ListView) findViewById(R.id.search_list_popular);
        listview_popular.setAdapter(adapter1);


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





//        for (int i = 0; i < tabHost1.getTabWidget().getChildCount(); i++) {
//            tabHost1.getTabWidget().getChildAt(i)
//                    .setBackgroundResource(nonselect_bar); // unselected
//        }
//        tabHost1.getTabWidget().getChildAt(tabHost1.getCurrentTab()).setBackgroundResource(R.drawable.select_bar);
//

        img_italy.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(mContext, Search_Detail_Layout.class);
            public void onClick(View v) {

                intent.putExtra("SearchString",arrList.get(4).getCategoryName());
                mContext.startActivity(intent);
                // your code here
            }
        });

        // selected
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

//        tabHost1.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//
//            public void onTabChanged(String arg0) {
//                for (int i = 0; i < tabHost1.getTabWidget().getChildCount(); i++) {
//                    tabHost1.getTabWidget().getChildAt(i)
//                            .setBackgroundResource(nonselect_bar); // unselected
//                }
//                tabHost1.getTabWidget().getChildAt(tabHost1.getCurrentTab())
//                        .setBackgroundResource(R.drawable.select_bar); // selected
//
//            }
//        });

//        grid1.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if(motionEvent.getAction() == MotionEvent.ACTION_MOVE)
//                {
//                    return true;
//                }
//                return false;
//            }
//
//        });


//
//        grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                Intent intent = new Intent(mContext, Search_Detail_Layout.class);
//                if (arrList.get(position).getRealName().equals(""))
//                    intent.putExtra("SearchString", arrList.get(position).getCategoryName());
//                else
//                    intent.putExtra("SearchString", arrList.get(position).getRealName());
//                intent.putExtra("Category", "NATION_NM");
//                mContext.startActivity(intent);
//
//
//
//                // TODO : use strText
//            }
//        });
//
//        grid2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                Intent intent = new Intent(mContext, Search_Detail_Layout.class);
//                if (arrList2.get(position).getRealName().equals(""))
//                    intent.putExtra("SearchString", arrList2.get(position).getCategoryName());
//                else
//                    intent.putExtra("SearchString", arrList2.get(position).getRealName());
//                intent.putExtra("Category", "TY_NM");
//                mContext.startActivity(intent);
//                // TODO : use strText
//            }
//        });

        listview_popular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 코드 계속 ...

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position);

                // TODO : use strText
            }
        });

        listview_resent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 코드 계속 ...

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                // get TextView's Text.
                String temp = "위치값" + position;
                Intent intent = new Intent(mContext, Search_Detail_Layout.class);
                intent.putExtra("SearchString", temp);
                mContext.startActivity(intent);
                // TODO : use strText
            }
        });


        search_button.setOnClickListener(new View.OnClickListener() { // 이미지 버튼 이벤트 정의
            @Override

            public void onClick(View v) { //클릭 했을경우
                if(search_edit.getText().toString().equals("")) {
                    Toast.makeText(mContext, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
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
