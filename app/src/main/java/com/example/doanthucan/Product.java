package com.example.doanthucan;

public class Product {
    String name,category,image;
    int price;
    public  Product(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Product(String name, String category, String image, int price) {
        this.name = name;

        this.category = category;
        this.image = image;
        this.price = price;
    }

}
