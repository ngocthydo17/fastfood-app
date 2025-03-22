package com.example.doanthucan.model;

public class XemHoaDonModel {
    int price1;
    String ngay;
    String gio;


    public XemHoaDonModel() {
    }

    public XemHoaDonModel(int price1,String ngay,String gio) {
        this.price1 = price1;
        this.ngay = ngay;
        this.gio = gio;
    }

    public int getPrice1() {
        return price1;
    }

    public void setPrice1(int price1) {
        this.price1 = price1;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }
}