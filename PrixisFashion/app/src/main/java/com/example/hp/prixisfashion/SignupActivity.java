package com.example.hp.prixisfashion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hp.prixisfashion.Admin.admin_home;
import com.example.hp.prixisfashion.Cusmoter.CustomerNavDrawerActivity;
import com.example.hp.prixisfashion.Model.AdminModels.AdminSignupModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;

public class SignupActivity extends AppCompatActivity {

    EditText mEdtTxtASName, mEdtTxtASEmail, mEdtTxtASPassword, mEdtTxtASPasswordRep, mEdtTxtASPhone;
    String mStrASName, mStrASEmail, mStrASPassword, mStrASPasswordRep, mStrASPhone;
    private static final String mStrCustomer = "customer";

    TextView mTxtVwGoBack;

    Button mBtnSignup;

    KProgressHUD kProgressHUD;


    AdminSignupModel adminSignupModel;

    DatabaseReference databaseReference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Registering Auth Here
        auth=FirebaseAuth.getInstance();

        //Making Table(Named As Admin) In Database Through FireDBInstance
        databaseReference=FirebaseDatabase.getInstance().getReference("Customer");

        //Registering All Edit Text Here
        mEdtTxtASName=findViewById(R.id.edt_txt_as_name);
        mEdtTxtASEmail=findViewById(R.id.edt_txt_as_email);
        mEdtTxtASPassword=findViewById(R.id.edt_txt_as_password);
        mEdtTxtASPasswordRep=findViewById(R.id.edt_txt_as_rep_password);
        mEdtTxtASPhone=findViewById(R.id.edt_txt_as_phone);

        //Registering TextView And Shifting Activity On Click TO Login Activity
        mTxtVwGoBack=findViewById(R.id.txt_vw_go_back);
        mTxtVwGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignupActivity.this, CustomerNavDrawerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Now Registering Button Here And Getting Values Of All Edit Texts To Strings And Validating It
        mBtnSignup=findViewById(R.id.btn_a_signup);
        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Getting Values Of Edit Text To Strings Here
                mStrASName=mEdtTxtASName.getText().toString();
                mStrASEmail=mEdtTxtASEmail.getText().toString();
                mStrASPassword=mEdtTxtASPassword.getText().toString();
                mStrASPasswordRep=mEdtTxtASPasswordRep.getText().toString();
                mStrASPhone=mEdtTxtASPhone.getText().toString();

                if(mStrASName.isEmpty()){
                    mEdtTxtASName.setError("Please Fill This Field");
                }else if(mStrASEmail.isEmpty()){
                    mEdtTxtASEmail.setError("Please Fill This Field");
                }else if(mStrASPassword.isEmpty()){
                    mEdtTxtASPassword.setError("Please Fill This Field");
                }else if(mStrASPasswordRep.isEmpty()){
                    mEdtTxtASPasswordRep.setError("Please Fill This Field");
                }else if(mStrASPhone.isEmpty()){
                    mEdtTxtASPhone.setError("Please Fill This Field");
                }else if(mStrASPassword.length() < 8 ){
                    mEdtTxtASPassword.setError("Password Should Be Greater Than 8");
                }else if(mStrASPasswordRep.length() < 8 ){
                    mEdtTxtASPasswordRep.setError("Password Should Be Greater Than 8");
                }else if(!(mStrASPassword.equalsIgnoreCase(mStrASPasswordRep))){
                    mEdtTxtASPasswordRep.setError("Password Didn't Matched");
                    mEdtTxtASPassword.setError("Password Didn't Matched");
                }else if(!(Patterns.EMAIL_ADDRESS.matcher(mStrASEmail).matches())){
                    mEdtTxtASEmail.setError("Email Not Valid");
                }else if(!(mEdtTxtASPhone.length() == 11)){
                    mEdtTxtASPhone.setError("Phone Number Must Be 11 Digits Long");
                }else{
                    kProgressHUD= KProgressHUD.create(SignupActivity.this)
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
                            if(task.isSuccessful()){
                                adminSignupModel= new AdminSignupModel(mStrASName, mStrASEmail, mStrASPassword, mStrASPhone, mStrCustomer);
                                databaseReference.push().setValue(adminSignupModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        mEdtTxtASName.getText().clear();
                                        mEdtTxtASEmail.getText().clear();
                                        mEdtTxtASPassword.getText().clear();
                                        mEdtTxtASPasswordRep.getText().clear();
                                        mEdtTxtASPhone.getText().clear();
                                        kProgressHUD.dismiss();
                                        Snackbar snackbar=Snackbar.make(v, "Registered Successfully", Snackbar.LENGTH_INDEFINITE);
                                        snackbar.setAction("Proceed", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent=new Intent(SignupActivity.this, CustomerNavDrawerActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                        snackbar.show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        kProgressHUD.dismiss();
                                        Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG).show();
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
    }
}
