package com.example.hp.prixisfashion.Cusmoter.Fragments.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.prixisfashion.Model.AdminModels.AdminProductModel;
import com.example.hp.prixisfashion.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ProductViewHolder> {


    private Context context;
    private List<AdminProductModel> mProductList;

    public productAdapter(Context context, List<AdminProductModel> mProductList) {
        this.context = context;
        this.mProductList = mProductList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_layout, viewGroup, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        AdminProductModel adminProductModel=mProductList.get(i);
        productViewHolder.postTitle.setText(adminProductModel.getProductTitle());
        productViewHolder.postDescription.setText(adminProductModel.getProductDetails());
        Glide.with(context).load(adminProductModel.getProductImageUrl()).into(productViewHolder.postImage);


//

    }

    @Override
    public int getItemCount() {
        return mProductList.size();    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView postImage;
        TextView postTitle;
        TextView postDescription;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = (ImageView) itemView.findViewById(R.id.postImage);
            postTitle = (TextView) itemView.findViewById(R.id.postTitle);
            postDescription = (TextView) itemView.findViewById(R.id.postDescription);
        }
    }

}