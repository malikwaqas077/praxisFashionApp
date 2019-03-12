package com.example.hp.prixisfashion.Model.AdminModels;

public class AdminCategoryModel {

    private String CategoryTitle;
    private String CategoryImgUrl;

    public String getCategoryImgUrl() {
        return CategoryImgUrl;
    }

    public void setCategoryImgUrl(String categoryImgUrl) {
        CategoryImgUrl = categoryImgUrl;
    }


    public AdminCategoryModel(String categoryTitle, String categoryImgUrl) {
        CategoryTitle = categoryTitle;
        CategoryImgUrl = categoryImgUrl;
    }

    public AdminCategoryModel() {
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }
}
