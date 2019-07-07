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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;

import java.util.ArrayList;

public class Mypage_Recycle_Adaper extends RecyclerView.Adapter{

    ArrayList<RecommendItem> arrList;
    JSONArray jsonArr;
    Context mContext;
    FragmentAdapter fragmentAdapter;
    private CircleAnimIndicator circleAnimIndicator;

    public Mypage_Recycle_Adaper(Context mContext, ArrayList<RecommendItem> arrList, JSONArray jsonArr,  FragmentAdapter fragmentAdapter)
    {
        this.mContext = mContext;
        this.arrList = arrList;
        this.jsonArr = jsonArr;
        this.fragmentAdapter = fragmentAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_recycle_item, null);
        return new KnowledgeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof KnowledgeViewHolder) {
            final RecommendItem item = arrList.get(position);

            Glide.with(mContext).load(item.getImage()).into(((KnowledgeViewHolder)viewHolder).imgImage);
            ((KnowledgeViewHolder)viewHolder).txtTitle.setText(item.getTitle());
            int levelImg = item.getLevel().equals("초보환영") ? R.drawable.level_low : item.getLevel().equals("보통") ? R.drawable.level_middle : R.drawable.level_hight;
            ((KnowledgeViewHolder)viewHolder).imgLevel.setImageResource(levelImg);
            ((KnowledgeViewHolder)viewHolder).txtSubTitle.setText(item.getSubtitle());
            ((KnowledgeViewHolder)viewHolder).txtTime.setText(item.getTime());

            ((KnowledgeViewHolder)viewHolder).mypage_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RecipeActivity_detail.class);
                    intent.putExtra("recipeTitle",item.getTitle());
                    mContext.startActivity(intent);
                }
            });
            }

    }

    @Override
    public int getItemCount()
    {
        return arrList.size(); }


    class KnowledgeViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtSubTitle,txtTime;
        ImageView imgImage,imgLevel;
        LinearLayout mypage_layout;
        public KnowledgeViewHolder(View itemView) {
            super(itemView);
            mypage_layout = itemView.findViewById(R.id.mypage_layout);
            txtTitle = itemView.findViewById(R.id.mypage_title);
            txtSubTitle = itemView.findViewById(R.id.mypage_subtitle);
            imgImage = itemView.findViewById(R.id.mypage_image);
            imgLevel = itemView.findViewById(R.id.mypage_level);
            txtTime = itemView.findViewById(R.id.mypage_time);



        }
    }

}
