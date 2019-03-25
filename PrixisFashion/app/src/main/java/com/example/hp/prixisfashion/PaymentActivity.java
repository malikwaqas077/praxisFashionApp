package com.example.hp.prixisfashion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.hp.prixisfashion.Cusmoter.CustomerNavDrawerActivity;
import com.example.hp.prixisfashion.Cusmoter.Fragments.MyOrdersFragment;
import com.example.hp.prixisfashion.Model.AdminModels.AdminProductModel;
import com.example.hp.prixisfashion.Model.AdminModels.CustomerModel.OrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;

    Button mOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        databaseReference = FirebaseDatabase.getInstance().getReference("Order");
        getData();
        mOrderBtn = findViewById(R.id.myOrders);
        mOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this, CustomerNavDrawerActivity.class));
            }
        });

    }


    void getData() {
        String products;

        String orderStatus = "preparing";
        String dateTime;
        String total = String.valueOf(CartListProducts.getTotalAmount());
        String orderBy = "5c83a878600f5f1fb0db1b73";
        products = setProducts();
        String quantities = setQuantities();
        dateTime = setDateTime();
        String deliveryAddress = "82/83-A satlight town sargodha, Sargodha";
        OrderModel orderModel = new OrderModel("null", dateTime, products, deliveryAddress, orderStatus, total, quantities, orderBy);
        databaseReference.push().setValue(orderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(PaymentActivity.this, "order placed successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PaymentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String setProducts() {
        StringBuilder productIds = new StringBuilder();
        List<AdminProductModel> cartProducts = CartListProducts.getInstance().getCartList();
        for (int i = 0; i < cartProducts.size(); i++) {
            AdminProductModel model = cartProducts.get(i);

            productIds.append(model.getProductTitle() + " ");
        }

        return String.valueOf(productIds);

    }

    private String setQuantities() {
        StringBuilder productQs = new StringBuilder();
        List<AdminProductModel> cartProducts = CartListProducts.getInstance().getCartList();
        for (int i = 0; i < cartProducts.size(); i++) {
            AdminProductModel model = cartProducts.get(i);

            productQs.append(model.getproductQuantity() + " ");
        }

        return String.valueOf(productQs);
    }

    private String setDateTime() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");

        return dateFormat.format(date);
    }


    public void FragmentLoadinManagerWithBackStack(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.replace_content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

}
