package com.example.hp.prixisfashion.Admin.Orders;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.prixisfashion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippedOrdersFragment extends Fragment {


    public ShippedOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shipped_orders, container, false);
    }

}
