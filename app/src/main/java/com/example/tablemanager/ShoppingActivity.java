package com.example.tablemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class ShoppingActivity extends Fragment {


    public static ShoppingActivity newInstance() {
        return new ShoppingActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping, container, false); // 여기서 UI를 생성해서 View를 return
    }
}
