package com.myfinal.ph21862.model;

public class ThuThu {
    private String matt, hoten, matkhau, level;

    public ThuThu(String matt, String hoten, String matkhau, String level) {
        this.matt = matt;
        this.hoten = hoten;
        this.matkhau = matkhau;
        this.level = level;
    }

    public String getMatt() {
        return matt;
    }

    public void setMatt(String matt) {
        this.matt = matt;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
