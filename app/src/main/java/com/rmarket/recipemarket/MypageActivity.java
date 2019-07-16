package com.rmarket.recipemarket;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MypageActivity extends Fragment {


    final ArrayList<RecommendItem> arrList = new ArrayList<>();
    RecyclerView mypage_recycle;
    ImageView basket; //장바구니 이미지
    ImageView userprofile;  //유저 프로필사진
    TextView accountmanager;    //계정관리 텍스트 버튼
    TextView username;      //유저 이름
    JSONArray jsonArr;
    FragmentAdapter fragmentAdapter;
    ImageView deliver;
    ImageView ivClippingFail;

    public static MypageActivity newInstance() {
        return new MypageActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage, container, false); // 여기서 UI를 생성해서 View를 return
        mypage_recycle = view.findViewById(R.id.mypage_recycle);
        basket = view.findViewById(R.id.mypage_basket);
        username = view.findViewById(R.id.mypage_username);
        userprofile = view.findViewById(R.id.mypage_userimage);
        userprofile.setBackground(new ShapeDrawable(new OvalShape()));
        userprofile.setClipToOutline(true);


        accountmanager =view.findViewById(R.id.mypage_accountmanage);
        deliver = view.findViewById(R.id.mypage_deliver);
        ivClippingFail = view.findViewById(R.id.clipping_fail_iv);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        //유저 계정 관리 띄위기
        accountmanager.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityAcoount.class);
                getActivity().startActivity(intent);
            }
        });

        //걍준비중 중간
        deliver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), "준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //장바구니 클릭이벤트
        basket.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityBasket.class);

                getActivity().startActivity(intent);
            }
        });
        mypage_recycle.setHasFixedSize(true);
        mypage_recycle.setLayoutManager(layoutManager);

        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                username.setText("로그인 해주세요"); //유저이름 설정
                mypage_recycle.setVisibility(View.GONE);
                ivClippingFail.setVisibility((View.VISIBLE));
            }

            @Override
            public void onSuccess(MeV2Response result) {
                username.setText(result.getNickname()); //유저이름 설정
                if(result.getProfileImagePath() != null)
                    GlideApp.with(getActivity()).load(result.getProfileImagePath()).into(userprofile);
                mypage_recycle.setVisibility(View.VISIBLE);
                ivClippingFail.setVisibility((View.GONE));
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("ID", result.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MypageActivityHttpConn http = new MypageActivityHttpConn(getActivity(), "GetClipping", new AppCompatDialog(getActivity()), mypage_recycle, jsonObject, ivClippingFail);
                http.execute();

                ItemClickSupport.addTo(mypage_recycle, R.id.home_recycle).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        ArrayList<RecommendItem> RecommandArrList = http.getClippingRecipeArrList();
                        Intent intent = new Intent(getActivity(), RecipeActivity_detail.class);
                        intent.putExtra("RecommandItem", RecommandArrList.get(position));
                        getActivity().startActivity(intent);
                    }
                });

            }
        });



        return view;
    }


}
