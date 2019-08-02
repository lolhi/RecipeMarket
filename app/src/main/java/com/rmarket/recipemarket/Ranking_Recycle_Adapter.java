package com.rmarket.recipemarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Ranking_Recycle_Adapter extends RecyclerView.Adapter {

    ArrayList<Ranking_Item> arrayList;

    public Ranking_Recycle_Adapter(ArrayList<Ranking_Item> arrayList)
    {
     this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item, parent, false);
        return new RankingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Ranking_Recycle_Adapter.RankingViewHolder recyclerViewHolder = (Ranking_Recycle_Adapter.RankingViewHolder) holder;
        Ranking_Item item = arrayList.get(position);
        recyclerViewHolder.material.setText(item.getMaterial());
        recyclerViewHolder.reduce.setText(item.getReduce()+"%");
        recyclerViewHolder.num.setText(""+(position+1));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RankingViewHolder extends RecyclerView.ViewHolder {
        TextView material,reduce,num;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            material= itemView.findViewById(R.id.detail_ranking_name);
            reduce = itemView.findViewById(R.id.detail_ranking_reduce);
            num = itemView.findViewById(R.id.detail_ranking_num);
        }
    }

}
