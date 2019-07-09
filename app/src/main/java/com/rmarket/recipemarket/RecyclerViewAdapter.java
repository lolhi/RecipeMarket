package com.rmarket.recipemarket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<SearchCategoryItem> arrList;

    public RecyclerViewAdapter(ArrayList<SearchCategoryItem> arrList) {
        this.arrList = arrList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_listview_item, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        SearchCategoryItem item = arrList.get(position);

        recyclerViewHolder.tvName.setText(item.getCategoryName());
        recyclerViewHolder.imgIcon.setImageResource(item.getImgIcon());
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvName;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = (ImageView) itemView.findViewById(R.id.imageview_icon);
            tvName = (TextView) itemView.findViewById(R.id.tv_category_name);
        }
    }
}
