package com.example.hp.prixisfashion.Model.AdminModels.CustomerModel;

public class CustomerSignupModel {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String type;



    private String imageUrl;

    public CustomerSignupModel(String name, String email, String password, String phone, String type, String imageUrl) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.type = type;
        this.imageUrl = imageUrl;
    }

    public CustomerSignupModel() {
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


