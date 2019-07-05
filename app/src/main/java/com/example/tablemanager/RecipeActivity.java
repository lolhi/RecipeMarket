package com.example.tablemanager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeActivity extends Fragment {

    static int Max_item=4;


    RecyclerView recipe_recycle;

    RecommendItem[] recipe_item= new RecommendItem[Max_item];

    public static RecipeActivity newInstance() {
        return new RecipeActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_recipe, container, false); // 여기서 UI를 생성해서 View를 return

        View view=inflater.inflate(R.layout.fragment_recipe, container, false); // 여기서 UI를 생성해서 View를 return


        recipe_item[0] = new RecommendItem(R.drawable.straw,"딸기요리",R.drawable.ic_dashboard_black_24dp,"디저트","30분");
        recipe_item[1] = new RecommendItem(R.drawable.mando,"만두요리",R.drawable.ic_dashboard_black_24dp,"야식쓰","20분");
        recipe_item[2] = new RecommendItem(R.drawable.podong,"포도요리",R.drawable.ic_launcher_foreground,"주식쓰","15분");
        recipe_item[3] = new RecommendItem(R.drawable.tomato,"토마토요리",R.drawable.ic_dashboard_black_24dp,"토메이","1시간");


        recipe_recycle = view.findViewById(R.id.recipe_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recipe_recycle.setHasFixedSize(true);
        recipe_recycle.setLayoutManager(layoutManager);
        recipe_recycle.setAdapter(new Recipe_Recycle_Adapter(getActivity(),recipe_item));
        recipe_recycle.setItemAnimator(new DefaultItemAnimator());
        Log.e("Frag", "Coffee");


        return view;
    }
}
