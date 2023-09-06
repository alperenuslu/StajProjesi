package com.arackiralama.view;

import com.arackiralama.helper.config;
import com.arackiralama.helper.helper;
import com.arackiralama.model.Kullanici;
import com.arackiralama.model.admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_user_mail;
    private JPasswordField fld_user_pass;
    private JButton btn_login;
    private JButton btn_signup;

    public LoginGUI(){
        add(wrapper);
        setSize(400,400);
        setLocation(helper.screenCenterPoint("x",getSize()),helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);



        btn_login.addActionListener(e -> {
            if (helper.isFieldEmpty(fld_user_mail) || helper.isFieldEmpty(fld_user_pass)){
                helper.showMsg("fill");
            }
            else {
                Kullanici k = Kullanici.getFetch(fld_user_mail.getText(), fld_user_pass.getText());
                if (k == null){
                    // Check if the user exists in the database
                    Kullanici user = Kullanici.getUserByEmail(fld_user_mail.getText());
                    if (user == null) {
                        helper.showMsg("Kullanıcı Bulunamadı!");
                    } else {
                        helper.showMsg("Şifre Yanlış!");
                    }
                } else {
                    switch (k.getEposta()){
                        case "admin":
                            adminGUI adGUI = new adminGUI((admin) k);
                            break;
                        default:
                            KullaniciGUI kuGUI = new KullaniciGUI((Kullanici) k);
                            break;
                    }
                    //helper.showMsg(k.getIsim() + " " + k.getSoyisim());
                    dispose();
                }
            }
        });



        btn_signup.addActionListener(e -> {
            NewUserGUI newGUI =new NewUserGUI();
        });

    }




    public static void main(String[] args){
        LoginGUI login=new LoginGUI();
        helper.setLayout();
    }

}
