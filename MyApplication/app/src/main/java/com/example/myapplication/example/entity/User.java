package com.example.myapplication.example.entity;

import android.graphics.Bitmap;

public class User {
    private int userId;

    private String username;

    private String password;

    private int status;

    private String role;

    private String name;
    private String phone;

    private double weight;
    private String image;
    private String data;
    public String getData(){return data;}
    public void setData(String data){this.data=data;}
    public String getImage(){return image;}
    public void setImage(String image){this.image=image;}
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    private Bitmap bitmap;
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName(){return name;}

    public void setName(String name){this.name=name;}

    public String getRole(){return role;}

    public void setRole(String role){this.role=role;}

    public int getUserId() { return userId; }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
