package com.example.hp.prixisfashion.Cusmoter.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hp.prixisfashion.Model.AdminModels.AdminSignupModel;
import com.example.hp.prixisfashion.R;
import com.example.hp.prixisfashion.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment {


    private EditText mEdtTxtEmail, mEdtTxtPassword;
    private Button mBtnLogin;
    private KProgressHUD kProgressHUD;
    private TextView mTxtVwNm;

    private String mStrEmail, mStrPassword, mStrUid, mStrIfAdmin, mStrIfCustomer;


    private FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReferenceAdmin, databaseReferenceCustomer, rootReference;

    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_signin, container, false);

        //Setting Up FireBase Auth Here
        auth=FirebaseAuth.getInstance();

        rootReference = FirebaseDatabase.getInstance().getReference();

        //Registering Edit Text Here
        mEdtTxtEmail=view.findViewById(R.id.edt_txt_l_email);
        mEdtTxtPassword=view.findViewById(R.id.edt_txt_l_password);

        mTxtVwNm= view.findViewById(R.id.txt_vw_nm);
        mTxtVwNm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        //Registering Button And Getting All Values From Edit Texts Here
        mBtnLogin=view.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Getting Edit Text Values To Strings
                mStrEmail=mEdtTxtEmail.getText().toString();
                mStrPassword=mEdtTxtPassword.getText().toString();

                if(mStrEmail.isEmpty()){
                    mEdtTxtEmail.setError("Please Fill Out This Field");
                }else if(mStrPassword.isEmpty()){
                    mEdtTxtPassword.setError("Please Fill This Field");
                }else if(!(Patterns.EMAIL_ADDRESS.matcher(mStrEmail).matches())){
                    mEdtTxtEmail.setError("Email Not Valid");
                }else{
                    kProgressHUD= KProgressHUD.create(getContext())
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setAnimationSpeed(2)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark))
                            .setLabel("Authenticating")
                            .setDetailsLabel("Please Wait...")
                            .setDimAmount(0.3f)
                            .show();
                    auth.signInWithEmailAndPassword(mStrEmail, mStrPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                firebaseUser = auth.getCurrentUser();
                                mStrUid = firebaseUser.getUid();
                                databaseReferenceAdmin = FirebaseDatabase.getInstance().getReference().child("Admin").child(mStrUid).child("type");
                                //databaseReferenceAdmin = rootReference.child(firebaseUser.getUid());
                        //        databaseReferenceAdmin = rootReference.child("Admin").child(mStrUid);
                                /*
                                databaseReferenceAdmin.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                            AdminSignupModel adminSignupModel = dataSnapshot1.getValue(AdminSignupModel.class);
                                            mStrIfAdmin = adminSignupModel.getType();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                                */

                             /*   ValueEventListener valueEventListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        mStrIfAdmin = dataSnapshot.child("type").getValue(String.class);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                };
                                databaseReferenceAdmin.addListenerForSingleValueEvent(valueEventListener);
                               */
                                //Snackbar.make(view, mStrIfAdmin, Snackbar.LENGTH_LONG).show();
                                kProgressHUD.dismiss();
                            }
                        }
                    });
                }
            }
        });

        return view;
    }

}
