package com.example.customnav.model;

public class Theloai {
   private int idtheloai;
   private String tentheloai;

    public Theloai(int idtheloai, String tentheloai) {
        this.idtheloai = idtheloai;
        this.tentheloai = tentheloai;
    }

    public Theloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }

    public int getIdtheloai() {
        return idtheloai;
    }

    public void setIdtheloai(int idtheloai) {
        this.idtheloai = idtheloai;
    }

    public String getTentheloai() {
        return tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }


}
