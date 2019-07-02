/*
*MADE : JH(ektj1@naver.com)
*
*USE : struct home
*
*METHOD : Home_Recycle_Top : use viewPager, viewpager adapter, view Recommend Recipe
*         Home_Recycle_Middle : just Text
*         Home_Recycle_Bottom : use Recycle, view Recommend Recipe
*
 */

package com.example.tablemanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import  androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Home_recycle_Adapter extends RecyclerView.Adapter {
    private Context mContext;


    private final int HEADER = 0;
    private final int MIDDLE = 1;
    private final int BODY = 2;

    List<RecommendItem> items;
    public Home_recycle_Adapter(Context mContext, RecommendItem[] item){
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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_header, null);
            return new Home_Recycle_Header(v);
        }
        else if (viewType == MIDDLE)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_middle, null);
            return new Home_Recycle_Middle(v);
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_bottom, null);
            return new Home_Recycle_Bottom(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof Home_Recycle_Bottom) {

        }

        /*
        if (viewHolder instanceof KnowledgeMiddle) {
            ((Home_Recycle_Middle) viewHolder).text1.setText("Text" + position);
        }
*/

        if(viewHolder instanceof Home_Recycle_Bottom) {
            final RecommendItem item = items.get(position - 2);

//            Drawable drawable=mContext.getResources().getDrawable(item.getImage());
            //((KnowledgeViewHolder)viewHolder).imgImage.setImageResource(item.getImage());
            ((Home_Recycle_Bottom)viewHolder).image.setImageResource(item.getImage());
            ((Home_Recycle_Bottom)viewHolder).title.setText(item.getTitle());
            ((Home_Recycle_Bottom)viewHolder).level.setImageResource(item.getLevel());
            ((Home_Recycle_Bottom)viewHolder).subtitle.setText(item.getSubtitle());

           ((Home_Recycle_Bottom)viewHolder).Recommend_Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Intent intent = new Intent(mContext, RecipeActivity_detail.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size()+2;
    }

    class Home_Recycle_Header extends RecyclerView.ViewHolder{


        public Home_Recycle_Header(View itemView) {
            super(itemView);

        }
    }


    class Home_Recycle_Middle extends RecyclerView.ViewHolder{


        public Home_Recycle_Middle(View itemView) {
            super(itemView);


        }
    }

    class Home_Recycle_Bottom extends RecyclerView.ViewHolder {

        LinearLayout Recommend_Layout;
        ImageView image,level;
        TextView title,subtitle;
        public Home_Recycle_Bottom(View itemView) {
            super(itemView);
            Recommend_Layout = itemView.findViewById(R.id.recommend_layout);
            image = itemView.findViewById(R.id.recommend_image);
            level = itemView.findViewById(R.id.recommend_level);
            title = itemView.findViewById(R.id.recommend_title);
            subtitle = itemView.findViewById(R.id.recommend_subtitle);


        }
    }
}
