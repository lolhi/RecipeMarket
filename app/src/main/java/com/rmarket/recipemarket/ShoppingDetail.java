package com.rmarket.recipemarket;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ShoppingDetail extends AppCompatActivity {
    Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
    }
}
