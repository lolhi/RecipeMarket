package com.example.tablemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGrid extends BaseAdapter {

    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
    private final String[] subtitleid;
    private final int[] levelid;
    private final String[] timeid;

    public CustomGrid(Context c, String[] web, int[] Imageid, int[] levelid, String[] subtitleid, String[] timeid) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        this.subtitleid = subtitleid;
        this.levelid = levelid;
        this.timeid = timeid;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
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
            TextView time =(TextView)grid.findViewById(R.id.grid_time);

            //받아온 parametar를 xml id에 연동
            subtitle.setText(subtitleid[position]);
            level.setImageResource(levelid[position]);
            title.setText(web[position]);
            image.setImageResource(Imageid[position]);
            time.setText(timeid[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
