package com.example.tablemanager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
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
    static int Max_item=4;


    RecyclerView home_recycle;

    RecommendItem[] item = new RecommendItem[Max_item];
    JSONArray jsonArr;
    FragmentAdapter fragmentAdapter;

    public static HomeActivity newInstance() {
        return new HomeActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        String jsonData;

        super.onCreate(savedInstanceState);
        HttpConnection http = new HttpConnection(getActivity(),UrlClass.Url + "TodaySpecialPrice");
        try {
            jsonData = http.execute().get();
            jsonArr = new JSONArray(jsonData);
            fragmentAdapter = new FragmentAdapter(getChildFragmentManager());
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



        item[0] = new RecommendItem(R.drawable.straw,"딸기요리",R.drawable.ic_dashboard_black_24dp,"디저트");
        item[1] = new RecommendItem(R.drawable.mando,"만두요리",R.drawable.ic_dashboard_black_24dp,"야식쓰");
        item[2] = new RecommendItem(R.drawable.podong,"포도요리",R.drawable.ic_launcher_foreground,"주식쓰");
        item[3] = new RecommendItem(R.drawable.tomato,"토마토요리",R.drawable.ic_dashboard_black_24dp,"토메이");
        home_recycle = view.findViewById(R.id.home_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        home_recycle.setHasFixedSize(true);
        home_recycle.setLayoutManager(layoutManager);
        home_recycle.setAdapter(new Home_recycle_Adapter(getActivity(),item, jsonArr, fragmentAdapter));
        home_recycle.setItemAnimator(new DefaultItemAnimator());
        Log.e("Frag", "Coffee");


        return view;
    }
}
class FragmentAdapter extends FragmentPagerAdapter {

    // ViewPager에 들어갈 Fragment들을 담을 리스트
    private ArrayList<Fragment> fragments = new ArrayList<>();

    // 필수 생성자
    FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    // List에 Fragment를 담을 함수
    void addItem(Fragment fragment) {
        fragments.add(fragment);
    }
}