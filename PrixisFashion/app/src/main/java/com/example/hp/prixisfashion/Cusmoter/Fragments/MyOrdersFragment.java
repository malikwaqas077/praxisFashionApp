package com.example.hp.prixisfashion.Cusmoter.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.prixisfashion.Adapters.OrderAdapter;
import com.example.hp.prixisfashion.Model.AdminModels.CustomerModel.OrderModel;
import com.example.hp.prixisfashion.R;
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
public class MyOrdersFragment extends Fragment {

    private ListView mLstVwCategories;

    private ArrayList<String> mOrderDate=new ArrayList<>();


    private ArrayAdapter<String> arrayAdapter;

    private FirebaseDatabase firebaseDatabase;
    DatabaseReference ProductReference;

    RecyclerView recyclerViewOrders;
    private OrderAdapter adapter;
    private List<OrderModel> modelList;

    private OrderModel orderModel;
    public MyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        ProductReference= FirebaseDatabase.getInstance().getReference().child("Order");
        getActivity().setTitle("My Orders");

        recyclerViewOrders = view.findViewById(R.id.recOrders);


        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        modelList = new ArrayList<>();
        getOrders();

//        getOrdersFromServer();

        return  view;
    }

    private ArrayList<String> getOrders(){
        mOrderDate=new ArrayList<>();
        ProductReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> categoriesList=dataSnapshot.getChildren();
                for (DataSnapshot categories:categoriesList){
                    orderModel=categories.getValue(OrderModel.class);
                    mOrderDate.add(orderModel.getDateTimeOrder());

                    String id=orderModel.getOrderId();
                    String time=orderModel.getDateTimeOrder();
                    String items=orderModel.getOrderedProductIds();
                    String address=orderModel.getDeliveryAddress();
                    String status=orderModel.getOrderStatus();
                    String price=orderModel.getTotalPrice();
                    String quantaties=orderModel.getQuantities();
                    String orderBy=orderModel.getOrderBy();
                    OrderModel model = new OrderModel(id,time,items,address,
                            status,price,quantaties,orderBy);

                    modelList.add(model);
                }
                adapter=new OrderAdapter(modelList,getActivity());
                adapter.notifyDataSetChanged();
                recyclerViewOrders.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return  mOrderDate;
    }


}
