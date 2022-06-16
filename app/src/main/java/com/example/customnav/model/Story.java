package com.example.customnav.model;

public class Story {
    private int idTruyen;
    private int idTheLoai;
    private String tacgia, tentruyen, tomtat, tinhtrang, hinhtruyen;
    private int slLike, slYeuThich;
    private String nhomdich, tentheloai;

    public Story(int idTruyen, int idTheLoai, String tacgia, String tentruyen, String tomtat, String tinhtrang, String hinhtruyen, int slLike, int slYeuThich, String nhomdich, String tentheloai) {
        this.idTruyen = idTruyen;
        this.idTheLoai = idTheLoai;
        this.tacgia = tacgia;
        this.tentruyen = tentruyen;
        this.tomtat = tomtat;
        this.tinhtrang = tinhtrang;
        this.hinhtruyen = hinhtruyen;
        this.slLike = slLike;
        this.slYeuThich = slYeuThich;
        this.nhomdich = nhomdich;
        this.tentheloai = tentheloai;
    }

    public int getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(int idTruyen) {
        this.idTruyen = idTruyen;
    }

    public int getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(int idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getTentruyen() {
        return tentruyen;
    }

    public void setTentruyen(String tentruyen) {
        this.tentruyen = tentruyen;
    }

    public String getTomtat() {
        return tomtat;
    }

    public void setTomtat(String tomtat) {
        this.tomtat = tomtat;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public String getHinhtruyen() {
        return hinhtruyen;
    }

    public void setHinhtruyen(String hinhtruyen) {
        this.hinhtruyen = hinhtruyen;
    }

    public int getSlLike() {
        return slLike;
    }

    public void setSlLike(int slLike) {
        this.slLike = slLike;
    }

    public int getSlYeuThich() {
        return slYeuThich;
    }

    public void setSlYeuThich(int slYeuThich) {
        this.slYeuThich = slYeuThich;
    }

    public String getNhomdich() {
        return nhomdich;
    }

    public void setNhomdich(String nhomdich) {
        this.nhomdich = nhomdich;
    }

    public String getTentheloai() {
        return tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }
}
