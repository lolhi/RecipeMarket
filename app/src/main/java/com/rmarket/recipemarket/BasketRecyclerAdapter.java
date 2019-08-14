package com.rmarket.recipemarket;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class BasketRecyclerAdapter extends RecyclerView.Adapter{
    private Context mContext;
    ArrayList<Basket_Item> BasketItem;

    private final int HEADER = 0;
    private final int MIDDLE = 1;
    private final int BOTTOM = 2;

    public BasketRecyclerAdapter(Context mContext,ArrayList<Basket_Item> BasketItem)
    {
        this.mContext = mContext;
        this.BasketItem = BasketItem;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEADER;
        else if (position == BasketItem.size() + 1)
            return BOTTOM;
        else
            return MIDDLE;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_recycle_top, null);
            return new BasketRecyclerAdapter.Basket_Recycle_Header(v);
        } else if(viewType == MIDDLE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_recycle_middle, null);
            return new BasketRecyclerAdapter.Basket_Recycle_Middle(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_recycle_bottom, null);
            return new BasketRecyclerAdapter.Basket_Recycle_Bottom(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {


        if (viewHolder instanceof Basket_Recycle_Header) {
            ((Basket_Recycle_Header) viewHolder).productTotalCount.setText("" + BasketItem.size());
        } else if (viewHolder instanceof Basket_Recycle_Middle) {
            Basket_Item item = BasketItem.get(position - 1);
            ((Basket_Recycle_Middle) viewHolder).productTitle.setText(item.getProductName());
            ((Basket_Recycle_Middle) viewHolder).productImage.setImageResource(item.getProductImage());
            ((Basket_Recycle_Middle) viewHolder).productCost.setText("" + item.getProductCost());
            ((Basket_Recycle_Middle) viewHolder).productCostUnder.setText("" + (item.getProductCost() * item.getProductCount()));
            ((Basket_Recycle_Middle) viewHolder).deliveryCost.setText("" + item.getDeliverCost());
            ((Basket_Recycle_Middle) viewHolder).deliveryCostUnder.setText("" + item.getDeliverCost());
            ((Basket_Recycle_Middle) viewHolder).productCount.setText("" + item.getProductCount());
            ((Basket_Recycle_Middle) viewHolder).totalCost.setText("" + ((item.getProductCost() * item.getProductCount()) + item.getDeliverCost()));

            ((Basket_Recycle_Middle) viewHolder).productDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "position" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return BasketItem.size()+2;
    }

    class Basket_Recycle_Header extends RecyclerView.ViewHolder {

        TextView productTotalCount;
        public Basket_Recycle_Header(View itemView) {
            super(itemView);
                productTotalCount = itemView.findViewById(R.id.basketTotalProduct);
        }
    }

    class Basket_Recycle_Middle extends RecyclerView.ViewHolder {

        TextView  productCount,deliveryCost,productCost,productCostUnder,deliveryCostUnder,shopName,totalCost,productTitle;
        ImageView productImage,productDel;
        CheckBox productCheck;
        public Basket_Recycle_Middle(View itemView) {
            super(itemView);
            productTitle = itemView.findViewById(R.id.basketRecycleProductName);
            shopName = itemView.findViewById(R.id.basketRecycleShopName); // 판매처이름
            productImage = itemView.findViewById(R.id.basketProductImage);  //상품 이미지
            GradientDrawable drawable=(GradientDrawable) mContext.getDrawable(R.drawable.background_rounding);
            productImage.setBackground(drawable);               //이미지 라운드처리
            productImage.setClipToOutline(true);
            productDel = itemView.findViewById(R.id.basketRecycleDel);      //상품삭제이미지
            productCost = itemView.findViewById(R.id.basketRecycleProductCost); // 상품가격
            productCostUnder = itemView.findViewById(R.id.basketRecycleProductCostUnder); // 하단에 나올 상품 가격
            deliveryCost = itemView.findViewById(R.id.basketRecycleDeliverCost); //배달비
            deliveryCostUnder = itemView.findViewById(R.id.basketRecycleDeliverCostUnder); //하단에 나올 배달비
            productCount = itemView.findViewById(R.id.basketRecycleProductCount); //아이템 수량
            totalCost = itemView.findViewById(R.id.basketRecycleTotalCost);
            productCheck = itemView.findViewById(R.id.basketCheckbox);

        }
    }

    class Basket_Recycle_Bottom extends RecyclerView.ViewHolder {


        public Basket_Recycle_Bottom(View itemView) {
            super(itemView);

        }
    }
}
