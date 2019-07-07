package com.example.tablemanager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MypageActivity extends Fragment{


    public static MypageActivity newInstance() {
        return new MypageActivity();
    }

    RecyclerView mypage_recycle ;
    JSONArray jsonArr;
    FragmentAdapter fragmentAdapter;
    final ArrayList<RecommendItem> arrList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        String jsonData;

        super.onCreate(savedInstanceState);
        HttpConnection http = new HttpConnection(getActivity(),UrlClass.Url + "TodaySpecialPrice");
        try {
            jsonData = http.execute().get();
            jsonArr = new JSONArray(jsonData);
            fragmentAdapter = new FragmentAdapter(getChildFragmentManager());
            for(int i = 0; i < jsonArr.length(); i++){
                JSONObject jsonObj = jsonArr.getJSONObject(i);

                arrList.add(new RecommendItem(jsonObj.getString("RECIPE_NM_KO"),
                        jsonObj.getString("TY_NM"),
                        jsonObj.getString("COOKING_TIME"),
                        jsonObj.getString("IMG_URL"),
                        jsonObj.getString("LEVEL_NM")));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false); // 여기서 UI를 생성해서 View를 return
        mypage_recycle = view.findViewById(R.id.mypage_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mypage_recycle.setHasFixedSize(true);
        mypage_recycle.setLayoutManager(layoutManager);
        mypage_recycle.setAdapter(new Mypage_Recycle_Adaper(getActivity(),arrList, jsonArr, fragmentAdapter));
        mypage_recycle.setItemAnimator(new DefaultItemAnimator());

        return view;
    }


}
