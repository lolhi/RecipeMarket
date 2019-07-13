package com.rmarket.recipemarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;

public class SplashActivity extends AppCompatActivity {
    private ISessionCallback iSessionCallback;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iSessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                Log.e("ISessionCallback ::","start Ma");
                goToMainActivity();
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                Log.e("ISessionCallback ::", "start lo");
                redirectToLoginActivity();
            }
        };

        Session.getCurrentSession().addCallback(iSessionCallback);

        findViewById(R.id.splash).postDelayed(()-> {
            if (!Session.getCurrentSession().checkAndImplicitOpen()) {
                Log.e("postDelayed ::", "call redir");
                redirectToLoginActivity();
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(iSessionCallback);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void redirectToLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}