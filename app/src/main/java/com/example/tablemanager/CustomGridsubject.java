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

public class CustomGridsubject extends BaseAdapter {
    private Context mContext;
    ArrayList<SearchCategoryItem> arrList = new ArrayList<>();
  ;
    public CustomGridsubject(Context mContext,  ArrayList<SearchCategoryItem> arrList) {
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
                grid = inflater.inflate(R.layout.search_listview_item, null);
                TextView title = (TextView) grid.findViewById(R.id.tv_category_name);
                ImageView image = (ImageView)grid.findViewById(R.id.imageview_icon);
                //받아온 parametar를 xml id에 연동
                title.setText(arrList.get(position).getCategoryName());
                image.setImageResource(arrList.get(position).getImgIcon());




        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
