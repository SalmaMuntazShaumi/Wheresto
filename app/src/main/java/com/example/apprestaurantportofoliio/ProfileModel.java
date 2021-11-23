package com.example.apprestaurantportofoliio;

import io.realm.RealmObject;

public class ProfileModel{

    String username, phone, email;


    public ProfileModel(String username, String phone, String email){
        this.username = username;
        this.phone = phone;
        this.email = email;
    }


    public String getUsername(){ return username; }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

}
