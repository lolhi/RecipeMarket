package com.rmarket.recipemarket;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class GridItem extends LinearLayout {
    private TextView subtitle;
    private TextView title;
    private ImageView image;
    private ImageView level;
    private TextView time;
    private Context mContext;
    private AppCompatDialog progressDialog;
    private final RequestManager glide;

    public GridItem(Context context, AppCompatDialog progressDialog, RequestManager glide) {
        super(context);
        this.mContext = context;
        this.progressDialog = progressDialog;
        this.glide = glide;
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_grid, this);

        GradientDrawable drawable= (GradientDrawable) context.getDrawable(R.drawable.background_rounding);

        subtitle = (TextView) findViewById(R.id.grid_subtitle);
        title = (TextView) findViewById(R.id.grid_title);
        image = (ImageView) findViewById(R.id.grid_image);
        image.setBackground(drawable);
        image.setClipToOutline(true);


        level = (ImageView) findViewById(R.id.grid_level);
        time = (TextView) findViewById(R.id.grid_time);
    }

    public void setData(RecommendItem item) {
        subtitle.setText(item.getSubtitle());
        int levelImg = item.getLevel().equals("초보환영") ? R.drawable.level_low : item.getLevel().equals("보통") ? R.drawable.level_middle : R.drawable.level_hight;
        //level.setImageResource(levelImg);
        glide.load(levelImg).into(level);
        title.setText(item.getTitle());
        glide.load(item.getImage()).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressOFF();
                return false;
            }
        }).into(image);
        time.setText(item.getTime());
    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
