package com.example.hp.prixisfashion.Admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.prixisfashion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminDashFragment extends Fragment {


    public AdminDashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle("Dash Board");

        return inflater.inflate(R.layout.fragment_admin_dash, container, false);
    }

}
