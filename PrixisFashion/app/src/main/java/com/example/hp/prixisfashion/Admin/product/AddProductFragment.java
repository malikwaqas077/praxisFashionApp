package com.example.hp.prixisfashion.Admin.product;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hp.prixisfashion.Model.AdminModels.AdminCategoryModel;
import com.example.hp.prixisfashion.Model.AdminModels.AdminProductModel;
import com.example.hp.prixisfashion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {

    private EditText mEdtTxtProdTitle, mEdtTxtProdPrice, mEdtTxtProdKeywords, mEdtTxtProdDetails;
    private CardView mCVAddProd;
    private KProgressHUD kProgressHUD;
    private Spinner mSpinnerCategories;
    private ArrayList<String> mCategories;

    StorageReference profilePicRef;

    private String mStrSpinnerValue, mStrProdTitle, mStrProdPrice, mStrProdDetails;
    
    int mProductQuantity;

    private StorageReference mProfilePicStorageReference;
    private static final int RC_PHOTO_PICKER = 1;
    private Uri selectedProfileImageUri;

    Button mSelectedImgBtn;
    ImageView profileImageView;
    String downloadUri;


    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private AdminCategoryModel adminCategoryModel;
    private AdminProductModel adminProductModel;


    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Add product");

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_product, container, false);


        //Getting Firebase Instance And Database Refrence Here
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Categories");
        mProfilePicStorageReference = FirebaseStorage.getInstance().getReference().child("ProfilePictures");

        //Registering EditText Here
        mEdtTxtProdTitle = view.findViewById(R.id.et_prod_title);
        mEdtTxtProdPrice = view.findViewById(R.id.et_prod_price);
        mEdtTxtProdKeywords = view.findViewById(R.id.et_prod_keywords);
        mEdtTxtProdDetails = view.findViewById(R.id.et_prod_details);
        profileImageView = view.findViewById(R.id.selected_img_vw);
        mSelectedImgBtn = view.findViewById(R.id.select_iamge_btn);
        mSelectedImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfilePicture();
            }
        });


        //Registering Spinner Here
        mSpinnerCategories = view.findViewById(R.id.spinner_categories);
        getCategories();

        //Registering CardView Here And Getting All Values To Strings After Being Clicked
        mCVAddProd = view.findViewById(R.id.cv_add_prod_btn);
        mCVAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStrProdTitle = mEdtTxtProdTitle.getText().toString();
                mStrSpinnerValue = mSpinnerCategories.getSelectedItem().toString();
                mStrProdPrice = mEdtTxtProdPrice.getText().toString();
                String mStrQnty = mEdtTxtProdKeywords.getText().toString();
                mProductQuantity=Integer.parseInt(mStrQnty);
                mStrProdDetails = mEdtTxtProdDetails.getText().toString();

                if (mStrProdTitle.isEmpty()) {
                    mEdtTxtProdTitle.setError("Please Fill This Field");
                } else if (mStrSpinnerValue.isEmpty()) {
                    Snackbar.make(view, "Please Select Category First", Snackbar.LENGTH_LONG).show();
                } else if (mStrProdPrice.isEmpty()) {
                    mEdtTxtProdPrice.setError("Please Fill This Filed");
                } else if (mStrQnty.isEmpty()) {
                    mEdtTxtProdKeywords.setError("Please Fill This Filed");
                } else if (mStrProdDetails.isEmpty()) {
                    mEdtTxtProdDetails.setError("Please Fill This Filed");
                } else {
                    kProgressHUD = KProgressHUD.create(getContext())
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setAnimationSpeed(2)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark))
                            .setLabel("Adding")
                            .setDetailsLabel("Please Wait...")
                            .setDimAmount(0.3f)
                            .show();
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
        });

        return view;
    }

    private ArrayList<String> getCategories() {
        mCategories = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> studentNameList = dataSnapshot.getChildren();
                for (DataSnapshot students : studentNameList) {
                    adminCategoryModel = students.getValue(AdminCategoryModel.class);
                    mCategories.add(adminCategoryModel.getCategoryTitle());
                    ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, mCategories);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerCategories.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return mCategories;
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


        // Toast.makeText(this, "User Name also Added", Toast.LENGTH_SHORT).show();



    public void uploadProduct(String ImageUrl){
        final View view=getView();
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        adminProductModel = new AdminProductModel(mStrProdTitle, mStrSpinnerValue, mStrProdPrice, mProductQuantity, mStrProdDetails, ImageUrl, false);
        databaseReference.push().setValue(adminProductModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mEdtTxtProdTitle.getText().clear();
                mEdtTxtProdPrice.getText().clear();
                mEdtTxtProdKeywords.getText().clear();
                mEdtTxtProdDetails.getText().clear();
                kProgressHUD.dismiss();
                Snackbar.make(view, "Product Added", Snackbar.LENGTH_SHORT).show();
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
