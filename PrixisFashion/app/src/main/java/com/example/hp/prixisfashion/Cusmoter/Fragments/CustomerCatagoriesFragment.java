package com.example.hp.prixisfashion.Cusmoter.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.hp.prixisfashion.Adapters.CatagoriesAdapter;
import com.example.hp.prixisfashion.Model.AdminModels.AdminCategoryModel;
import com.example.hp.prixisfashion.Model.AdminModels.AdminCategoryModel;
import com.example.hp.prixisfashion.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerCatagoriesFragment extends Fragment {

    int images[]={R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
    private ViewFlipper simpleViewFlipper;

    private ArrayList<String> mCategories=new ArrayList<>();


    int countInt, incrementalCount;
    DatabaseReference ProductReference;
    RecyclerView mProductRecycVw;
    String count;

    public CustomerCatagoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_catagories2, container, false);
        ProductReference= FirebaseDatabase.getInstance().getReference().child("Categories");
        getActivity().setTitle("Catagories");


        mProductRecycVw=view.findViewById(R.id.recycler_vw_catagory);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mProductRecycVw.setLayoutManager(mLayoutManager);        // loop for creating ImageView's
        
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminCategoryModel> options=new FirebaseRecyclerOptions.Builder<AdminCategoryModel>()
                .setQuery(ProductReference, AdminCategoryModel.class)
                .build();

        FirebaseRecyclerAdapter<AdminCategoryModel, CustomerCatagoriesFragment.ProductViewHolder> adapter=new FirebaseRecyclerAdapter<AdminCategoryModel, CustomerCatagoriesFragment.ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CustomerCatagoriesFragment.ProductViewHolder holder, int position, @NonNull AdminCategoryModel model) {


                DisplayMetrics displaymetrics = new DisplayMetrics();
                (getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width

                holder.postTitle.setText(model.getCategoryTitle());
                Glide.with(getActivity().getApplicationContext()).load(model.getCategoryImgUrl()).into(holder.postImage);


            }

            @NonNull
            @Override
            public CustomerCatagoriesFragment.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.catagory_layout, viewGroup,false);
                CustomerCatagoriesFragment.ProductViewHolder productViewHolder=new CustomerCatagoriesFragment.ProductViewHolder(view);
                return productViewHolder;
            }
        };

        mProductRecycVw.setAdapter(adapter);
        adapter.startListening();

    }


    public static class ProductViewHolder extends  RecyclerView.ViewHolder{


        ImageView postImage;
        TextView postTitle;
        TextView postDescription;
        Button mAddToCartBtn;
        LinearLayout mItemCountLin;

        ImageView mPlusBtn, mMinusBtn;
        TextView mTotalCount;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = (ImageView) itemView.findViewById(R.id.CatagoryImage);
            postTitle = (TextView) itemView.findViewById(R.id.postTitle);
            postDescription = (TextView) itemView.findViewById(R.id.postDescription);
            mAddToCartBtn=itemView.findViewById(R.id.add_to_cart_btn);


        }
    }
}

