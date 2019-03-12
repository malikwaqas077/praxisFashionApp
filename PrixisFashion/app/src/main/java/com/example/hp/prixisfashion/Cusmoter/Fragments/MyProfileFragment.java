package com.example.hp.prixisfashion.Cusmoter.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.prixisfashion.Cusmoter.CardViewAdapter;
import com.example.hp.prixisfashion.Cusmoter.CardViewItem;
import com.example.hp.prixisfashion.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {

    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;


    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        ArrayList<CardViewItem> cardViewItems = new ArrayList<>();
        cardViewItems.add(new CardViewItem(R.drawable.user, "Username", "Adil Mehmood"));
        cardViewItems.add(new CardViewItem(R.drawable.accountssmall, "Email Address", "adl@gmail.com"));
        cardViewItems.add(new CardViewItem(R.drawable.adminsmall, "Phone Number", "03087896991"));

        mRecyclerview = view.findViewById(R.id.recycle_view_profile);
        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new CardViewAdapter(cardViewItems);

        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);

        return  view;
    }

}
