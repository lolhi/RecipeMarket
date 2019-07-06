package com.example.tablemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;


public class ViewPagerFragment extends Fragment {
    private int layoutValue;
    private int imgviewValue;

    public ViewPagerFragment(int layoutValue, int imgviewValue) {
        this.layoutValue = layoutValue;
        this.imgviewValue= imgviewValue;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutValue, container, false);

        ImageView imageView = view.findViewById(imgviewValue);

        if (getArguments() != null) {
            Bundle args = getArguments();
            // MainActivity에서 받아온 Resource를 ImageView에 셋팅

            Glide.with(this).load(args.getString("imgurl")).into(imageView);
        }

        return view;
    }
}
