package com.example.tablemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class Recipe_Recycle_Adapter  extends RecyclerView.Adapter{

    private Context mContext;


    private final int HEADER = 0;
    private final int MIDDLE = 1;
    private final int BODY = 2;

    List<RecommendItem> items;

    public Recipe_Recycle_Adapter(Context mContext, RecommendItem[] item){
        this.mContext = mContext;
        this.items = Arrays.asList(item);

    }

    @Override
    public int getItemViewType(int position){

        if(position == 0)
        return HEADER;
        else if (position==1)
            return MIDDLE;
        else
            return BODY;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recipe_header, null);
            return new Recipe_Recycle_Adapter.Recipe_Recycle_Header(v);
        }
        else if (viewType == MIDDLE)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recipe_middle, null);
            return new Recipe_Recycle_Adapter.Recipe_Recycle_Middle(v);
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recipe_bottom, null);
            return new Recipe_Recycle_Adapter.Recipe_Recycle_Bottom(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount()
        { return  3;
//                items.size()+2;
        }

    class Recipe_Recycle_Header extends RecyclerView.ViewHolder{


        public Recipe_Recycle_Header(View itemView) {
            super(itemView);

        }
    }


    class Recipe_Recycle_Middle extends RecyclerView.ViewHolder{


        public Recipe_Recycle_Middle(View itemView) {
            super(itemView);


        }
    }

    class Recipe_Recycle_Bottom extends RecyclerView.ViewHolder {


        GridView grid ;

        public Recipe_Recycle_Bottom(View itemView) {
            super(itemView);
            grid = itemView.findViewById(R.id.grid);
//            CustomGrid adapter = new CustomGrid(mContext, title, imageId,level,subtitle,time);




        }
    }
}
