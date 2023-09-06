package com.arackiralama.model;

import com.arackiralama.helper.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class Rezervasyonlar {
    private int id;
    private int ehliyetno;
    private String plaka;
    private java.sql.Date rtarihi;
    private java.sql.Date itarihi;
    private int fiyat;
    private String ayeri;
    private String iyeri;
    private String durumu;
    private Araclar araclar;
    private Kullanici kullanici;

    public Rezervasyonlar(int id, int ehliyetno, String plaka, Date rtarihi, Date itarihi, int fiyat, String ayeri, String iyeri, String durumu) {
        this.id = id;
        this.ehliyetno = ehliyetno;
        this.plaka = plaka;
        this.rtarihi = rtarihi;
        this.itarihi = itarihi;
        this.fiyat = fiyat;
        this.ayeri = ayeri;
        this.iyeri = iyeri;
        this.durumu = durumu;
        this.araclar= Araclar.getFetch(plaka);
        this.kullanici=Kullanici.getFetch(ehliyetno);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEhliyetno() {
        return ehliyetno;
    }

    public void setEhliyetno(int ehliyetno) {
        this.ehliyetno = ehliyetno;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public Date getRtarihi() {
        return rtarihi;
    }

    public void setRtarihi(Date rtarihi) {
        this.rtarihi = rtarihi;
    }

    public Date getItarihi() {
        return itarihi;
    }

    public void setItarihi(Date itarihi) {
        this.itarihi = itarihi;
    }

    public int getFiyat() {
        return fiyat;
    }

    public void setFiyat(int fiyat) {
        this.fiyat = fiyat;
    }

    public String getAyeri() {
        return ayeri;
    }

    public void setAyeri(String ayeri) {
        this.ayeri = ayeri;
    }

    public String getIyeri() {
        return iyeri;
    }

    public void setIyeri(String iyeri) {
        this.iyeri = iyeri;
    }

    public String getDurumu() {
        return durumu;
    }

    public void setDurumu(String durumu) {
        this.durumu = durumu;
    }

    public Araclar getAraclar() {
        return araclar;
    }

    public void setAraclar(Araclar araclar) {
        this.araclar = araclar;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }
    public static ArrayList<Rezervasyonlar> getList(){
        ArrayList<Rezervasyonlar> rezervasyonlarList =new ArrayList<>();
        Rezervasyonlar obj;
        try {
            Statement st= DBConnection.getInstance().createStatement();
            ResultSet rs=st.executeQuery("select * from rezervasyonlar");
            while (rs.next()){
                int id=rs.getInt("id");
                int ehliyetno=rs.getInt("ehliyetno");
                String plaka=rs.getString("plaka");
                String rtarihiString = rs.getString("rezervasyontarihi");
                String itarihiString = rs.getString("iadetarihi");
                java.sql.Date rtarihi = java.sql.Date.valueOf(rtarihiString);
                java.sql.Date itarihi = java.sql.Date.valueOf(itarihiString);
                int fiyat=rs.getInt("fiyat");
                String ayeri=rs.getString("alısyeri");
                String iyeri=rs.getString("iadeyeri");
                String durumu=rs.getString("durumu");
                obj=new Rezervasyonlar(id,ehliyetno,plaka,rtarihi,itarihi,fiyat,ayeri,iyeri,durumu);
                rezervasyonlarList.add(obj);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rezervasyonlarList;
    }
    public static boolean add(int ehliyetno, String plaka, Date rtarihi, Date itarihi, int fiyat, String ayeri, String iyeri, String durumu){
        String query = "INSERT INTO rezervasyonlar (ehliyetno, plaka, rezervasyontarihi, iadetarihi, fiyat, alısyeri, iadeyeri, durumu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pr=DBConnection.getInstance().prepareStatement(query);
            pr.setInt(1, ehliyetno);
            pr.setString(2, plaka);
            pr.setDate(3, rtarihi);
            pr.setDate(4, itarihi);
            pr.setInt(5, fiyat);
            pr.setString(6, ayeri);
            pr.setString(7, iyeri);
            pr.setString(8, durumu);
            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean delete(int ehliyetno){
        String query="delete from rezervasyonlar where ehliyetno=?";
        try {
            PreparedStatement pr = DBConnection.getInstance().prepareStatement(query);
            pr.setInt(1,ehliyetno);
            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean delete(String plaka) {
        String query = "DELETE FROM rezervasyonlar WHERE plaka=?";
        try {
            PreparedStatement pr = DBConnection.getInstance().prepareStatement(query);
            pr.setString(1, plaka);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Rezervasyonlar> getListByRezervasyonlar(int ehliyetno) {
        ArrayList<Rezervasyonlar> rezervasyonlarList = new ArrayList<>();
        Rezervasyonlar obj;
        try {
            Statement st = DBConnection.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM rezervasyonlar WHERE ehliyetno=" + ehliyetno);
            while (rs.next()) {
                obj = new Rezervasyonlar(rs.getInt("id"), rs.getInt("ehliyetno"), rs.getString("plaka"),
                        rs.getDate("rezervasyontarihi"), rs.getDate("iadetarihi"), rs.getInt("fiyat"),
                        rs.getString("alısyeri"), rs.getString("iadeyeri"), rs.getString("durumu"));
                rezervasyonlarList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rezervasyonlarList;
    }
    public static ArrayList<Rezervasyonlar> getListByRezervasyonlarPlaka(String plaka) {
        ArrayList<Rezervasyonlar> rezervasyonlarList = new ArrayList<>();
        Rezervasyonlar obj;
        try {
            Statement st = DBConnection.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM rezervasyonlar WHERE plaka='" + plaka + "'");
            while (rs.next()) {
                obj = new Rezervasyonlar(rs.getInt("id"), rs.getInt("ehliyetno"), rs.getString("plaka"),
                        rs.getDate("rezervasyontarihi"), rs.getDate("iadetarihi"), rs.getInt("fiyat"),
                        rs.getString("alısyeri"), rs.getString("iadeyeri"), rs.getString("durumu"));
                rezervasyonlarList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rezervasyonlarList;
    }


}
