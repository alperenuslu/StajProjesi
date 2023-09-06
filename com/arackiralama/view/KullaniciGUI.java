package com.arackiralama.view;

import com.arackiralama.helper.config;
import com.arackiralama.helper.helper;
import com.arackiralama.model.Araclar;
import com.arackiralama.model.Kullanici;
import com.arackiralama.model.Rezervasyonlar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class KullaniciGUI extends JFrame{
    private JPanel wrapper;
    private JTabbedPane tab_admin;
    private JPanel pnl_sec_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_aracsec_list;
    private JPanel pnl_araclar_list;
    private JScrollPane scrl_araclar_list;
    private JTable tbl_araclar_list;
    private JPanel pnl_araclar_add;
    private JTextField fld_useraraclar_plaka;
    private JTextField fld_useraraclar_markasi;
    private JTextField fld_useraraclar_fiyat;
    private JTextField fld_useraraclar_motorgucu;
    private JTextField fld_araclar_ehliyetno;
    private JButton btn_araclar_add;
    private JPanel pnl_booking_list;
    private JScrollPane scrl_booking_list;
    private JTable tbl_booking_list;
    private JTextField fld_booking_ehliyetno;
    private JTextField fld_booking_plaka;
    private JTextField fld_booking_rtarihi;
    private JTextField fld_booking_itarihi;
    private JTextField fld_booking_fiyat;
    private JButton btn_booking_sh;
    private JComboBox cmb_booking_ayeri;
    private JComboBox cmb_booking_iyeri;
    private JComboBox cmb_booking_durumu;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JComboBox cmb_useraraclar_vitesturu;
    private JComboBox cmb_useraraclar_yakitturu;
    private JComboBox cmb_useraraclar_konumu;
    private JButton btn_arac_sec;
    private JButton btn_arac_ara;
    private JTextField fld_arac_markasi;
    private JComboBox cmb_arac_vitesturu;
    private JComboBox cmb_arac_yakıtturu;
    private JTextField fld_arac_motorgucu;
    private JTextField fld_arac_fiyat;
    private JComboBox cmb_arac_konumu;
    private JTextField fld_arac_plaka;
    private JTextField fld_arac_rtarihi;
    private JTextField fld_arac_itarihi;
    private JTextField fld_arac_fiyatı;
    private JComboBox cmb_arac_ayeri;
    private JComboBox cmb_arac_iyeri;
    private JComboBox cmb_arac_durumu;

    private DefaultTableModel mdl_araclar_list;
    private DefaultTableModel mdl_arac_list;
    private Object[] row_araclar_list;
    private Object[] row_arac_list;
    private DefaultTableModel mdl_booking_list;
    private Object[] row_booking_list;
    private final Kullanici kullanici;

    public KullaniciGUI(Kullanici kullanici) {
        this.kullanici=kullanici;
        add(wrapper);
        setSize(1000, 600);
        setLocation(helper.screenCenterPoint("x", getSize()), helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(config.PROJECT_TITLE);
        //setResizable(false);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldin: " + kullanici.getIsim() + " " + kullanici.getSoyisim());

        mdl_araclar_list = new DefaultTableModel();
        Object[] col_araclar_list = {"plaka", "markasi", "vitesturu", "yakitturu", "gunlukfiyat", "motorgucu", "konumu"};
        mdl_araclar_list.setColumnIdentifiers(col_araclar_list);
        mdl_arac_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_arac_list = {"plaka", "markasi", "vitesturu", "yakitturu", "gunlukfiyat", "motorgucu", "konumu"};
        mdl_arac_list.setColumnIdentifiers(col_arac_list);

        tbl_araclar_list.setModel(mdl_araclar_list);
        tbl_araclar_list.getTableHeader().setReorderingAllowed(false);
        row_araclar_list = new Object[col_araclar_list.length]; // Initialize row_araclar_list here

        // Remove these redundant calls
        // loadAraclarModel();
        // loadAracModel();

        tbl_aracsec_list.setModel(mdl_arac_list);
        tbl_aracsec_list.getTableHeader().setReorderingAllowed(false);
        row_arac_list = new Object[col_arac_list.length];

        mdl_booking_list=new DefaultTableModel();
        Object[] col_booking_list={"Id","ehliyetno","plaka","rezervasyontarihi","iadetarihi","fiyat","alışyeri","iadeyeri","durumu"};
        mdl_booking_list.setColumnIdentifiers(col_booking_list);
        row_booking_list=new Object[col_booking_list.length];

        loadBookingModel();
        tbl_booking_list.setModel(mdl_booking_list);
        tbl_booking_list.getTableHeader().setReorderingAllowed(false);

        tbl_aracsec_list.getSelectionModel().addListSelectionListener(e -> {
          try {
              String select_plaka=tbl_aracsec_list.getValueAt(tbl_aracsec_list.getSelectedRow(),0).toString();
              fld_arac_plaka.setText(select_plaka);
          }
          catch (Exception exception){

          }
        });


        loadAraclarModel();
        loadAracModel();
        tbl_aracsec_list.setModel(mdl_arac_list);
        tbl_aracsec_list.getTableHeader().setReorderingAllowed(false);
        row_arac_list = new Object[col_arac_list.length]; // Initialize row_arac_list here

        loadAraclarModel();
        loadAracModel();
        //loadAraclarCombo();


        btn_araclar_add.addActionListener(e -> {
            if (helper.isFieldEmpty(fld_useraraclar_plaka) || helper.isFieldEmpty(fld_useraraclar_markasi) || helper.isFieldEmpty(fld_useraraclar_fiyat) || helper.isFieldEmpty(fld_useraraclar_motorgucu)) {
                helper.showMsg("fill");
            } else {
                int ehliyetno = kullanici.getEhliyetno();

                if (Araclar.add(fld_useraraclar_plaka.getText(), fld_useraraclar_markasi.getText(), (String) cmb_useraraclar_vitesturu.getSelectedItem(),
                        (String) cmb_useraraclar_yakitturu.getSelectedItem(), Integer.parseInt(fld_useraraclar_fiyat.getText()), Integer.parseInt(fld_useraraclar_motorgucu.getText()),
                        (String) cmb_useraraclar_konumu.getSelectedItem(), ehliyetno)) {
                    helper.showMsg("done");
                    loadAraclarModel(); // Pass the list of Arac objects to the loadAraclarModel() method
                    loadAracModel();
                    //loadAraclarCombo();
                    fld_useraraclar_plaka.setText(null);
                    fld_useraraclar_markasi.setText(null);
                    fld_useraraclar_fiyat.setText(null);
                    fld_useraraclar_motorgucu.setText(null);
                    loadAraclarModel();
                    loadAracModel();
                } else {
                    helper.showMsg("error");
                }
            }
        });

        btn_arac_ara.addActionListener(e -> {
            String markasi = fld_arac_markasi.getText();
            String vitesturu = (String) cmb_arac_vitesturu.getSelectedItem();
            String yakitturu = (String) cmb_arac_yakıtturu.getSelectedItem();
            Integer gunlukfiyat = null;
            try {
                gunlukfiyat = Integer.parseInt(fld_arac_fiyat.getText());
            } catch (NumberFormatException ex) {}
            String konumu = (String) cmb_arac_konumu.getSelectedItem();
            String query = Araclar.searchQuery(markasi, vitesturu, yakitturu, gunlukfiyat, konumu);
            loadAracModel(Araclar.searchAraclarList(query));
        });

        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login=new LoginGUI();
        });
        btn_arac_sec.addActionListener(e -> {
            if (helper.isFieldEmpty(fld_arac_plaka) || helper.isFieldEmpty(fld_arac_rtarihi) || helper.isFieldEmpty(fld_arac_itarihi) || helper.isFieldEmpty(fld_arac_fiyatı)) {
                helper.showMsg("fill");
            } else {
                int ehliyetno = kullanici.getEhliyetno();
                java.sql.Date rtarihi=java.sql.Date.valueOf(fld_arac_rtarihi.getText());
                java.sql.Date itarihi=java.sql.Date.valueOf(fld_arac_itarihi.getText());
                if (Rezervasyonlar.add(ehliyetno,fld_arac_plaka.getText(), rtarihi, itarihi,Integer.parseInt(fld_arac_fiyatı.getText()),(String) cmb_arac_ayeri.getSelectedItem(),
                        (String) cmb_arac_iyeri.getSelectedItem(), (String) cmb_arac_durumu.getSelectedItem())) {
                    helper.showMsg("done");
                    loadBookingModel();
                    fld_arac_plaka.setText(null);
                    fld_arac_rtarihi.setText(null);
                    fld_arac_itarihi.setText(null);
                    fld_arac_fiyatı.setText(null);
                    loadBookingModel();
                } else {
                    helper.showMsg("error");
                }
            }
        });

    }

    private void loadAraclarModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_araclar_list.getModel();
        clearModel.setRowCount(0);
        int i;
        int kullaniciEhliyetNo = kullanici.getEhliyetno(); // Kullanıcının ehliyet numarasını alın

        for (Araclar obj : Araclar.getList()) {
            if (obj.getEhliyetno() == kullaniciEhliyetNo) { // Sadece kullanıcının yüklediği araçları listelemek için kontrol ekleyin
                i = 0;
                row_araclar_list[i++] = obj.getPlaka();
                row_araclar_list[i++] = obj.getMarkasi();
                row_araclar_list[i++] = obj.getVitesturu();
                row_araclar_list[i++] = obj.getYakitturu();
                row_araclar_list[i++] = obj.getGunlukfiyat();
                row_araclar_list[i++] = obj.getMotorgucu();
                row_araclar_list[i++] = obj.getKonumu();
                mdl_araclar_list.addRow(row_araclar_list);
            }
        }
    }

    private void loadAracModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_aracsec_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Araclar obj : Araclar.getList()) {
            i = 0;
            row_arac_list[i++] = obj.getPlaka();
            row_arac_list[i++] = obj.getMarkasi();
            row_arac_list[i++] = obj.getVitesturu();
            row_arac_list[i++] = obj.getYakitturu();
            row_arac_list[i++] = obj.getGunlukfiyat();
            row_arac_list[i++] = obj.getMotorgucu();
            row_arac_list[i++] = obj.getKonumu();
            mdl_arac_list.addRow(row_arac_list);
        }
    }

    private void loadAraclarModel(ArrayList<Araclar> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_araclar_list.getModel();
        clearModel.setRowCount(0);
        int i;
        int kullaniciEhliyetNo = kullanici.getEhliyetno(); // Kullanıcının ehliyet numarasını alın

        for (Araclar obj : list) {
            if (obj.getEhliyetno() == kullaniciEhliyetNo) { // Sadece kullanıcının yüklediği araçları listelemek için kontrol ekleyin
                i = 0;
                row_araclar_list[i++] = obj.getPlaka();
                row_araclar_list[i++] = obj.getMarkasi();
                row_araclar_list[i++] = obj.getVitesturu();
                row_araclar_list[i++] = obj.getYakitturu();
                row_araclar_list[i++] = obj.getGunlukfiyat();
                row_araclar_list[i++] = obj.getMotorgucu();
                row_araclar_list[i++] = obj.getKonumu();
                mdl_araclar_list.addRow(row_araclar_list);
            }
        }
    }
    private void loadAracModel(ArrayList<Araclar> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_aracsec_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Araclar obj : list) {
            i = 0;
            row_arac_list[i++] = obj.getPlaka();
            row_arac_list[i++] = obj.getMarkasi();
            row_arac_list[i++] = obj.getVitesturu();
            row_arac_list[i++] = obj.getYakitturu();
            row_arac_list[i++] = obj.getGunlukfiyat();
            row_arac_list[i++] = obj.getMotorgucu();
            row_arac_list[i++] = obj.getKonumu();
            mdl_arac_list.addRow(row_arac_list);
        }
    }


    private void loadBookingModel(ArrayList<Rezervasyonlar> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_booking_list.getModel();
        clearModel.setRowCount(0);
        int i;
        int kullaniciEhliyetNo = kullanici.getEhliyetno();

        for (Rezervasyonlar obj : list) {
            if (obj.getEhliyetno() == kullaniciEhliyetNo) {
                i = 0;
                row_booking_list[i++] = obj.getId();
                row_booking_list[i++] = obj.getKullanici().getEhliyetno();
                row_booking_list[i++] = obj.getAraclar().getPlaka();
                row_booking_list[i++] = obj.getRtarihi();
                row_booking_list[i++] = obj.getItarihi();
                row_booking_list[i++] = obj.getFiyat();
                row_booking_list[i++] = obj.getAyeri();
                row_booking_list[i++] = obj.getIyeri();
                row_booking_list[i++] = obj.getDurumu();
                mdl_booking_list.addRow(row_booking_list);
            }
        }
    }

    private void loadBookingModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_booking_list.getModel();
        clearModel.setRowCount(0);
        int i;
        int kullaniciEhliyetNo = kullanici.getEhliyetno();

        for (Rezervasyonlar obj : Rezervasyonlar.getList()) {
            if (obj.getEhliyetno() == kullaniciEhliyetNo) {
                i = 0;
                row_booking_list[i++] = obj.getId();
                row_booking_list[i++] = obj.getKullanici().getEhliyetno();
                row_booking_list[i++] = obj.getAraclar().getPlaka();
                row_booking_list[i++] = obj.getRtarihi();
                row_booking_list[i++] = obj.getItarihi();
                row_booking_list[i++] = obj.getFiyat();
                row_booking_list[i++] = obj.getAyeri();
                row_booking_list[i++] = obj.getIyeri();
                row_booking_list[i++] = obj.getDurumu();
                mdl_booking_list.addRow(row_booking_list);
            }
        }
    }


}
