package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * HttpConnection.java
 *
 * @author Yongju Jang
 * @version 1.0.0
 * @since 2019-07-05
 **/

public class HttpConnection extends AsyncTask<String, String, String> {
    private Context mContext;
    private Exception e;
    private String sUrl;
    private JSONObject jsonObj;
    private HttpURLConnection conn;
    private ImageView scrap_image;

    public HttpConnection(Context mContext, String sUrl, JSONObject josnObj) {
        this.sUrl = sUrl;
        this.jsonObj = josnObj;
        this.mContext = mContext;
    }

    public HttpConnection(Context mContext, String sUrl, JSONObject josnObj, ImageView scrap_image) {
        this.sUrl = sUrl;
        this.jsonObj = josnObj;
        this.mContext = mContext;
        this.scrap_image = scrap_image;
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
        if(sUrl.equals("AddClipping")) {
            if (receiveMsg.equals("exist"))
                publishProgress("스크랩 취소 하였습니다.", "cancel", sUrl);
            else
                publishProgress("스크랩 하였습니다.", "add", sUrl);
        }
        else if(sUrl.equals("ConfirmClipping")) {
            if (receiveMsg.equals("exist"))
                publishProgress("", "cancel", sUrl);
            else
                publishProgress("", "add", sUrl);
        }
        else if(sUrl.equals("DeleteComment")){
            if(receiveMsg.equals("delete complete")){
                publishProgress("댓글을 삭제하였습니다.", "", sUrl);
            }
        }

        return receiveMsg;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(e != null){
            ExceptionHandling exceptHandling = new ExceptionHandling(e, mContext,"인터넷 연결이 불안정 합니다. \n인터넷 연결 상태를 확인 후 어플리케이션을 재실행 하십시오.");
            exceptHandling.StartingExceptionDialog();
            return;
        }
        if(sUrl.equals("payment")){
            JSONObject jsonObject;
            Intent intent = new Intent(mContext, ActivityPaymentWebView.class);
            try {
                jsonObject = new JSONObject(s);
                intent.putExtra("sUrl", jsonObject.getString("next_redirect_app_url"));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            mContext.startActivity(intent);
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if(values[2].equals("AddClipping")) {
            Toast.makeText(mContext, values[0], Toast.LENGTH_SHORT).show();
            if (values[1].equals("cancel"))
                scrap_image.setImageResource(R.drawable.scrap);
            else
                scrap_image.setImageResource(R.drawable.scrapcancel);
        }
        else if(values[2].equals("ConfirmClipping")) {
            if (values[1].equals("cancel"))
                scrap_image.setImageResource(R.drawable.scrapcancel);
            else
                scrap_image.setImageResource(R.drawable.scrap);
        }
        else if(values[2].equals("DeleteComment")) {
            Toast.makeText(mContext, values[0], Toast.LENGTH_SHORT).show();
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
