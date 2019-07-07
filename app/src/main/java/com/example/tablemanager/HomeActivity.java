package com.example.tablemanager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class HomeActivity extends Fragment{
    RecyclerView home_recycle;

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

        home_recycle = view.findViewById(R.id.home_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        home_recycle.setHasFixedSize(true);
        home_recycle.setLayoutManager(layoutManager);
        HomeActivityHttpConn http = new HomeActivityHttpConn(getActivity(),"TodaySpecialPrice",getChildFragmentManager(), home_recycle, new ArrayList<RecommendItem>(), new AppCompatDialog(getActivity()));
        http.execute();
        return view;
    }
}