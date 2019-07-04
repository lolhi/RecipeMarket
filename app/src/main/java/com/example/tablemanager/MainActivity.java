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


package com.example.tablemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView search_button;
    Context mContext;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(HomeActivity.newInstance());
                    return true;
                case R.id.navigation_recipe:
                    replaceFragment(RecipeActivity.newInstance());
                    return true;
                case R.id.navigation_shopping:
                    replaceFragment(ShoppingActivity.newInstance());
                    return true;
                case R.id.navigation_mypage:
                    replaceFragment(MypageActivity.newInstance());
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
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search_button=findViewById(R.id.search_button);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,HomeActivity.newInstance()).commit();



        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext, SearchRayout.class);
                mContext.startActivity(intent);
            }
        });
    }


}
