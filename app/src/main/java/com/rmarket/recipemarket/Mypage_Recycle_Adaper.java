package com.rmarket.recipemarket;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class Mypage_Recycle_Adaper extends RecyclerView.Adapter {

    ArrayList<RecommendItem> arrList;
    Context mContext;
    AppCompatDialog progressDialog;
    private final RequestManager glide;

    public Mypage_Recycle_Adaper(Context mContext, ArrayList<RecommendItem> arrList, AppCompatDialog progressDialog, RequestManager glide) {
        this.mContext = mContext;
        this.arrList = arrList;
        this.progressDialog = progressDialog;
        this.glide = glide;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_recycle_item, null);
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mypage, null);
        return new KnowledgeViewHolder(v, rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof KnowledgeViewHolder) {
                ((KnowledgeViewHolder) viewHolder).mypage_recycer.setVisibility(View.VISIBLE);
                ((KnowledgeViewHolder) viewHolder).ivClippingFail.setVisibility(View.GONE);
                final RecommendItem item = arrList.get(position);

                glide.load(item.getImage()).addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressOFF();
                        return false;
                    }
                }).into(((KnowledgeViewHolder) viewHolder).imgImage);
                ((KnowledgeViewHolder) viewHolder).txtTitle.setText(item.getTitle());
                int levelImg = item.getLevel().equals("초보환영") ? R.drawable.level_low : item.getLevel().equals("보통") ? R.drawable.level_middle : R.drawable.level_hight;
                //((KnowledgeViewHolder) viewHolder).imgLevel.setImageResource(levelImg);
                glide.load(levelImg).into(((KnowledgeViewHolder)viewHolder).imgLevel);
                ((KnowledgeViewHolder) viewHolder).txtSubTitle.setText(item.getSubtitle());
                ((KnowledgeViewHolder) viewHolder).txtTime.setText(item.getTime());
        }

    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    class KnowledgeViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtSubTitle, txtTime;
        ImageView imgImage, imgLevel;
        LinearLayout mypage_layout;
        RecyclerView mypage_recycer;
        ImageView ivClippingFail;

        public KnowledgeViewHolder(View itemView, View rootView) {
            super(itemView);
            mypage_layout = itemView.findViewById(R.id.mypage_layout);
            txtTitle = itemView.findViewById(R.id.mypage_title);
            txtSubTitle = itemView.findViewById(R.id.mypage_subtitle);
            imgImage = itemView.findViewById(R.id.mypage_image);
            imgLevel = itemView.findViewById(R.id.mypage_level);
            GradientDrawable drawable=(GradientDrawable) mContext.getDrawable(R.drawable.background_rounding);
            imgImage.setBackground(drawable);
            imgImage.setClipToOutline(true);
            txtTime = itemView.findViewById(R.id.mypage_time);

            mypage_recycer = rootView.findViewById(R.id.mypage_recycle);
            ivClippingFail = rootView.findViewById(R.id.clipping_fail_iv);
        }
    }
}

