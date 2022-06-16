package com.example.customnav.model;

public class YeuThich {
    private int idyeuthich, idtruyen, idtheloai,sllike, slyeuthich;
    private String tentruyen, tacgia, tinhtrang, tomtat, taikhoannhomdich, hinhtruyen, taikhoan, tentheloai;

    public YeuThich(int idyeuthich, int idtruyen, int idtheloai, int sllike, int slyeuthich, String tentruyen, String tacgia, String tinhtrang, String tomtat, String taikhoannhomdich, String hinhtruyen, String taikhoan, String tentheloai) {
        this.idyeuthich = idyeuthich;
        this.idtruyen = idtruyen;
        this.idtheloai = idtheloai;
        this.sllike = sllike;
        this.slyeuthich = slyeuthich;
        this.tentruyen = tentruyen;
        this.tacgia = tacgia;
        this.tinhtrang = tinhtrang;
        this.tomtat = tomtat;
        this.taikhoannhomdich = taikhoannhomdich;
        this.hinhtruyen = hinhtruyen;
        this.taikhoan = taikhoan;
        this.tentheloai = tentheloai;
    }

    public int getIdyeuthich() {
        return idyeuthich;
    }

    public void setIdyeuthich(int idyeuthich) {
        this.idyeuthich = idyeuthich;
    }

    public int getIdtruyen() {
        return idtruyen;
    }

    public void setIdtruyen(int idtruyen) {
        this.idtruyen = idtruyen;
    }

    public int getIdtheloai() {
        return idtheloai;
    }

    public void setIdtheloai(int idtheloai) {
        this.idtheloai = idtheloai;
    }

    public int getSllike() {
        return sllike;
    }

    public void setSllike(int sllike) {
        this.sllike = sllike;
    }

    public int getSlyeuthich() {
        return slyeuthich;
    }

    public void setSlyeuthich(int slyeuthich) {
        this.slyeuthich = slyeuthich;
    }

    public String getTentruyen() {
        return tentruyen;
    }

    public void setTentruyen(String tentruyen) {
        this.tentruyen = tentruyen;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public String getTomtat() {
        return tomtat;
    }

    public void setTomtat(String tomtat) {
        this.tomtat = tomtat;
    }

    public String getTaikhoannhomdich() {
        return taikhoannhomdich;
    }

    public void setTaikhoannhomdich(String taikhoannhomdich) {
        this.taikhoannhomdich = taikhoannhomdich;
    }

    public String getHinhtruyen() {
        return hinhtruyen;
    }

    public void setHinhtruyen(String hinhtruyen) {
        this.hinhtruyen = hinhtruyen;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getTentheloai() {
        return tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }
}
