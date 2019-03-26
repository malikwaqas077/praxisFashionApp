package com.example.hp.prixisfashion.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hp.prixisfashion.Admin.Customrer.AddAdminFragment;
import com.example.hp.prixisfashion.Admin.Customrer.CustomersFragment;
import com.example.hp.prixisfashion.Admin.Customrer.VIewAdminFragment;
import com.example.hp.prixisfashion.Admin.Orders.InProgessOrdersFragment;
import com.example.hp.prixisfashion.Admin.Orders.NewOrdersFragment;
import com.example.hp.prixisfashion.Admin.Orders.ShippedOrdersFragment;
import com.example.hp.prixisfashion.Admin.product.AddCategoryFragment;
import com.example.hp.prixisfashion.Admin.product.AddProductFragment;
import com.example.hp.prixisfashion.Admin.product.CatagoriesFragment;
import com.example.hp.prixisfashion.Admin.product.ProductsFragment;
import com.example.hp.prixisfashion.LoginActivity;
import com.example.hp.prixisfashion.R;
import com.google.firebase.auth.FirebaseAuth;



public class admin_home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;


    FirebaseAuth auth;


    FrameLayout  OrderMenu, ProductMenu, CategoriesMenu, CustomerMenu,
            AdminMenu;
    LinearLayout DashboardSubMenu, OrderSubMenu, ProductSubMenu, CategoriesSubMenu,  CustomerSubMenu,
             TransprtSubMenu, AdminSubMenu;

    TextView NavSubmneuNewOrder, NavSubmneuInProSubMenu, NavSubmneuShippedSubMenu,
            NavSubMenuAddProduct, NavSubMenuViewProducts, NavSubMenuAddCategoty, NavSubmMenuViewCategoty,
            NavSubMenuViewCustomer, NavSubmAddAdmin, NavSubViewAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Admin Dashboard");


        setContentView(R.layout.activity_admin_home);
        //Setting Up Firebase Auth Here


        auth=FirebaseAuth.getInstance();

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FragmentLoadinManagerWithBackStack(new AdminDashFragment());

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);

        DashboardSubMenu=headerLayout.findViewById(R.id.dasboard_ll);
        DashboardSubMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseTheDrawer();

                FragmentLoadinManagerWithBackStack(new AdminDashFragment());
            }
        });


        OrderMenu =  headerLayout.findViewById(R.id.order_menu);
        OrderSubMenu = headerLayout.findViewById(R.id.order_sub_menu);
        OrderMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderSubMenu.setVisibility(View.VISIBLE);
                ClosetheRestOfSubMenu(view);
            }
        });


        // open fragment on submenu

        NavSubmneuNewOrder =  headerLayout.findViewById(R.id.new_order_sub_tv);
        NavSubmneuNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if drawer is opened then Close it
                CloseTheDrawer();
                NewOrdersFragment NewOrdersFragment = new NewOrdersFragment();
                FragmentLoadinManagerWithBackStack(NewOrdersFragment);


            }
        });

        // opening inprogress fragment

        NavSubmneuInProSubMenu =  headerLayout.findViewById(R.id.inprogress_sub_tv);
        NavSubmneuInProSubMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if drawer is opened then Close it
                CloseTheDrawer();
                InProgessOrdersFragment inProgessOrdersFragment = new InProgessOrdersFragment();
                FragmentLoadinManagerWithBackStack(inProgessOrdersFragment);


            }
        });

        // open shipped fragment




        // product catagoris

        ProductMenu =  headerLayout.findViewById(R.id.product_menu);
        ProductSubMenu = headerLayout.findViewById(R.id.product_sub_menu);
        ProductMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductSubMenu.setVisibility(View.VISIBLE);
                ClosetheRestOfSubMenu(view);
            }
        });


        // open fragment on submenu add product

        NavSubMenuAddProduct =  headerLayout.findViewById(R.id.add_pro_sub_tv);
        NavSubMenuAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if drawer is opened then Close it
                CloseTheDrawer();
                AddProductFragment addProductFragment = new AddProductFragment();
                FragmentLoadinManagerWithBackStack(addProductFragment);


            }
        });

        // opening view product fragment

        NavSubMenuViewProducts =  headerLayout.findViewById(R.id.viw_pro_sub_tv);
        NavSubMenuViewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if drawer is opened then Close it
                CloseTheDrawer();
                ProductsFragment productsFragment = new ProductsFragment();
                FragmentLoadinManagerWithBackStack(productsFragment);


            }
        });

        // open shipped fragment





                // categories menu

        CategoriesMenu = headerLayout.findViewById(R.id.categories_menu);
        CategoriesSubMenu =  headerLayout.findViewById(R.id.categories_sub_menu);
        CategoriesMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoriesSubMenu.setVisibility(View.VISIBLE);
                ClosetheRestOfSubMenu(view);
            }
        });



        NavSubMenuAddCategoty =  headerLayout.findViewById(R.id.add_cat_sub_tv);
        NavSubMenuAddCategoty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                if drawer is opened then Close it
                CloseTheDrawer();
                AddCategoryFragment addCategoryFragment = new AddCategoryFragment();
                FragmentLoadinManagerWithBackStack(addCategoryFragment);


            }
        });

        NavSubmMenuViewCategoty =  headerLayout.findViewById(R.id.view_cat_sub_tv);
        NavSubmMenuViewCategoty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                if drawer is opened then Close it
                CloseTheDrawer();
                CatagoriesFragment catagoriesFragment = new CatagoriesFragment();
                FragmentLoadinManagerWithBackStack(catagoriesFragment);

            }
        });


        // Customer Menu

        CustomerMenu =  headerLayout.findViewById(R.id.customer_menu);
        CustomerSubMenu =  headerLayout.findViewById(R.id.customer_sub_menu);
        CustomerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerSubMenu.setVisibility(View.VISIBLE);
                ClosetheRestOfSubMenu(view);

            }
        });


        NavSubMenuViewCustomer =  headerLayout.findViewById(R.id.view_customer_sub_tv);
        NavSubMenuViewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseTheDrawer();
                CustomersFragment customersFragment = new CustomersFragment();
                FragmentLoadinManagerWithBackStack(customersFragment);

            }
        });


        // Admin menu



        AdminMenu = headerLayout.findViewById(R.id.admin_menu);
        AdminSubMenu = headerLayout.findViewById(R.id.admin_subMenu);
        AdminMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getting the data for all the hostel details from the server
                
                AdminSubMenu.setVisibility(View.VISIBLE);
