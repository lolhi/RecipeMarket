package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ShoppingActivity extends Fragment {

    Context mContext;

       LinearLayout shopping1,shopping2,shopping3;
    public static ShoppingActivity newInstance() {
        return new ShoppingActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false); // 여기서 UI를 생성해서 View를 return

        shopping1 =view.findViewById(R.id.linear_shopping1);
        shopping2 =view.findViewById(R.id.linear_shopping2);
        shopping3 =view.findViewById(R.id.linear_shopping3);


       shopping1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShoppingDetail.class);

                getActivity().startActivity(intent);
            }
        });

        shopping2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), shopping_detail2.class);

                getActivity().startActivity(intent);
            }
        });

        shopping3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(mContext, "판매자와 협의중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
