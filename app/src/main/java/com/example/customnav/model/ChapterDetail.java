package com.example.customnav.model;

public class ChapterDetail {
    private String machap;
    private int idtruyen,tenchap;
    private String tieude, noidung;
    private String ngaycapnhat;

    public ChapterDetail(String machap, int idtruyen, int tenchap, String tieude, String noidung, String ngaycapnhat) {
        this.machap = machap;
        this.idtruyen = idtruyen;
        this.tenchap = tenchap;
        this.tieude = tieude;
        this.noidung = noidung;
        this.ngaycapnhat = ngaycapnhat;
    }
    public String getMachap() {
        return machap;
    }

    public void setMachap(String machap) {
        this.machap = machap;
    }

    public int getIdtruyen() {
        return idtruyen;
    }

    public void setIdtruyen(int idtruyen) {
        this.idtruyen = idtruyen;
    }

    public int getTenchap() {
        return tenchap;
    }

    public void setTenchap(int tenchap) {
        this.tenchap = tenchap;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNgaycapnhat() {
        return ngaycapnhat;
    }

    public void setNgaycapnhat(String ngaycapnhat) {
        this.ngaycapnhat = ngaycapnhat;
    }

}