//                ClosetheRestOfSubMenu(view);

            }
        });
        
        NavSubmAddAdmin =  headerLayout.findViewById(R.id.add_Admin_sub_tv);
        NavSubmAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CloseTheDrawer();
                AddAdminFragment addAdminFragment = new AddAdminFragment();
                FragmentLoadinManagerWithBackStack(addAdminFragment);
            }
        });

        NavSubViewAdmin =  headerLayout.findViewById(R.id.view_admin_sub_tv);
        NavSubViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CloseTheDrawer();
                VIewAdminFragment vIewAdminFragment = new VIewAdminFragment();
                FragmentLoadinManagerWithBackStack(vIewAdminFragment);
            }
        });






    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            admin_home.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.menu_btn_logout){
            auth.signOut();
            Snackbar snackbar=Snackbar.make(getWindow().getDecorView().getRootView(), "Logged Out", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Okay", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(admin_home.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            snackbar.show();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ClosetheRestOfSubMenu(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.order_menu:
                ProductSubMenu.setVisibility(View.GONE);
                CategoriesSubMenu.setVisibility(View.GONE);
                CustomerSubMenu.setVisibility(View.GONE);
                AdminSubMenu.setVisibility(View.GONE);
                break;
            case R.id.product_menu:
                OrderSubMenu.setVisibility(View.GONE);
                CategoriesSubMenu.setVisibility(View.GONE);
                CustomerSubMenu.setVisibility(View.GONE);
                AdminSubMenu.setVisibility(View.GONE);
                break;
            case R.id.categories_menu:
                OrderSubMenu.setVisibility(View.GONE);
                ProductSubMenu.setVisibility(View.GONE);
                CustomerSubMenu.setVisibility(View.GONE);
                AdminSubMenu.setVisibility(View.GONE);
                break;
            case R.id.customer_menu:
                OrderSubMenu.setVisibility(View.GONE);
                ProductSubMenu.setVisibility(View.GONE);
                CategoriesSubMenu.setVisibility(View.GONE);
                AdminSubMenu.setVisibility(View.GONE);
                break;


            case R.id.admin_menu:
                OrderSubMenu.setVisibility(View.GONE);
                ProductSubMenu.setVisibility(View.GONE);
                CategoriesSubMenu.setVisibility(View.GONE);
                CustomerSubMenu.setVisibility(View.GONE);
                break;

            default:
                OrderSubMenu.setVisibility(View.GONE);
                ProductSubMenu.setVisibility(View.GONE);
                CategoriesSubMenu.setVisibility(View.GONE);
                CustomerSubMenu.setVisibility(View.GONE);
                TransprtSubMenu.setVisibility(View.GONE);
                AdminSubMenu.setVisibility(View.GONE);
                break;
        }

    }


    public void CloseTheDrawer() {
        drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void FragmentLoadinManagerWithBackStack(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }


}
