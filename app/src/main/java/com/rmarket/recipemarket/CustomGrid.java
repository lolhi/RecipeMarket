package com.rmarket.recipemarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.app.AppCompatDialog;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class CustomGrid extends BaseAdapter {

    private Context mContext;
    private ArrayList<RecommendItem> arrList;
    private AppCompatDialog progressDialog;
    private final RequestManager glide;

    public CustomGrid(Context mContext, ArrayList<RecommendItem> arrList, AppCompatDialog progressDialog, RequestManager glide) {
        this.mContext = mContext;
        this.arrList = arrList;
        this.progressDialog = progressDialog;
        this.glide = glide;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arrList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        int levelImg;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = new GridItem(mContext, progressDialog, glide);
        }
        ((GridItem) convertView).setData(arrList.get(position));

        return convertView;
    }
}
