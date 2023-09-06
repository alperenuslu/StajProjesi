package com.arackiralama.view;

import com.arackiralama.helper.config;
import com.arackiralama.helper.helper;
import com.arackiralama.model.Kullanici;

import javax.swing.*;

public class NewUserGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_new_ehliyetno;
    private JButton btn_new_add;
    private JTextField fld_new_isim;
    private JTextField fld_new_soyisim;
    private JTextField fld_new_eposta;
    private JTextField fld_new_telefon;
    private JTextField fld_new_sifre;

    public NewUserGUI() {
        add(wrapper);
        setSize(400, 400);
        setLocation(helper.screenCenterPoint("x", getSize()), helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);


        btn_new_add.addActionListener(e -> {
            if (helper.isFieldEmpty(fld_new_ehliyetno) || helper.isFieldEmpty(fld_new_isim) || helper.isFieldEmpty(fld_new_soyisim)
                    || helper.isFieldEmpty(fld_new_eposta) || helper.isFieldEmpty(fld_new_telefon) || helper.isFieldEmpty(fld_new_sifre)) {
                helper.showMsg("fill");
            } else {
                String isim = fld_new_isim.getText();
                String soyisim = fld_new_soyisim.getText();
                String ehliyetno = fld_new_ehliyetno.getText();
                String telefon = fld_new_telefon.getText();
                String eposta = fld_new_eposta.getText();
                String sifre = fld_new_sifre.getText();
                if (ehliyetno.length() != 5 || !ehliyetno.matches("\\d+")) {
                    helper.showMsg("Ehliyet numarası 5 karakterlik bir sayı olmalıdır");
                } else if (!Character.isUpperCase(isim.charAt(0)) || isim.length() < 2) {
                    helper.showMsg("İsim en az iki harf olmalı ve baş harfi büyük olmalıdır");
                } else if (!Character.isUpperCase(soyisim.charAt(0)) || soyisim.length() < 2) {
                    helper.showMsg("Soyisim en az iki harf olmalı ve baş harfi büyük olmalıdır");
                } else if (!eposta.contains("@") || !eposta.endsWith(".com")) {
                    helper.showMsg("Geçersiz eposta adresi");
                } else if (telefon.length() != 10 || !telefon.matches("\\d+")) {
                    helper.showMsg("Telefon numarası 10 karakterlik bir sayı olmalıdır");
                } else if (sifre.length() < 8) {
                    helper.showMsg("Şifreniz 8 karakterden az olamaz");
                } else {
                    int ehliyetnoInt = Integer.parseInt(ehliyetno);
                    if (Kullanici.add(ehliyetnoInt, isim, soyisim, eposta, telefon, sifre)) {
                        helper.showMsg("done");

                        fld_new_ehliyetno.setText(null);
                        fld_new_isim.setText(null);
                        fld_new_soyisim.setText(null);
                        fld_new_eposta.setText(null);
                        fld_new_telefon.setText(null);
                        fld_new_sifre.setText(null);
                    }

                }

            }
            dispose();
        });
    }
}