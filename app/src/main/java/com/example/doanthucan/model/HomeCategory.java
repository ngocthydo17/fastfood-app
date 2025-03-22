package com.example.doanthucan.model;

public class HomeCategory {
    private String name,image,type,category;

    public HomeCategory() {
    }

    public HomeCategory(String name, String image, String type,String category) {
        this.name = name;
        this.image = image;
        this.type = type;
        this.category=category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
