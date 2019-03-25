package com.example.hp.prixisfashion.Cusmoter.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.prixisfashion.Cusmoter.CustomerNavDrawerActivity;
import com.example.hp.prixisfashion.Model.AdminModels.CustomerModel.CustomerSignupModel;
import com.example.hp.prixisfashion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerSignupFragment extends Fragment {
    EditText mEdtTxtASName, mEdtTxtASEmail, mEdtTxtASPassword, mEdtTxtASPasswordRep, mEdtTxtASPhone;
    String mStrASName, mStrASEmail, mStrASPassword, mStrASPasswordRep, mStrASPhone;
    private static final String mStrCustomer = "customer";

    TextView mTxtVwGoBack;

    Button mBtnSignup;


    Button mSelectedImgBtn;
    ImageView profileImageView;
    String downloadUri;

    KProgressHUD kProgressHUD;

    private StorageReference mProfilePicStorageReference;
    private static final int RC_PHOTO_PICKER = 1;
    private Uri selectedProfileImageUri;

    CustomerSignupModel customerSignupModel;

    DatabaseReference databaseReference;
    FirebaseAuth auth;
    StorageReference profilePicRef;


    public CustomerSignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_signup, container, false);


        auth = FirebaseAuth.getInstance();
        mProfilePicStorageReference = FirebaseStorage.getInstance().getReference().child("CustomerPictures");

        //Making Table(Named As Admin) In Database Through FireDBInstance
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer");

        //Registering All Edit Text Here
        mEdtTxtASName = view.findViewById(R.id.edt_txt_as_name);
        mEdtTxtASEmail = view.findViewById(R.id.edt_txt_as_email);
        mEdtTxtASPassword = view.findViewById(R.id.edt_txt_as_password);
        mEdtTxtASPasswordRep = view.findViewById(R.id.edt_txt_as_rep_password);
        mEdtTxtASPhone = view.findViewById(R.id.edt_txt_as_phone);
        profileImageView = view.findViewById(R.id.selected_img_vw);
        mSelectedImgBtn = view.findViewById(R.id.select_iamge_btn);
        mSelectedImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfilePicture();
            }
        });

        //Registering TextView And Shifting Activity On Click TO Login Activity
        mTxtVwGoBack = view.findViewById(R.id.txt_vw_go_back);
        mTxtVwGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
                    kProgressHUD = KProgressHUD.create(getActivity())
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

                                profilePicRef = mProfilePicStorageReference.child(selectedProfileImageUri.getLastPathSegment());
                                profilePicRef.putFile(selectedProfileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        profilePicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                downloadUri=uri.toString();
                                                uploadProduct(downloadUri);
                                            }
                                        });
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

    public void getProfilePicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            selectedProfileImageUri = selectedImageUri;
            profileImageView.setImageURI(selectedImageUri);
            profileImageView.setVisibility(View.VISIBLE);
        }

    }

    public void uploadProduct(String ImageUrl){
        final View view=getView();
        customerSignupModel = new CustomerSignupModel(mStrASName, mStrASEmail, mStrASPassword, mStrASPhone, mStrCustomer, ImageUrl);
        databaseReference.child(auth.getUid()).setValue(customerSignupModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mEdtTxtASName.getText().clear();
                mEdtTxtASEmail.getText().clear();
                mEdtTxtASPassword.getText().clear();
                mEdtTxtASPasswordRep.getText().clear();
                mEdtTxtASPhone.getText().clear();
                kProgressHUD.dismiss();
                Snackbar snackbar = Snackbar.make(view, "Registered Successfully", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Proceed", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CustomerNavDrawerActivity.class);
                        startActivity(intent);
                    }
                });
                snackbar.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                kProgressHUD.dismiss();
                Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
