package com.example.hp.prixisfashion.Admin.product;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hp.prixisfashion.Model.AdminModels.AdminCategoryModel;
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
public class CatagoriesFragment extends Fragment {


    private ListView mLstVwCategories;

    private ArrayList<String> mCategories=new ArrayList<>();
    private ArrayList<String> mCatIds = new ArrayList<>();

    private ArrayAdapter<String> arrayAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private AdminCategoryModel adminCategoryModel;


    public CatagoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("view Catagory");

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_catagories, container, false);

        //Setting DataBase Reference And FireBase Database Here
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Categories");

        //Registering ListView Here
        mLstVwCategories=view.findViewById(R.id.lst_vw_cats);
        getProducts();

        mLstVwCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete Category")
                        .setMessage("Selected Category Will Be Deleted Permanently")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCategories.remove(position);
                                databaseReference.getRoot().child("Categories").child(mCatIds.get(position)).removeValue();
                                mCatIds.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                Snackbar.make(view, "Deleted", Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        return view;
    }


    private ArrayList<String> getProducts(){
        mCategories=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> categoriesList=dataSnapshot.getChildren();
                for (DataSnapshot categories:categoriesList){
                    mCatIds.add(categories.getKey());
                    adminCategoryModel=categories.getValue(AdminCategoryModel.class);
                    mCategories.add(adminCategoryModel.getCategoryTitle());
                    arrayAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mCategories);
                    mLstVwCategories.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return  mCategories;
    }

}
