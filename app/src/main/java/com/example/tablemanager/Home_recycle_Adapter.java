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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class Home_recycle_Adapter extends RecyclerView.Adapter {
    private final int HEADER = 0;
    private final int MIDDLE = 1;
    private final int BOTTOM = 2;
    ArrayList<RecommendItem> arrList;
    ArrayList<NoticeItem> NoticeArrList;
    FragmentAdapter fragmentAdapter;
    private Context mContext;
    private AppCompatDialog progressDialog;
    private CircleAnimIndicator circleAnimIndicator;
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            circleAnimIndicator.selectDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    public Home_recycle_Adapter(Context mContext, ArrayList<RecommendItem> arrList, ArrayList<NoticeItem> NoticeArrList, FragmentAdapter fragmentAdapter, AppCompatDialog progressDialog) {
        this.mContext = mContext;
        this.arrList = arrList;
        this.NoticeArrList = NoticeArrList;
        this.fragmentAdapter = fragmentAdapter;
        this.progressDialog = progressDialog;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0)
            return HEADER;
        else if (position == 1)
            return MIDDLE;
        else
            return BOTTOM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_header, null);
            return new Home_Recycle_Header(v);
        } else if (viewType == MIDDLE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_middle, null);
            return new Home_Recycle_Middle(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_bottom, null);
            return new Home_Recycle_Bottom(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof Home_Recycle_Header) {
            // ViewPager와  FragmentAdapter 연결
            ((Home_Recycle_Header) viewHolder).viewPager.setAdapter(fragmentAdapter);
            ((Home_Recycle_Header) viewHolder).viewPager.addOnPageChangeListener(mOnPageChangeListener);
            //Indicator 초기화
            this.initIndicaotor();

            // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
            for (int i = 0; i < NoticeArrList.size(); i++) {
                ViewPagerFragment ViewPager = new ViewPagerFragment(R.layout.fragment_image, R.id.imageView, progressDialog);
                Bundle bundle = new Bundle();

                bundle.putString("imgurl", NoticeArrList.get(i).getImg());
                ViewPager.setArguments(bundle);
                fragmentAdapter.addItem(ViewPager);
            }
            fragmentAdapter.notifyDataSetChanged();
        }

        /*
        if (viewHolder instanceof KnowledgeMiddle) {
            ((Home_Recycle_Middle) viewHolder).text1.setText("Text" + position);
        }
*/

        if (viewHolder instanceof Home_Recycle_Bottom) {
            final RecommendItem item = arrList.get(position - 2);

            GlideApp.with(mContext).load(item.getImage()).into(((Home_Recycle_Bottom) viewHolder).image);
            ((Home_Recycle_Bottom) viewHolder).title.setText(item.getTitle());
            int levelImg = item.getLevel().equals("초보환영") ? R.drawable.level_low : item.getLevel().equals("보통") ? R.drawable.level_middle : R.drawable.level_hight;
            ((Home_Recycle_Bottom) viewHolder).level.setImageResource(levelImg);
            ((Home_Recycle_Bottom) viewHolder).subtitle.setText(item.getSubtitle());
            ((Home_Recycle_Bottom) viewHolder).time.setText(item.getTime());

            ((Home_Recycle_Bottom) viewHolder).Recommend_Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RecipeActivity_detail.class);
                    intent.putExtra("recipeTitle", item.getTitle());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrList.size() + 2;
    }

    private void initIndicaotor() {

        //원사이의 간격
        circleAnimIndicator.setItemMargin(15);
        //애니메이션 속도
        circleAnimIndicator.setAnimDuration(300);
        //indecator 생성
        circleAnimIndicator.createDotPanel(NoticeArrList.size(), R.drawable.dot_no, R.drawable.dot_color);
    }

    class Home_Recycle_Header extends RecyclerView.ViewHolder {
        ViewPager viewPager;

        public Home_Recycle_Header(View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.viewPager);
            circleAnimIndicator = itemView.findViewById(R.id.circleAnimIndicator);
        }
    }

    class Home_Recycle_Middle extends RecyclerView.ViewHolder {
        public Home_Recycle_Middle(View itemView) {
            super(itemView);
        }
    }

    class Home_Recycle_Bottom extends RecyclerView.ViewHolder {

        LinearLayout Recommend_Layout;
        ImageView image, level;
        TextView title, subtitle, time;

        public Home_Recycle_Bottom(View itemView) {
            super(itemView);
            Recommend_Layout = itemView.findViewById(R.id.recommend_layout);
            image = itemView.findViewById(R.id.recommend_image);


            level = itemView.findViewById(R.id.recommend_level);
            title = itemView.findViewById(R.id.recommend_title);
            subtitle = itemView.findViewById(R.id.recommend_subtitle);
            time = itemView.findViewById(R.id.recommend_time);
        }
    }
}
