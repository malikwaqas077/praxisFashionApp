package com.example.hp.prixisfashion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hp.prixisfashion.Adapters.CartAdapter;
import com.example.hp.prixisfashion.Model.AdminModels.AdminProductModel;


import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerViewCart;
    private CartAdapter adapter;
    TextView tvTotalCartItems,tvAddMore,tvSubTotalCart,tvServiceFee,tvTotalAmountCart;
    ImageButton btnGoConfirmOrder;

    List<AdminProductModel> productModels;
    private int totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerViewCart=findViewById(R.id.recyclerCart);
        tvTotalCartItems=findViewById(R.id.tvTotalCartItems);
        tvAddMore=findViewById(R.id.tvAddMoreCart);
        tvSubTotalCart=findViewById(R.id.tvSubTotalCart);
        tvServiceFee=findViewById(R.id.tvServiceFeeCart);
        tvTotalAmountCart=findViewById(R.id.totalAmountCart);
        btnGoConfirmOrder=findViewById(R.id.btnGoConfirmOrder);

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));

        productModels =CartListProducts.getInstance().getCartList();
        setSubTotal();



        btnGoConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this,ConfirmOrderActivity.class));
            }
        });

        String totalItems="Items in cart("+String.valueOf(productModels.size())+")";
        tvTotalCartItems.setText(totalItems);

        tvAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(CartActivity.this,MainActivity.class));
                finish();
            }
        });


        adapter=new CartAdapter(productModels,this);

        recyclerViewCart.setAdapter(adapter);
    }

    public void setSubTotal(){
        totalAmount =0;
        int subTotal=0;
        for (int i =0;i<productModels.size();i++){
            AdminProductModel model = productModels.get(i);

            int priceS = Integer.parseInt(model.getProductPrice());
            int quantity = 1;
            subTotal= subTotal+(priceS*quantity);
        }
        String subTotalStr= "Rs: "+String.valueOf(subTotal)+".0/-";
        totalAmount=totalAmount+subTotal;
        setServiceFee(subTotal);
        setDeliveryCharges();
        tvSubTotalCart.setText(subTotalStr);

        String totalAmountStr = "Pay Rs: "+String.valueOf(totalAmount)+"/-";
        tvTotalAmountCart.setText(totalAmountStr);




    }
    private void setServiceFee(int subTotal){
        int fourPercent= (subTotal/100)*4;
        totalAmount = totalAmount+fourPercent;
        String serviceFee="Rs: " +String.valueOf(fourPercent)+".0/-";
        tvServiceFee.setText(serviceFee);
    }
    private void setDeliveryCharges(){
        totalAmount=totalAmount+45;
    }
}
