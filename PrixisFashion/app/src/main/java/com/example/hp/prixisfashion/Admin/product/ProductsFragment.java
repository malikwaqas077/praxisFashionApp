package com.example.hp.prixisfashion.Admin.product;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.hp.prixisfashion.Model.AdminModels.AdminProductModel;
import com.example.hp.prixisfashion.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    private ListView mLstVwProducts;

    private ArrayList<String> mProducts=new ArrayList<>();
    private ArrayList<String> mProductsIds = new ArrayList<>();

    private ArrayAdapter<String> arrayAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private AdminProductModel adminProductModel;
    private String[] mSampleArr={"ArratItem", "ArratItem", "ArratItem", "ArratItem", "ArratItem", "ArratItem",
            "ArratItem", "ArratItem", "ArratItem", "ArratItem", "ArratItem", "ArratItem", "ArratItem"};


    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle("View Catagory");

        View view = inflater.inflate(R.layout.fragment_products, container, false);

        //Setting DataBase Refrence And Firebase Database Here
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Products");

        //Registering ListView Here
        mLstVwProducts=view.findViewById(R.id.lst_vw_prods);
        getProducts();

        mLstVwProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete Product")
                        .setMessage("Product Will Be Permanently Deleted.Are You Sure")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mProducts.remove(position);
                                databaseReference.getRoot().child("Products").child(mProductsIds.get(position)).removeValue();
                                mProductsIds.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                Snackbar.make(view, "Deleted Successfully", Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        return view;
    }

    private ArrayList<String> getProducts(){
        mProducts=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> productsList=dataSnapshot.getChildren();
                for (DataSnapshot products:productsList){
                    mProductsIds.add(products.getKey());
                    adminProductModel=products.getValue(AdminProductModel.class);
                    mProducts.add(adminProductModel.getProductTitle());

                    arrayAdapter=new ArrayAdapter(getActivity(), R.layout.list_item_design, R.id.list_item_txt_vw, mProducts);
//                    arrayAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mProducts);
                    mLstVwProducts.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return  mProducts;
    }
}
