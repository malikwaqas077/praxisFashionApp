package com.example.hp.prixisfashion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.example.hp.prixisfashion.Adapters.ConfirmOrderAdapter;
import com.example.hp.prixisfashion.Model.AdminModels.AdminProductModel;


import java.util.List;

public class ConfirmOrderActivity extends AppCompatActivity {
    RecyclerView recyclerViewConfirmOrder;
    TextView tvSub,tvService,tvCharges,tvTotal;
    LinearLayout tvproccedToPayment;
    ConfirmOrderAdapter adapter;
    List<AdminProductModel> AdminProductModels;
    private int totalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        recyclerViewConfirmOrder=findViewById(R.id.recConfirmOrder);

        tvSub=findViewById(R.id.tvSubTotalConfirmOrder);
        tvService=findViewById(R.id.tvServiceFeeConfirmOrder);
        tvCharges=findViewById(R.id.tvDeliveryChargesConfirmOrder);
        tvTotal=findViewById(R.id.tvTotalAmountConfirmOrder);
        tvproccedToPayment=findViewById(R.id.tvProceedToPayment);

        tvproccedToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmOrderActivity.this,PaymentActivity.class));
            }
        });

        recyclerViewConfirmOrder.setLayoutManager(new LinearLayoutManager(this));

        AdminProductModels =CartListProducts.getInstance().getCartList();
        setSubTotal();

        adapter=new ConfirmOrderAdapter(AdminProductModels,this);
        recyclerViewConfirmOrder.setAdapter(adapter);
    }


    public void setSubTotal(){
        totalAmount =0;
        int subTotal=0;
        for (int i =0;i<AdminProductModels.size();i++){
            AdminProductModel model = AdminProductModels.get(i);

            int priceS = Integer.parseInt(model.getProductPrice());
            int quantity = model.getproductQuantity();
            subTotal= subTotal+(priceS*quantity);
        }
        String subTotalStr= "Rs: "+String.valueOf(subTotal)+".0/-";
        totalAmount=totalAmount+subTotal;
        setServiceFee(subTotal);
        setDeliveryCharges();
        tvSub.setText(subTotalStr);

        String totalAmountStr = "Rs: "+String.valueOf(totalAmount)+"/-";
        tvTotal.setText(totalAmountStr);




    }
    private void setServiceFee(int subTotal){
        int fourPercent= (subTotal/100)*4;
        totalAmount = totalAmount+fourPercent;
        String serviceFee="Rs: " +String.valueOf(fourPercent)+".0/-";
        tvService.setText(serviceFee);
    }
    private void setDeliveryCharges(){
        totalAmount=totalAmount+45;

        CartListProducts.setTotalAmount(totalAmount);
    }
}
