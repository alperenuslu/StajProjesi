package com.arackiralama.model;

import com.arackiralama.helper.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Araclar {
    private String plaka;
    private String markasi;
    private String vitesturu;
    private String yakitturu;
    private int gunlukfiyat;
    private int motorgucu;
    private String konumu;
    private int ehliyetno;

    public Araclar(String plaka, String markasi, String vitesturu, String yakitturu, int gunlukfiyat, int motorgucu, String konumu, int ehliyetno) {
        this.plaka = plaka;
        this.markasi = markasi;
        this.vitesturu = vitesturu;
        this.yakitturu = yakitturu;
        this.gunlukfiyat = gunlukfiyat;
        this.motorgucu = motorgucu;
        this.konumu = konumu;
        this.ehliyetno = ehliyetno;
    }

    public Araclar() {

    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public String getMarkasi() {
        return markasi;
    }

    public void setMarkasi(String markasi) {
        this.markasi = markasi;
    }

    public String getVitesturu() {
        return vitesturu;
    }

    public void setVitesturu(String vitesturu) {
        this.vitesturu = vitesturu;
    }

    public String getYakitturu() {
        return yakitturu;
    }

    public void setYakitturu(String yakitturu) {
        this.yakitturu = yakitturu;
    }

    public int getGunlukfiyat() {
        return gunlukfiyat;
    }

    public void setGunlukfiyat(int gunlukfiyat) {
        this.gunlukfiyat = gunlukfiyat;
    }

    public int getMotorgucu() {
        return motorgucu;
    }

    public void setMotorgucu(int motorgucu) {
        this.motorgucu = motorgucu;
    }

    public String getKonumu() {
        return konumu;
    }

    public void setKonumu(String konumu) {
        this.konumu = konumu;
    }

    public int getEhliyetno() {
        return ehliyetno;
    }

    public void setEhliyetno(int ehliyetno) {
        this.ehliyetno = ehliyetno;
    }
    public static ArrayList<Araclar> getList(){
        ArrayList<Araclar> araclarList=new ArrayList<>();
        Araclar obj;
        try {
            Statement st= DBConnection.getInstance().createStatement();
            ResultSet rs=st.executeQuery("select * from araclar");
            while (rs.next()){
                obj=new Araclar(rs.getString("plaka"),rs.getString("markasi"),rs.getString("vitesturu"),
                        rs.getString("yakitturu"),rs.getInt("gunlukfiyat"),rs.getInt("motorgucu"),
                        rs.getString("konumu"),rs.getInt("ehliyetno"));
                araclarList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return araclarList;
    }
    public static boolean add(String plaka, String markasi, String vitesturu, String yakitturu, int gunlukfiyat, int motorgucu, String konumu, int ehliyetno){
        String query = "INSERT INTO araclar (plaka, markasi, vitesturu, yakitturu, gunlukfiyat, motorgucu, konumu, ehliyetno) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pr= DBConnection.getInstance().prepareStatement(query);
            pr.setString(1, plaka);
            pr.setString(2, markasi);
            pr.setString(3, vitesturu);
            pr.setString(4, yakitturu);
            pr.setInt(5, gunlukfiyat);
            pr.setInt(6, motorgucu);
            pr.setString(7, konumu);
            pr.setInt(8, ehliyetno);
            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean update(String plaka, String markasi, String vitesturu, String yakitturu, int gunlukfiyat, int motorgucu, String konumu, int ehliyetno){
        String query = "UPDATE araclar SET markasi = ?, vitesturu = ?, yakitturu = ?, gunlukfiyat = ?, motorgucu = ?, konumu = ?, ehliyetno = ? WHERE plaka = ?";
        try {
            PreparedStatement pr= DBConnection.getInstance().prepareStatement(query);
            pr.setString(1, markasi);
            pr.setString(2, vitesturu);
            pr.setString(3, yakitturu);
            pr.setInt(4, gunlukfiyat);
            pr.setInt(5, motorgucu);
            pr.setString(6, konumu);
            pr.setInt(7, ehliyetno);
            pr.setString(8, plaka);
            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Araclar getFetch(String plaka) {
        Araclar obj = null;
        String query = "select * from araclar where plaka=?";
        try {
            PreparedStatement pr = DBConnection.getInstance().prepareStatement(query);
            pr.setString(1, plaka);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Araclar(
                        rs.getString("plaka"),
                        rs.getString("markasi"),
                        rs.getString("vitesturu"),
                        rs.getString("yakitturu"),
                        rs.getInt("gunlukfiyat"),
                        rs.getInt("motorgucu"),
                        rs.getString("konumu"),
                        rs.getInt("ehliyetno")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static boolean delete(String plaka) {
        String query = "DELETE FROM araclar WHERE plaka=?";
        ArrayList<Rezervasyonlar> rezervasyonlarList = Rezervasyonlar.getListByRezervasyonlarPlaka(plaka);
        for (Rezervasyonlar r : rezervasyonlarList) {
            Rezervasyonlar.delete(r.getPlaka());
        }
        try {
            PreparedStatement pr = DBConnection.getInstance().prepareStatement(query);
            pr.setString(1, plaka);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Araclar> getListByAraclar(int ehliyetno){
        ArrayList<Araclar> araclarList=new ArrayList<>();
        Araclar obj;
        try {
            Statement st= DBConnection.getInstance().createStatement();
            ResultSet rs=st.executeQuery("select * from araclar where ehliyetno="+ehliyetno);
            while (rs.next()){
                obj=new Araclar(rs.getString("plaka"),rs.getString("markasi"),rs.getString("vitesturu"),
                        rs.getString("yakitturu"),rs.getInt("gunlukfiyat"),rs.getInt("motorgucu"),
                        rs.getString("konumu"),rs.getInt("ehliyetno"));
                araclarList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return araclarList;
    }
    public static boolean delete(int ehliyetno){
        String query="delete from araclar where ehliyetno=?";

        try {
            PreparedStatement pr = DBConnection.getInstance().prepareStatement(query);
            pr.setInt(1,ehliyetno);
            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Araclar> searchAraclarList(String query) {
        ArrayList<Araclar> araclarList = new ArrayList<>();
        Araclar obj;
        try {
            Statement st = DBConnection.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Araclar();
                obj.setPlaka(rs.getString("plaka"));
                obj.setMarkasi(rs.getString("markasi"));
                obj.setVitesturu(rs.getString("vitesturu"));
                obj.setYakitturu(rs.getString("yakitturu"));
                obj.setGunlukfiyat(rs.getInt("gunlukfiyat"));
                obj.setMotorgucu(rs.getInt("motorgucu"));
                obj.setKonumu(rs.getString("konumu"));
                obj.setEhliyetno(rs.getInt("ehliyetno"));
                araclarList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return araclarList;
    }





    public static String searchQuery(String markasi, String vitesturu, String yakitturu,Integer gunlukfiyat,String konumu) {
        String query = "SELECT * FROM araclar WHERE 1=1";
        if (markasi != null) {
            query += " AND markasi LIKE '%{{markasi}}%'";
            query = query.replace("{{markasi}}", markasi);
        }
        if (vitesturu != null) {
            query += " AND vitesturu LIKE '%{{vitesturu}}%'";
            query = query.replace("{{vitesturu}}", vitesturu);
        }
        if (yakitturu != null) {
            query += " AND yakitturu LIKE '%{{yakitturu}}%'";
            query = query.replace("{{yakitturu}}", yakitturu);
        }
        if (gunlukfiyat != null) {
            query += " AND gunlukfiyat = {{gunlukfiyat}}";
            query = query.replace("{{gunlukfiyat}}", Integer.toString(gunlukfiyat));
        }
        if (konumu != null) {
            query += " AND konumu LIKE '%{{konumu}}%'";
            query = query.replace("{{konumu}}", konumu);
        }

        return query;
    }

}


