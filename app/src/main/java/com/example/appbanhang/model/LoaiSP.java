package com.example.appbanhang.model;

public class LoaiSP {
    public int Id;
    public String TenloaiSP;
    public String ImageSP;

    public LoaiSP(int id, String tenloaiSP, String imageSP) {
        Id = id;
        TenloaiSP = tenloaiSP;
        ImageSP = imageSP;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenloaiSP() {
        return TenloaiSP;
    }

    public void setTenloaiSP(String tenloaiSP) {
        TenloaiSP = tenloaiSP;
    }

    public String getImageSP() {
        return ImageSP;
    }

    public void setImageSP(String imageSP) {
        ImageSP = imageSP;
    }
}
