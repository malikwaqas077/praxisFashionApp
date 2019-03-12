package com.example.hp.prixisfashion.Admin.Customrer;


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
import android.widget.ProgressBar;

import com.example.hp.prixisfashion.Model.AdminModels.AdminSignupModel;
import com.example.hp.prixisfashion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAdminFragment extends Fragment {


    private EditText mEdtTxtASName, mEdtTxtASEmail, mEdtTxtASPassword, mEdtTxtASPasswordRep, mEdtTxtASPhone;
    private String mStrASName, mStrASEmail, mStrASPassword, mStrASPasswordRep, mStrASPhone;
    private static final String mStrAdmin = "admin";
    private Button mBtnSignup;
    private KProgressHUD kProgressHUD;

    private AdminSignupModel adminSignupModel;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;


    public AddAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_admin, container, false);
        //Registering Auth Here
        auth = FirebaseAuth.getInstance();

        //Making Table(Named As Admin) In Database Through FireDBInstance
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin");

        //Registering All Edit Text Here
        mEdtTxtASName = view.findViewById(R.id.edt_txt_as_name);
        mEdtTxtASEmail = view.findViewById(R.id.edt_txt_as_email);
        mEdtTxtASPassword = view.findViewById(R.id.edt_txt_as_password);
        mEdtTxtASPasswordRep = view.findViewById(R.id.edt_txt_as_rep_password);
        mEdtTxtASPhone = view.findViewById(R.id.edt_txt_as_phone);

        //Now Registering Button Here And Getting Values Of All Edit Texts To Strings And Validating It
        mBtnSignup = view.findViewById(R.id.btn_a_signup);
        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Getting Values Of Edit Text To Strings Here
                mStrASName = mEdtTxtASName.getText().toString();
                mStrASEmail = mEdtTxtASEmail.getText().toString();
                mStrASPassword = mEdtTxtASPassword.getText().toString();
                mStrASPasswordRep = mEdtTxtASPasswordRep.getText().toString();
                mStrASPhone = mEdtTxtASPhone.getText().toString();

                if (mStrASName.isEmpty()) {
                    mEdtTxtASName.setError("Please Fill This Field");
                } else if (mStrASEmail.isEmpty()) {
                    mEdtTxtASEmail.setError("Please Fill This Field");
                } else if (mStrASPassword.isEmpty()) {
                    mEdtTxtASPassword.setError("Please Fill This Field");
                } else if (mStrASPasswordRep.isEmpty()) {
                    mEdtTxtASPasswordRep.setError("Please Fill This Field");
                } else if (mStrASPhone.isEmpty()) {
                    mEdtTxtASPhone.setError("Please Fill This Field");
                } else if (mStrASPassword.length() < 8) {
                    mEdtTxtASPassword.setError("Password Should Be Greater Than 8");
                } else if (mStrASPasswordRep.length() < 8) {
                    mEdtTxtASPasswordRep.setError("Password Should Be Greater Than 8");
                } else if (!(mStrASPassword.equalsIgnoreCase(mStrASPasswordRep))) {
                    mEdtTxtASPasswordRep.setError("Password Didn't Matched");
                    mEdtTxtASPassword.setError("Password Didn't Matched");
                } else if (!(Patterns.EMAIL_ADDRESS.matcher(mStrASEmail).matches())) {
                    mEdtTxtASEmail.setError("Email Not Valid");
                } else if (!(mEdtTxtASPhone.length() == 11)) {
                    mEdtTxtASPhone.setError("Phone Number Must Be 11 Digits Long");
                } else {
                    kProgressHUD= KProgressHUD.create(getContext())
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setAnimationSpeed(2)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark))
                            .setLabel("Registering")
                            .setDetailsLabel("Please Wait...")
                            .setDimAmount(0.3f)
                            .show();
                    auth.createUserWithEmailAndPassword(mStrASEmail, mStrASPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                adminSignupModel = new AdminSignupModel(mStrASName, mStrASEmail, mStrASPassword, mStrASPhone, mStrAdmin);
                                databaseReference.push().setValue(adminSignupModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        mEdtTxtASName.getText().clear();
                                        mEdtTxtASEmail.getText().clear();
                                        mEdtTxtASPassword.getText().clear();
                                        mEdtTxtASPasswordRep.getText().clear();
                                        mEdtTxtASPhone.getText().clear();
                                        Snackbar.make(v, "Admin Registered", Snackbar.LENGTH_SHORT).show();
                                        kProgressHUD.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG).show();
                                        kProgressHUD.dismiss();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            kProgressHUD.dismiss();
                            Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }
}