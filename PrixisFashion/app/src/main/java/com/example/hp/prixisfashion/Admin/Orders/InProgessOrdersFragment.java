package com.example.hp.prixisfashion.Admin.Orders;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.hp.prixisfashion.Adapters.OrderAdapter;
import com.example.hp.prixisfashion.Model.AdminModels.CustomerModel.OrderModel;
import com.example.hp.prixisfashion.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InProgessOrdersFragment extends Fragment {


    DatabaseReference CustomerReference;
    RecyclerView mCustomerRecycVw;
    Query mQuery;



    public InProgessOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_in_progess_orders, container, false);
        getActivity().setTitle("New Orders");

        CustomerReference= FirebaseDatabase.getInstance().getReference().child("Order");
        mQuery = CustomerReference.orderByChild("orderStatus").equalTo("Shipped");

        mCustomerRecycVw=view.findViewById(R.id.main_recycler_vw);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mCustomerRecycVw.setLayoutManager(mLayoutManager);

//        getOrdersFromServer();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<OrderModel> options=new FirebaseRecyclerOptions.Builder<OrderModel>()
                .setQuery(mQuery, OrderModel.class)
                .build();

        final FirebaseRecyclerAdapter<OrderModel, InProgessOrdersFragment.CustomersViewHolder> adapter=new FirebaseRecyclerAdapter<OrderModel, InProgessOrdersFragment.CustomersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final InProgessOrdersFragment.CustomersViewHolder holder, final int position, @NonNull final OrderModel model) {


                DisplayMetrics displaymetrics = new DisplayMetrics();
                (getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width

                mQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot,  String s) {
                       for (DataSnapshot shipped: dataSnapshot.getChildren()){
                           String date=String.valueOf(dataSnapshot.child("dateTimeOrder").getValue());
                           String productid=String.valueOf(dataSnapshot.child("orderedProductIds").getValue());
                           String price=String.valueOf(dataSnapshot.child("totalPrice").getValue());
                           String status=String.valueOf(dataSnapshot.child("orderStatus").getValue());
                           holder.mOrderDate.setText(date);
                           holder.mTotalItems.setText(productid);
                           holder.mOrderStatus.setText(status);
                           holder.mOrderAmount.setText(price);

                       }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





                holder.btnOrderShipped.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference key= getRef(position);
                        key.child("orderStatus").setValue("Shipped");
                    }
                });





            }

            @NonNull
            @Override
            public InProgessOrdersFragment.CustomersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_new_order_list, viewGroup,false);
                InProgessOrdersFragment.CustomersViewHolder customersViewHolder=new InProgessOrdersFragment.CustomersViewHolder(view);
                return customersViewHolder;
            }
        };

        mCustomerRecycVw.setAdapter(adapter);
        adapter.startListening();

    }


    public static class CustomersViewHolder extends  RecyclerView.ViewHolder{


        TextView mOrderDate, mTotalItems, mOrderStatus, mOrderAmount;
        TextView postDescription;
        Button btnOrderShipped;

        public CustomersViewHolder(@NonNull View itemView) {
            super(itemView);

            mOrderDate =  itemView.findViewById(R.id.newOrderDate);
            mTotalItems =  itemView.findViewById(R.id.newOrderTotalItems);
            mOrderStatus=itemView.findViewById(R.id.newOrderStatus);
            mOrderAmount=itemView.findViewById(R.id.newOrderTotalAmount);
            btnOrderShipped=itemView.findViewById(R.id.btnShipped);


        }
    }




}