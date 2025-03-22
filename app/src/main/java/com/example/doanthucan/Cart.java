package com.example.doanthucan;

public class Cart {
    String anh;
    String name,category;
    int price;
    String totalQuantity,saveCurrentDate,savetime;
       String documentID;
    public Cart(){

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Cart(String anh, String name, String category, int price, String totalQuantity, String saveCurrentDate, String savetime, String documentID) {
        this.anh = anh;
        this.name = name;
        this.category = category;
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.saveCurrentDate = saveCurrentDate;
        this.savetime = savetime;
        this.documentID = documentID;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getSaveCurrentDate() {
        return saveCurrentDate;
    }

    public void setSaveCurrentDate(String saveCurrentDate) {
        this.saveCurrentDate = saveCurrentDate;
    }

    public String getSavetime() {
        return savetime;
    }

    public void setSavetime(String savetime) {
        this.savetime = savetime;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public Cart(String anh, String name, int price, String totalQuantity, String savetime, String saveCurrentDate, String documentID) {
        this.anh = anh;
        this.name = name;
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.saveCurrentDate = saveCurrentDate;
        this.savetime = savetime;
        this.documentID = documentID;
    }


}
