package com.example.tablemanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends Fragment{
    static int Max_item=4;

    LinearLayout test;
    RecyclerView home_recycle;
    List<RecommendItem> items = new ArrayList<>();

    RecommendItem[] item = new RecommendItem[Max_item];

    public static HomeActivity newInstance() {
        return new HomeActivity();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_home, container, false); // 여기서 UI를 생성해서 View를 return


        item[0] = new RecommendItem(R.drawable.straw,"딸기요리",R.drawable.ic_dashboard_black_24dp,"디저트");
        item[1] = new RecommendItem(R.drawable.mando,"만두요리",R.drawable.ic_dashboard_black_24dp,"야식쓰");
        item[2] = new RecommendItem(R.drawable.podong,"포도요리",R.drawable.ic_launcher_foreground,"주식쓰");
        item[3] = new RecommendItem(R.drawable.tomato,"토마토요리",R.drawable.ic_dashboard_black_24dp,"토메이");
        home_recycle = view.findViewById(R.id.home_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        home_recycle.setHasFixedSize(true);
        home_recycle.setLayoutManager(layoutManager);
        home_recycle.setAdapter(new Home_recycle_Adapter(getActivity(),item));
        home_recycle.setItemAnimator(new DefaultItemAnimator());
        Log.e("Frag", "Coffee");




        return view;
    }
}
