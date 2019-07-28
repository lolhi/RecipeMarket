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

package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Home_recycle_Adapter extends RecyclerView.Adapter {
    private final int HEADER = 0;
    private final int MIDDLE = 1;
    private final int BOTTOM = 2;
    private final int END  = 3;
    ArrayList<RecommendItem> arrList;
    ArrayList<NoticeItem> NoticeArrList;
    FragmentAdapter fragmentAdapter;
    private Context mContext;
    private AppCompatDialog progressDialog;
    private CircleAnimIndicator circleAnimIndicator;
    private int flag = 0;
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
        else if (position == arrList.size() + 2)
            return END;
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

        }
        else if (viewType == END)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_end, null);
            return new Home_Recycle_End(v);
        }
        else {
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

            ((Home_Recycle_Header) viewHolder).viewPager.setOnTouchListener(new View.OnTouchListener() {
                private float pointX;
                private float pointY;
                private int tolerance = 50;
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch(motionEvent.getAction()){
                        case MotionEvent.ACTION_MOVE:
                            return false; //This is important, if you return TRUE the action of swipe will not take place.
                        case MotionEvent.ACTION_DOWN:
                            pointX = motionEvent.getX();
                            pointY = motionEvent.getY();
                            break;
                        case MotionEvent.ACTION_UP:
                            boolean sameX = pointX + tolerance > motionEvent.getX() && pointX - tolerance < motionEvent.getX();
                            boolean sameY = pointY + tolerance > motionEvent.getY() && pointY - tolerance < motionEvent.getY();
                            if(sameX && sameY){

                            Intent intent = new Intent(mContext, ActivityNotice.class);

                            mContext.startActivity(intent);

//                                Toast.makeText(mContext, "공지사항" + ((Home_Recycle_Header) viewHolder).viewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
                            }
                    }
                    return false;
                }
            });
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
            GlideApp.with(mContext).load(levelImg).into(((Home_Recycle_Bottom) viewHolder).level);
            ((Home_Recycle_Bottom) viewHolder).subtitle.setText(item.getSubtitle());
            ((Home_Recycle_Bottom) viewHolder).time.setText(item.getTime());
        }


    }

    @Override
    public int getItemCount() {
        return arrList.size() + 3;
    }

    private void initIndicaotor() {

        //원사이의 간격
        circleAnimIndicator.setItemMargin(15);
        //애니메이션 속도
        circleAnimIndicator.setAnimDuration(300);
        //indecator 생성
        circleAnimIndicator.createDotPanel(flag ==0 ? NoticeArrList.size() : 0, R.drawable.dot_no, R.drawable.dot_color);
        flag = 1;
    }

    class Home_Recycle_Header extends RecyclerView.ViewHolder {
        ViewPager viewPager;

        public Home_Recycle_Header(View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.viewPager);
            circleAnimIndicator = itemView.findViewById(R.id.circleAnimIndicator);
        }
    }

    class Home_Recycle_End extends RecyclerView.ViewHolder {
        ViewPager viewPager;

        public Home_Recycle_End(View itemView) {
            super(itemView);

        }
    }

    class Home_Recycle_Middle extends RecyclerView.ViewHolder {
        public Home_Recycle_Middle(View itemView) {
            super(itemView);
        }
    }

    class Home_Recycle_Bottom extends RecyclerView.ViewHolder {

        ImageView image, level;
        TextView title, subtitle, time;

        public Home_Recycle_Bottom(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recommend_image);
            GradientDrawable drawable=(GradientDrawable) mContext.getDrawable(R.drawable.background_rounding);
            image.setBackground(drawable);
            image.setClipToOutline(true);
            level = itemView.findViewById(R.id.recommend_level);
            title = itemView.findViewById(R.id.recommend_title);
            subtitle = itemView.findViewById(R.id.recommend_subtitle);
            time = itemView.findViewById(R.id.recommend_time);
        }
    }
}
