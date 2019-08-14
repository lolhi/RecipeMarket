package com.rmarket.recipemarket;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


public class ViewPagerFragment extends Fragment {
    private int layoutValue;
    private int imgviewValue;
    private AppCompatDialog progressDialog;
    private final RequestManager glide;

    public ViewPagerFragment(int layoutValue, int imgviewValue, AppCompatDialog progressDialog, RequestManager glide) {
        this.layoutValue = layoutValue;
        this.imgviewValue = imgviewValue;
        this.progressDialog = progressDialog;
        this.glide = glide;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutValue, container, false);

        ImageView imageView = view.findViewById(imgviewValue);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (getArguments() != null) {
            Bundle args = getArguments();
            // MainActivity에서 받아온 Resource를 ImageView에 셋팅

            GlideApp.with(this).load(args.getString("imgurl")).addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressOFF();
                    return false;
                }
            }).into(imageView);
        }

        return view;
    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
