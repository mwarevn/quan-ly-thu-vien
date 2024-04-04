package com.myfinal.ph21862.model;

public class PhieuMuon {
    private int mapm, matv, masach, trasach, tienthue;
    private String matt, ngay;
    private String tenTv, tenTt, tenSach;

    public PhieuMuon(int mapm, int matv, String tenTv, String matt, String tenTt, int masach, String tenSach, String ngay, int trasach, int tienthue) {
        this.mapm = mapm;
        this.matv = matv;
        this.masach = masach;
        this.trasach = trasach;
        this.tienthue = tienthue;
        this.matt = matt;
        this.ngay = ngay;
        this.tenTv = tenTv;
        this.tenTt = tenTt;
        this.tenSach = tenSach;
    }

    public PhieuMuon(int matv,String matt, int masach, String ngay, int trasach, int tienthue) {
        this.matv = matv;
        this.masach = masach;
        this.trasach = trasach;
        this.tienthue = tienthue;
        this.matt = matt;
        this.ngay = ngay;
    }

    public int getMapm() {
        return mapm;
    }

    public void setMapm(int mapm) {
        this.mapm = mapm;
    }

    public int getMatv() {
        return matv;
    }

    public void setMatv(int matv) {
        this.matv = matv;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public int getTrasach() {
        return trasach;
    }

    public void setTrasach(int trasach) {
        this.trasach = trasach;
    }

    public int getTienthue() {
        return tienthue;
    }

    public void setTienthue(int tienthue) {
        this.tienthue = tienthue;
    }

    public String getMatt() {
        return matt;
    }

    public void setMatt(String matt) {
        this.matt = matt;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTenTv() {
        return tenTv;
    }

    public void setTenTv(String tenTv) {
        this.tenTv = tenTv;
    }

    public String getTenTt() {
        return tenTt;
    }

    public void setTenTt(String tenTt) {
        this.tenTt = tenTt;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
}
