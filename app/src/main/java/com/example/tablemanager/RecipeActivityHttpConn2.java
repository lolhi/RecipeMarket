package com.example.tablemanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * HomeActivityHttpConn.java
 *
 * @author Yongju Jang
 * @version 1.0.0
 * @since 2019-07-05
 **/

public class RecipeActivityHttpConn2 extends AsyncTask<String, String, String> {
    private Context context;
    private Exception e;
    private AppCompatDialog progressDialog;
    private String sUrl;
    private FragmentManager fm;
    private ArrayList<RecommendItem> RecommandaArrList = new ArrayList<>();
    private ArrayList<RecommendItem> FullRecipeArrList;
    private FragmentAdapter fragmentAdapter;
    private JSONArray jsonArr;
    private View rootView;
    private CircleAnimIndicator circleAnimIndicator;
    private ImageView[] grid_image = new ImageView[6];
    private ImageView[] grid_level = new ImageView[6];
    private TextView[] grid_title = new TextView[6];
    private TextView[] grid_subtitle = new TextView[6];
    private TextView[] grid_time = new TextView[6];
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

    public RecipeActivityHttpConn2(Context context, String sUrl, FragmentManager fm, ArrayList<RecommendItem> FullRecipeArrList, AppCompatDialog progressDialog, View rootView) {
        this.context = context;
        this.sUrl = sUrl;
        this.fm = fm;
        this.FullRecipeArrList = FullRecipeArrList;
        this.progressDialog = progressDialog;
        this.rootView = rootView;
    }

