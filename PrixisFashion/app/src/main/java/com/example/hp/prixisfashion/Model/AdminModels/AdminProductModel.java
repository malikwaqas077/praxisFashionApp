package com.example.hp.prixisfashion.Model.AdminModels;

public class AdminProductModel {

    private String ProductTitle;
    private String ProductCategories;
    private String ProductPrice;
    private String ProductKeywords;
    private String ProductDetails;



    private String ProductImageUrl;


    public AdminProductModel(String productTitle, String productCategories, String productPrice, String productKeywords, String productDetails, String productImageUrl) {
        ProductTitle = productTitle;
        ProductCategories = productCategories;
        ProductPrice = productPrice;
        ProductKeywords = productKeywords;
        ProductDetails = productDetails;
        ProductImageUrl= productImageUrl;
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

    public String getProductKeywords() {
        return ProductKeywords;
    }

    public void setProductKeywords(String productKeywords) {
        ProductKeywords = productKeywords;
    }

    public String getProductDetails() {
        return ProductDetails;
    }

    public void setProductDetails(String productDetails) {
        ProductDetails = productDetails;
    }
}
