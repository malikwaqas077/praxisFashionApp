package com.example.hp.prixisfashion.Cusmoter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.hp.prixisfashion.Admin.product.CatagoriesFragment;
import com.example.hp.prixisfashion.Cusmoter.Fragments.Adapters.productAdapter;
import com.example.hp.prixisfashion.Cusmoter.Fragments.CustomerCatagoriesFragment;
import com.example.hp.prixisfashion.Cusmoter.Fragments.DashBoardFragment;
import com.example.hp.prixisfashion.Cusmoter.Fragments.MyCartFragment;
import com.example.hp.prixisfashion.Cusmoter.Fragments.MyOrdersFragment;
import com.example.hp.prixisfashion.Cusmoter.Fragments.MyProfileFragment;
import com.example.hp.prixisfashion.Cusmoter.Fragments.SigninFragment;
import com.example.hp.prixisfashion.Model.AdminModels.AdminProductModel;
import com.example.hp.prixisfashion.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerNavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int images[]={R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
    private ViewFlipper simpleViewFlipper;
    private ArrayList<String> mCategories=new ArrayList<>();


    DatabaseReference ProductReference;
    productAdapter mProductAdapter;
    RecyclerView mProductRecycVw;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ProductReference= FirebaseDatabase.getInstance().getReference().child("Products");
//
//
//        simpleViewFlipper = (ViewFlipper) findViewById(R.id.simpleViewFlipper); // get the reference of ViewFlipper
//        mProductRecycVw=findViewById(R.id.main_recycler_vw);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
//
//        mProductRecycVw.setLayoutManager(mLayoutManager);        // loop for creating ImageView's
//        for (int i = 0; i < images.length; i++) {
//            // create the object of ImageView
//            ImageView imageView = new ImageView(this);
//            imageView.setImageResource(images[i]); // set image in ImageView
//            simpleViewFlipper.addView(imageView); // add the created ImageView in ViewFlipper
//        }
//        // Declare in and out animations and load them using AnimationUtils class
//        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
//        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
//        // set the animation type's to ViewFlipper
//        simpleViewFlipper.setInAnimation(in);
//        simpleViewFlipper.setOutAnimation(out);
//        // set interval time for flipping between views
//        simpleViewFlipper.setFlipInterval(3000);
//        // set auto start for flipping between views
//        simpleViewFlipper.setAutoStart(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DashBoardFragment fragment =new DashBoardFragment();

        FragmentLoadinManagerWithBackStack(fragment);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_btn_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_dashboard) {

            DashBoardFragment dashBoardFragment = new DashBoardFragment();
            FragmentLoadinManagerWithBackStack(dashBoardFragment);

        } else if (id == R.id.nav_catagory) {
            CustomerCatagoriesFragment customerCatagoriesFragment =new CustomerCatagoriesFragment();
            FragmentLoadinManagerWithBackStack(customerCatagoriesFragment);

            // Handle the camera action
        } else if (id == R.id.nav_orders) {

            MyOrdersFragment myOrdersFragment=new MyOrdersFragment();
            FragmentLoadinManagerWithBackStack(myOrdersFragment);

        } else if (id == R.id.nav_cart) {

            MyCartFragment myCartFragment=new MyCartFragment();
            FragmentLoadinManagerWithBackStack(myCartFragment);

        } else if (id == R.id.nav_profile) {

            MyProfileFragment myProfileFragment=new MyProfileFragment();
            FragmentLoadinManagerWithBackStack(myProfileFragment);

        } else if (id == R.id.nav_signin) {
            SigninFragment signinFragment=new SigninFragment();
            FragmentLoadinManagerWithBackStack(signinFragment);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void FragmentLoadinManagerWithBackStack(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.customer_content_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }



//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerOptions<AdminProductModel> options=new FirebaseRecyclerOptions.Builder<AdminProductModel>()
//                .setQuery(ProductReference, AdminProductModel.class)
//                .build();
//
//        FirebaseRecyclerAdapter<AdminProductModel, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<AdminProductModel, ProductViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull AdminProductModel model) {
//
//                holder.postTitle.setText(model.getProductTitle());
//                holder.postDescription.setText(model.getProductDetails());
//                Glide.with(getApplicationContext()).load(model.getProductImageUrl()).into(holder.postImage);
//            }
//
//            @NonNull
//            @Override
//            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//
//                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_layout, viewGroup,false);
//                ProductViewHolder productViewHolder=new ProductViewHolder(view);
//                return productViewHolder;
//            }
//        };
//
//        mProductRecycVw.setAdapter(adapter);
//        adapter.startListening();
//
//    }
//
//
//    public static class ProductViewHolder extends  RecyclerView.ViewHolder{
//
//
//        ImageView postImage;
//        TextView postTitle;
//        TextView postDescription;
//        public ProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            postImage = (ImageView) itemView.findViewById(R.id.postImage);
//            postTitle = (TextView) itemView.findViewById(R.id.postTitle);
//            postDescription = (TextView) itemView.findViewById(R.id.postDescription);
//        }
//    }

}
