package com.example.appbanhang.model;

public class GioHang {
    public int id_sp;
    public String name_sp;
    public long price_sp;
    public String image_sp;
    public int soluong_sp;

    public GioHang(int id_sp, String name_sp, long price_sp, String image_sp, int soluong_sp) {
        this.id_sp = id_sp;
        this.name_sp = name_sp;
        this.price_sp = price_sp;
        this.image_sp = image_sp;
        this.soluong_sp = soluong_sp;
    }

    public int getId_sp() {
        return id_sp;
    }

    public void setId_sp(int id_sp) {
        this.id_sp = id_sp;
    }

    public String getName_sp() {
        return name_sp;
    }

    public void setName_sp(String name_sp) {
        this.name_sp = name_sp;
    }

    public long getPrice_sp() {
        return price_sp;
    }

    public void setPrice_sp(long price_sp) {
        this.price_sp = price_sp;
    }

    public String getImage_sp() {
        return image_sp;
    }

    public void setImage_sp(String image_sp) {
        this.image_sp = image_sp;
    }

    public int getSoluong_sp() {
        return soluong_sp;
    }

    public void setSoluong_sp(int soluong_sp) {
        this.soluong_sp = soluong_sp;
    }
}
