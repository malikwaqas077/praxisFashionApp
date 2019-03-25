package com.example.hp.prixisfashion.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.prixisfashion.Model.AdminModels.AdminProductModel;
import com.example.hp.prixisfashion.R;


import java.util.List;

public class ConfirmOrderAdapter extends RecyclerView.Adapter<ConfirmOrderAdapter.ConfirmOrderViewHolder>  {
    private List<AdminProductModel> modelList;
    private Context context;


    public ConfirmOrderAdapter(List<AdminProductModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ConfirmOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =    LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.confirm_order_item,viewGroup,false);

        ConfirmOrderViewHolder viewHolder=new ConfirmOrderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmOrderViewHolder confirmOrderViewHolder, int i) {
        confirmOrderViewHolder.bind(modelList.get(i));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ConfirmOrderViewHolder extends RecyclerView.ViewHolder{
        TextView tvPName,tvPQuantity,tvPPrice;
        public ConfirmOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPName=itemView.findViewById(R.id.tvCOrdPName);
            tvPQuantity=itemView.findViewById(R.id.tvCOrdPQuantity);
            tvPPrice=itemView.findViewById(R.id.tvCOrdPPrice);
        }

        void bind(AdminProductModel model){
            tvPName.setText(model.getProductTitle());
            String price="Rs: "+model.getProductPrice()+"/-";
            tvPPrice.setText(price);
            String quantity= "(*"+model.getproductQuantity()+")";
            tvPQuantity.setText(quantity);
        }
    }
}

