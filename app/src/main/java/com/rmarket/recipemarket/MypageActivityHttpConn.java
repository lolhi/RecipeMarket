package com.rmarket.recipemarket;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
 * MypageActivityHttpConn.java
 *
 * @author Yongju Jang
 * @version 1.0.0
 * @since 2019-07-05
 **/

public class MypageActivityHttpConn extends AsyncTask<String, Integer, String> {
    private Context context;
    private Exception e;
    private AppCompatDialog progressDialog;
    private String sUrl;
    private ArrayList<RecommendItem> ClippingRecipeArrList = new ArrayList<>();
    private JSONArray jsonArr;
    private RecyclerView mypage_recycle;
    private HttpURLConnection conn;
    private JSONObject jsonObj;
    private ImageView ivClippingFail;
    private final RequestManager glide;

    public MypageActivityHttpConn(Context context, String sUrl, AppCompatDialog progressDialog, RecyclerView mypage_recycle, JSONObject jsonObject, ImageView ivClippingFail, RequestManager glide) {
        this.context = context;
        this.sUrl = sUrl;
        this.progressDialog = progressDialog;
        this.mypage_recycle = mypage_recycle;
        this.jsonObj = jsonObject;
        this.ivClippingFail = ivClippingFail;
        this.glide = glide;
    }

    public ArrayList<RecommendItem> getClippingRecipeArrList() {
        return ClippingRecipeArrList;
    }

    @Override
    protected void onPreExecute() {
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
                conn.setRequestMethod("POST");

                //서버에 요청을 보내가 응답 결과를 받아옵니다.
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                conn.setDefaultUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                os.write(jsonObj.toString().getBytes("UTF-8"));
                os.flush();
                os.close();

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

        if(receiveMsg.equals(""))
            publishProgress(View.GONE, View.VISIBLE);
        else
            publishProgress(View.VISIBLE, View.GONE);
        return receiveMsg;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mypage_recycle.setVisibility(values[0]);
        ivClippingFail.setVisibility(values[1]);
    }

    @Override
    protected void onPostExecute(String jsonData) {

        if(e != null){
            ExceptionHandling exceptHandling = new ExceptionHandling(e,context,"인터넷 연결이 불안정 합니다. \n인터넷 연결 상태를 확인 후 어플리케이션을 재실행 하십시오.");
            exceptHandling.StartingExceptionDialog();
            return;
        }

        try {
            if(jsonData.equals("")) {
                progressOFF();
                return;
            }
            jsonArr = new JSONArray(jsonData);

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);

                ClippingRecipeArrList.add(new RecommendItem(jsonObj.getString("RECIPE_NM_KO"),
                        jsonObj.getString("SUMRY"),
                        jsonObj.getString("COOKING_TIME"),
                        jsonObj.getString("IMG_URL"),
                        jsonObj.getString("LEVEL_NM"),
                        jsonObj.getString("CALORIE"),
                        jsonObj.getString("RECIPE_ID")));
            }
            Mypage_Recycle_Adaper adapter = new Mypage_Recycle_Adaper(context, ClippingRecipeArrList, progressDialog, glide);
            mypage_recycle.setAdapter(adapter);
            mypage_recycle.setItemAnimator(new DefaultItemAnimator());
            adapter.notifyDataSetChanged();

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

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
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
