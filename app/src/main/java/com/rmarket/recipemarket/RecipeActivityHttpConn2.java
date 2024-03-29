package com.rmarket.recipemarket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
    private ArrayList<String> TipArrList = new ArrayList<>();
    private ArrayList<RecommendItem> FullRecipeArrList;
    private FragmentAdapter fragmentAdapter;
    private JSONArray jsonArr;
    private View rootView;
    private CircleAnimIndicator circleAnimIndicator;
    private RecipeActivityHttpConn2 http2;
    private ImageView[] grid_image = new ImageView[6];
    private ImageView[] grid_level = new ImageView[6];
    private TextView[] grid_title = new TextView[6];
    private TextView[] grid_subtitle = new TextView[6];
    private TextView[] grid_time = new TextView[6];
    private LinearLayout[] ll_gridlayout = new LinearLayout[6];
    private ImageView ivBanner;
    private CustomScrollView recipeScrollView;
    private boolean mLockScrollView = false;
    private int position = 6;
    private int idx = 1;
    private HttpURLConnection conn;
    private final RequestManager glide;

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

    public RecipeActivityHttpConn2(Context context, String sUrl, FragmentManager fm, ArrayList<RecommendItem> FullRecipeArrList, AppCompatDialog progressDialog, View rootView, CustomScrollView recipeScrollView, RequestManager glide) {
        this.context = context;
        this.sUrl = sUrl;
        this.fm = fm;
        this.FullRecipeArrList = FullRecipeArrList;
        this.progressDialog = progressDialog;
        this.rootView = rootView;
        this.recipeScrollView = recipeScrollView;
        this.glide = glide;
    }

    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
    }

    public ArrayList<RecommendItem> getFullRecipeArrList() {
        return FullRecipeArrList;
    }

    @Override
    protected void onPreExecute() {
        if (sUrl.equals("FullRecipe"))
            progressON(context);
    }

    @Override
    protected String doInBackground(String... strings) {
        String str, receiveMsg = "";
        URL url = null;
        try {
            url = new URL(UrlClass.Url + sUrl);
            trustAllHosts();
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
            conn = httpsURLConnection;
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
        finally {
            if(conn != null)
                conn.disconnect();
        }
        publishProgress(receiveMsg);
        return receiveMsg;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        try {
            jsonArr = new JSONArray(values[0]);
            fragmentAdapter = new FragmentAdapter(fm);
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);

                FullRecipeArrList.add(new RecommendItem(jsonObj.getString("RECIPE_NM_KO"),
                        jsonObj.getString("SUMRY"),
                        jsonObj.getString("COOKING_TIME"),
                        jsonObj.getString("IMG_URL"),
                        jsonObj.getString("LEVEL_NM"),
                        jsonObj.getString("CALORIE"),
                        jsonObj.getString("RECIPE_ID")));
            }

            TipArrList.add(UrlClass.Url + "img/viewtip1.png");
            TipArrList.add(UrlClass.Url + "img/viewtip2.png");
            TipArrList.add(UrlClass.Url + "img/viewtip3.png");
            TipArrList.add(UrlClass.Url + "img/viewtip4.png");
            TipArrList.add(UrlClass.Url + "img/viewtip5.png");

            // UI Update
            final ViewPager viewPager = rootView.findViewById(R.id.recipe_viewpager);
            circleAnimIndicator = rootView.findViewById(R.id.circleAnimIndicator2);
            FragmentAdapter fragmentAdapter = new FragmentAdapter(fm);
            viewPager.setAdapter(fragmentAdapter);
            viewPager.addOnPageChangeListener(mOnPageChangeListener);
            //Indicator 초기화
            this.initIndicaotor();
            // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
            for (int i = 0; i < TipArrList.size(); i++) {
                ViewPagerFragment ViewPager = new ViewPagerFragment(R.layout.fragment_recipe_viewpager_image, R.id.recipe_viewpager_imageview, progressDialog, glide);
                Bundle bundle = new Bundle();
                bundle.putString("imgurl", TipArrList.get(i));
                ViewPager.setArguments(bundle);
                fragmentAdapter.addItem(ViewPager);
            }
            fragmentAdapter.notifyDataSetChanged();

            CallGridlayout callGl = new CallGridlayout(context);
            View childView = callGl.getChildView();
            SetFindViewById(childView);
            for (int i = 0; i < 6; i++) {
                glide.load(FullRecipeArrList.get(i).getImage()).into(grid_image[i]);
                int levelImg = FullRecipeArrList.get(i).getLevel().equals("초보환영") ? R.drawable.level_low : FullRecipeArrList.get(i).getLevel().equals("보통") ? R.drawable.level_middle : R.drawable.level_hight;
                //grid_level[i].setImageResource(levelImg);
                glide.load(levelImg).into(grid_level[i]);
                grid_subtitle[i].setText(FullRecipeArrList.get(i).getSubtitle());
                grid_time[i].setText(FullRecipeArrList.get(i).getTime());
                grid_title[i].setText(FullRecipeArrList.get(i).getTitle());
                ll_gridlayout[i].setTag(i);
                ll_gridlayout[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int tag = (Integer) view.getTag();

                        Intent intent = new Intent(context, RecipeActivity_detail.class);
                        intent.putExtra("RecommandItem", FullRecipeArrList.get(tag));
                        context.startActivity(intent);
                    }

                });
            }
            LinearLayout con = rootView.findViewById(R.id.con);
            con.addView(callGl);

            recipeScrollView.setScrollViewListener(new ScrollViewListener() {

                @Override
                public void onScrollChanged(CustomScrollView scrollView, int x, int y, int oldx, int oldy) {
                    final View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
                    int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

                    if (diff == 0 && mLockScrollView == false) { // 스크롤 bottom
                        int length = position + 6;
                        CallGridlayout callGl = new CallGridlayout(context);
                        View childView = callGl.getChildView();
                        SetFindViewById(childView);

                        switch (idx) {
                            case 0:
                                //ivBanner.setImageResource(R.drawable.banner1);
                                glide.load(R.drawable.banner1).into(ivBanner);
                                idx += 1;
                                break;
                            case 1:
                                //ivBanner.setImageResource(R.drawable.banner2);
                                glide.load(R.drawable.banner2).into(ivBanner);
                                idx += 1;
                                break;
                            case 2:
                                //ivBanner.setImageResource(R.drawable.banner3);
                                glide.load(R.drawable.banner3).into(ivBanner);
                                idx = 0;
                                break;
                        }
                        for (int i = 0; position < length; i++, position++) {
                            glide.load(FullRecipeArrList.get(position).getImage()).into(grid_image[i]);
                            int levelImg = FullRecipeArrList.get(position).getLevel().equals("초보환영") ? R.drawable.level_low : FullRecipeArrList.get(position).getLevel().equals("보통") ? R.drawable.level_middle : R.drawable.level_hight;
                            //grid_level[i].setImageResource(levelImg);
                            glide.load(levelImg).into(grid_level[i]);
                            grid_subtitle[i].setText(FullRecipeArrList.get(position).getSubtitle());
                            grid_time[i].setText(FullRecipeArrList.get(position).getTime());
                            grid_title[i].setText(FullRecipeArrList.get(position).getTitle());
                            ll_gridlayout[i].setTag(position);
                            ll_gridlayout[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int tag = (Integer) view.getTag();

                                    Intent intent = new Intent(context, RecipeActivity_detail.class);
                                    intent.putExtra("RecommandItem", FullRecipeArrList.get(tag));
                                    context.startActivity(intent);
                                }
                            });
                        }
                        LinearLayout con = view.findViewById(R.id.con);
                        con.addView(callGl);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);
        if(e != null){
            ExceptionHandling exceptHandling = new ExceptionHandling(e,context,"인터넷 연결이 불안정 합니다. \n인터넷 연결 상태를 확인 후 어플리케이션을 재실행 하십시오.");
            exceptHandling.StartingExceptionDialog();
            return;
        }
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

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void initIndicaotor() {

        //원사이의 간격
        circleAnimIndicator.setItemMargin(15);
        //애니메이션 속도
        circleAnimIndicator.setAnimDuration(300);
        //indecator 생성
        circleAnimIndicator.createDotPanel(TipArrList.size(), R.drawable.dot_no, R.drawable.dot_color);
    }

    private void SetFindViewById(View childView) {
        ivBanner = childView.findViewById(R.id.banner_iv);
        ll_gridlayout[0] = childView.findViewById(R.id.ll_gridlayout0);
        ll_gridlayout[1] = childView.findViewById(R.id.ll_gridlayout1);
        ll_gridlayout[2] = childView.findViewById(R.id.ll_gridlayout2);
        ll_gridlayout[3] = childView.findViewById(R.id.ll_gridlayout3);
        ll_gridlayout[4] = childView.findViewById(R.id.ll_gridlayout4);
        ll_gridlayout[5] = childView.findViewById(R.id.ll_gridlayout5);

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

    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType)
                    throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType)
                    throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
