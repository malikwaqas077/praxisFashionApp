package com.example.hp.prixisfashion.Model.AdminModels;

public class AdminProductModel {

    private String ProductTitle;
    private String ProductCategories;
    private String ProductPrice;
    private int ProductQuantity;
    private String ProductDetails;
    private Boolean AddedToCart;

    public boolean isAddedToCart() {
        return AddedToCart;
    }

    public void setAddedToCart(boolean addedToCart) {
        AddedToCart = addedToCart;
    }



    private String ProductImageUrl;


    public AdminProductModel(String productTitle, String productCategories, String productPrice, int productQuantity, String productDetails, String productImageUrl, boolean addedToCart) {
        ProductTitle = productTitle;
        ProductCategories = productCategories;
        ProductPrice = productPrice;
        ProductQuantity = productQuantity;
        ProductDetails = productDetails;
        ProductImageUrl= productImageUrl;
        AddedToCart = addedToCart;

    }

    public AdminProductModel() {
    }
    public String getProductImageUrl() {
        return ProductImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        ProductImageUrl = productImageUrl;
    }
    public String getProductTitle() {
        return ProductTitle;
    }

    public void setProductTitle(String productTitle) {
        ProductTitle = productTitle;
    }

    public String getProductCategories() {
        return ProductCategories;
    }

    public void setProductCategories(String productCategories) {
        ProductCategories = productCategories;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public int getproductQuantity() {
        return ProductQuantity;
    }

    public void setproductQuantity(int productQuantity) {
        ProductQuantity = productQuantity;
    }

    public String getProductDetails() {
        return ProductDetails;
    }

    public void setProductDetails(String productDetails) {
        ProductDetails = productDetails;
    }
}
