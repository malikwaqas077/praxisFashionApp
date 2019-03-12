package com.example.hp.prixisfashion.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.prixisfashion.Model.AdminModels.AdminCategoryModel;
import com.example.hp.prixisfashion.R;


import java.util.List;

public class CatagoriesAdapter extends RecyclerView.Adapter<CatagoriesAdapter.WeekTimeTableViewHolder> {
    private List<AdminCategoryModel> catagoryModelList;
    private Context context;

    public CatagoriesAdapter(List<AdminCategoryModel> catagoryModelList, Context context) {
        this.catagoryModelList = catagoryModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public WeekTimeTableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        int layoutId=R.layout.catagory_item_layout;
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(layoutId,viewGroup,false);
        WeekTimeTableViewHolder viewHolder=new WeekTimeTableViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeekTimeTableViewHolder weekTimeTableViewHolder, int i) {
        AdminCategoryModel timecatagoryModel = catagoryModelList.get(i);
        weekTimeTableViewHolder.bind(timecatagoryModel);
    }

    @Override
    public int getItemCount() {
        return catagoryModelList.size();
    }

    public class WeekTimeTableViewHolder extends RecyclerView.ViewHolder{
        TextView CatName,tvTime,tvSubject,tvRoom;
        public WeekTimeTableViewHolder(@NonNull View itemView) {
            super(itemView);
            CatName=itemView.findViewById(R.id.item_tv);
            
        }

        void bind(AdminCategoryModel catagoryModel){
            CatName.setText(catagoryModel.getCategoryTitle());
        }
    }
}

