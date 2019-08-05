package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ShoppingActivity extends Fragment {

    Context mContext;

       LinearLayout shopping1,shopping2,shopping3;
       ImageView userimport;
    public static ShoppingActivity newInstance() {
        return new ShoppingActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false); // 여기서 UI를 생성해서 View를 return

        ViewPager viewPager = view.findViewById(R.id.fragment_shopping_viewpager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
        for (int i = 0; i < 2; i++) {
            ViewPagerFragment1 ViewPager = new ViewPagerFragment1(R.layout.fragment_shopping_viewpager, R.id.iv_shopping_viewpager_product);
            Bundle bundle = new Bundle();
            bundle.putInt("imgurl", R.drawable.banner1);
            ViewPager.setArguments(bundle);
            fragmentAdapter.addItem(ViewPager);
        }
        fragmentAdapter.notifyDataSetChanged();


        shopping1 =view.findViewById(R.id.linear_shopping1);
        shopping2 =view.findViewById(R.id.linear_shopping2);
        shopping3 =view.findViewById(R.id.linear_shopping3);
        userimport =view.findViewById(R.id.userimport);

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

                Toast.makeText(getActivity(), "판매자와 협의중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        userimport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ActivityUserImport.class);

                getActivity().startActivity(intent);
            }
        });

        return view;
    }
}
