package com.example.doanthucan.model;

import java.io.Serializable;

public class ViewAll implements Serializable {
    private String user,name,image,category;
    private int price;

    public ViewAll(String user, String name, String image, String category, int price) {
        this.user = user;
        this.name = name;
        this.image = image;
        this.category = category;
        this.price = price;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ViewAll() {
    }

    public ViewAll(String name, String image, String category, int price) {
        this.name = name;
        this.image = image;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
