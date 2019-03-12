package com.example.hp.prixisfashion.Model.AdminModels;

public class AdminSignupModel {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String type;

    public AdminSignupModel(String name, String email, String password, String phone, String type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.type = type;
    }

    public AdminSignupModel() {
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
