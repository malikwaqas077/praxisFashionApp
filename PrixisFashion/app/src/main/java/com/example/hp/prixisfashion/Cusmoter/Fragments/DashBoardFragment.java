package com.example.hp.prixisfashion.Cusmoter.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.hp.prixisfashion.CartListProducts;
import com.example.hp.prixisfashion.Adapters.productAdapter;
import com.example.hp.prixisfashion.Model.AdminModels.AdminProductModel;
import com.example.hp.prixisfashion.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {
    int images[]={R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
    private ViewFlipper simpleViewFlipper;
    private ArrayList<String> mCategories=new ArrayList<>();


    int countInt, incrementalCount;
    DatabaseReference ProductReference;
    productAdapter mProductAdapter;
    RecyclerView mProductRecycVw;
    String count;

    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dash_board, container, false);
        ProductReference= FirebaseDatabase.getInstance().getReference().child("Products");


        simpleViewFlipper = (ViewFlipper) view.findViewById(R.id.simpleViewFlipper); // get the reference of ViewFlipper
        mProductRecycVw=view.findViewById(R.id.main_recycler_vw);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mProductRecycVw.setLayoutManager(mLayoutManager);        // loop for creating ImageView's
        for (int i = 0; i < images.length; i++) {
            // create the object of ImageView
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[i]); // set image in ImageView
            simpleViewFlipper.addView(imageView); // add the created ImageView in ViewFlipper
        }
        // Declare in and out animations and load them using AnimationUtils class
        Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);
        // set the animation type's to ViewFlipper
        simpleViewFlipper.setInAnimation(in);
        simpleViewFlipper.setOutAnimation(out);
        // set interval time for flipping between views
        simpleViewFlipper.setFlipInterval(3000);
        // set auto start for flipping between views
        simpleViewFlipper.setAutoStart(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminProductModel> options=new FirebaseRecyclerOptions.Builder<AdminProductModel>()
                .setQuery(ProductReference, AdminProductModel.class)
                .build();

        FirebaseRecyclerAdapter<AdminProductModel, DashBoardFragment.ProductViewHolder> adapter=new FirebaseRecyclerAdapter<AdminProductModel, DashBoardFragment.ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DashBoardFragment.ProductViewHolder holder, int position, @NonNull final AdminProductModel model) {


                DisplayMetrics displaymetrics = new DisplayMetrics();
                (getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width

                holder.postTitle.setText(model.getProductTitle());
                holder.postDescription.setText("Rs"+ " "+model.getProductPrice());
                Glide.with(getActivity().getApplicationContext()).load(model.getProductImageUrl()).into(holder.postImage);

                if (model.isAddedToCart()){
                    holder.mAddToCartBtn.setVisibility(View.GONE);
                    holder.cardView.setVisibility(View.GONE);
                    holder.AddedItem.setVisibility(View.VISIBLE);
                }else {
                    holder.mAddToCartBtn.setVisibility(View.VISIBLE);
                    holder.cardView.setVisibility(View.VISIBLE);
                    holder.AddedItem.setVisibility(View.GONE);
                }


                holder.mAddToCartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CartListProducts cartListProducts = CartListProducts.getInstance();
                        model.setAddedToCart(true);
                        model.setproductQuantity(1);
                        cartListProducts.addCartItem(model);
                        holder.mAddToCartBtn.setVisibility(View.GONE);
                        holder.cardView.setVisibility(View.GONE);
                        holder.AddedItem.setVisibility(View.VISIBLE);

                    }
                });



//                holder.mMinusBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        count=holder.mTotalCount.getText().toString();
//                        countInt=Integer.parseInt(count);
//                        incrementalCount=countInt--;
//
//                        holder.mTotalCount.setText(countInt+"");
//                    }
//                });


            }

            @NonNull
            @Override
            public DashBoardFragment.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_layout, viewGroup,false);
                DashBoardFragment.ProductViewHolder productViewHolder=new DashBoardFragment.ProductViewHolder(view);
                return productViewHolder;
            }
        };

        mProductRecycVw.setAdapter(adapter);
        adapter.startListening();

    }


    public static class ProductViewHolder extends  RecyclerView.ViewHolder{


        ImageView postImage, AddedItem;
        TextView postTitle;
        TextView postDescription;
        Button mAddToCartBtn;
        LinearLayout mItemCountLin;
        Button btnOrderNow;
        CardView cardView;

        ImageView mPlusBtn, mMinusBtn;
        TextView mTotalCount;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = (ImageView) itemView.findViewById(R.id.postImage);
            postTitle = (TextView) itemView.findViewById(R.id.dashPostTitle);
            postDescription = (TextView) itemView.findViewById(R.id.postDescription);
            mAddToCartBtn=itemView.findViewById(R.id.add_to_cart_btn);
            cardView=itemView.findViewById(R.id.cardVBtn);
            AddedItem = itemView.findViewById(R.id.tvAddedItem);


        }
    }
    }

