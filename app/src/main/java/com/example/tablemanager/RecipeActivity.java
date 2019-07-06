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

import java.util.concurrent.ExecutionException;

public class RecipeActivity extends Fragment {

    static int Max_item=4;
    RecyclerView recipe_recycle;
    RecommendItem[] recipe_item= new RecommendItem[Max_item];
    JSONArray jsonArr;
    FragmentAdapter fragmentAdapter;

    public static RecipeActivity newInstance() {
        return new RecipeActivity();
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
//        return inflater.inflate(R.layout.fragment_recipe, container, false); // 여기서 UI를 생성해서 View를 return

        View view=inflater.inflate(R.layout.fragment_recipe, container, false); // 여기서 UI를 생성해서 View를 return


        recipe_item[0] = new RecommendItem(new Integer(R.drawable.straw).toString(),"딸기요리",new Integer(R.drawable.ic_dashboard_black_24dp).toString(),"디저트","30분");
        recipe_item[1] = new RecommendItem(new Integer(R.drawable.mando).toString(),"만두요리",new Integer(R.drawable.ic_dashboard_black_24dp).toString(),"야식쓰","20분");
        recipe_item[2] = new RecommendItem(new Integer(R.drawable.podong).toString(),"포도요리",new Integer(R.drawable.ic_launcher_foreground).toString(),"주식쓰","15분");
        recipe_item[3] = new RecommendItem(new Integer(R.drawable.tomato).toString(),"토마토요리",new Integer(R.drawable.ic_dashboard_black_24dp).toString(),"토메이","1시간");


        recipe_recycle = view.findViewById(R.id.recipe_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recipe_recycle.setHasFixedSize(true);
        recipe_recycle.setLayoutManager(layoutManager);
        recipe_recycle.setAdapter(new Recipe_Recycle_Adapter(getActivity(),recipe_item, jsonArr, fragmentAdapter));
        recipe_recycle.setItemAnimator(new DefaultItemAnimator());
        Log.e("Frag", "Coffee");


        return view;
    }
}
