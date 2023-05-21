package com.info.benimisim;

public class Kullanıcı {
    private String Email;
    private String Telefon;
    private String İş_Tanımı;
    private String ücret;
    private String Konum;
    private String id;
    private String baslik;
    private String ülkeadi;
    private String sehir;
    private String mahalle;


    public Kullanıcı(String Email, String Telefon, String İş_Tanımı, String ücret, String Konum, String id, String baslik,String ülkeadi,String sehir,String mahalle) {
        this.Email = Email;
        this.Telefon = Telefon;
        this.İş_Tanımı = İş_Tanımı;
        this.ücret = ücret;
        this.Konum = Konum;
        this.id=id;
        this.baslik=baslik;
        this.ülkeadi=ülkeadi;
        this.sehir=sehir;
        this.mahalle=mahalle;
    }

    public Kullanıcı() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String Telefon) {
        this.Telefon = Telefon;
    }

    public String getIş_Tanımı() {
        return İş_Tanımı;
    }

    public void setIş_Tanımı(String iş_Tanımı) {
        this.İş_Tanımı = iş_Tanımı;
    }

    public String getücret() {
        return ücret;
    }

    public void setücret(String ücret) {
        this.ücret = ücret;
    }

    public String getKonum() {
        return Konum;
    }

    public void setKonum(String Konum) {
        this.Konum = Konum;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getÜlkeadi() {
        return ülkeadi;
    }

    public void setÜlkeadi(String ülkeadi) {
        this.ülkeadi = ülkeadi;
    }

    public String getSehir() {
        return sehir;
    }

    public void setSehir(String sehir) {
        this.sehir = sehir;
    }

    public String getMahalle() {
        return mahalle;
    }

    public void setMahalle(String mahalle) {
        this.mahalle = mahalle;
    }
}
