package com.rmarket.recipemarket;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


public class ViewPagerFragment1 extends Fragment {
    private int layoutValue;
    private int imgviewValue;
    private Context mContext;

    public ViewPagerFragment1(int layoutValue, int imgviewValue, Context mContext) {
        this.layoutValue = layoutValue;
        this.imgviewValue = imgviewValue;
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutValue, container, false);

        ImageView imageView = view.findViewById(imgviewValue);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        TextView tvFundingName = view.findViewById(R.id.tv_shopping_viewpager_funding_name);
        TextView tvProductName = view.findViewById(R.id.tv_shopping_viewpager_product_name);
        TextView tvProductSubName = view.findViewById(R.id.tv_shopping_viewpager_product_subname);
        TextView tvGoalAmount = view.findViewById(R.id.tv_shopping_viewpager_funding_goal_amount);
        TextView tvPresentAmount = view.findViewById(R.id.tv_shopping_viewpager_funding_present_amount);
        TextView tvFundingPercent = view.findViewById(R.id.tv_shopping_viewpager_funding_percent);

        if (getArguments() != null) {
            Bundle args = getArguments();
            // MainActivity에서 받아온 Resource를 ImageView에 셋팅

            GlideApp.with(this).load(args.getInt("imgurl")).addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(imageView);
        }

        return view;
    }
}
