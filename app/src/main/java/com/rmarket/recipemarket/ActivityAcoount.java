package com.rmarket.recipemarket;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityAcoount extends AppCompatActivity {
    Context mContext;
    ImageView back;
    LinearLayout account_exit;  //계정탈퇴
    LinearLayout account_logout;    //로그아웃

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acoount);
        mContext = this;

        account_logout = findViewById(R.id.account_logout); // 로그아웃 클릭이벤트 및 아이디 연결
        account_logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String appendMessage = "레시피마켓에서 로그아웃 하시겠습니까?";
                new AlertDialog.Builder(mContext)
                        .setMessage(appendMessage)
                        .setPositiveButton(getString(R.string.com_kakao_ok_button),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                                            @Override
                                            public void onCompleteLogout() {
                                                goToLoginActivity();
                                            }

                                            @Override
                                            public void onSessionClosed(ErrorResult errorResult) {
                                                super.onSessionClosed(errorResult);
                                                Toast.makeText(mContext, "로그인 상태가 아닙니다. 로그인 후 시도해주세요.", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onNotSignedUp() {
                                                super.onNotSignedUp();
                                                Toast.makeText(mContext, "로그인 상태가 아닙니다. 로그인 후 시도해주세요.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton(getString(R.string.com_kakao_cancel_button),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
            }
        });

        account_exit = findViewById(R.id.account_exit); // 계정탈퇴 클릭이벤트 및 아이디 연결
        account_exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String appendMessage = "레시피마켓에서 탈퇴하시겠습니까?\n레시피 마켓에서 탈퇴하시면 지금까지 설정했던 모든 정보를 잃게 됩니다.";
                new AlertDialog.Builder(mContext)
                        .setMessage(appendMessage)
                        .setPositiveButton(getString(R.string.com_kakao_ok_button),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                                            @Override
                                            public void onFailure(ErrorResult errorResult) {
                                                Log.e("onFailure ::", errorResult.toString());
                                            }

                                            @Override
                                            public void onSessionClosed(ErrorResult errorResult) {
                                                Toast.makeText(mContext, "로그인 상태가 아닙니다. 로그인 후 시도해주세요.", Toast.LENGTH_SHORT).show();
                                                Log.e("onSessionClosed ::", errorResult.toString());
                                            }

                                            @Override
                                            public void onNotSignedUp() {
                                                Log.e("onNotSignedUp ::", "Not SignedUp");
                                                Toast.makeText(mContext, "로그인 상태가 아닙니다. 로그인 후 시도해주세요.", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onSuccess(Long userId) {
                                                Log.e("onSuccess ::", "unlink success");
                                                //서버로 데이터 보내서 삭제
                                                JSONObject jsonObj = new JSONObject();
                                                try {
                                                    jsonObj.put("ID" ,userId);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                HttpConnection connPost = new HttpConnection(ActivityAcoount.this,"UnlinkUser",jsonObj);
                                                connPost.execute();
                                                goToLoginActivity();

                                            }
                                        });
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton(getString(R.string.com_kakao_cancel_button),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
            }
        });


        back = findViewById(R.id.account_back);//엑티비티 탈퇴
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(ActivityAcoount.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}