package com.example.hp.prixisfashion;

import com.example.hp.prixisfashion.Model.AdminModels.AdminProductModel;

import java.util.ArrayList;
import java.util.List;

public class CartListProducts {

    private List<AdminProductModel> cartList=cartList=new ArrayList<>();
    private static  int totalAmount;

    private static final CartListProducts cartListProducts = new CartListProducts();


    public static int getTotalAmount() {
        return totalAmount;
    }

    public static void setTotalAmount(int totalAmount) {
        CartListProducts.totalAmount = totalAmount;
    }

    public List<AdminProductModel> getCartList() {
        return cartList;
    }

    public void addCartItem(AdminProductModel item) {
        cartList.add(item);
    }

    public static CartListProducts getInstance(){
        return  cartListProducts;
    }

}
