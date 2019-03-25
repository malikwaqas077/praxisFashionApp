package com.example.hp.prixisfashion.Adapters;



import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.prixisfashion.Model.AdminModels.CustomerModel.CustomerSignupModel;
import com.example.hp.prixisfashion.R;


import java.util.List;

public class CustomerProfileAdapter extends RecyclerView.Adapter<CustomerProfileAdapter.CustomerProfileViewHolder> {
    private List<CustomerSignupModel> customerModelList;
    private Context context;

    public CustomerProfileAdapter(List<CustomerSignupModel> customerModelList, Context context) {
        this.customerModelList = customerModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerProfileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        int layoutId=R.layout.user_profile_layout;
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(layoutId,viewGroup,false);
        CustomerProfileViewHolder viewHolder=new CustomerProfileViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerProfileViewHolder CustomerProfileViewHolder, int i) {
        CustomerSignupModel timecustomerSignupModel = customerModelList.get(i);
        CustomerProfileViewHolder.bind(timecustomerSignupModel);
    }

    @Override
    public int getItemCount() {
        return customerModelList.size();
    }

    public class CustomerProfileViewHolder extends RecyclerView.ViewHolder{
        TextView userName,tvEmail,tvSubject,tvRoom;
        ImageView userImage;
        public CustomerProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.nameTv);
            tvEmail=itemView.findViewById(R.id.EmailTv);
            userImage=itemView.findViewById(R.id.cusProfile);

        }

        void bind(CustomerSignupModel customerSignupModel){
            userName.setText(customerSignupModel.getName());
            tvEmail.setText(customerSignupModel.getEmail());
            Glide.with(context).load(customerSignupModel.getImageUrl()).into(userImage);

        }
    }
}




