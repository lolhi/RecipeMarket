package com.rmarket.recipemarket;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * RankingRecyclerHttpConn.java
 *
 * @author Yongju Jang
 * @version 1.0.0
 * @since 2019-07-05
 **/

public class RankingRecyclerHttpConn extends AsyncTask<String, Void, String> {
    private Exception e;
    private HttpURLConnection conn;
    private JSONArray jsonArr;
    private String sUrl;
    private ArrayList<Ranking_Item> RankingArrayList = new ArrayList<>();
    private RecyclerView detail_recycle;
    private Context context;
    private Animation aniFlow = null;
    private TextView main_reduce,main_title,main_num;
    private LinearLayout main_anim;


    public RankingRecyclerHttpConn(Context context, String sUrl,  RecyclerView detail_recycle, TextView main_reduce, TextView main_title, TextView main_num, LinearLayout main_anim) {
        this.sUrl = sUrl;
        this.detail_recycle = detail_recycle;
        this.context = context;
        this.main_reduce = main_reduce;
        this.main_title = main_title;
        this.main_num = main_num;
        this.main_anim = main_anim;
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

        return receiveMsg;
    }

    @Override
    protected void onPostExecute(String jsonData) {

        if(e != null){
            ExceptionHandling exceptHandling = new ExceptionHandling(e,context,"인터넷 연결이 불안정 합니다. \n인터넷 연결 상태를 확인 후 어플리케이션을 재실행 하십시오.");
            exceptHandling.StartingExceptionDialog();
            return;
        }

        try {
            jsonArr = new JSONArray(jsonData);
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);

                RankingArrayList.add(new Ranking_Item(jsonObj.getString("PRDLST_NAME"),
                        jsonObj.getDouble("CommonYearReduction")));
            }

            Ranking_Recycle_Adapter mAdapter = new Ranking_Recycle_Adapter(RankingArrayList);
            detail_recycle.setAdapter(mAdapter);

            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    int Num = -1;
                    while (true) {
                        if(Num<5) {
                            Num++;
                            Ranking_Item buffer = (Ranking_Item) RankingArrayList.get(Num);
                            test(buffer,Num);
                        }
                        else {
                            Num = 0;
                            Ranking_Item buffer = (Ranking_Item) RankingArrayList.get(Num);
                            test(buffer,Num);
                        }
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            th.start();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPostExecute(jsonData);
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

    public void test(Ranking_Item buffer,int Num) {
        aniFlow = AnimationUtils.loadAnimation(context, R.anim.ani_flow);
        main_anim.setAnimation(aniFlow);
        main_title.setText(buffer.getMaterial());
        main_num.setText(""+(Num+1));
        main_reduce.setText(String.format("%.2f",buffer.getReduce())+"%");
    }
}
