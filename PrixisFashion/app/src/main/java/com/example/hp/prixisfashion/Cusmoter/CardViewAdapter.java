package com.example.hp.prixisfashion.Cusmoter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.prixisfashion.R;

import java.util.ArrayList;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {
    private ArrayList<CardViewItem> mCardViewList;

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mPermanentName;
        public TextView mRealName;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_vw_user_name);
            mPermanentName = itemView.findViewById(R.id.txt_vw_user_name);
            mRealName = itemView.findViewById(R.id.txt_vw_real_name);
        }
    }

    public CardViewAdapter(ArrayList<CardViewItem> cardViewItems) {
        mCardViewList = cardViewItems;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_item, viewGroup, false);
        CardViewHolder cvh = new CardViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {
        CardViewItem currentItem =  mCardViewList.get(i);
        cardViewHolder.mImageView.setImageResource(currentItem.getmImageResource());
        cardViewHolder.mPermanentName.setText(currentItem.getmUsername());
        cardViewHolder.mRealName.setText(currentItem.getmRealName());
    }

    @Override
    public int getItemCount() {
        return mCardViewList.size();
    }
}
