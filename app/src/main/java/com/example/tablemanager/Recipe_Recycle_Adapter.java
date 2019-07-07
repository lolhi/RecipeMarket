package com.example.tablemanager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

public class Recipe_Recycle_Adapter  extends RecyclerView.Adapter{

    private Context mContext;


    private final int HEADER = 0;
    private final int MIDDLE = 1;
    private final int BODY = 2;

    List<RecommendItem> items;
    JSONArray jsonArr;
    FragmentAdapter fragmentAdapter;
    private CircleAnimIndicator circleAnimIndicator;

    public Recipe_Recycle_Adapter(Context mContext, RecommendItem[] item, JSONArray jsonArr,  FragmentAdapter fragmentAdapter){
        this.mContext = mContext;
        this.items = Arrays.asList(item);
        this.jsonArr = jsonArr;
        this.fragmentAdapter = fragmentAdapter;
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
        if(holder instanceof Recipe_Recycle_Adapter.Recipe_Recycle_Header) {
            // ViewPager와  FragmentAdapter 연결
            ((Recipe_Recycle_Adapter.Recipe_Recycle_Header)holder).viewPager.setAdapter(fragmentAdapter);
            ((Recipe_Recycle_Adapter.Recipe_Recycle_Header)holder).viewPager.addOnPageChangeListener(mOnPageChangeListener);
            //Indicator 초기화
            this.initIndicaotor();
            // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
            for (int i = 0; i < jsonArr.length(); i++) {
                ViewPagerFragment ViewPager = new ViewPagerFragment(R.layout.fragment_recipe_viewpager_image, R.id.recipe_viewpager_imageview, new AppCompatDialog(mContext));
                Bundle bundle = new Bundle();
                try {
                    bundle.putString("imgurl", jsonArr.getJSONObject(i).getString("IMG_URL"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ViewPager.setArguments(bundle);
                fragmentAdapter.addItem(ViewPager);
            }
            fragmentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
            return items.size() + 2;
    }

    class Recipe_Recycle_Header extends RecyclerView.ViewHolder{
        ViewPager viewPager;

        public Recipe_Recycle_Header(View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.recipe_viewpager);
            circleAnimIndicator = itemView.findViewById(R.id.circleAnimIndicator2);
        }
    }


    class Recipe_Recycle_Middle extends RecyclerView.ViewHolder{


        public Recipe_Recycle_Middle(View itemView) {
            super(itemView);
        }
    }

    class Recipe_Recycle_Bottom extends RecyclerView.ViewHolder {
        GridView grid;

        public Recipe_Recycle_Bottom(View itemView) {
            super(itemView);
            grid = itemView.findViewById(R.id.grid);
//            CustomGrid adapter = new CustomGrid(mContext, title, imageId,level,subtitle,time);
        }
    }

    private void initIndicaotor(){

        //원사이의 간격
        circleAnimIndicator.setItemMargin(15);
        //애니메이션 속도
        circleAnimIndicator.setAnimDuration(300);
        //indecator 생성
        circleAnimIndicator.createDotPanel(jsonArr.length(), R.drawable.dot_no , R.drawable.dot_color);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            circleAnimIndicator.selectDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
}
