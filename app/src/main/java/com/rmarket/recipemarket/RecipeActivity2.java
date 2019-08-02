package com.rmarket.recipemarket;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class RecipeActivity2 extends Fragment {

    private CustomScrollView recipeScrollView;
    private ImageView[] grid_image = new ImageView[6];
    private ImageView[] grid_level = new ImageView[6];
    private TextView[] grid_title = new TextView[6];
    private TextView[] grid_subtitle = new TextView[6];
    private TextView[] grid_time = new TextView[6];
    private LinearLayout[] ll_gridlayout = new LinearLayout[6];

    public static RecipeActivity2 newInstance() {
        return new RecipeActivity2();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_recipe, container, false); // 여기서 UI를 생성해서 View를 return

        final View view = inflater.inflate(R.layout.fragment_recipe2, container, false); // 여기서 UI를 생성해서 View를 return
        recipeScrollView = view.findViewById(R.id.scrollview_recipe);

        final RecipeActivityHttpConn2 httpConn2 = new RecipeActivityHttpConn2(getActivity(), "FullRecipe", getChildFragmentManager(), new ArrayList<RecommendItem>(), new AppCompatDialog(getActivity()), view, recipeScrollView);
        httpConn2.execute();

        return view;
    }

    private void SetFindViewById(View childView) {

        ll_gridlayout[0] = childView.findViewById(R.id.ll_gridlayout0);
        ll_gridlayout[1] = childView.findViewById(R.id.ll_gridlayout1);
        ll_gridlayout[2] = childView.findViewById(R.id.ll_gridlayout2);
        ll_gridlayout[3] = childView.findViewById(R.id.ll_gridlayout3);
        ll_gridlayout[4] = childView.findViewById(R.id.ll_gridlayout4);
        ll_gridlayout[5] = childView.findViewById(R.id.ll_gridlayout5);



        grid_image[0] = childView.findViewById(R.id.grid_image0);
        grid_image[1] = childView.findViewById(R.id.grid_image1);
        grid_image[2] = childView.findViewById(R.id.grid_image2);
        grid_image[3] = childView.findViewById(R.id.grid_image3);
        grid_image[4] = childView.findViewById(R.id.grid_image4);
        grid_image[5] = childView.findViewById(R.id.grid_image5);



        grid_level[0] = childView.findViewById(R.id.grid_level0);
        grid_level[1] = childView.findViewById(R.id.grid_level1);
        grid_level[2] = childView.findViewById(R.id.grid_level2);
        grid_level[3] = childView.findViewById(R.id.grid_level3);
        grid_level[4] = childView.findViewById(R.id.grid_level4);
        grid_level[5] = childView.findViewById(R.id.grid_level5);

        grid_title[0] = childView.findViewById(R.id.grid_title0);
        grid_title[1] = childView.findViewById(R.id.grid_title1);
        grid_title[2] = childView.findViewById(R.id.grid_title2);
        grid_title[3] = childView.findViewById(R.id.grid_title3);
        grid_title[4] = childView.findViewById(R.id.grid_title4);
        grid_title[5] = childView.findViewById(R.id.grid_title5);

        grid_subtitle[0] = childView.findViewById(R.id.grid_subtitle0);
        grid_subtitle[1] = childView.findViewById(R.id.grid_subtitle1);
        grid_subtitle[2] = childView.findViewById(R.id.grid_subtitle2);
        grid_subtitle[3] = childView.findViewById(R.id.grid_subtitle3);
        grid_subtitle[4] = childView.findViewById(R.id.grid_subtitle4);
        grid_subtitle[5] = childView.findViewById(R.id.grid_subtitle5);

        grid_time[0] = childView.findViewById(R.id.grid_time0);
        grid_time[1] = childView.findViewById(R.id.grid_time1);
        grid_time[2] = childView.findViewById(R.id.grid_time2);
        grid_time[3] = childView.findViewById(R.id.grid_time3);
        grid_time[4] = childView.findViewById(R.id.grid_time4);
        grid_time[5] = childView.findViewById(R.id.grid_time5);
    }

}
