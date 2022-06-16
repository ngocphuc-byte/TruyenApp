package com.example.customnav.model;

public class News {
    private int idnews;
    private String tieude,noidung,url,image;

    public News(int idnews, String tieude, String noidung,String url, String image) {
        this.idnews = idnews;
        this.tieude = tieude;
        this.noidung = noidung;
        this.url = url;
        this.image = image;
    }

    public int getIdnews() {
        return idnews;
    }

    public void setIdnews(int idnews) {
        this.idnews = idnews;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
