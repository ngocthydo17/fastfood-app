package com.example.doanthucan.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private String tensp;
    //    private String tensp;
//    private int giasp;
    private int hinhsp;

    public SanPham(String tensp,  int hinhsp) {
//        this.masp = masp;
        this.tensp = tensp;
//        this.giasp = giasp;
        this.hinhsp = hinhsp;
    }

//    public String getMasp() {
//        return masp;
//    }
//
//    public void setMasp(String masp) {
//        this.masp = masp;
//    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }
//
//    public int getGiasp() {
//        return giasp;
//    }
//
//    public void setGiasp(int giasp) {
//        this.giasp = giasp;
//    }

    public int getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(int hinhsp) {
        this.hinhsp = hinhsp;
    }

    public SanPham() {
    }
}
