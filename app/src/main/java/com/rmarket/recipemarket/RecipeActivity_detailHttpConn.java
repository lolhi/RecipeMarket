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
 * RecipeActivity_detailHttpConn.java
 *
 * @author Yongju Jang
 * @version 1.0.0
 * @since 2019-07-05
 **/

public class RecipeActivity_detailHttpConn extends AsyncTask<String, Void, String> {
    private Context context;
    private Exception e;
    private AppCompatDialog progressDialog;
    private String sUrl;
    private RecommendItem recommandItem;
    private ArrayList<Materialitem> MaterialArrList;
    private ArrayList<ProcessItem> ProcessArrList = new ArrayList<>();
    private RecyclerView detail_recycle;
    private JSONArray jsonArr;
    private RecipeActivity_detailHttpConn httpConn;
    private HttpURLConnection conn;

    public RecipeActivity_detailHttpConn(Context context, String sUrl, RecyclerView detail_recycle, RecommendItem recommandItem,ArrayList<Materialitem> MaterialArrList, AppCompatDialog progressDialog) {
        this.context = context;
        this.sUrl = sUrl;
        this.detail_recycle = detail_recycle;
        this.recommandItem = recommandItem;
        this.MaterialArrList = MaterialArrList;
        this.progressDialog = progressDialog;
    }

    @Override
    protected void onPreExecute() {
        if (sUrl.contains("GetMaterial"))
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
            if (sUrl.contains("GetMaterial")) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);

                    MaterialArrList.add(new Materialitem(jsonObj.getString("IRDNT_SN"),
                            jsonObj.getString("IRDNT_NM"),
                            jsonObj.getString("IRDNT_CPCTY"),
                            jsonObj.getString("IRDNT_TY_NM")));
                }
            } else if (sUrl.contains("GetProcess")) {
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);

                    ProcessArrList.add(new ProcessItem(Integer.parseInt(jsonObj.getString("COOKING_NO")),
                            jsonObj.getString("COOKING_DC"),
                            jsonObj.getString("STRE_STEP_IMAGE_URL"),
                            jsonObj.getString("STEP_TIP")));
                }
            }

            if (MaterialArrList.size() != 0 && ProcessArrList.size() != 0) {
                Recipe_Detail_Recle_Adapter adapter = new Recipe_Detail_Recle_Adapter(context, recommandItem, MaterialArrList, ProcessArrList,  progressDialog);
                detail_recycle.setAdapter(adapter);
                detail_recycle.setItemAnimator(new DefaultItemAnimator());
                adapter.notifyDataSetChanged();
            }
            else{
                httpConn = new RecipeActivity_detailHttpConn(context,"GetProcess/" + recommandItem.getId(), detail_recycle, recommandItem,MaterialArrList,progressDialog);
                httpConn.execute();
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
