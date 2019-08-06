package com.rmarket.recipemarket;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

//여기 detail
public class Recipe_Detail_Recle_Adapter extends RecyclerView.Adapter {
    private final int HEADER = 0;
    private final int MIDDLE = 1;
    private final int BODY = 2;
    private final int END = 3;

    private RecommendItem recommendItem;
    private Context mContext;
    private ArrayList<Materialitem> MaterialArrList;
    private ArrayList<ProcessItem> ProcessArrList;
    private ArrayList<Ranking_Item> RankingArrList;
    private AppCompatDialog progressDialog;

    public Recipe_Detail_Recle_Adapter(Context mContext, RecommendItem recommendItem, ArrayList<Materialitem> MaterialArrList, ArrayList<ProcessItem> ProcessArrList, ArrayList<Ranking_Item> RankingArrList, AppCompatDialog progressDialog) {
        this.mContext = mContext;
        this.recommendItem = recommendItem;
        this.MaterialArrList = MaterialArrList;
        this.ProcessArrList = ProcessArrList;
        this.RankingArrList = RankingArrList;
        Collections.sort(ProcessArrList);
        this.progressDialog = progressDialog;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0)
            return HEADER;
        else if (position == 1)
            return MIDDLE;
        else if (position == 2)
            return BODY;
        else
            return END;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_top, null);
            return new Recipe_Detail_Recle_Adapter.Recipe_Recycle_Header(v);
        } else if (viewType == MIDDLE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_middle, null);
            return new Recipe_Detail_Recle_Adapter.Recipe_Recycle_Middle(v);
        } else if (viewType == BODY) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_bottom, null);
            return new Recipe_Detail_Recle_Adapter.Recipe_Recycle_Bottom(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_end, null);
            return new Recipe_Detail_Recle_Adapter.Recipe_Recycle_End(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof Recipe_Detail_Recle_Adapter.Recipe_Recycle_Middle) {
            for(int i = 0; i < 8 && i < ProcessArrList.size(); i++){
                if(!(ProcessArrList.get(i).getProcessDc().equals(""))){
                    ((Recipe_Recycle_Middle) viewHolder).textView[i].setVisibility(View.VISIBLE);
                    ((Recipe_Recycle_Middle) viewHolder).textView[i].setText(ProcessArrList.get(i).getProcessNum() + "." + ProcessArrList.get(i).getProcessDc());
                }
                if(!(ProcessArrList.get(i).getProcessStepImg().equals(""))){
                    ((Recipe_Recycle_Middle) viewHolder).imgView[i].setVisibility(View.VISIBLE);
                    GlideApp.with(mContext).load(ProcessArrList.get(i).getProcessStepImg()).into(((Recipe_Recycle_Middle) viewHolder).imgView[i]);
                }
            }
        }

        /*
        if (viewHolder instanceof KnowledgeMiddle) {
            ((Home_Recycle_Middle) viewHolder).text1.setText("Text" + position);
        }
*/

        if (viewHolder instanceof Recipe_Detail_Recle_Adapter.Recipe_Recycle_Header) {
            GlideApp.with(mContext).load(recommendItem.getImage()).addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressOFF();
                    return false;
                }
            }).into(((Recipe_Recycle_Header) viewHolder).image);
            int levelImg = recommendItem.getLevel().equals("초보환영") ? R.drawable.level_low : recommendItem.getLevel().equals("보통") ? R.drawable.level_middle : R.drawable.level_hight;
            ((Recipe_Recycle_Header) viewHolder).level.setImageResource(levelImg);
            ((Recipe_Recycle_Header) viewHolder).title.setText(recommendItem.getTitle());
            ((Recipe_Recycle_Header) viewHolder).calorie.setText(recommendItem.getCal());
            ((Recipe_Recycle_Header) viewHolder).subtitle.setText(recommendItem.getSubtitle());
        }
    }

    @Override
    public int getItemCount() {
        return ProcessArrList.size() + 3;
    }

    class Recipe_Recycle_Header extends RecyclerView.ViewHolder {

        TextView hash1, hash2, time, calorie, title, subtitle,material_reduce;
        ImageView level, image;
        RecyclerView materail_recycle;
        MaterialAdapter mAdapter;
        LinearLayout material_layout;

        public Recipe_Recycle_Header(View itemView) {
            super(itemView);

            materail_recycle = itemView.findViewById(R.id.material_recycle);

            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            materail_recycle.setHasFixedSize(true);
            materail_recycle.setLayoutManager(layoutManager);

            mAdapter = new MaterialAdapter(MaterialArrList, RankingArrList);
            materail_recycle.setAdapter(mAdapter);

            calorie = itemView.findViewById(R.id.detail_calorie);
            hash1 = itemView.findViewById(R.id.detail_hash1);
            hash2 = itemView.findViewById(R.id.detail_hash2);
            title = itemView.findViewById(R.id.detail_title);
            time = itemView.findViewById(R.id.detail_time);
            subtitle = itemView.findViewById(R.id.detail_subtile);

            GradientDrawable drawable= (GradientDrawable) mContext.getDrawable(R.drawable.background_rounding);

            image = itemView.findViewById(R.id.detail_image);
            image.setBackground(drawable);
            image.setClipToOutline(true);



            level = itemView.findViewById(R.id.detail_level);
        }
    }


    class Recipe_Recycle_Middle extends RecyclerView.ViewHolder {

            TextView [] textView;
            ImageView [] imgView;
        public Recipe_Recycle_Middle(View itemView) {
            super(itemView);
            textView = new TextView[]{itemView.findViewById(R.id.recipe_text1), itemView.findViewById(R.id.recipe_text2), itemView.findViewById(R.id.recipe_text3), itemView.findViewById(R.id.recipe_text4), itemView.findViewById(R.id.recipe_text5), itemView.findViewById(R.id.recipe_text6), itemView.findViewById(R.id.recipe_text7), itemView.findViewById(R.id.recipe_text8), itemView.findViewById(R.id.recipe_text9), itemView.findViewById(R.id.recipe_text10), itemView.findViewById(R.id.recipe_text11), itemView.findViewById(R.id.recipe_text12), itemView.findViewById(R.id.recipe_text13), itemView.findViewById(R.id.recipe_text14), itemView.findViewById(R.id.recipe_text15)};
            imgView = new ImageView[]{itemView.findViewById(R.id.recipe_image1),itemView.findViewById(R.id.recipe_image2),itemView.findViewById(R.id.recipe_image3),itemView.findViewById(R.id.recipe_image4),itemView.findViewById(R.id.recipe_image5),itemView.findViewById(R.id.recipe_image6),itemView.findViewById(R.id.recipe_image7), itemView.findViewById(R.id.recipe_image8), itemView.findViewById(R.id.recipe_image9), itemView.findViewById(R.id.recipe_image10), itemView.findViewById(R.id.recipe_image11), itemView.findViewById(R.id.recipe_image12), itemView.findViewById(R.id.recipe_image13), itemView.findViewById(R.id.recipe_image14), itemView.findViewById(R.id.recipe_image15)};
        }
    }

    class Recipe_Recycle_Bottom extends RecyclerView.ViewHolder {


        public Recipe_Recycle_Bottom(View itemView) {
            super(itemView);


        }
    }

    class Recipe_Recycle_End extends RecyclerView.ViewHolder {


        public Recipe_Recycle_End(View itemView) {
            super(itemView);


        }
    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
