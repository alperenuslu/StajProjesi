package com.arackiralama.model;

import com.arackiralama.helper.DBConnection;
import com.arackiralama.helper.helper;
import org.w3c.dom.html.HTMLBRElement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Kullanici {
    private int ehliyetno;
    private String isim;
    private String soyisim;
    private String eposta;
    private String telefon;
    private String sifre;

    public Kullanici(){}
    public Kullanici(int ehliyetno, String isim, String soyisim, String eposta, String telefon, String sifre) {
        this.ehliyetno = ehliyetno;
        this.isim = isim;
        this.soyisim = soyisim;
        this.eposta = eposta;
        this.telefon = telefon;
        this.sifre = sifre;
    }

    public int getEhliyetno() {
        return ehliyetno;
    }

    public void setEhliyetno(int ehliyetno) {
        this.ehliyetno = ehliyetno;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public static ArrayList<Kullanici> getList(){
        ArrayList<Kullanici> kullaniciList=new ArrayList<>();
        String query = "Select * from Kullanicilar";
        Kullanici obj;
        try {
            Statement st = DBConnection.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj=new Kullanici();
                obj.setEhliyetno(rs.getInt("ehliyetno"));
                obj.setIsim(rs.getString("isim"));
                obj.setSoyisim(rs.getString("soyisim"));
                obj.setEposta(rs.getString("eposta"));
                obj.setTelefon(rs.getString("telefon"));
                obj.setSifre(rs.getString("sifre"));
                kullaniciList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return kullaniciList;
    }

    public static boolean add(int ehliyetno, String isim, String soyisim, String eposta, String telefon, String sifre) {
        String query = "insert into kullanicilar (ehliyetno,isim,soyisim,eposta,telefon,sifre) values (?,?,?,?,?,?)";
        Kullanici findUser = Kullanici.getFetch(ehliyetno);
        Kullanici findMail = Kullanici.getFetchMail(eposta);
        Kullanici findPhone = Kullanici.getFetchPhone(telefon);
        if (findMail != null) {
            helper.showMsg("Bu eposta daha önceden eklenmiş, Tekrar deneyiniz.");
            return false;
        }
        if (findUser != null) {
            helper.showMsg("Bu ehliyet no daha önceden eklenmiş, Tekrar deneyiniz.");
            return false;
        }
        if (findPhone != null) {
            helper.showMsg("Bu telefon no daha önceden eklenmiş, Tekrar deneyiniz.");
            return false;
        }
        try {
            PreparedStatement pr = DBConnection.getInstance().prepareStatement(query);
            pr.setInt(1, ehliyetno);
            pr.setString(2, isim);
            pr.setString(3, soyisim);
            pr.setString(4, eposta);
            pr.setString(5, telefon);
            pr.setString(6, sifre);
            int response = pr.executeUpdate();
            if (response == -1) {
                helper.showMsg("error");
            }
            return response != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static Kullanici getFetch(int ehliyetno){
        Kullanici obj=null;
        String query ="select * from kullanicilar where ehliyetno=?";
        try {
            PreparedStatement pr = DBConnection.getInstance().prepareStatement(query);
            pr.setInt(1,ehliyetno);
            ResultSet rs= pr.executeQuery();
            if (rs.next()){
                obj=new Kullanici();
                obj.setEhliyetno(rs.getInt("ehliyetno"));
                obj.setIsim(rs.getString("isim"));
                obj.setSoyisim(rs.getString("soyisim"));
                obj.setEposta(rs.getString("eposta"));
                obj.setTelefon(rs.getString("telefon"));
                obj.setSifre(rs.getString("sifre"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }
    public static Kullanici getFetchMail(String eposta){
        Kullanici obj=null;
        String query ="select * from kullanicilar where eposta=?";
        try {
            PreparedStatement pr = DBConnection.getInstance().prepareStatement(query);
            pr.setString(1,eposta);
            ResultSet rs= pr.executeQuery();
            if (rs.next()){
                obj=new Kullanici();
                obj.setEhliyetno(rs.getInt("ehliyetno"));
                obj.setIsim(rs.getString("isim"));
                obj.setSoyisim(rs.getString("soyisim"));
                obj.setEposta(rs.getString("eposta"));
                obj.setTelefon(rs.getString("telefon"));
                obj.setSifre(rs.getString("sifre"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }
    public static Kullanici getFetchPhone(String telefon) {
        Kullanici obj = null;
        String query = "select * from kullanicilar where telefon=?";
        try {
            PreparedStatement pr = DBConnection.getInstance().prepareStatement(query);
            pr.setString(1, telefon);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Kullanici();
                obj.setEhliyetno(rs.getInt("ehliyetno"));
                obj.setIsim(rs.getString("isim"));
                obj.setSoyisim(rs.getString("soyisim"));
                obj.setEposta(rs.getString("eposta"));
                obj.setTelefon(rs.getString("telefon"));
                obj.setSifre(rs.getString("sifre"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }


    public static Kullanici getFetch(String eposta,String sifre) {
        Kullanici obj = null;
        String query = "select * from kullanicilar where eposta=? and sifre=?";
        try {
            PreparedStatement pr = DBConnection.getInstance().prepareStatement(query);
            pr.setString(1, eposta);
            pr.setString(2, sifre);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                switch (rs.getString("eposta")){
                    case "admin":
                        obj = new admin();
                        break;
                    default:
                        obj = new Kullanici();
                }
                obj.setEhliyetno(rs.getInt("ehliyetno"));
                obj.setIsim(rs.getString("isim"));
                obj.setSoyisim(rs.getString("soyisim"));
                obj.setEposta(rs.getString("eposta"));
                obj.setTelefon(rs.getString("telefon"));
                obj.setSifre(rs.getString("sifre"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static Kullanici getUserByEmail(String eposta) {
        Kullanici obj = null;
        String query = "select * from kullanicilar where eposta=?";
        try {
            PreparedStatement pr = DBConnection.getInstance().prepareStatement(query);
            pr.setString(1, eposta);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                switch (rs.getString("eposta")){
                    case "admin":
                        obj = new admin();
                        break;
                    default:
                        obj = new Kullanici();
                }
                obj.setEhliyetno(rs.getInt("ehliyetno"));
                obj.setIsim(rs.getString("isim"));
                obj.setSoyisim(rs.getString("soyisim"));
                obj.setEposta(rs.getString("eposta"));
                obj.setTelefon(rs.getString("telefon"));
                obj.setSifre(rs.getString("sifre"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }




    public static boolean delete(int ehliyetno) {
        String query = "DELETE FROM kullanicilar WHERE ehliyetno=?";
        ArrayList<Araclar> araclarList = Araclar.getListByAraclar(ehliyetno);
        for (Araclar a : araclarList) {
            Araclar.delete(a.getEhliyetno());
        }
        ArrayList<Rezervasyonlar> rezervasyonlarList = Rezervasyonlar.getListByRezervasyonlar(ehliyetno);
        for (Rezervasyonlar r : rezervasyonlarList) {
            Rezervasyonlar.delete(r.getEhliyetno());
        }
        try {
            PreparedStatement pr = DBConnection.getInstance().prepareStatement(query);
            pr.setInt(1, ehliyetno);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean update(int ehliyetno,String isim,String soyisim,String eposta,String telefon,String sifre){
        String query = "update kullanicilar set isim=?,soyisim=?,eposta=?,telefon=?,sifre=? where ehliyetno=?";
        Kullanici findUser = Kullanici.getFetch(ehliyetno);
        if (findUser != null && findUser.getEhliyetno() != ehliyetno){
            helper.showMsg("Bu ehliyet no daha önceden eklenmiş, Tekrar deneyiniz.");
            return false;
        }
        try {
            PreparedStatement pr= DBConnection.getInstance().prepareStatement(query);
            pr.setString(1,isim);
            pr.setString(2,soyisim);
            pr.setString(3,eposta);
            pr.setString(4,telefon);
            pr.setString(5,sifre);
            pr.setInt(6,ehliyetno);
            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Kullanici> searchUserList(String query){
        ArrayList<Kullanici> kullaniciList=new ArrayList<>();
        Kullanici obj;
        try {
            Statement st = DBConnection.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj=new Kullanici();
                obj.setEhliyetno(rs.getInt("ehliyetno"));
                obj.setIsim(rs.getString("isim"));
                obj.setSoyisim(rs.getString("soyisim"));
                obj.setEposta(rs.getString("eposta"));
                obj.setTelefon(rs.getString("telefon"));
                obj.setSifre(rs.getString("sifre"));
                kullaniciList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return kullaniciList;
    }
    public static String searchQuery(Integer ehliyetno, String isim, String soyisim){
        String query = "SELECT * FROM kullanicilar WHERE 1=1";
        if (ehliyetno != null) {
            query += " AND CAST(ehliyetno AS text) LIKE '%{{ehliyetno}}%'";
            query = query.replace("{{ehliyetno}}", Integer.toString(ehliyetno));
        }
        query += " AND isim LIKE '%{{isim}}%' AND soyisim LIKE '%{{soyisim}}%'";
        query = query.replace("{{isim}}", isim);
        query = query.replace("{{soyisim}}", soyisim);
        return query;
    }

}
