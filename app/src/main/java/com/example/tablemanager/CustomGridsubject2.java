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

public class CustomGridsubject2 extends BaseAdapter {
    ArrayList<SearchCategoryItem> arrList = new ArrayList<>();
    private Context mContext;

    public CustomGridsubject2(Context mContext, ArrayList<SearchCategoryItem> arrList) {
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


            grid = inflater.inflate(R.layout.search_listview_item2, null);
            TextView title = (TextView) grid.findViewById(R.id.tv_category_name1);
            ImageView image = (ImageView) grid.findViewById(R.id.imageview_icon1);
            //받아온 parametar를 xml id에 연동
            title.setText(arrList.get(position).getCategoryName());
            Glide.with(mContext).load(arrList.get(position).getImgIcon()).into(image);


        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
