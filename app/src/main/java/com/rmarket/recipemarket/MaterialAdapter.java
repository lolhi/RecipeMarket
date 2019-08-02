package com.rmarket.recipemarket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MaterialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Materialitem> arrList;

    public MaterialAdapter(ArrayList<Materialitem> arrList) {
        this.arrList = arrList;
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

    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public static class RecyclerViewHolder1 extends RecyclerView.ViewHolder {
       TextView material,amount;

        public RecyclerViewHolder1(@NonNull View itemView) {
            super(itemView);
            material= itemView.findViewById(R.id.material);
            amount = itemView.findViewById(R.id.amount);

        }
    }
}
