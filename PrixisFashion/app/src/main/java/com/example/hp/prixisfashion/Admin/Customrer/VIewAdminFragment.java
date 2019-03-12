package com.example.hp.prixisfashion.Admin.Customrer;


import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.hp.prixisfashion.Model.AdminModels.AdminSignupModel;
import com.example.hp.prixisfashion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class VIewAdminFragment extends Fragment {


    private ListView mLstVwAdmins;

    private ArrayList<String> mAdmins=new ArrayList<>();
    private ArrayList<String> mAdminsIds = new ArrayList<>();

    private ArrayAdapter<String> arrayAdapter;

    private DatabaseReference databaseReference;

    private AdminSignupModel adminSignupModel;


    public VIewAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_admin, container, false);

        final FirebaseDatabase firebaseDatabase;

        //Setting DataBase Refrence And Firebase Database Here
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Admin");

        //Registering ListView Here
        mLstVwAdmins=view.findViewById(R.id.lst_vw_admins);
        getAdmins();

        mLstVwAdmins.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete Admin")
                        .setMessage("Selected Admin Will Be Deleted Permanently")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAdmins.remove(position);
                                databaseReference.getRoot().child("Admin").child(mAdminsIds.get(position)).removeValue();
                                mAdminsIds.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                Snackbar.make(view, "Deleted", Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        return view;
    }


    private ArrayList<String> getAdmins(){
        mAdmins=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> adminsList=dataSnapshot.getChildren();
                for (DataSnapshot admins:adminsList){
                    mAdminsIds.add(admins.getKey());
                    adminSignupModel=admins.getValue(AdminSignupModel.class);
                    mAdmins.add(adminSignupModel.getName());
                    arrayAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mAdmins);
                    mLstVwAdmins.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return  mAdmins;
    }


}
