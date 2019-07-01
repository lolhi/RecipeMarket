package com.example.tablemanager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Recipe_Detail_Recle_Adapter extends RecyclerView.Adapter{
    private Context mContext;


    private final int HEADER = 0;
    private final int MIDDLE = 1;
    private final int BODY = 2;
    private final int END =3;

    public Recipe_Detail_Recle_Adapter(Context mContext){
        this.mContext = mContext;
      //  this.items = Arrays.asList(item);

    }

    @Override
    public int getItemViewType(int position){

        if(position == 0)
            return HEADER;
        else if (position==1)
            return MIDDLE;
        else if(position==2)
            return BODY;
        else
            return END;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_top, null);
            return new Recipe_Detail_Recle_Adapter.Recipe_Recycle_Header(v);
        }
        else if (viewType == MIDDLE)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_middle, null);
            return new Recipe_Detail_Recle_Adapter.Recipe_Recycle_Middle(v);
        }
        else if(viewType == BODY) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_bottom, null);
            return new Recipe_Detail_Recle_Adapter.Recipe_Recycle_Bottom(v);
        }

        else  {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_end, null);
            return new Recipe_Detail_Recle_Adapter.Recipe_Recycle_End(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof Home_recycle_Adapter.Home_Recycle_Bottom) {

        }

        /*
        if (viewHolder instanceof KnowledgeMiddle) {
            ((Home_Recycle_Middle) viewHolder).text1.setText("Text" + position);
        }
*/

        if(viewHolder instanceof Recipe_Detail_Recle_Adapter.Recipe_Recycle_Header) {

//
            ((Recipe_Recycle_Header)viewHolder).image.setImageResource(R.drawable.chick);
            ((Recipe_Recycle_Header)viewHolder).level.setImageResource(R.drawable.ic_home_black_24dp);
            ((Recipe_Recycle_Header)viewHolder).title.setText("치킨");
            ((Recipe_Recycle_Header)viewHolder).hash1.setText("#마시쪙");
            ((Recipe_Recycle_Header)viewHolder).hash2.setText("#사줘성현아");
            ((Recipe_Recycle_Header)viewHolder).calorie.setText("550cal");
            ((Recipe_Recycle_Header)viewHolder).subtitle.setText("야식");

        }
    }

    @Override
    public int getItemCount() { return 4; }

    class Recipe_Recycle_Header extends RecyclerView.ViewHolder{

        TextView hash1,hash2,time,calorie,title,subtitle;
        ImageView level,image;
        public Recipe_Recycle_Header(View itemView) {
            super(itemView);
           calorie=itemView.findViewById(R.id.detail_calorie);
            hash1=itemView.findViewById(R.id.detail_hash1);
            hash2=itemView.findViewById(R.id.detail_hash2);
            title=itemView.findViewById(R.id.detail_title);
            time=itemView.findViewById(R.id.detail_time);
            subtitle=itemView.findViewById(R.id.detail_subtile);
            image=itemView.findViewById(R.id.detail_image);
            level=itemView.findViewById(R.id.detail_level);
        }
    }


    class Recipe_Recycle_Middle extends RecyclerView.ViewHolder{


        public Recipe_Recycle_Middle(View itemView) {
            super(itemView);


        }
    }

    class Recipe_Recycle_Bottom extends RecyclerView.ViewHolder {


        public Recipe_Recycle_Bottom(View itemView) {
            super(itemView);


        }
    }

    class Recipe_Recycle_End extends RecyclerView.ViewHolder{


        public Recipe_Recycle_End(View itemView) {
            super(itemView);


        }
    }
}
