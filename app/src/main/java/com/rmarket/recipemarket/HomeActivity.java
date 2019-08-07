package com.rmarket.recipemarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class HomeActivity extends Fragment {
    private RecyclerView home_recycle;
    private HomeActivityHttpConn http;

    public static HomeActivity newInstance() {
        return new HomeActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false); // 여기서 UI를 생성해서 View를 return

        home_recycle = view.findViewById(R.id.home_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        home_recycle.setHasFixedSize(true);
        home_recycle.setLayoutManager(layoutManager);
        http = new HomeActivityHttpConn(getActivity(), "TodaySpecialPrice", getChildFragmentManager(), home_recycle, new ArrayList<RecommendItem>(), new AppCompatDialog(getActivity()));
        http.execute();

        ItemClickSupport.addTo(home_recycle, R.id.home_recycle).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if(position == 8 || position == 1)
                    return;
                ArrayList<RecommendItem> RecommandArrList = http.getRecommandaArrList();
                Intent intent = new Intent(getActivity(), RecipeActivity_detail.class);
                intent.putExtra("RecommandItem", RecommandArrList.get(position - 2));
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}