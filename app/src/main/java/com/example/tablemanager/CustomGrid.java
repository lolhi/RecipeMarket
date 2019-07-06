package com.example.tablemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomGrid extends BaseAdapter {

    private Context mContext;
    private ArrayList<RecommendItem> arrList;

    public CustomGrid(Context mContext, ArrayList<RecommendItem> arrList) {
        this.mContext = mContext;
        this.arrList = arrList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrList.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        int levelImg;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);

            //xml id 연동
            grid = inflater.inflate(R.layout.search_grid, null);
            TextView subtitle = (TextView) grid.findViewById(R.id.grid_subtitle);
            TextView title = (TextView) grid.findViewById(R.id.grid_title);
            ImageView image = (ImageView)grid.findViewById(R.id.grid_image);
            ImageView level = (ImageView)grid.findViewById(R.id.grid_level);
            TextView time = (TextView)grid.findViewById(R.id.grid_time);

            //받아온 parametar를 xml id에 연동
            subtitle.setText(arrList.get(position).getSubtitle());
            levelImg = arrList.get(position).getLevel().equals("초보환영") ? R.drawable.level_low : arrList.get(position).getLevel().equals("보통") ? R.drawable.level_middle : R.drawable.level_hight;
            level.setImageResource(levelImg);
            title.setText(arrList.get(position).getTitle());
            Glide.with(mContext).load(arrList.get(position).getImage()).into(image);
            time.setText(arrList.get(position).getTime());
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
