package com.example.doanthucan.model;

import java.io.Serializable;

public class BestSeller implements Serializable {
    private String tensp;
    private int giasp;
    private int hinhsp;
    public int getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(int hinhsp) {
        this.hinhsp = hinhsp;
    }
    public BestSeller(String tensp,  int giasp, int hinhsp) {
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhsp = hinhsp;
    }
    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }
    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }
    public BestSeller() {
    }
    }
