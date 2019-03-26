package com.example.hp.prixisfashion.Admin.Orders;


import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.hp.prixisfashion.Adapters.OrderAdapter;
import com.example.hp.prixisfashion.Model.AdminModels.CustomerModel.OrderModel;
import com.example.hp.prixisfashion.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrdersFragment extends Fragment {


    DatabaseReference CustomerReference;
    RecyclerView mCustomerRecycVw;



    public NewOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_orders, container, false);
        getActivity().setTitle("Shipped Orders");

        CustomerReference= FirebaseDatabase.getInstance().getReference().child("Order");
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
                .setQuery(CustomerReference, OrderModel.class)
                .build();

        final FirebaseRecyclerAdapter<OrderModel, NewOrdersFragment.CustomersViewHolder> adapter=new FirebaseRecyclerAdapter<OrderModel, NewOrdersFragment.CustomersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final NewOrdersFragment.CustomersViewHolder holder, final int position, @NonNull final OrderModel model) {


                DisplayMetrics displaymetrics = new DisplayMetrics();
                (getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width


                holder.mOrderDate.setText(model.getDateTimeOrder());
                holder.mTotalItems.setText(model.getOrderedProductIds());
                holder.mOrderStatus.setText(model.getOrderStatus());
                holder.mOrderAmount.setText(model.getTotalPrice());



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
            public NewOrdersFragment.CustomersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_new_order_list, viewGroup,false);
                NewOrdersFragment.CustomersViewHolder customersViewHolder=new NewOrdersFragment.CustomersViewHolder(view);
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