package com.myfinal.ph21862.model;

public class Sach {
    private int masach;
    private String tensach;
    private int giathue;
    private int maloai;
    private int soluondamuon;
    private int saleoff;

    public int getSaleoff() {
        return saleoff;
    }

    public void setSaleoff(int saleoff) {
        this.saleoff = saleoff;
    }

    public Sach(int masach, String tensach, int giathue, int maloai, int soluondamuon, int saleoff) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
        this.soluondamuon = soluondamuon;
        this.saleoff = saleoff;
    }

    public int getSoluondamuon() {
        return soluondamuon;
    }

    public void setSoluondamuon(int soluondamuon) {
        this.soluondamuon = soluondamuon;
    }

    public Sach(int masach, String tensach, int soluondamuon) {
        this.masach = masach;
        this.tensach = tensach;
        this.soluondamuon = soluondamuon;
    }

    public Sach() {
    }

    public Sach(int masach, String tensach, int giathue, int maloai, int saleoff) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
        this.saleoff = saleoff;
    }

    public Sach(int masach, String tensach, int giathue, int maloai) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public int getGiathue() {
        return giathue;
    }

    public void setGiathue(int giathue) {
        this.giathue = giathue;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }
}
