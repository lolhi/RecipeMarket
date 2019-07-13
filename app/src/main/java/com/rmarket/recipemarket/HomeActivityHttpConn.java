package com.rmarket.recipemarket;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

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

public class HomeActivityHttpConn extends AsyncTask<String, Void, String> {
    private Context context;
    private Exception e;
    private AppCompatDialog progressDialog;
    private String sUrl;
    private FragmentManager fm;
    private ArrayList<RecommendItem> RecommandaArrList;
    private ArrayList<NoticeItem> NoticeArrList = new ArrayList<>();
    private RecyclerView home_recycle;
    private FragmentAdapter fragmentAdapter;
    private JSONArray jsonArr;

    public HomeActivityHttpConn(Context context, String sUrl, FragmentManager fm, RecyclerView home_recycle, ArrayList<RecommendItem> RecommandaArrList, AppCompatDialog progressDialog) {
        this.context = context;
        this.sUrl = sUrl;
        this.fm = fm;
        this.home_recycle = home_recycle;
        this.RecommandaArrList = RecommandaArrList;
        this.progressDialog = progressDialog;
    }

    public ArrayList<RecommendItem> getRecommandaArrList() {
        return RecommandaArrList;
    }

    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
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
            trustAllHosts();
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
            HttpURLConnection conn = httpsURLConnection;
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

        return receiveMsg;
    }

    @Override
    protected void onPostExecute(String jsonData) {

        try {
            jsonArr = new JSONArray(jsonData);
            fragmentAdapter = new FragmentAdapter(fm);
            if (sUrl.equals("TodaySpecialPrice")) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);

                    RecommandaArrList.add(new RecommendItem(jsonObj.getString("RECIPE_NM_KO"),
                            jsonObj.getString("SUMRY"),
                            jsonObj.getString("COOKING_TIME"),
                            jsonObj.getString("IMG_URL"),
                            jsonObj.getString("LEVEL_NM"),
                            jsonObj.getString("CALORIE"),
                            jsonObj.getString("RECIPE_ID")));
                }
            } else if (sUrl.equals("GetNotice")) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);

                    NoticeArrList.add(new NoticeItem(jsonObj.getString("NOTICE_TITLE"),
                            jsonObj.getString("NOTICE_IMG"),
                            jsonObj.getString("NOTICE_WRITER"),
                            jsonObj.getString("NOTICE_CONTENTS")));
                }
            }

            if (NoticeArrList.size() != 0 && RecommandaArrList.size() != 0) {
                Home_recycle_Adapter adapter = new Home_recycle_Adapter(context, RecommandaArrList, NoticeArrList, fragmentAdapter, progressDialog);
                home_recycle.setAdapter(adapter);
                home_recycle.setItemAnimator(new DefaultItemAnimator());
                adapter.notifyDataSetChanged();

            } else {
                HomeActivityHttpConn http2 = new HomeActivityHttpConn(context, "GetNotice", fm, home_recycle, this.getRecommandaArrList(), progressDialog);
                http2.execute();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
