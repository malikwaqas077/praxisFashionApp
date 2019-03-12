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

import com.example.hp.prixisfashion.Admin.admin_home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kaopiz.kprogresshud.KProgressHUD;

public class LoginActivity extends AppCompatActivity {

    private EditText mEdtTxtAEmail, mEdtTxtAPassword;
    private Button mBtnLogin;
    private KProgressHUD kProgressHUD;

    private String mStrAEmail, mStrAPassword;

    private FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        auth=FirebaseAuth.getInstance();
        if(auth.getUid() != null ){
            Intent intent=new Intent(LoginActivity.this, admin_home.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Setting Up FireBase Auth Here
        auth=FirebaseAuth.getInstance();

        //Registering Edit Text Here
        mEdtTxtAEmail=findViewById(R.id.edt_txt_al_email);
        mEdtTxtAPassword=findViewById(R.id.edt_txt_al_password);

        //Registering Button And Getting All Values From Edit Texts Here
        mBtnLogin=findViewById(R.id.btn_a_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Getting Edit Text Values To Strings
                mStrAEmail=mEdtTxtAEmail.getText().toString();
                mStrAPassword=mEdtTxtAPassword.getText().toString();

                if(mStrAEmail.isEmpty()){
                    mEdtTxtAEmail.setError("Please Fill Out This Field");
                }else if(mStrAPassword.isEmpty()){
                    mEdtTxtAPassword.setError("Please Fill This Field");
                }else if(!(Patterns.EMAIL_ADDRESS.matcher(mStrAEmail).matches())){
                    mEdtTxtAEmail.setError("Email Not Valid");
                }else{
                    kProgressHUD= KProgressHUD.create(LoginActivity.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setAnimationSpeed(2)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark))
                            .setLabel("Authenticating")
                            .setDetailsLabel("Please Wait...")
                            .setDimAmount(0.3f)
                            .show();
                    auth.signInWithEmailAndPassword(mStrAEmail, mStrAPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                kProgressHUD.dismiss();
                                Snackbar snackbar=Snackbar.make(v, "Log In Success", Snackbar.LENGTH_INDEFINITE);
                                snackbar.setAction("Proceed", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(LoginActivity.this, admin_home.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                snackbar.show();
                            }
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
        });

    }
}
