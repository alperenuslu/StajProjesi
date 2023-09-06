package com.arackiralama.view;

import com.arackiralama.helper.config;
import com.arackiralama.helper.helper;
import com.arackiralama.model.Araclar;

import javax.swing.*;

public class UpdateAraclarGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_araclar_plaka;
    private JButton btn_update;
    private JTextField fld_araclar_markasi;
    private JTextField fld_araclar_vitesturu;
    private JTextField fld_araclar_yakitturu;
    private JTextField fld_araclar_gunlukfiyat;
    private JTextField fld_araclar_motorgucu;
    private JTextField fld_araclar_konumu;
    private JTextField fld_araclar_ehliyetno;
    private Araclar araclar;
    public UpdateAraclarGUI(Araclar araclar){
        this.araclar =araclar;
        add(wrapper);
        setSize(300,400);
        setLocation(helper.screenCenterPoint("x",getSize()),helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(config.PROJECT_TITLE);
        setVisible(true);
        fld_araclar_plaka.setText(araclar.getPlaka());
        fld_araclar_markasi.setText(araclar.getMarkasi());
        fld_araclar_vitesturu.setText(araclar.getVitesturu());
        fld_araclar_yakitturu.setText(araclar.getYakitturu());
        fld_araclar_gunlukfiyat.setText(String.valueOf(araclar.getGunlukfiyat()));
        fld_araclar_motorgucu.setText(String.valueOf(araclar.getMotorgucu()));
        fld_araclar_konumu.setText(araclar.getKonumu());
        fld_araclar_ehliyetno.setText(String.valueOf(araclar.getEhliyetno()));

        btn_update.addActionListener(e -> {
            if (helper.isFieldEmpty(fld_araclar_plaka) || helper.isFieldEmpty(fld_araclar_markasi) || helper.isFieldEmpty(fld_araclar_vitesturu)
                    || helper.isFieldEmpty(fld_araclar_yakitturu) || helper.isFieldEmpty(fld_araclar_gunlukfiyat) || helper.isFieldEmpty(fld_araclar_motorgucu)
                    || helper.isFieldEmpty(fld_araclar_konumu) || helper.isFieldEmpty(fld_araclar_ehliyetno)){
                helper.showMsg("fill");
            }
            else {
                if (Araclar.update(araclar.getPlaka(), fld_araclar_markasi.getText(), fld_araclar_vitesturu.getText(),
                        fld_araclar_yakitturu.getText(), Integer.parseInt(fld_araclar_gunlukfiyat.getText()),
                        Integer.parseInt(fld_araclar_motorgucu.getText()),
                        fld_araclar_konumu.getText(), Integer.parseInt(fld_araclar_ehliyetno.getText()))) {
                    helper.showMsg("done");
                }


                dispose();
            }
        });
    }

}
