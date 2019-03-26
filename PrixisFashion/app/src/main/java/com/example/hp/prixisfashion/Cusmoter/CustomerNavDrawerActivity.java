package com.example.hp.prixisfashion.Cusmoter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hp.prixisfashion.CartActivity;
import com.example.hp.prixisfashion.Cusmoter.Fragments.CustomerCatagoriesFragment;
import com.example.hp.prixisfashion.Cusmoter.Fragments.CustomerSignupFragment;
import com.example.hp.prixisfashion.Cusmoter.Fragments.DashBoardFragment;
import com.example.hp.prixisfashion.Cusmoter.Fragments.MyOrdersFragment;
import com.example.hp.prixisfashion.Cusmoter.Fragments.MyProfileFragment;
import com.example.hp.prixisfashion.Cusmoter.Fragments.SigninFragment;
import com.example.hp.prixisfashion.R;
import com.google.firebase.auth.FirebaseAuth;


public class CustomerNavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_nav_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DashBoardFragment fragment = new DashBoardFragment();

        FragmentLoadinManagerWithBackStack(fragment);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                finishAndRemoveTask();
                            }
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
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
        if (id == R.id.action_logout) {
            auth.signOut();
            FragmentLoadinManagerWithBackStack(new DashBoardFragment());
            Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Logged Out", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return true;
        } else if (id == R.id.optCart) {
            if (null != auth.getCurrentUser()) {
                startActivity(new Intent(CustomerNavDrawerActivity.this, CartActivity.class));

            } else {
                Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Please Login First", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Login", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentLoadinManagerWithBackStack(new SigninFragment());
                    }
                });
                snackbar.show();
            }
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
            CustomerCatagoriesFragment customerCatagoriesFragment = new CustomerCatagoriesFragment();
            FragmentLoadinManagerWithBackStack(customerCatagoriesFragment);

            // Handle the camera action
        } else if (id == R.id.nav_orders) {
            if (null != auth.getCurrentUser()) {
                MyOrdersFragment myOrdersFragment = new MyOrdersFragment();
                FragmentLoadinManagerWithBackStack(myOrdersFragment);
            } else {
                Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Please Login First", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Login", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentLoadinManagerWithBackStack(new SigninFragment());
                    }
                });
                snackbar.show();
            }


        } else if (id == R.id.nav_cart) {
            if (null != auth.getCurrentUser()) {
                startActivity(new Intent(CustomerNavDrawerActivity.this, CartActivity.class));

            } else {
                Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Please Login First", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Login", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentLoadinManagerWithBackStack(new SigninFragment());
                    }
                });
                snackbar.show();

            }
//            MyCartFragment myCartFragment=new MyCartFragment();
//            FragmentLoadinManagerWithBackStack(myCartFragment);

        } else if (id == R.id.nav_profile) {

            if (null != auth.getCurrentUser()) {
                MyProfileFragment myProfileFragment = new MyProfileFragment();
                FragmentLoadinManagerWithBackStack(myProfileFragment);
            } else {
                Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Please Login First", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Login", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentLoadinManagerWithBackStack(new SigninFragment());
                    }
                });
                snackbar.show();
            }


        } else if (id == R.id.nav_signin) {

            if (null != auth.getCurrentUser()) {
                Toast.makeText(getApplicationContext(), "you already are signed in", Toast.LENGTH_SHORT).show();


            } else {
                SigninFragment signinFragment = new SigninFragment();
                FragmentLoadinManagerWithBackStack(signinFragment);
            }


        } else if (id == R.id.nav_signup) {

            if (null != auth.getCurrentUser()) {
                Toast.makeText(getApplicationContext(), "Please Logout first to signup", Toast.LENGTH_SHORT).show();

            } else {
                FragmentLoadinManagerWithBackStack(new CustomerSignupFragment());

            }


        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
