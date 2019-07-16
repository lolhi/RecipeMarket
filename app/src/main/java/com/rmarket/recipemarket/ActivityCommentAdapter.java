package com.rmarket.recipemarket;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Commetn_Item> arrList;
    private Context mContext;

    public ActivityCommentAdapter(Context mContext, ArrayList<Commetn_Item> arrList) {
        this.arrList = arrList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new RecyclerViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder1 recyclerViewHolder = (RecyclerViewHolder1) holder;
        Commetn_Item item = arrList.get(position);

        recyclerViewHolder.text.setText(item.getCommentText());
        recyclerViewHolder.time.setText(item.getCommetTime());
        recyclerViewHolder.name.setText(item.getCommentName());
        if(!item.getCommentProfile().equals("NoImg"))
            GlideApp.with(mContext).load(item.getCommentProfile()).into(recyclerViewHolder.profile);
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public static class RecyclerViewHolder1 extends RecyclerView.ViewHolder {
       ImageView profile;
       TextView name, time, text;

        public RecyclerViewHolder1(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.comment_profile);
            profile.setBackground(new ShapeDrawable(new OvalShape()));
            profile.setClipToOutline(true);
            name = itemView.findViewById(R.id.comment_name);
            time = itemView.findViewById(R.id.comment_time);
            text = itemView.findViewById(R.id.comment_text);
        }
    }
}
