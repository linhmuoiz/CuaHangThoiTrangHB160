/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import dao.ChiTietHDDAO;
import dao.HoaDonDAO;
import dto.ChiTietHDDTO;
import dto.HoaDonDTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admim
 */
public class QuanLyHoaDonJpanel extends javax.swing.JPanel {

    /**
     * Creates new form QuanLyHoaDonJpanel
     */
    public QuanLyHoaDonJpanel() {
        initComponents();
        readHoaDon();
    }

    private void readHoaDon() {
        HoaDonDAO hoaDonDAO = new HoaDonDAO();
        List<HoaDonDTO> hoaDonLst = hoaDonDAO.readHoaDon();

        DefaultTableModel tableHoaDon = (DefaultTableModel) this.tblDanhMuc.getModel();
        tableHoaDon.setRowCount(0);

        for (HoaDonDTO hoaDon : hoaDonLst) {
            tableHoaDon.addRow(new Object[]{
                hoaDon.getID(),
                hoaDon.getTenKH(),
                hoaDon.getSDT(),
                hoaDon.getThanhTien(),
                hoaDon.getHinhThucTT(),
                hoaDon.getNgayTao(),
                hoaDon.getTrangThai()
            });
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        dcNgayTimKiem = new com.toedter.calendar.JDateChooser();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDanhMuc = new rojerusan.RSTableMetro();
        rdoMa = new javax.swing.JRadioButton();
        rdoSDT = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDanhMuc1 = new rojerusan.RSTableMetro();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1500, 1000));
        setMinimumSize(new java.awt.Dimension(1500, 1000));
        setName(""); // NOI18N

