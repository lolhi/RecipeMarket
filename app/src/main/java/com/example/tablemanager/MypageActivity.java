package com.example.tablemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;


public class MypageActivity extends Fragment {


    final ArrayList<RecommendItem> arrList = new ArrayList<>();
    RecyclerView mypage_recycle;
    JSONArray jsonArr;
    FragmentAdapter fragmentAdapter;

    public static MypageActivity newInstance() {
        return new MypageActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage, container, false); // 여기서 UI를 생성해서 View를 return
        mypage_recycle = view.findViewById(R.id.mypage_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mypage_recycle.setHasFixedSize(true);
        mypage_recycle.setLayoutManager(layoutManager);
        MypageActivityHttpConn http = new MypageActivityHttpConn(getActivity(), "TodaySpecialPrice", new AppCompatDialog(getActivity()), mypage_recycle);
        http.execute();
        return view;
    }


}
