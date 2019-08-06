package com.rmarket.recipemarket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MaterialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Materialitem> arrList;
    ArrayList<Ranking_Item> RankingArrList;

    public MaterialAdapter(ArrayList<Materialitem> arrList, ArrayList<Ranking_Item> RankingArrList) {
        this.arrList = arrList;
        this.RankingArrList = RankingArrList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_item, parent, false);
        return new RecyclerViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder1 recyclerViewHolder = (RecyclerViewHolder1) holder;
        Materialitem item = arrList.get(position);
        recyclerViewHolder.material.setText(item.getMeterialName());
        recyclerViewHolder.amount.setText(item.getMeterialCpcty());

        for(int i = 0; i < RankingArrList.size(); i++){
            if(RankingArrList.get(i).getMaterial().equals(item.getMeterialName())){
                if(RankingArrList.get(i).getReduce() > 0.0){
                    recyclerViewHolder.material_layout.setVisibility(View.VISIBLE);
                    recyclerViewHolder.material_reduce.setText(String.format("%.2f",RankingArrList.get(i).getReduce()) + "%");
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public static class RecyclerViewHolder1 extends RecyclerView.ViewHolder {
       TextView material,amount, material_reduce;
        LinearLayout material_layout;

        public RecyclerViewHolder1(@NonNull View itemView) {
            super(itemView);
            material= itemView.findViewById(R.id.material);
            amount = itemView.findViewById(R.id.amount);

            material_reduce = itemView.findViewById(R.id.material_reduce); // 증감률 넣기
            material_layout = itemView.findViewById(R.id.material_layout); // visible 시킬 레이아웃 현제 invisible 로 되있어서

        }
    }
}
