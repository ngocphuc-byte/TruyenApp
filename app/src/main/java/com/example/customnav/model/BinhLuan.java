package com.example.customnav.model;

public class BinhLuan {
    private int idBinhluan, idTruyen;
    private String taikhoan, noidung;

    public BinhLuan(int idBinhluan, int idTruyen, String taikhoan, String noidung) {
        this.idBinhluan = idBinhluan;
        this.idTruyen = idTruyen;
        this.taikhoan = taikhoan;
        this.noidung = noidung;
    }

    public int getIdBinhluan() {
        return idBinhluan;
    }

    public void setIdBinhluan(int idBinhluan) {
        this.idBinhluan = idBinhluan;
    }

    public int getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(int idTruyen) {
        this.idTruyen = idTruyen;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
