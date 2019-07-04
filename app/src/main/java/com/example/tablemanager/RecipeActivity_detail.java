package com.example.tablemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RecipeActivity_detail extends AppCompatActivity {

    TextView title;
    View view;
    Context mcontext;
    RecyclerView detail_recycle;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_good:

                    return true;
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_comment:

                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mcontext =this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        String search_text ; //getIntent롤 넒겨온 title 값
        Intent intent;
        intent = getIntent();
        search_text  = intent.getStringExtra("recipeTitle");


        BottomNavigationView navView = findViewById(R.id.menu_recoomend);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        detail_recycle = findViewById(R.id.recommend_detail_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext);
        detail_recycle.setHasFixedSize(true);
        detail_recycle.setLayoutManager(layoutManager);
        detail_recycle.setAdapter(new Recipe_Detail_Recle_Adapter(mcontext,search_text));
        detail_recycle.setItemAnimator(new DefaultItemAnimator());
    }
}
