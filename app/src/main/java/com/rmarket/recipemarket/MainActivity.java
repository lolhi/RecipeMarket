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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    LinearLayout main_card;
    Context mContext;
    TextView main_reduce,main_title,main_num,detail_time;
    ImageView main_btn,detail_btn;
    LinearLayout main_ranking,detail_ranking,main_anim;
    RecyclerView detail_recycle;
    private ImageView search_button;
    Animation aniFlow = null;

   ArrayList rankingArrayList = new ArrayList<Ranking_Item> ();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(HomeActivity.newInstance());
                    main_card.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_recipe:
                    replaceFragment(RecipeActivity2.newInstance());
                    main_card.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_shopping:
                    replaceFragment(ShoppingActivity.newInstance());
                    main_card.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_mypage:

                    replaceFragment(MypageActivity.newInstance());
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
        rankingArrayList.add(new Ranking_Item("감자","25.8"));
        rankingArrayList.add(new Ranking_Item("양파","13.5"));
        rankingArrayList.add(new Ranking_Item("가지","8.7"));
        rankingArrayList.add(new Ranking_Item("미나리","6.5"));
        rankingArrayList.add(new Ranking_Item("요시","5.5"));
        rankingArrayList.add(new Ranking_Item("쿠사리","4.5"));

        detail_recycle =findViewById(R.id.ranking_detail_recycle); // 내렸을때 리싸이클러뷰

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        detail_recycle.setHasFixedSize(true);
        detail_recycle.setLayoutManager(layoutManager);
        Ranking_Recycle_Adapter mAdapter = new Ranking_Recycle_Adapter(rankingArrayList);
        detail_recycle.setAdapter(mAdapter);

        detail_ranking.setVisibility(View.GONE);

        main_card = findViewById(R.id.main_card);
        search_button = findViewById(R.id.search_button);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, HomeActivity.newInstance()).commit();

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                int Num = 0;
                while (true) {

                    if(Num<5)
                    {
                        Num++;


                        Ranking_Item buffer = (Ranking_Item) rankingArrayList.get(Num);
                        test(buffer,Num);


                    }
                    else
                    {
                        Num = 0;
                        Ranking_Item buffer = (Ranking_Item) rankingArrayList.get(Num);
                       test(buffer,Num);
                    }

                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        th.start();



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

   public void test(Ranking_Item buffer,int Num)
   {



       aniFlow = AnimationUtils.loadAnimation(this, R.anim.ani_flow);
       main_anim.setAnimation(aniFlow);
       main_title.setText(buffer.getMaterial());
       main_num.setText(""+(Num+1));
       main_reduce.setText(buffer.getReduce());

   }


}
