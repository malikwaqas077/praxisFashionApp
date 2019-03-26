package com.example.hp.prixisfashion.Admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.prixisfashion.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminDashFragment extends Fragment {

    DatabaseReference CustomerReference, databaseReference, OrderReference, CatagoryReference;
    ;

    TextView mTotalCustomerTv, mTotalProducts, mTotalOrders, mTotalCatagories;

    public AdminDashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle("Dash Board");

        View view= inflater.inflate(R.layout.fragment_admin_dash, container, false);

        mTotalCustomerTv=view.findViewById(R.id.totalCustomer);
        mTotalProducts=view.findViewById(R.id.totalProducts);
        mTotalOrders=view.findViewById(R.id.totalOrders);
        mTotalCatagories=view.findViewById(R.id.totalCatagories);

        CustomerReference= FirebaseDatabase.getInstance().getReference().child("Customer");
        databaseReference=FirebaseDatabase.getInstance().getReference("Products");
        OrderReference= FirebaseDatabase.getInstance().getReference().child("Order");
        CatagoryReference= FirebaseDatabase.getInstance().getReference().child("Categories");


//You can use the single or the value.. depending if you want to keep track
        CustomerReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTotalCustomerTv.setText(dataSnapshot.getChildrenCount()+"");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTotalProducts.setText(dataSnapshot.getChildrenCount()+"");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        OrderReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTotalOrders.setText(dataSnapshot.getChildrenCount()+"");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        CatagoryReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    mTotalCatagories.setText(dataSnapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return  view;
    }

}
