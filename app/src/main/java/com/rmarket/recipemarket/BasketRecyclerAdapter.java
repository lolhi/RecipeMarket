package com.rmarket.recipemarket;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class BasketRecyclerAdapter extends RecyclerView.Adapter{
    private Context mContext;
    ArrayList<Basket_Item> BasketItem;

    private final int HEADER = 0;
    private final int MIDDLE = 1;
    private final int BOTTOM = 2;
    private final int END  = 3;

    public BasketRecyclerAdapter(Context mContext,ArrayList<Basket_Item> BasketItem)
    {
        this.mContext = mContext;
        this.BasketItem = BasketItem;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0)
            return HEADER;

        else
            return END;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_recycle_top, null);
            return new BasketRecyclerAdapter.Basket_Recycle_Header(v);
        } else  {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_recycle_middle, null);
            return new BasketRecyclerAdapter.Basket_Recycle_Middle(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return BasketItem.size()+1;
    }

    class Basket_Recycle_Header extends RecyclerView.ViewHolder {

        public Basket_Recycle_Header(View itemView) {
            super(itemView);

        }
    }

    class Basket_Recycle_Middle extends RecyclerView.ViewHolder {

        ImageView image, level;
        TextView title, subtitle, time;

        public Basket_Recycle_Middle(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recommend_image);
            GradientDrawable drawable=(GradientDrawable) mContext.getDrawable(R.drawable.background_rounding);
            image.setBackground(drawable);
            image.setClipToOutline(true);
            level = itemView.findViewById(R.id.recommend_level);
            title = itemView.findViewById(R.id.recommend_title);
            subtitle = itemView.findViewById(R.id.recommend_subtitle);
            time = itemView.findViewById(R.id.recommend_time);
        }
    }
}
