package com.example.doanthucan;

public class AdressModel {
    String userAdress;
    boolean isSelected;

    public AdressModel() {
    }

    public String getUserAdress() {
        return userAdress;
    }

    public void setUserAdress(String userAddress) {
        this.userAdress = userAddress;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public AdressModel(String userAdress, boolean isSelected) {
        this.userAdress = userAdress;
        this.isSelected = isSelected;
    }

}

