package com.arackiralama.view;

import com.arackiralama.helper.*;
import com.arackiralama.model.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class adminGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane tab_admin;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_isim;
    private JTextField fld_user_email;
    private JTextField fld_user_telefon;
    private JTextField fld_user_sifre;
    private JButton btn_user_add;
    private JTextField fld_user_soyisim;
    private JTextField fld_user_ehliyetno;
    private JButton btn_user_delete;
    private JTextField fld_sh_user_isim;
    private JTextField fld_sh_user_soyisim;
    private JButton btn_user_sh;
    private JTextField fld_sh_user_ehliyetno;
    private JPanel pnl_araclar_list;
    private JScrollPane scrl_araclar_list;
    private JTable tbl_araclar_list;
    private JPanel pnl_araclar_add;
    private JTextField fld_araclar_plaka;
    private JTextField fld_araclar_vites;
    private JTextField fld_araclar_yakit;
    private JTextField fld_araclar_fiyat;
    private JTextField fld_araclar_motorgucu;
    private JTextField fld_araclar_konumu;
    private JTextField fld_araclar_ehliyetno;
    private JButton btn_araclar_add;
    private JTextField fld_araclar_markasi;
    private JPanel pnl_booking_list;
    private JScrollPane scrl_booking_list;
    private JTable tbl_booking_list;
    private JTextField fld_booking_ehliyetno;
    private JTextField fld_booking_plaka;
    private JComboBox cmb_booking_ayeri;
    private JComboBox cmb_booking_iyeri;
    private JComboBox cmb_booking_durumu;
    private JTextField fld_booking_rtarihi;
    private JTextField fld_booking_itarihi;
    private JTextField fld_booking_fiyat;
    private JButton btn_booking_add;
    private JPanel pnl_faturalar_list;
    private JTable tbl_faturalar_list;
    private JComboBox cmb_araclar_vitesturu;
    private JComboBox cmb_araclar_yakitturu;
    private JComboBox cmb_araclar_konumu;
    private JTextField fld_sh_user_isiö;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_araclar_list;
    private Object[] row_araclar_list;
    private JPopupMenu araclarMenu;
    private DefaultTableModel mdl_booking_list;
    private Object[] row_booking_list;
    private DefaultTableModel mdl_faturalar_list;
    private Object[] row_faturalar_list;
    private final admin Admin;

    public adminGUI(admin Admin){
        this.Admin=Admin;

        add(wrapper);
        setSize(1000,550);
        int x = helper.screenCenterPoint("x",getSize());
        int y = helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(config.PROJECT_TITLE);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldin: " + Admin.getIsim() + " " + Admin.getSoyisim());
        //ModelUserList

        mdl_user_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }

                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list={"EhliyetNo","İsim","Soyisim","Email","Telefon","Şifre"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list=new Object[col_user_list.length];

        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);
        tbl_user_list.getModel().addTableModelListener(e -> {
            if(e.getType()==TableModelEvent.UPDATE){
                int ehliyetno=Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString());
                String isim=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1).toString();
                String soyisim=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2).toString();
                String eposta=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3).toString();
                String telefon=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString();
                String sifre=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),5).toString();

                if(Kullanici.update(ehliyetno,isim,soyisim,eposta,telefon,sifre)){
                    helper.showMsg("done");

                }
                loadUserModel();
            }
        });
        /////////////////////
        araclarMenu=new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem(("Sil"));
        araclarMenu.add(updateMenu);
        araclarMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            String plaka = tbl_araclar_list.getValueAt(tbl_araclar_list.getSelectedRow(), 0).toString();
            String markasi = tbl_araclar_list.getValueAt(tbl_araclar_list.getSelectedRow(), 1).toString();
            String vitesturu = tbl_araclar_list.getValueAt(tbl_araclar_list.getSelectedRow(), 2).toString();
            String yakitturu = tbl_araclar_list.getValueAt(tbl_araclar_list.getSelectedRow(), 3).toString();
            int gunlukfiyat = Integer.parseInt(tbl_araclar_list.getValueAt(tbl_araclar_list.getSelectedRow(), 4).toString());
            int motorgucu = Integer.parseInt(tbl_araclar_list.getValueAt(tbl_araclar_list.getSelectedRow(), 5).toString());
            String konumu = tbl_araclar_list.getValueAt(tbl_araclar_list.getSelectedRow(), 6).toString();
            int ehliyetno = Integer.parseInt(tbl_araclar_list.getValueAt(tbl_araclar_list.getSelectedRow(), 7).toString());
            UpdateAraclarGUI updateGUI = new UpdateAraclarGUI(Araclar.getFetch(plaka));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadAraclarModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if (helper.confirm("sure")){
                String plaka = tbl_araclar_list.getValueAt(tbl_araclar_list.getSelectedRow(), 0).toString();
                if (Araclar.delete(plaka)){
                    helper.showMsg("done");
                    loadAraclarModel();
                    loadBookingModel();
                    //loadAraclarCombo();
                }
                else {
                    helper.showMsg("error");
                }
            }
        });


        mdl_araclar_list=new DefaultTableModel();
        Object[] col_araclar_list={"plaka","markasi","vitesturu","yakitturu","gunlukfiyat","motorgucu","konumu","ehliyetno"};
        mdl_araclar_list.setColumnIdentifiers(col_araclar_list);
        row_araclar_list=new Object[col_araclar_list.length];
        loadAraclarModel();
        //loadAraclarCombo();


        tbl_araclar_list.setModel(mdl_araclar_list);
        tbl_araclar_list.setComponentPopupMenu(araclarMenu);
        tbl_araclar_list.getTableHeader().setReorderingAllowed(false);
        //tbl_araclar_list.getColumnModel().getColumn(0).setMaxWidth(100);

        tbl_araclar_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point=e.getPoint();
                int selected_row=tbl_araclar_list.rowAtPoint(point);
                tbl_araclar_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });



        mdl_booking_list=new DefaultTableModel();
        Object[] col_booking_list={"Id","ehliyetno","plaka","rezervasyontarihi","iadetarihi","fiyat","alışyeri","iadeyeri","durumu"};
        mdl_booking_list.setColumnIdentifiers(col_booking_list);
        row_booking_list=new Object[col_booking_list.length];

        loadBookingModel();
        tbl_booking_list.setModel(mdl_booking_list);
        tbl_booking_list.getTableHeader().setReorderingAllowed(false);
        //loadAraclarCombo();

        mdl_faturalar_list=new DefaultTableModel();
        Object[] col_faturalar_list={"faturaid","rezervasyonid", "fiyat"," kartno", "cvv", "karttarihi"};
        mdl_faturalar_list.setColumnIdentifiers(col_faturalar_list);
        row_faturalar_list=new Object[col_faturalar_list.length];
        loadFaturalarModel();
        tbl_faturalar_list.setModel(mdl_faturalar_list);
        tbl_faturalar_list.getTableHeader().setReorderingAllowed(false);


        btn_user_add.addActionListener(e -> {
            if (helper.isFieldEmpty(fld_user_ehliyetno) || helper.isFieldEmpty(fld_user_isim)|| helper.isFieldEmpty(fld_user_soyisim)
                    || helper.isFieldEmpty(fld_user_email)|| helper.isFieldEmpty(fld_user_sifre)|| helper.isFieldEmpty(fld_user_telefon)){
                helper.showMsg("fill");
            }else{
                int ehliyetno= Integer.parseInt(fld_user_ehliyetno.getText());
                String isim= fld_user_isim.getText();
                String soyisim= fld_user_soyisim.getText();
                String eposta= fld_user_email.getText();
                String sifre= fld_user_telefon.getText();
                String telefon= fld_user_sifre.getText();
                if (Kullanici.add(ehliyetno,isim,soyisim,eposta,telefon,sifre)){
                    helper.showMsg("done");
                    loadUserModel();
                    fld_user_ehliyetno.setText(null);
                    fld_user_isim.setText(null);
                    fld_user_soyisim.setText(null);
                    fld_user_email.setText(null);
                    fld_user_telefon.setText(null);
                    fld_user_sifre.setText(null);
                }
            }
        });
        btn_user_delete.addActionListener(e -> {
            if (helper.isFieldEmpty(fld_user_ehliyetno)){
                helper.showMsg("fill");
            } else {
                if (helper.confirm("sure")) {
                    int ehliyetno = Integer.parseInt(fld_user_ehliyetno.getText());
                    if (Kullanici.delete(ehliyetno)) {
                        helper.showMsg("done");
                        loadUserModel();
                        loadAraclarModel();
                        loadBookingModel();
                    } else {
                        helper.showMsg("error");
                    }
                }
            }
        });
        btn_user_sh.addActionListener(e -> {
            Integer ehliyetno = null;
            try {
                ehliyetno = Integer.parseInt(fld_sh_user_ehliyetno.getText());
            } catch (NumberFormatException ex) {}
            String isim= fld_sh_user_isim.getText();
            String soyisim= fld_sh_user_soyisim.getText();
            String query= Kullanici.searchQuery(ehliyetno,isim,soyisim);
            loadUserModel(Kullanici.searchUserList(query));
        });



        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login=new LoginGUI();
        });
        btn_araclar_add.addActionListener(e -> {
            if (helper.isFieldEmpty(fld_araclar_plaka) || helper.isFieldEmpty(fld_araclar_markasi) || helper.isFieldEmpty(fld_araclar_fiyat) || helper.isFieldEmpty(fld_araclar_motorgucu)  || helper.isFieldEmpty(fld_araclar_ehliyetno)) {
                helper.showMsg("fill");

            }else {
                if (Araclar.add(fld_araclar_plaka.getText(), fld_araclar_markasi.getText(), (String) cmb_araclar_vitesturu.getSelectedItem(),
                        (String) cmb_araclar_yakitturu.getSelectedItem(), Integer.parseInt(fld_araclar_fiyat.getText()), Integer.parseInt(fld_araclar_motorgucu.getText()),
                        (String) cmb_araclar_konumu.getSelectedItem(), Integer.parseInt(fld_araclar_ehliyetno.getText()))) {
                    helper.showMsg("done");
                    loadAraclarModel();
                    //loadAraclarCombo();
                    fld_araclar_plaka.setText(null);
                    fld_araclar_markasi.setText(null);


                    fld_araclar_fiyat.setText(null);
                    fld_araclar_motorgucu.setText(null);

                    fld_araclar_ehliyetno.setText(null);
                } else {
                    helper.showMsg("error");
                }
            }

        });

        btn_booking_add.addActionListener(e -> {
            //item araclaritem = (item) cmb_booking_ayeri.getSelectedItem();
            if (helper.isFieldEmpty(fld_booking_ehliyetno) || helper.isFieldEmpty(fld_booking_plaka) || helper.isFieldEmpty(fld_booking_rtarihi) || helper.isFieldEmpty(fld_booking_itarihi) || helper.isFieldEmpty(fld_booking_fiyat)) {
                helper.showMsg("fill");
            } else {

                String rtarihiText = fld_booking_rtarihi.getText();
                java.sql.Date rtarihi = java.sql.Date.valueOf(rtarihiText);

                String itarihiText = fld_booking_itarihi.getText();
                java.sql.Date itarihi = java.sql.Date.valueOf(itarihiText);

                if (Rezervasyonlar.add(Integer.parseInt(fld_booking_ehliyetno.getText()), fld_booking_plaka.getText(), rtarihi, itarihi, Integer.parseInt(fld_booking_fiyat.getText()), (String) cmb_booking_ayeri.getSelectedItem(), (String) cmb_booking_iyeri.getSelectedItem(), (String) cmb_booking_durumu.getSelectedItem())) {
                    helper.showMsg("done");
                    loadBookingModel();
                    fld_booking_ehliyetno.setText(null);
                    fld_booking_plaka.setText(null);
                    fld_booking_rtarihi.setText(null);
                    fld_booking_itarihi.setText(null);
                    fld_booking_fiyat.setText(null);
                } else {
                    helper.showMsg("error");
                }

            }
        });



    }

    private void loadFaturalarModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_faturalar_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Faturalar obj : Faturalar.getList()) {
            i = 0;
            row_faturalar_list[i++] = obj.getFaturaid();
            row_faturalar_list[i++] = obj.getRezervasyonid();
            row_faturalar_list[i++] = obj.getFiyat();
            row_faturalar_list[i++] = obj.getKartno();
            row_faturalar_list[i++] = obj.getCvv();
            row_faturalar_list[i++] = obj.getKarttarihi();
            mdl_faturalar_list.addRow(row_faturalar_list);
        }
    }


    private void loadBookingModel() {
        DefaultTableModel clearModel=(DefaultTableModel) tbl_booking_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Rezervasyonlar obj:Rezervasyonlar.getList()){
            i=0;
            row_booking_list[i++]=obj.getId();
            row_booking_list[i++]=obj.getKullanici().getEhliyetno();
            row_booking_list[i++]=obj.getAraclar().getPlaka();
            row_booking_list[i++]=obj.getRtarihi();
            row_booking_list[i++]=obj.getItarihi();
            row_booking_list[i++]=obj.getFiyat();
            row_booking_list[i++]=obj.getAyeri();
            row_booking_list[i++]=obj.getIyeri();
            row_booking_list[i++]=obj.getDurumu();
            mdl_booking_list.addRow(row_booking_list);
        }
    }

    private void loadAraclarModel() {
        DefaultTableModel clearModel= (DefaultTableModel) tbl_araclar_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Araclar obj : Araclar.getList()){
            i=0;
            row_araclar_list[i++]=obj.getPlaka();
            row_araclar_list[i++]=obj.getMarkasi();
            row_araclar_list[i++]=obj.getVitesturu();
            row_araclar_list[i++]=obj.getYakitturu();
            row_araclar_list[i++]=obj.getGunlukfiyat();
            row_araclar_list[i++]=obj.getMotorgucu();
            row_araclar_list[i++]=obj.getKonumu();
            row_araclar_list[i++]=obj.getEhliyetno();
            mdl_araclar_list.addRow(row_araclar_list);
        }
    }

    public void loadUserModel(){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Kullanici obj : Kullanici.getList()){
            i=0;
            row_user_list[i++]=obj.getEhliyetno();
            row_user_list[i++]=obj.getIsim();
            row_user_list[i++]=obj.getSoyisim();
            row_user_list[i++]=obj.getEposta();
            row_user_list[i++]=obj.getTelefon();
            row_user_list[i++]=obj.getSifre();
            mdl_user_list.addRow(row_user_list);
        }
    }
    public void loadUserModel(ArrayList<Kullanici>list){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        for (Kullanici obj : list){
            int i= 0;
            row_user_list[i++]=obj.getEhliyetno();
            row_user_list[i++]=obj.getIsim();
            row_user_list[i++]=obj.getSoyisim();
            row_user_list[i++]=obj.getEposta();
            row_user_list[i++]=obj.getTelefon();
            row_user_list[i++]=obj.getSifre();
            mdl_user_list.addRow(row_user_list);
        }
    }


    /*public void loadAraclarCombo(){
        cmb_booking_ayeri.removeAllItems();
        Set<String> addedItems = new LinkedHashSet<>();
        for(Araclar obj : Araclar.getList()){
            String konumu = obj.getKonumu();
            if (!addedItems.contains(konumu)) {
                cmb_booking_ayeri.addItem(new item(konumu, konumu));
                addedItems.add(konumu);
            }
        }
    }*/



    public static void main(String[] args) {
        helper.setLayout();

        admin firstadmin=new admin();
        firstadmin.setEhliyetno(1);
        firstadmin.setIsim("Alperen");
        firstadmin.setSoyisim("Uslu");
        firstadmin.setEposta("alperen@alperen.com");
        firstadmin.setTelefon("5555555555");
        firstadmin.setSifre("alperen");
        adminGUI adGUI = new adminGUI(firstadmin);
    }
}
