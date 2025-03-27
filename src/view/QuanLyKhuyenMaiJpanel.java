/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import dao.KhuyenMaiDAO;
import enity.KhuyenMai;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admim
 */
public class QuanLyKhuyenMaiJpanel extends javax.swing.JPanel {

    /**
     * Creates new form QuanLyKhuyenMaiJpanel
     */
    public QuanLyKhuyenMaiJpanel() {
        initComponents();
        fillTable();
    }

    private void fillTable() {
        KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
        List<KhuyenMai> khuyenMaiLst = khuyenMaiDAO.readKhuyenMai();

        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);

        for (KhuyenMai khuyenMai : khuyenMaiLst) {
            Object[] rowdata = {
                khuyenMai.getMaKM(),
                khuyenMai.getTenKM(),
                khuyenMai.getCodeGiamGia(),
                khuyenMai.getGoiGiamGia(),
                khuyenMai.getNgayBatDau(),
                khuyenMai.getNgayKetThuc()
            };
            model.addRow(rowdata);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        dcNgayBatDauSearch = new com.toedter.calendar.JDateChooser();
        jLabel19 = new javax.swing.JLabel();
        dcNgayKetThucSearch = new com.toedter.calendar.JDateChooser();
        btnTim = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDanhSach = new rojerusan.RSTableMetro();
        jPanel7 = new javax.swing.JPanel();
        jTextField24 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        dcNgayKetThuc = new com.toedter.calendar.JDateChooser();
        jLabel46 = new javax.swing.JLabel();
        dcNgayBatDau = new com.toedter.calendar.JDateChooser();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        rSComboMetro6 = new rojerusan.RSComboMetro();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1500, 1000));
        setMinimumSize(new java.awt.Dimension(1500, 1000));
        setPreferredSize(new java.awt.Dimension(1500, 1000));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 28)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(128, 0, 0));
        jLabel2.setText("Điều Chỉnh Thông Tin Khuyến Mãi");

        jPanel6.setBackground(new java.awt.Color(246, 225, 225));
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel6.setPreferredSize(new java.awt.Dimension(1300, 400));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(128, 0, 0));
        jLabel17.setText("Tìm Kiếm");

        jPanel8.setBackground(new java.awt.Color(246, 225, 225));
        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(128, 0, 0)));

        txtTimKiem.setBackground(new java.awt.Color(246, 225, 225));
        txtTimKiem.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txtTimKiem.setForeground(new java.awt.Color(128, 0, 0));
        txtTimKiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        txtTimKiem.setMinimumSize(new java.awt.Dimension(300, 30));
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnTimKiem.setBackground(new java.awt.Color(246, 225, 225));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search-13-24.png"))); // NOI18N
        btnTimKiem.setBorder(null);
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(246, 225, 225));
        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(128, 0, 0)));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(128, 0, 0));
        jLabel18.setText("Ngày Bắt Đầu:");

        dcNgayBatDauSearch.setBackground(new java.awt.Color(246, 225, 225));
        dcNgayBatDauSearch.setDateFormatString("yyyy-MM-dd");

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(128, 0, 0));
        jLabel19.setText("Ngày Kết Thúc: ");

        dcNgayKetThucSearch.setBackground(new java.awt.Color(246, 225, 225));
        dcNgayKetThucSearch.setDateFormatString("yyyy-MM-dd");

        btnTim.setBackground(new java.awt.Color(255, 255, 255));
        btnTim.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnTim.setForeground(new java.awt.Color(128, 0, 0));
        btnTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search-13-24.png"))); // NOI18N
        btnTim.setText("Tìm");
        btnTim.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcNgayBatDauSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dcNgayKetThucSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(0, 191, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(dcNgayBatDauSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addGap(25, 25, 25)
                .addComponent(dcNgayKetThucSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTim)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Khuyến Mại", "Tên Khuyến Mại", "Code Khuyến Mại", "Gói Giảm Giá", "Ngày Bắt Đầu", "Ngày Kết Thúc"
            }
        ));
        tblDanhSach.setColorBackgoundHead(new java.awt.Color(128, 0, 0));
        tblDanhSach.setColorBordeFilas(new java.awt.Color(128, 0, 0));
        tblDanhSach.setColorBordeHead(new java.awt.Color(246, 225, 225));
        tblDanhSach.setColorFilasBackgound1(new java.awt.Color(246, 225, 225));
        tblDanhSach.setColorFilasBackgound2(new java.awt.Color(246, 225, 225));
        tblDanhSach.setColorFilasForeground1(new java.awt.Color(128, 0, 0));
        tblDanhSach.setColorFilasForeground2(new java.awt.Color(128, 0, 0));
        tblDanhSach.setColorForegroundHead(new java.awt.Color(246, 225, 225));
        tblDanhSach.setColorSelBackgound(new java.awt.Color(128, 0, 0));
        tblDanhSach.setColorSelForeground(new java.awt.Color(246, 225, 225));
        tblDanhSach.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        tblDanhSach.setFuenteFilas(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tblDanhSach.setFuenteHead(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane4.setViewportView(tblDanhSach);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 953, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(246, 225, 225));
        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel7.setPreferredSize(new java.awt.Dimension(1300, 400));

        jTextField24.setBackground(new java.awt.Color(246, 225, 225));
        jTextField24.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jTextField24.setForeground(new java.awt.Color(128, 0, 0));
        jTextField24.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        jTextField24.setMinimumSize(new java.awt.Dimension(300, 30));
        jTextField24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField24jTextField12ActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(128, 0, 0));
        jLabel43.setText("Tên Khuyến Mãi:");

        jLabel45.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(128, 0, 0));
        jLabel45.setText("Code Giảm Giá: ");

        jTextField26.setBackground(new java.awt.Color(246, 225, 225));
        jTextField26.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jTextField26.setForeground(new java.awt.Color(128, 0, 0));
        jTextField26.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        jTextField26.setMinimumSize(new java.awt.Dimension(300, 30));
        jTextField26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField26jTextField14ActionPerformed(evt);
            }
        });

        dcNgayKetThuc.setBackground(new java.awt.Color(246, 225, 225));

        jLabel46.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(128, 0, 0));
        jLabel46.setText("Ngày Kết Thúc");

        dcNgayBatDau.setBackground(new java.awt.Color(246, 225, 225));
        dcNgayBatDau.setForeground(new java.awt.Color(128, 0, 0));

        jLabel47.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(128, 0, 0));
        jLabel47.setText("Ngày Bắt Đầu:");

        jLabel48.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(128, 0, 0));
        jLabel48.setText("Gói Giảm Giá Theo %:");

        rSComboMetro6.setBackground(new java.awt.Color(246, 225, 225));
        rSComboMetro6.setForeground(new java.awt.Color(128, 0, 0));
        rSComboMetro6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "10", "20", "30", "50", " " }));
        rSComboMetro6.setColorArrow(new java.awt.Color(128, 0, 0));
        rSComboMetro6.setColorBorde(new java.awt.Color(128, 0, 0));
        rSComboMetro6.setColorFondo(new java.awt.Color(246, 225, 225));
        rSComboMetro6.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        rSComboMetro6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSComboMetro6rSComboMetro3ActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(255, 255, 255));
        jButton18.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jButton18.setForeground(new java.awt.Color(128, 0, 0));
        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add-user-3-48.png"))); // NOI18N
        jButton18.setText("Thêm ");
        jButton18.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18jButton9ActionPerformed(evt);
            }
        });

        jButton19.setBackground(new java.awt.Color(255, 255, 255));
        jButton19.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jButton19.setForeground(new java.awt.Color(128, 0, 0));
        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-48.png"))); // NOI18N
        jButton19.setText("Xoá");
        jButton19.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19jButton10ActionPerformed(evt);
            }
        });

        jButton20.setBackground(new java.awt.Color(255, 255, 255));
        jButton20.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jButton20.setForeground(new java.awt.Color(128, 0, 0));
        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/available-updates-48.png"))); // NOI18N
        jButton20.setText("Sửa");
        jButton20.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(323, 323, 323)
                        .addComponent(jLabel48)
                        .addGap(18, 18, 18)
                        .addComponent(rSComboMetro6, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel47)
                                    .addGap(38, 38, 38)
                                    .addComponent(dcNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(164, 164, 164)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                            .addGap(249, 249, 249)
                                            .addComponent(dcNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(rSComboMetro6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addComponent(dcNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dcNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton20)
                    .addComponent(jButton18)
                    .addComponent(jButton19))
                .addGap(32, 32, 32))
        );

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 28)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(128, 0, 0));
        jLabel3.setText("Thông Tin Khuyến Mãi");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1377, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 1377, Short.MAX_VALUE)))
                .addGap(47, 47, 47))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng mong muốn

        Date ngayBatDau = dcNgayBatDauSearch.getDate();  // Lấy đối tượng Date
        Date ngayKetThuc = dcNgayKetThucSearch.getDate(); // Lấy đối tượng Date

        if (ngayBatDau == null || ngayKetThuc == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày bắt đầu và ngày kết thúc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try { // Thêm khối try-catch xung quanh việc định dạng ngày tháng và truy cập DB
            String ngayBatDauFormatted = dateFormat.format(ngayBatDau);
            String ngayKetThucFormatted = dateFormat.format(ngayKetThuc);

            KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
            List<KhuyenMai> khuyenMaiLst = khuyenMaiDAO.TimKiemSanPhamTheoNgay(ngayBatDauFormatted, ngayKetThucFormatted);

            DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
            model.setRowCount(0);

            for (KhuyenMai khuyenMai : khuyenMaiLst) {
                Object[] rowdata = {
                    khuyenMai.getMaKM(),
                    khuyenMai.getTenKM(),
                    khuyenMai.getCodeGiamGia(),
                    khuyenMai.getGoiGiamGia(),
                    khuyenMai.getNgayBatDau(),
                    khuyenMai.getNgayKetThuc()
                };
                model.addRow(rowdata);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi trong quá trình tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  // Hiển thị lỗi cho người dùng
            e.printStackTrace(); // In lỗi ra console để gỡ lỗi
        }
    }//GEN-LAST:event_btnTimActionPerformed

    private void jButton19jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton19jButton10ActionPerformed

    private void jButton18jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton18jButton9ActionPerformed

    private void rSComboMetro6rSComboMetro3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSComboMetro6rSComboMetro3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rSComboMetro6rSComboMetro3ActionPerformed

    private void jTextField24jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField24jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField24jTextField12ActionPerformed

    private void jTextField26jTextField14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField26jTextField14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField26jTextField14ActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        String TimKiem = txtTimKiem.getText();
        KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
        List<KhuyenMai> khuyenMaiLst = khuyenMaiDAO.TimKiemKhuyenMai(TimKiem);

        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);

        for (KhuyenMai khuyenMai : khuyenMaiLst) {
            Object[] rowdata = {
                khuyenMai.getMaKM(),
                khuyenMai.getTenKM(),
                khuyenMai.getCodeGiamGia(),
                khuyenMai.getGoiGiamGia(),
                khuyenMai.getNgayBatDau(),
                khuyenMai.getNgayKetThuc()
            };
            model.addRow(rowdata);
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnTimKiem;
    private com.toedter.calendar.JDateChooser dcNgayBatDau;
    private com.toedter.calendar.JDateChooser dcNgayBatDauSearch;
    private com.toedter.calendar.JDateChooser dcNgayKetThuc;
    private com.toedter.calendar.JDateChooser dcNgayKetThucSearch;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton20;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField26;
    private rojerusan.RSComboMetro rSComboMetro6;
    private rojerusan.RSTableMetro tblDanhSach;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
