package com.example.hp.prixisfashion.Admin.product;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.prixisfashion.Model.AdminModels.AdminCategoryModel;
import com.example.hp.prixisfashion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryFragment extends Fragment {

    private EditText mEdtTxtCategory;
    private CardView mCVAddCategory;
    private KProgressHUD kProgressHUD;
    StorageReference profilePicRef;

    private String mStrCatTitle;
    private StorageReference mProfilePicStorageReference;
    private static final int RC_PHOTO_PICKER = 1;
    private Uri selectedProfileImageUri;

    Button mSelectedImgBtn;
    ImageView profileImageView;
    String downloadUri;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;


    private AdminCategoryModel adminCategoryModel;


    public AddCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Add Catagory");
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_add_category, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Categories");
        mProfilePicStorageReference = FirebaseStorage.getInstance().getReference().child("pictures");


        //Registering EditText Of Category Here
        mEdtTxtCategory=view.findViewById(R.id.et_cat_title);
        profileImageView = view.findViewById(R.id.selected_img_vw_cat);

        mSelectedImgBtn = view.findViewById(R.id.select_iamge_btn_cat);

        mSelectedImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfilePicture();
            }
        });
        //Registering CardView As Button Here
        mCVAddCategory=view.findViewById(R.id.cv_add_category_btn);

        //Getting CardView Click Here And Checking If Edit Text Is Empty Or Not
        mCVAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting Edit Text Values To Strings
                mStrCatTitle=mEdtTxtCategory.getText().toString();

                if(mStrCatTitle.isEmpty()){
                    mEdtTxtCategory.setError("Please Fill This Field");
                }else{
                    kProgressHUD= KProgressHUD.create(getContext())
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setAnimationSpeed(2)
                            .setBackgroundColor(getResources().getColor(R.color.colorAccent))
                            .setLabel("Adding")
                            .setDetailsLabel("Please Wait...")
                            .setDimAmount(0.3f)
                            .show();
                    profilePicRef = mProfilePicStorageReference.child(selectedProfileImageUri.getLastPathSegment());
                    profilePicRef.putFile(selectedProfileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(getActivity(), "image uploaded", Toast.LENGTH_SHORT).show();

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
        return  view;
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
        databaseReference = FirebaseDatabase.getInstance().getReference("Categories");
        adminCategoryModel = new AdminCategoryModel( mStrCatTitle,ImageUrl);
        databaseReference.push().setValue(adminCategoryModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                kProgressHUD.dismiss();
                Snackbar.make(view, "Catagory Added", Snackbar.LENGTH_SHORT).show();
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