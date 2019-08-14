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

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class RecipeActivity2 extends Fragment {

    private CustomScrollView recipeScrollView;
    private RequestManager glide;

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
        glide = ((MainActivity)getActivity()).getGlide();
        final View view = inflater.inflate(R.layout.fragment_recipe2, container, false); // 여기서 UI를 생성해서 View를 return
        recipeScrollView = view.findViewById(R.id.scrollview_recipe);

        final RecipeActivityHttpConn2 httpConn2 = new RecipeActivityHttpConn2(getActivity(), "FullRecipe", getChildFragmentManager(), new ArrayList<RecommendItem>(), new AppCompatDialog(getActivity()), view, recipeScrollView, glide);
        httpConn2.execute();

        return view;
    }
    }
