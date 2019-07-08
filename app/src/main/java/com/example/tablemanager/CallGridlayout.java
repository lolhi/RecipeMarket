package com.example.tablemanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class CallGridlayout extends LinearLayout{
    private View childView;

    public CallGridlayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public CallGridlayout(Context context) {
        super(context);

        init(context);
    }
    private void init(Context context){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        childView = inflater.inflate(R.layout.gridlayout,this,true);
    }

    public View getChildView() {
        return childView;
    }
}
