package com.example.tablemanager;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchRayout extends AppCompatActivity {

    Context mContext;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            mContext = this;
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
        }

}
