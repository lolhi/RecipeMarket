package com.rmarket.recipemarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private SessionCallback sessionCallback;
    private ImageView im_custom_login;
    private LoginButton btn_kakao_login;
    private ImageView im_login_later;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        im_custom_login = (ImageView) findViewById(R.id.im_custom_login);
        btn_kakao_login = (LoginButton) findViewById(R.id.btn_kakao_login);
        im_login_later = (ImageView) findViewById(R.id.login_later_im);

        im_custom_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn_kakao_login.performClick();
            }
        });

        im_login_later.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goToMainActivity();
            }
        });
        sessionCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(sessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    private class SessionCallback implements ISessionCallback {
        // 로그인에 성공한 상태
        @Override
        public void onSessionOpened() {
            requestMe();
        }

        // 로그인에 실패한 상태
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
        }

        // 사용자 정보 요청
        public void requestMe() {
            // 사용자정보 요청 결과에 대한 Callback
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                // 세션 오픈 실패. 세션이 삭제된 경우,
                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());
                }

                // 사용자정보 요청에 성공한 경우,
                @Override
                public void onSuccess(MeV2Response response) {
                    // 서버로 정보 보냄
                    //Create JSONObject here
                    JSONObject jsonParam = new JSONObject();
                    try {
                        jsonParam.put("ID", response.getId());
                        jsonParam.put("NICKNAME", response.getNickname());
                        jsonParam.put("PROFILE_IMG", response.getProfileImagePath() == null ? "NoImg" : response.getProfileImagePath());
                        JSONArray temp4 = new JSONArray();
                        jsonParam.put("RECENT_SEARCH",temp4);
                        JSONArray temp2 = new JSONArray();
                        jsonParam.put("CLIPPING", temp2);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("HttpConnection :: ", jsonParam.toString());
                    HttpConnection httpconn = new HttpConnection(LoginActivity.this, "RegisterUser", jsonParam);
                    httpconn.execute();
                    goToMainActivity();
/*

                    Log.e("SessionCallback :: ", "onSuccess");
                    Log.e("Profile : ", "user id : " + response.getId());
                    Log.e("Profile : ", "nickname: " + response.getNickname());
                    Log.e("Profile : ", "gender: " + response.getKakaoAccount().getGender());
                    Log.e("Profile : ", "profile image: " + response.getProfileImagePath());
                    Log.e("Profile : ", "age: " + response.getKakaoAccount().getAgeRange().getValue());

                UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("UnLinkResponseCallback ", errorResult.toString());
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("UnLinkResponseCallback ", "onSessionClosed");
                    }

                    @Override
                    public void onNotSignedUp() {
                        Log.e("UnLinkResponseCallback ", "onNotSignedUp");
                    }

                    @Override
                    public void onSuccess(Long userId) {
                        Log.e("UnLinkResponseCallback ", "onSuccess");
                    }
                });
*/
                }

                // 사용자 정보 요청 실패
                @Override
                public void onFailure(ErrorResult errorResult) {
                    Log.e("SessionCallback :: ", "onFailure : " + errorResult.getErrorMessage());
                }
            });
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