        jPanel2.setBackground(new java.awt.Color(246, 225, 225));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel2.setPreferredSize(new java.awt.Dimension(1300, 400));

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

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(128, 0, 0));
        jLabel14.setText("Ngày: ");

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(128, 0, 0));
        jLabel16.setText("Tìm Kiếm Hoá Đơn:");

        dcNgayTimKiem.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N

        jButton6.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jButton6.setForeground(new java.awt.Color(128, 0, 0));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/filled-filter-32.png"))); // NOI18N
        jButton6.setText("Lọc");
        jButton6.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jButton7.setForeground(new java.awt.Color(128, 0, 0));
        jButton7.setText("Cài Lại");
        jButton7.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton16.setBackground(new java.awt.Color(246, 225, 225));
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search-13-24.png"))); // NOI18N
        jButton16.setBorder(null);
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        tblDanhMuc.setBackground(new java.awt.Color(246, 225, 225));
        tblDanhMuc.setForeground(new java.awt.Color(255, 255, 255));
        tblDanhMuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hoá Đơn", "Tên Khách Hàng", "Số Điện Thoại", "Tổng Tiền", "Hình Thức Thanh Toán", "Ngày Tạo", "Trạng Thái"
            }
        ));
        tblDanhMuc.setColorBackgoundHead(new java.awt.Color(128, 0, 0));
        tblDanhMuc.setColorBordeFilas(new java.awt.Color(128, 0, 0));
        tblDanhMuc.setColorBordeHead(new java.awt.Color(246, 225, 225));
        tblDanhMuc.setColorFilasBackgound1(new java.awt.Color(246, 225, 225));
        tblDanhMuc.setColorFilasBackgound2(new java.awt.Color(246, 225, 225));
        tblDanhMuc.setColorFilasForeground1(new java.awt.Color(128, 0, 0));
        tblDanhMuc.setColorFilasForeground2(new java.awt.Color(128, 0, 0));
        tblDanhMuc.setColorForegroundHead(new java.awt.Color(246, 225, 225));
        tblDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhMucMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDanhMuc);

        buttonGroup1.add(rdoMa);
        rdoMa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        rdoMa.setForeground(new java.awt.Color(128, 0, 0));
        rdoMa.setSelected(true);
        rdoMa.setText("Theo Mã");
        rdoMa.setToolTipText("");

        buttonGroup1.add(rdoSDT);
        rdoSDT.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        rdoSDT.setForeground(new java.awt.Color(128, 0, 0));
        rdoSDT.setText("Theo SDT");
        rdoSDT.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(rdoMa)
                        .addGap(18, 18, 18)
                        .addComponent(rdoSDT))
                    .addComponent(dcNgayTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
            .addComponent(jScrollPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel16))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(22, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(dcNgayTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoMa)
                            .addComponent(rdoSDT))
                        .addGap(18, 18, 18)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(128, 0, 0));
        jLabel1.setText("Chi tiết Hoá Đơn");

        jPanel3.setBackground(new java.awt.Color(246, 225, 225));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel3.setPreferredSize(new java.awt.Dimension(1300, 400));

        tblDanhMuc1.setBackground(new java.awt.Color(246, 225, 225));
        tblDanhMuc1.setForeground(new java.awt.Color(255, 255, 255));
        tblDanhMuc1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Danh Mục", "Màu", "Kích Thước", "Số Lượng", "Giá Bán", "Tổng Tiền", "Trạng Thái"
            }
        ));
        tblDanhMuc1.setColorBackgoundHead(new java.awt.Color(128, 0, 0));
        tblDanhMuc1.setColorBordeFilas(new java.awt.Color(128, 0, 0));
        tblDanhMuc1.setColorBordeHead(new java.awt.Color(246, 225, 225));
        tblDanhMuc1.setColorFilasBackgound1(new java.awt.Color(246, 225, 225));
        tblDanhMuc1.setColorFilasBackgound2(new java.awt.Color(246, 225, 225));
        tblDanhMuc1.setColorFilasForeground1(new java.awt.Color(128, 0, 0));
        tblDanhMuc1.setColorFilasForeground2(new java.awt.Color(128, 0, 0));
        tblDanhMuc1.setColorForegroundHead(new java.awt.Color(246, 225, 225));
        jScrollPane3.setViewportView(tblDanhMuc1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 28)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(128, 0, 0));
        jLabel2.setText("Hoá Đơn");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1304, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1304, Short.MAX_VALUE))
                        .addGap(109, 109, 109))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 

        Date ngayTimKiem = dcNgayTimKiem.getDate(); 

        if (ngayTimKiem == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày tìm kiếm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String ngayTimKiemFormatted = dateFormat.format(ngayTimKiem);

            HoaDonDAO hoaDonDAO = new HoaDonDAO();
            List<HoaDonDTO> hoaDonLst = hoaDonDAO.TimKiemHoaDonTheoNgay(ngayTimKiemFormatted);

            DefaultTableModel tableHoaDon = (DefaultTableModel) this.tblDanhMuc.getModel();
            tableHoaDon.setRowCount(0);

            for (HoaDonDTO hoaDon : hoaDonLst) {
                tableHoaDon.addRow(new Object[]{
                    hoaDon.getID(),
                    hoaDon.getTenKH(),
                    hoaDon.getSDT(),
                    hoaDon.getThanhTien(),
                    hoaDon.getHinhThucTT(),
                    hoaDon.getNgayTao(),
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi trong quá trình tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);  // Hiển thị lỗi cho người dùng
            e.printStackTrace(); // In lỗi ra console để gỡ lỗi
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        txtTimKiem.setText("");
        DefaultTableModel Tmodel = (DefaultTableModel) tblDanhMuc1.getModel();
        Tmodel.setRowCount(0);
        tblDanhMuc1.setModel(Tmodel);

    }//GEN-LAST:event_jButton7ActionPerformed

    private void tblDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhMucMouseClicked
        DefaultTableModel tableChiTietHD = (DefaultTableModel) this.tblDanhMuc1.getModel();
        tableChiTietHD.setRowCount(0);

        int soDongChon = tblDanhMuc.getSelectedRow();
        int MaHD = Integer.parseInt(tblDanhMuc.getValueAt(soDongChon, 0).toString());

        ChiTietHDDAO chiTietHDDAO = new ChiTietHDDAO();
        List<ChiTietHDDTO> chiTietHDLst = chiTietHDDAO.readChiTietHD(MaHD);

        // Số tiền giảm của từng sản phẩm = (Giá trị sản phẩm / Tổng giá trị đơn hàng) * Tổng tiền giảm
        double TongDonHang = Double.parseDouble(tblDanhMuc.getValueAt(soDongChon, 3).toString());

        for (ChiTietHDDTO hoaDon : chiTietHDLst) {
            double TongGiaTriSanPham = hoaDon.getGia();

            tableChiTietHD.addRow(new Object[]{
                hoaDon.getMaSP(),
                hoaDon.getTenSP(),
                hoaDon.getTenDM(),
                hoaDon.getTenMS(),
                hoaDon.getTenKT(),
                hoaDon.getSoLuong(),
                hoaDon.getGia(),
                hoaDon.getSoLuong() * hoaDon.getGia(),
                hoaDon.getTrangThai() 
            });
        }
    }//GEN-LAST:event_tblDanhMucMouseClicked

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        String theo = rdoMa.isSelected() ? "Theo Mã" : "Theo SDT";

        if ("Theo Mã".equals(theo)) {
            String TimKiem = txtTimKiem.getText();
            HoaDonDAO hoaDonDAO = new HoaDonDAO();
            List<HoaDonDTO> hoaDonLst = hoaDonDAO.TimKiemHoaDonTheoMa(TimKiem);

            DefaultTableModel tableHoaDon = (DefaultTableModel) this.tblDanhMuc.getModel();
            tableHoaDon.setRowCount(0);

            for (HoaDonDTO hoaDon : hoaDonLst) {
                tableHoaDon.addRow(new Object[]{
                    hoaDon.getID(),
                    hoaDon.getTenKH(),
                    hoaDon.getSDT(),
                    hoaDon.getThanhTien(),
                    hoaDon.getHinhThucTT(),
                    hoaDon.getNgayTao(),
                    hoaDon.getTrangThai()
                });
            }
        }
        else if("Theo SDT".equals(theo)){
            String TimKiem = txtTimKiem.getText();
            HoaDonDAO hoaDonDAO = new HoaDonDAO();
            List<HoaDonDTO> hoaDonLst = hoaDonDAO.TimKiemHoaDonTheoSDT(TimKiem);

            DefaultTableModel tableHoaDon = (DefaultTableModel) this.tblDanhMuc.getModel();
            tableHoaDon.setRowCount(0);

            for (HoaDonDTO hoaDon : hoaDonLst) {
                tableHoaDon.addRow(new Object[]{
                    hoaDon.getID(),
                    hoaDon.getTenKH(),
                    hoaDon.getSDT(),
                    hoaDon.getThanhTien(),
                    hoaDon.getHinhThucTT(),
                    hoaDon.getNgayTao(),
                    hoaDon.getTrangThai()
                });
            }
        }
    }//GEN-LAST:event_jButton16ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser dcNgayTimKiem;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton rdoMa;
    private javax.swing.JRadioButton rdoSDT;
    private rojerusan.RSTableMetro tblDanhMuc;
    private rojerusan.RSTableMetro tblDanhMuc1;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
