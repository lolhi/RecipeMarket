package com.rmarket.recipemarket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URISyntaxException;

public class ActivityPaymentWebView extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_web_view);

        Intent intent = getIntent();
        String sUrl = intent.getStringExtra("sUrl");

        mWebView = (WebView)findViewById(R.id.wb_payment);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.loadUrl(sUrl);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClientClass());
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null && url.startsWith("intent://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                    if (existPackage != null) {
                        startActivity(intent);
                    } else {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                        marketIntent.setData(Uri.parse("market://details?id="+intent.getPackage()));
                        startActivity(marketIntent);
                    }
                    return true;
                }catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (url != null && url.startsWith("market://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    if (intent != null) {
                        startActivity(intent);
                    }
                    return true;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            else if (url != null && url.contains(UrlClass.Url + "readysuccess")){
                Intent intent = new Intent(ActivityPaymentWebView.this, ActivityPaymentComplete.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                ActivityPaymentWebView.this.startActivity(intent);
            }
            view.loadUrl(url);
            return false;
        }
    }

}
