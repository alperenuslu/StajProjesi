package com.arackiralama.model;

import com.arackiralama.helper.DBConnection;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Faturalar {
    private int faturaid;
    private int rezervasyonid;
    private int fiyat;
    private String kartno;
    private int cvv;
    private java.sql.Date karttarihi;

    public Faturalar(int faturaid, int rezervasyonid, int fiyat, String kartno, int cvv, Date karttarihi) {
        this.faturaid = faturaid;
        this.rezervasyonid = rezervasyonid;
        this.fiyat = fiyat;
        this.kartno = kartno;
        this.cvv = cvv;
        this.karttarihi = karttarihi;
    }

    public int getFaturaid() {
        return faturaid;
    }

    public void setFaturaid(int faturaid) {
        this.faturaid = faturaid;
    }

    public int getRezervasyonid() {
        return rezervasyonid;
    }

    public void setRezervasyonid(int rezervasyonid) {
        this.rezervasyonid = rezervasyonid;
    }

    public int getFiyat() {
        return fiyat;
    }

    public void setFiyat(int fiyat) {
        this.fiyat = fiyat;
    }

    public String getKartno() {
        return kartno;
    }

    public void setKartno(String kartno) {
        this.kartno = kartno;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public Date getKarttarihi() {
        return karttarihi;
    }

    public void setKarttarihi(Date karttarihi) {
        this.karttarihi = karttarihi;
    }
    public static ArrayList<Faturalar> getList(){
        ArrayList<Faturalar> faturalarList=new ArrayList<>();
        Faturalar obj;
        try {
            Statement st= DBConnection.getInstance().createStatement();
            ResultSet rs= st.executeQuery("select * from faturalar");
            while (rs.next()){
                String karttarihiString = rs.getString("karttarihi");
                java.sql.Date karttarihi = java.sql.Date.valueOf(karttarihiString);
                obj=new Faturalar(rs.getInt("faturaid"),rs.getInt("rezervasyonid"),rs.getInt("fiyat"),rs.getString("kartno"),rs.getInt("cvv"), karttarihi);
                faturalarList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return faturalarList;
    }

}