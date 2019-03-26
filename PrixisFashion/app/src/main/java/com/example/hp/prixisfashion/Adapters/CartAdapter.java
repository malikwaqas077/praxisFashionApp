package com.example.hp.prixisfashion.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.prixisfashion.CartActivity;
import com.example.hp.prixisfashion.Model.AdminModels.AdminProductModel;
import com.example.hp.prixisfashion.R;


import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<AdminProductModel> modelList;
    private Context context;
    private int subTotal;

    public CartAdapter(List<AdminProductModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
        subTotal = 0;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =    LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item,viewGroup,false);

        CartViewHolder viewHolder=new CartViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {
        cartViewHolder.bind(modelList.get(i));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCartItem;
        TextView tvProductName,tvProductPrice,tvQuantity;
        ImageButton btnAdd,btnSub;
        private int quantity=1;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCartItem=itemView.findViewById(R.id.imgCartItem);
            tvProductName=itemView.findViewById(R.id.tvProductNameCartItem);
            tvProductPrice=itemView.findViewById(R.id.tvProductPriceCartItem);
            tvQuantity=itemView.findViewById(R.id.tvProductQuantityCartItem);
            btnAdd=itemView.findViewById(R.id.btnImgPositiveCart);
            btnSub=itemView.findViewById(R.id.btnImgNegativeCart);

        }

        void bind(final AdminProductModel model){
            String url = checker(model.getProductImageUrl());
            Glide.with(context)
                    .load( url)
                    //.load(model.getImgURL())
                    .into(imgCartItem);


            tvProductName.setText(model.getProductTitle());
            String price="Rs: "+model.getProductPrice()+"/-";
            tvProductPrice.setText(price);
            try {
                tvQuantity.setText("1");
            }catch (Exception e){
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }


            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity=quantity+1;
                    tvQuantity.setText(String.valueOf(quantity));
                    model.setproductQuantity(quantity);
                    ((CartActivity)context).setSubTotal();
                }
            });

            btnSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (quantity>1){
                        quantity=quantity-1;
                        model.setproductQuantity(quantity);
                        tvQuantity.setText(String.valueOf(quantity));
                        ((CartActivity)context).setSubTotal();
                    }
                }
            });


        }
        private String checker(String url) {
            StringBuilder builder = new StringBuilder(url);
            builder.setCharAt(7, '/');

            String modified = String.valueOf(builder);
            return modified;
        }


    }
    public int getSubTotal(){
        return subTotal;
    }
}
