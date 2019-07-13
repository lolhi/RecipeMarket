package com.rmarket.recipemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class ShoppingActivity extends Fragment {

    Context mContext;
    ImageView img;
    public static ShoppingActivity newInstance() {
        return new ShoppingActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false); // 여기서 UI를 생성해서 View를 return

       img = view.findViewById(R.id.store_main);
       img.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), ShoppingDetail.class);

               getActivity().startActivity(intent);
           }
       });

        return view;
    }
}
