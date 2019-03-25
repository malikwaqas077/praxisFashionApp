package com.example.hp.prixisfashion.Cusmoter.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.prixisfashion.Adapters.CustomerProfileAdapter;
import com.example.hp.prixisfashion.Adapters.OrderAdapter;
import com.example.hp.prixisfashion.Cusmoter.CardViewAdapter;
import com.example.hp.prixisfashion.Cusmoter.CardViewItem;
import com.example.hp.prixisfashion.Model.AdminModels.CustomerModel.CustomerSignupModel;
import com.example.hp.prixisfashion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {

    private List<CustomerSignupModel> modelList;
    private CustomerProfileAdapter adapter;
    RecyclerView recyclerViewOrders;
    DatabaseReference ProductReference;



    TextView mEmail, edName, mPhoneTv, edEmail;
    EditText degree;
    Button btnEdit;
    RadioButton rBtnMale, rBtnFemale;
    RadioGroup profileRadioGrp;
    ImageButton profileBrowseBtn;
    int IMAGE_CODE=1;
    Uri imageUri;
    StorageReference imageReference;
    DatabaseReference databaseReference;
    ChildEventListener studentInfoListener;
    ValueEventListener studentValueListener;
    FirebaseAuth mAuth;
    String Uid;

    boolean disables;
    String gender, Id, name,email,session, Degree;




    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;
    String mStrUid;
    DatabaseReference databaseReferenceUser;
    ArrayList <CustomerProfileAdapter> list;

    CustomerSignupModel customerSignupModel;
    ImageView userProfileImage;

    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mStrUid = currentFirebaseUser.getUid();
        recyclerViewOrders = view.findViewById(R.id.recOrders);
        modelList = new ArrayList<>();

        edName=view.findViewById(R.id.nameTv);
        mEmail=view.findViewById(R.id.EmailTv);
        mPhoneTv=view.findViewById(R.id.mobileNo);

        userProfileImage=view.findViewById(R.id.cusProfile);


        mAuth = FirebaseAuth.getInstance();
        Uid = mAuth.getCurrentUser().getUid();
        ProductReference= FirebaseDatabase.getInstance().getReference().child("Customer").child(Uid);

        getOrders();


        return  view;

    }





    private void getOrders(){
        ProductReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> categoriesList=dataSnapshot.getChildren();


                    String id=dataSnapshot.child("name").getValue().toString();
                    String time=dataSnapshot.child("email").getValue().toString();
                    String address=dataSnapshot.child("phone").getValue().toString();
                    String price=dataSnapshot.child("imageUrl").getValue().toString();


                    edName.setText(id);
                    Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
                    mEmail.setText(time);
                    Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();

                    mPhoneTv.setText(address);
                    Glide.with(getContext()).load(price).into(userProfileImage);

//                    CustomerSignupModel model = new CustomerSignupModel(id,time,items,address,
//                            status,price);
//
//                    modelList.add(model);
                }
//                adapter=new CustomerProfileAdapter(modelList,getActivity());
//                adapter.notifyDataSetChanged();
//                recyclerViewOrders.setAdapter(adapter);


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}
