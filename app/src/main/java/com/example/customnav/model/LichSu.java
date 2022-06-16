package com.example.customnav.model;

public class LichSu {
    int idLichSu, maChap, idTruyen, tenChap;
    String tenTruyen,tieuDe, noiDung, ngayCapNhat, taiKhoan, hinhTruyen;

    public LichSu(int idLichSu, int maChap, int idTruyen, int tenChap, String tenTruyen, String tieuDe, String noiDung, String ngayCapNhat, String taiKhoan, String hinhTruyen) {
        this.idLichSu = idLichSu;
        this.maChap = maChap;
        this.idTruyen = idTruyen;
        this.tenChap = tenChap;
        this.tenTruyen = tenTruyen;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.ngayCapNhat = ngayCapNhat;
        this.taiKhoan = taiKhoan;
        this.hinhTruyen = hinhTruyen;
    }

    public int getIdLichSu() {
        return idLichSu;
    }

    public void setIdLichSu(int idLichSu) {
        this.idLichSu = idLichSu;
    }

    public int getMaChap() {
        return maChap;
    }

    public void setMaChap(int maChap) {
        this.maChap = maChap;
    }

    public int getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(int idTruyen) {
        this.idTruyen = idTruyen;
    }

    public int getTenChap() {
        return tenChap;
    }

    public void setTenChap(int tenChap) {
        this.tenChap = tenChap;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(String ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getHinhTruyen() {
        return hinhTruyen;
    }

    public void setHinhTruyen(String hinhTruyen) {
        this.hinhTruyen = hinhTruyen;
    }
}

