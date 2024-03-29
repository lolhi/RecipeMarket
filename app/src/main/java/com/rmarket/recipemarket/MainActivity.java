/*
 *MADE : JH(ektj1@naver.com)
 *
 *USE : Main Activity
 *
 *METHOD : Replace_fragment : replace fragment , fragment View
 *         Home_Recycle_Middle : just Text
 *         Home_Recycle_Bottom : use Recycle, view Recommend Recipe
 *
 */


package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    LinearLayout main_card;
    Context mContext;
    TextView main_reduce,main_title,main_num,detail_time;
    ImageView main_btn,detail_btn;
    LinearLayout main_ranking,detail_ranking,main_anim;
    RecyclerView detail_recycle;
    private ImageView search_button;
    private long mLastClickTime = SystemClock.elapsedRealtime();

    RequestManager getGlide(){
        return GlideApp.with(this);
    }

   ArrayList rankingArrayList = new ArrayList<Ranking_Item> ();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return false;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(HomeActivity.newInstance());
                    detail_ranking.setVisibility(View.GONE);
                    main_ranking.setVisibility(View.VISIBLE);
                    main_card.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_recipe:
                    replaceFragment(RecipeActivity2.newInstance());
                    detail_ranking.setVisibility(View.GONE);
                    main_ranking.setVisibility(View.VISIBLE);
                    main_card.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_shopping:
                    replaceFragment(ShoppingActivity.newInstance());
                    detail_ranking.setVisibility(View.GONE);
                    main_ranking.setVisibility(View.VISIBLE);
                    main_card.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_mypage:

                    replaceFragment(MypageActivity.newInstance());
                    detail_ranking.setVisibility(View.GONE);
                    main_ranking.setVisibility(View.VISIBLE);
                    main_card.setVisibility(View.GONE);
                    return true;

            }
            return false;
        }
    };

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GlideApp.get(this).clearMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        GlideApp.get(this).trimMemory(level);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mContext = this;

        main_ranking = findViewById(R.id.main_ranking); // main 리니어 레이아웃(랭킹 아래로 내리기전)
        main_btn = findViewById(R.id.ranking_main_btn); // 아래로 내리기 버튼
        main_num = findViewById(R.id.ranking_main_num); // 랭킹 순위
        main_title = findViewById(R.id.ranking_main_title); // 재료 이름
        main_reduce = findViewById(R.id.ranking_main_reduce); // 증감률
        main_anim = findViewById(R.id.main_anim); // 메인 리니어에서 에니메이션 효과 넣을부분



        detail_ranking = findViewById(R.id.detail_ranking); // detail 리니어 레이아웃(랭킹 내려간 후)
        detail_btn = findViewById(R.id.ranking_detail_btn); // 위로 올리기 버튼
        detail_time = findViewById(R.id.ranking_detail_time); // 시간기준

        SimpleDateFormat format2 = new SimpleDateFormat( "yyyy.MM.dd");

        Date time = new Date();

        String time1 = format2.format(time);
        detail_time.setText(time1 + " 기준");

        detail_recycle = findViewById(R.id.ranking_detail_recycle); // 내렸을때 리싸이클러뷰

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        detail_recycle.setHasFixedSize(true);
        detail_recycle.setLayoutManager(layoutManager);
        RankingRecyclerHttpConn httpConn = new RankingRecyclerHttpConn(getApplicationContext(),"Getpopular", detail_recycle, main_reduce, main_title, main_num, main_anim);
        httpConn.execute();

        ItemClickSupport.addTo(detail_recycle,R.id.ranking_detail_recycle).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(mContext, Search_Detail_Layout.class);
                intent.putExtra("Category", "");
                intent.putExtra("SearchString", httpConn.getRankingArrayList().get(position).getMaterial());
                mContext.startActivity(intent);
            }
        });

        detail_ranking.setVisibility(View.GONE);

        main_card = findViewById(R.id.main_card);
        search_button = findViewById(R.id.search_button);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, HomeActivity.newInstance()).commit();

        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            main_ranking.setVisibility(View.GONE);
            detail_ranking.setVisibility(View.VISIBLE);

            }
        });

        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                detail_ranking.setVisibility(View.GONE);
                main_ranking.setVisibility(View.VISIBLE);

            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext, SearchRayout.class);
                mContext.startActivity(intent);
            }
        });


    }
}
