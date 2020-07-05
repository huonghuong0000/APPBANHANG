package com.example.appbanhang.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int id;
    public String namesp;
    public Integer pricesp;
    public String imagesp;
    public String detailsp;
    public int id_lsp;

    public SanPham(int id, String namesp, Integer pricesp, String imagesp, String detailsp, int id_lsp) {
        this.id = id;
        this.namesp = namesp;
        this.pricesp = pricesp;
        this.imagesp = imagesp;
        this.detailsp = detailsp;
        this.id_lsp = id_lsp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamesp() {
        return namesp;
    }

    public void setNamesp(String namesp) {
        this.namesp = namesp;
    }

    public Integer getPricesp() {
        return pricesp;
    }

    public void setPricesp(Integer pricesp) {
        this.pricesp = pricesp;
    }

    public String getImagesp() {
        return imagesp;
    }

    public void setImagesp(String imagesp) {
        this.imagesp = imagesp;
    }

    public String getDetailsp() {
        return detailsp;
    }

    public void setDetailsp(String detailsp) {
        this.detailsp = detailsp;
    }

    public int getId_lsp() {
        return id_lsp;
    }

    public void setId_lsp(int id_lsp) {
        this.id_lsp = id_lsp;
    }
}