    public ArrayList<RecommendItem> getRecommandaArrList() {
        return RecommandaArrList;
    }

    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
    }

    public ArrayList<RecommendItem> getFullRecipeArrList() {
        return FullRecipeArrList;
    }

    @Override
    protected void onPreExecute() {
        if (sUrl.equals("TodaySpecialPrice"))
            progressON(context);
    }

    @Override
    protected String doInBackground(String... strings) {
        String str, receiveMsg = "";
        URL url = null;
        try {
            url = new URL(UrlClass.Url + sUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                //연결 제한 시간을 1/1000 초 단위로 지정합니다.
                //0이면 무한 대기입니다.
                conn.setConnectTimeout(10000);

                //읽기 제한 시간을 지정합니다. 0이면 무한 대기합니다.
                //conn.setReadTimeout(0);

                //캐쉬 사용여부를 지정합니다.
                conn.setUseCaches(false);

                //http 연결의 경우 요청방식을 지정할수 있습니다.
                //지정하지 않으면 디폴트인 GET 방식이 적용됩니다.
                conn.setRequestMethod("GET");

                //서버에 요청을 보내가 응답 결과를 받아옵니다.
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                int resCode = conn.getResponseCode();


                if (resCode == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    reader.close();
                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }
            }
        } catch (IOException e) {
            //인터넷 연결 실패에 대한 exception 추가
            this.e = e;
        }
        publishProgress(receiveMsg);
        return receiveMsg;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        try {
            jsonArr = new JSONArray(values[0]);
            fragmentAdapter = new FragmentAdapter(fm);
            if (sUrl.equals("TodaySpecialPrice")) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);

                    RecommandaArrList.add(new RecommendItem(jsonObj.getString("RECIPE_NM_KO"),
                            jsonObj.getString("SUMRY"),
                            jsonObj.getString("COOKING_TIME"),
                            jsonObj.getString("IMG_URL"),
                            jsonObj.getString("LEVEL_NM")));
                }
            } else if (sUrl.equals("FullRecipe")) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);

                    FullRecipeArrList.add(new RecommendItem(jsonObj.getString("RECIPE_NM_KO"),
                            jsonObj.getString("SUMRY"),
                            jsonObj.getString("COOKING_TIME"),
                            jsonObj.getString("IMG_URL"),
                            jsonObj.getString("LEVEL_NM")));
                }
            }

            if (FullRecipeArrList.size() != 0 && RecommandaArrList.size() != 0) {
                // UI Update
                ViewPager viewPager = rootView.findViewById(R.id.recipe_viewpager);
                circleAnimIndicator = rootView.findViewById(R.id.circleAnimIndicator2);
                FragmentAdapter fragmentAdapter = new FragmentAdapter(fm);
                viewPager.setAdapter(fragmentAdapter);
                viewPager.addOnPageChangeListener(mOnPageChangeListener);
                //Indicator 초기화
                this.initIndicaotor();
                // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
                for (int i = 0; i < RecommandaArrList.size(); i++) {
                    ViewPagerFragment ViewPager = new ViewPagerFragment(R.layout.fragment_recipe_viewpager_image, R.id.recipe_viewpager_imageview, progressDialog);
                    Bundle bundle = new Bundle();
                    bundle.putString("imgurl", RecommandaArrList.get(i).getImage());
                    ViewPager.setArguments(bundle);
                    fragmentAdapter.addItem(ViewPager);
                }
                fragmentAdapter.notifyDataSetChanged();

                CallGridlayout callGl = new CallGridlayout(context);
                View childView = callGl.getChildView();
                SetFindViewById(childView);
                for (int i = 0; i < 6; i++) {
                    GlideApp.with(context).load(FullRecipeArrList.get(i).getImage()).into(grid_image[i]);
                    int levelImg = FullRecipeArrList.get(i).getLevel().equals("초보환영") ? R.drawable.level_low : FullRecipeArrList.get(i).getLevel().equals("보통") ? R.drawable.level_middle : R.drawable.level_hight;
                    grid_level[i].setImageResource(levelImg);
                    grid_subtitle[i].setText(FullRecipeArrList.get(i).getSubtitle());
                    grid_time[i].setText(FullRecipeArrList.get(i).getTime());
                    grid_title[i].setText(FullRecipeArrList.get(i).getTitle());
                }
                LinearLayout con = rootView.findViewById(R.id.con);
                con.addView(callGl);

            } else {
                RecipeActivityHttpConn2 http2 = new RecipeActivityHttpConn2(context, "TodaySpecialPrice", fm, this.getFullRecipeArrList(), progressDialog, rootView);
                http2.execute();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);
    }

    public void progressON(Context mContext) {
        if (((Activity) mContext).isFinishing()) {
            return;
        }

        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.show();

        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.iv_frame_loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });
    }

    private void initIndicaotor() {

        //원사이의 간격
        circleAnimIndicator.setItemMargin(15);
        //애니메이션 속도
        circleAnimIndicator.setAnimDuration(300);
        //indecator 생성
        circleAnimIndicator.createDotPanel(RecommandaArrList.size(), R.drawable.dot_no, R.drawable.dot_color);
    }

    private void SetFindViewById(View childView) {
        grid_image[0] = childView.findViewById(R.id.grid_image0);
        grid_image[1] = childView.findViewById(R.id.grid_image1);
        grid_image[2] = childView.findViewById(R.id.grid_image2);
        grid_image[3] = childView.findViewById(R.id.grid_image3);
        grid_image[4] = childView.findViewById(R.id.grid_image4);
        grid_image[5] = childView.findViewById(R.id.grid_image5);

        grid_level[0] = childView.findViewById(R.id.grid_level0);
        grid_level[1] = childView.findViewById(R.id.grid_level1);
        grid_level[2] = childView.findViewById(R.id.grid_level2);
        grid_level[3] = childView.findViewById(R.id.grid_level3);
        grid_level[4] = childView.findViewById(R.id.grid_level4);
        grid_level[5] = childView.findViewById(R.id.grid_level5);

        grid_title[0] = childView.findViewById(R.id.grid_title0);
        grid_title[1] = childView.findViewById(R.id.grid_title1);
        grid_title[2] = childView.findViewById(R.id.grid_title2);
        grid_title[3] = childView.findViewById(R.id.grid_title3);
        grid_title[4] = childView.findViewById(R.id.grid_title4);
        grid_title[5] = childView.findViewById(R.id.grid_title5);

        grid_subtitle[0] = childView.findViewById(R.id.grid_subtitle0);
        grid_subtitle[1] = childView.findViewById(R.id.grid_subtitle1);
        grid_subtitle[2] = childView.findViewById(R.id.grid_subtitle2);
        grid_subtitle[3] = childView.findViewById(R.id.grid_subtitle3);
        grid_subtitle[4] = childView.findViewById(R.id.grid_subtitle4);
        grid_subtitle[5] = childView.findViewById(R.id.grid_subtitle5);

        grid_time[0] = childView.findViewById(R.id.grid_time0);
        grid_time[1] = childView.findViewById(R.id.grid_time1);
        grid_time[2] = childView.findViewById(R.id.grid_time2);
        grid_time[3] = childView.findViewById(R.id.grid_time3);
        grid_time[4] = childView.findViewById(R.id.grid_time4);
        grid_time[5] = childView.findViewById(R.id.grid_time5);
    }
}
