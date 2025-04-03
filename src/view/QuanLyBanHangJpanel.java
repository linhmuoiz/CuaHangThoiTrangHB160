/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import dao.ChiTietHDDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.KhuyenMaiDAO;
import dao.SanPhamDAO;
import dto.ChiTietHDDTO;
import dto.SanPhamDTO;
import enity.ChiTietHD;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import enity.HoaDon;
import enity.KhachHang;
import enity.KhuyenMai;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import utils.GlobalState;

public class QuanLyBanHangJpanel extends javax.swing.JPanel {

    /**
     * Creates new form QuanLyBanHangJpanel
     */
    public QuanLyBanHangJpanel() {
        initComponents();
        readHoaDonCho();
        readKhachHang();
        readSanPham();
        resetTableChiTietHD();
    }

    private void readHoaDonCho() {
        HoaDonDAO hoaDonDAO = new HoaDonDAO();
        List<HoaDon> hoaDonLst = hoaDonDAO.readHoaDonCho();

        DefaultTableModel tableHoaDonCho = (DefaultTableModel) this.rSTableMetro1.getModel();
        tableHoaDonCho.setRowCount(0);

        for (HoaDon hoaDon : hoaDonLst) {
            tableHoaDonCho.addRow(new Object[]{
                hoaDon.getID(),
                hoaDon.getNgayTao(),
                hoaDon.getTrangThai(),});
        }
    }

    private void readKhachHang() {
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        List<KhachHang> khachHangLst = khachHangDAO.readKhachHang();

        DefaultTableModel tableKhachHang = (DefaultTableModel) this.rSTableMetro4.getModel();
        tableKhachHang.setRowCount(0);

        for (KhachHang khachHang : khachHangLst) {
            tableKhachHang.addRow(new Object[]{
                khachHang.getTenKH(),
                khachHang.getSDT(),});
        }
    }

    private void readSanPham() {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        List<SanPhamDTO> sanPhamLst = sanPhamDAO.readSanPhamQLY();

        DefaultTableModel tableSanPham = (DefaultTableModel) this.rSTableMetro2.getModel();
        tableSanPham.setRowCount(0);

        for (SanPhamDTO sanPham : sanPhamLst) {
            tableSanPham.addRow(new Object[]{
                sanPham.getID(),
                sanPham.getTenSP(),
                sanPham.getTenDM(),
                sanPham.getTenMS(),
                sanPham.getTenKT(),
                sanPham.getSoLuong(),
                sanPham.getGia(),});
        }
    }

    public void resetTableChiTietHD() {
        DefaultTableModel tableSanPhamChon = (DefaultTableModel) this.rSTableMetro3.getModel();
        tableSanPhamChon.setRowCount(0);
    }

    public void thayDoiThongTinHoaDon() {
        // Thay đổi bảng Chi tiết hóa đơn
        // Thay đổi Số lượng bán sẽ thay đổi Thành Tiền
        DefaultTableModel tableSanPhamChon = (DefaultTableModel) this.rSTableMetro3.getModel();

        if (tableSanPhamChon.getRowCount() == 0) {
            return;
        }

        int soDongChon = rSTableMetro3.getSelectedRow();

        // Nếu chưa chọn thì khi ấn Thêm sẽ lấy dòng đầu tiên của bảng Chi tiết hóa đơn
        if (soDongChon < 0) {
            soDongChon = 0;
        }

        String MaSPChon = rSTableMetro3.getValueAt(soDongChon, 0).toString();
        String SoLuongSPChon = rSTableMetro3.getValueAt(soDongChon, 5).toString();

        GlobalState.MaSPChon = Integer.parseInt(MaSPChon);
        GlobalState.SoLuongSPChon = Integer.parseInt(SoLuongSPChon);

        double ThanhTien = GlobalState.GiaSanPhamChon * GlobalState.SoLuongSPChon;
        rSTableMetro3.setValueAt(ThanhTien, soDongChon, 6);

        // Thay đổi Số lượng bán sẽ thay đổi Thông tin hóa đơn
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date NgayThanhLap = java.sql.Date.valueOf(sdf.format(new java.util.Date()));

        int SoLuongSP = 0;
        double TongTienSP = 0.0;
        double TongTienKhuyenMai = 0.0;
        double TongThanhToan = 0.0;
        double TienHoanLai = 0.0;

        jDateChooser10.setDate(NgayThanhLap);
        for (int i = 0; i < tableSanPhamChon.getRowCount(); i++) {
            SoLuongSP += Double.parseDouble(tableSanPhamChon.getValueAt(i, 5).toString());
            TongTienSP += Double.parseDouble(tableSanPhamChon.getValueAt(i, 6).toString());
        }

        TongThanhToan = TongTienSP - TongTienKhuyenMai;

        jTextField12.setText(String.valueOf(SoLuongSP));
        jTextField14.setText(String.valueOf(TongTienSP));
        jTextField16.setText(String.valueOf(TongTienKhuyenMai));
        jTextField17.setText(String.valueOf(TongThanhToan));
        jTextField19.setText(String.valueOf(TienHoanLai));
    }

    private void sayMessage(String Message) {
        JOptionPane.showMessageDialog(this, Message);
    }

    private void generateQR(String amount, int maHD) {
        String vietqrAPI = "https://img.vietqr.io/image/mbbank-031307002777-compact.png?amount=" + amount + "&addInfo=Khach%20hang%20chuyen%20khoan%20don%20ma%20" + maHD;

        try {
            // Process QR from server :3
            BufferedImage qrCode = ImageIO.read(new URL(vietqrAPI));
            if (qrCode == null) {
                throw new IOException("Lỗi load QR");
            }

            JPanel buttonPanel = new JPanel();
            JButton payNowButton = new JButton("Đã xong");
            payNowButton.setForeground(Color.RED);
            payNowButton.setFont(new Font("Arial", Font.BOLD, 20));

            buttonPanel.add(payNowButton);

            JFrame frame = new JFrame("QR");
            payNowButton.addActionListener(e -> {

                HoaDonDAO hoaDonDAO = new HoaDonDAO();
                int ketQua = hoaDonDAO.updateHoaDon(MaHD, HinhThucThanhToan, TrangThai);

                ChiTietHDDAO chiTietHDDAO = new ChiTietHDDAO();
                List<ChiTietHD> chiTietHDLst = chiTietHDDAO.findChiTietHD(MaHD);

                for (ChiTietHD chiTietHD : chiTietHDLst) {
                    SanPhamDAO sanPhamDAO = new SanPhamDAO();
                    sanPhamDAO.updateSanPham(chiTietHD.getMaSP(), chiTietHD.getSoLuong());
                }

                if (ketQua == 1) {
                    this.readSanPham();
                    this.readHoaDonCho();
                    JOptionPane.showMessageDialog(this, "Thanh toán thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Thanh toán thất bại!");
                }
                frame.dispose();
            });

            frame.setLocationRelativeTo(null);
            frame.add(buttonPanel, BorderLayout.SOUTH);
            //Add QR to Panel =))
            JLabel label = new JLabel(new ImageIcon(qrCode));
            frame.add(label);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        } catch (IOException e) {
            // Error feedback
            System.err.println("Lỗi khi tạo QR: " + e.getMessage());
            e.printStackTrace();
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

        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        rSTableMetro1 = new rojerusan.RSTableMetro();
        jLabel2 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        rSTableMetro4 = new rojerusan.RSTableMetro();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jDateChooser10 = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        rSComboMetro4 = new rojerusan.RSComboMetro();
        jButton6 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        QRScanPanel = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        rSTableMetro3 = new rojerusan.RSTableMetro();
        jLabel20 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        rSTableMetro2 = new rojerusan.RSTableMetro();
        jButton15 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1500, 1000));
        setMinimumSize(new java.awt.Dimension(1500, 1000));
        setPreferredSize(new java.awt.Dimension(1500, 1000));

        jPanel7.setBackground(new java.awt.Color(246, 225, 225));
        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel7.setPreferredSize(new java.awt.Dimension(1300, 400));

        rSTableMetro1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Hoá Đơn", "Ngày Lập", "Trạng Thái"
            }
        ));
        rSTableMetro1.setColorBackgoundHead(new java.awt.Color(128, 0, 0));
        rSTableMetro1.setColorFilasBackgound1(new java.awt.Color(246, 225, 225));
        rSTableMetro1.setColorFilasBackgound2(new java.awt.Color(246, 225, 225));
        rSTableMetro1.setFuenteHead(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rSTableMetro1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSTableMetro1MouseClicked(evt);
            }
        });
        rSTableMetro1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                rSTableMetro1PropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(rSTableMetro1);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(128, 0, 0));
        jLabel2.setText("Hoá Đơn Chờ");

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jButton8.setForeground(new java.awt.Color(128, 0, 0));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add-48.png"))); // NOI18N
        jButton8.setText("Tạo Hoá Đơn");
        jButton8.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(366, 366, 366))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 812, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jPanel13.setBackground(new java.awt.Color(246, 225, 225));
        jPanel13.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel13.setPreferredSize(new java.awt.Dimension(1300, 300));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(128, 0, 0));
        jLabel17.setText("Danh Sách Khách Hàng");

        rSTableMetro4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Tên Khách Hàng", "Số Điện Thoại"
            }
        ));
        rSTableMetro4.setColorBackgoundHead(new java.awt.Color(128, 0, 0));
        rSTableMetro4.setColorBordeFilas(new java.awt.Color(128, 0, 0));
        rSTableMetro4.setColorBordeHead(new java.awt.Color(246, 225, 225));
        rSTableMetro4.setColorFilasBackgound1(new java.awt.Color(246, 225, 225));
        rSTableMetro4.setColorFilasBackgound2(new java.awt.Color(246, 225, 225));
        rSTableMetro4.setColorFilasForeground1(new java.awt.Color(128, 0, 0));
        rSTableMetro4.setColorFilasForeground2(new java.awt.Color(128, 0, 0));
        rSTableMetro4.setColorForegroundHead(new java.awt.Color(246, 225, 225));
        rSTableMetro4.setColorSelBackgound(new java.awt.Color(128, 0, 0));
        rSTableMetro4.setColorSelForeground(new java.awt.Color(246, 225, 225));
        rSTableMetro4.setFuenteFilas(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        rSTableMetro4.setFuenteHead(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane4.setViewportView(rSTableMetro4);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel8.setBackground(new java.awt.Color(246, 225, 225));
        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel8.setPreferredSize(new java.awt.Dimension(1300, 400));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(128, 0, 0));
        jLabel3.setText("Hoá Đơn");

        jPanel10.setBackground(new java.awt.Color(246, 225, 225));
        jPanel10.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel10.setPreferredSize(new java.awt.Dimension(1300, 300));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(128, 0, 0));
        jLabel7.setText("Thông Tin Hoá Đơn:");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(128, 0, 0));
        jLabel8.setText("Ngày Thành Lập: ");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(128, 0, 0));
        jLabel9.setText("Số Lượng SP:");

        jDateChooser10.setBackground(new java.awt.Color(246, 225, 225));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(128, 0, 0));
        jLabel10.setText("Tổng Tiền SP:");

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(128, 0, 0));
        jLabel11.setText("Code Khuyến Mãi:");

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(128, 0, 0));
        jLabel12.setText("Tổng Tiền Khuyến Mãi:");

        jTextField12.setEditable(false);
        jTextField12.setBackground(new java.awt.Color(246, 225, 225));
        jTextField12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(128, 0, 0));
        jTextField12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        jTextField12.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField12.setMinimumSize(new java.awt.Dimension(300, 30));
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });

        jTextField13.setBackground(new java.awt.Color(246, 225, 225));
        jTextField13.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField13.setForeground(new java.awt.Color(128, 0, 0));
        jTextField13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        jTextField13.setMinimumSize(new java.awt.Dimension(300, 30));
        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });

        jTextField14.setEditable(false);
        jTextField14.setBackground(new java.awt.Color(246, 225, 225));
        jTextField14.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField14.setForeground(new java.awt.Color(128, 0, 0));
        jTextField14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        jTextField14.setMinimumSize(new java.awt.Dimension(300, 30));
        jTextField14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField14ActionPerformed(evt);
            }
        });

        jTextField16.setEditable(false);
        jTextField16.setBackground(new java.awt.Color(246, 225, 225));
        jTextField16.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField16.setForeground(new java.awt.Color(128, 0, 0));
        jTextField16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        jTextField16.setMinimumSize(new java.awt.Dimension(300, 30));
        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });

        jTextField17.setEditable(false);
        jTextField17.setBackground(new java.awt.Color(246, 225, 225));
        jTextField17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField17.setForeground(new java.awt.Color(128, 0, 0));
        jTextField17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        jTextField17.setMinimumSize(new java.awt.Dimension(300, 30));
        jTextField17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField17ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(128, 0, 0));
        jLabel13.setText("Tổng Thanh Toán:");

        jTextField18.setBackground(new java.awt.Color(246, 225, 225));
        jTextField18.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField18.setForeground(new java.awt.Color(128, 0, 0));
        jTextField18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        jTextField18.setMinimumSize(new java.awt.Dimension(300, 30));
        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(128, 0, 0));
        jLabel14.setText("Tiền Khách Đưa:");

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(246, 225, 225));
        jTextField19.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField19.setForeground(new java.awt.Color(128, 0, 0));
        jTextField19.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        jTextField19.setMinimumSize(new java.awt.Dimension(300, 30));
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(128, 0, 0));
        jLabel15.setText("Tiền Hoàn Lại: ");

        jButton16.setBackground(new java.awt.Color(246, 225, 225));
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search-13-24.png"))); // NOI18N
        jButton16.setBorder(null);

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(128, 0, 0));
        jLabel21.setText("Phương Thức TT:");

        rSComboMetro4.setBackground(new java.awt.Color(246, 225, 225));
        rSComboMetro4.setForeground(new java.awt.Color(128, 0, 0));
        rSComboMetro4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tiền Mặt", "Chuyển Khoản" }));
        rSComboMetro4.setColorArrow(new java.awt.Color(128, 0, 0));
        rSComboMetro4.setColorBorde(new java.awt.Color(128, 0, 0));
        rSComboMetro4.setColorFondo(new java.awt.Color(246, 225, 225));
        rSComboMetro4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        rSComboMetro4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSComboMetro4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel21))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jDateChooser10, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(rSComboMetro4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jDateChooser10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(rSComboMetro4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jButton6.setForeground(new java.awt.Color(128, 0, 0));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-48.png"))); // NOI18N
        jButton6.setText("Xoá Hoá Đơn");
        jButton6.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(246, 225, 225));
        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel9.setPreferredSize(new java.awt.Dimension(1300, 300));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(128, 0, 0));
        jLabel4.setText("Thông Tin Khách Hàng:");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(128, 0, 0));
        jLabel5.setText("Tên Khách Hàng: ");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(128, 0, 0));
        jLabel6.setText("Số Điện Thoại: ");

        jTextField9.setBackground(new java.awt.Color(246, 225, 225));
        jTextField9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField9.setForeground(new java.awt.Color(128, 0, 0));
        jTextField9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        jTextField9.setMinimumSize(new java.awt.Dimension(300, 30));
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jTextField10.setBackground(new java.awt.Color(246, 225, 225));
        jTextField10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(128, 0, 0));
        jTextField10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        jTextField10.setMinimumSize(new java.awt.Dimension(300, 30));
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(255, 255, 255));
        jButton11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton11.setForeground(new java.awt.Color(128, 0, 0));
        jButton11.setText("Thêm");
        jButton11.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(327, 327, 327))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jButton11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jButton7.setForeground(new java.awt.Color(128, 0, 0));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/banknotes-48.png"))); // NOI18N
        jButton7.setText("Thanh Toán");
        jButton7.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(218, 218, 218))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(246, 225, 225));
        jPanel11.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel11.setPreferredSize(new java.awt.Dimension(1300, 300));

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(128, 0, 0));
        jLabel16.setText("Quét mã vạch sản phẩm");

        javax.swing.GroupLayout QRScanPanelLayout = new javax.swing.GroupLayout(QRScanPanel);
        QRScanPanel.setLayout(QRScanPanelLayout);
        QRScanPanelLayout.setHorizontalGroup(
            QRScanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        QRScanPanelLayout.setVerticalGroup(
            QRScanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jButton12.setBackground(new java.awt.Color(255, 255, 255));
        jButton12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton12.setForeground(new java.awt.Color(128, 0, 0));
        jButton12.setText("Mở");
        jButton12.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(255, 255, 255));
        jButton13.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton13.setForeground(new java.awt.Color(128, 0, 0));
        jButton13.setText("Đóng");
        jButton13.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(QRScanPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jButton12)
                    .addComponent(jButton13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QRScanPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(246, 225, 225));
        jPanel12.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel12.setPreferredSize(new java.awt.Dimension(1300, 300));

        rSTableMetro3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Danh Mục", "Màu Sắc", "Kích Thước ", "Số Lượng Bán", "Thành Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rSTableMetro3.setColorBackgoundHead(new java.awt.Color(128, 0, 0));
        rSTableMetro3.setColorBordeFilas(new java.awt.Color(128, 0, 0));
        rSTableMetro3.setColorBordeHead(new java.awt.Color(246, 225, 225));
        rSTableMetro3.setColorFilasBackgound1(new java.awt.Color(246, 225, 225));
        rSTableMetro3.setColorFilasBackgound2(new java.awt.Color(246, 225, 225));
        rSTableMetro3.setColorFilasForeground1(new java.awt.Color(128, 0, 0));
        rSTableMetro3.setColorFilasForeground2(new java.awt.Color(128, 0, 0));
        rSTableMetro3.setColorForegroundHead(new java.awt.Color(246, 225, 225));
        rSTableMetro3.setColorSelBackgound(new java.awt.Color(128, 0, 0));
        rSTableMetro3.setColorSelForeground(new java.awt.Color(246, 225, 225));
        rSTableMetro3.setFuenteFilas(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        rSTableMetro3.setFuenteHead(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rSTableMetro3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                rSTableMetro3PropertyChange(evt);
            }
        });
        jScrollPane3.setViewportView(rSTableMetro3);

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(128, 0, 0));
        jLabel20.setText("Chi Tiết Hoá Đơn");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(246, 225, 225));
        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel15.setPreferredSize(new java.awt.Dimension(1300, 300));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(128, 0, 0));
        jLabel18.setText("Danh Sách Sản Phẩm");

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(128, 0, 0));
        jLabel19.setText("Mã Sản Phẩm: ");

        jTextField15.setBackground(new java.awt.Color(246, 225, 225));
        jTextField15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField15.setForeground(new java.awt.Color(128, 0, 0));
        jTextField15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        jTextField15.setMinimumSize(new java.awt.Dimension(300, 30));
        jTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField15ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton9.setForeground(new java.awt.Color(128, 0, 0));
        jButton9.setText("Thêm");
        jButton9.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(255, 255, 255));
        jButton10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton10.setForeground(new java.awt.Color(128, 0, 0));
        jButton10.setText("Làm Mới");
        jButton10.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        rSTableMetro2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Danh Mục", "Màu Sắc", "Kích Thước ", "Số Lượng", "Đơn Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rSTableMetro2.setColorBackgoundHead(new java.awt.Color(128, 0, 0));
        rSTableMetro2.setColorBordeFilas(new java.awt.Color(128, 0, 0));
        rSTableMetro2.setColorBordeHead(new java.awt.Color(246, 225, 225));
        rSTableMetro2.setColorFilasBackgound1(new java.awt.Color(246, 225, 225));
        rSTableMetro2.setColorFilasBackgound2(new java.awt.Color(246, 225, 225));
        rSTableMetro2.setColorFilasForeground1(new java.awt.Color(128, 0, 0));
        rSTableMetro2.setColorFilasForeground2(new java.awt.Color(128, 0, 0));
        rSTableMetro2.setColorForegroundHead(new java.awt.Color(246, 225, 225));
        rSTableMetro2.setColorSelBackgound(new java.awt.Color(128, 0, 0));
        rSTableMetro2.setColorSelForeground(new java.awt.Color(246, 225, 225));
        rSTableMetro2.setFuenteFilas(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        rSTableMetro2.setFuenteHead(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rSTableMetro2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSTableMetro2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(rSTableMetro2);

        jButton15.setBackground(new java.awt.Color(246, 225, 225));
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search-13-24.png"))); // NOI18N
        jButton15.setBorder(null);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 267, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)))
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
            .addComponent(jScrollPane2)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton9)
                        .addComponent(jButton10))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 378, Short.MAX_VALUE)
                .addGap(29, 29, 29))
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void jTextField14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14ActionPerformed

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void jTextField17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17ActionPerformed

    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField18ActionPerformed
        double TongThanhToan = Double.parseDouble(jTextField17.getText());
        double TienKhachDua = Double.parseDouble(jTextField18.getText());
        double TienHoanLai = TienKhachDua - TongThanhToan;
        jTextField19.setText(String.valueOf(TienHoanLai));
    }//GEN-LAST:event_jTextField18ActionPerformed

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField19ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // Chọn bảng hóa đơn chờ sẽ xóa hóa đơn đó
        int MaHD = GlobalState.MaHDChoChon;

        System.out.println("MaHD");
        System.out.println(MaHD);

        HoaDonDAO hoaDonDAO = new HoaDonDAO();
        int ketQuaHoaDon = hoaDonDAO.updateHoaDonHuy(MaHD);

        if (ketQuaHoaDon == 1) {
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            this.readHoaDonCho();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!");
        }
    }//GEN-LAST:event_jButton6ActionPerformed
    int MaHD;
    String HinhThucThanhToan;
    String TrangThai;
    String amount;
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        MaHD = GlobalState.MaHDChon;
        HinhThucThanhToan = rSComboMetro4.getSelectedItem().toString();
        TrangThai = "Hoàn thành";
        amount = jTextField17.getText();
        if (HinhThucThanhToan.equals("Chuyển Khoản") == true) {
            this.generateQR(amount, MaHD);

        } else {
            HoaDonDAO hoaDonDAO = new HoaDonDAO();
            int ketQua = hoaDonDAO.updateHoaDon(MaHD, HinhThucThanhToan, TrangThai);

            ChiTietHDDAO chiTietHDDAO = new ChiTietHDDAO();
            List<ChiTietHD> chiTietHDLst = chiTietHDDAO.findChiTietHD(MaHD);

            for (ChiTietHD chiTietHD : chiTietHDLst) {
                SanPhamDAO sanPhamDAO = new SanPhamDAO();
                sanPhamDAO.updateSanPham(chiTietHD.getMaSP(), chiTietHD.getSoLuong());
            }

            if (ketQua == 1) {
                this.readSanPham();
                this.readHoaDonCho();
                JOptionPane.showMessageDialog(this, "Thanh toán thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Thanh toán thất bại!");
            }
        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // Tạo hóa đơn
        int MaNV = GlobalState.MaNV;
        int MaKH = GlobalState.MaKH;
        double ThanhTien = Double.parseDouble(jTextField17.getText());
        String HinhThucThanhToan = rSComboMetro4.getSelectedItem().toString();
        Date NgayTao = jDateChooser10.getDate();
        String TrangThai = "Đang xử lý";

        HoaDon hoaDon = new HoaDon(MaNV, MaKH, ThanhTien, HinhThucThanhToan, NgayTao, TrangThai);

        HoaDonDAO hoaDonDAO = new HoaDonDAO();
        int ketQua = hoaDonDAO.createHoaDon(hoaDon);

        if (ketQua == 1) {

            // Tạo chi tiết hóa đơn
            // Tìm ID hóa đơn vừa tạo
            int MaHDMoi = hoaDonDAO.findHoaDonMoi();
            // Lặp qua các sản phẩm ở bảng Chi tiết sản phẩm 
            DefaultTableModel tableChiTietHD = (DefaultTableModel) rSTableMetro3.getModel();
            int rowCount = tableChiTietHD.getRowCount();

            for (int i = 0; i < rowCount; i++) {
                int MaSP = Integer.parseInt(tableChiTietHD.getValueAt(i, 0).toString());
                int SoLuong = Integer.parseInt(tableChiTietHD.getValueAt(i, 5).toString());

                // Trong SQL INSERT INTO 1 mã khuyến mại thế này để nếu không điền mã sẽ là không được khuyến mãi
                // (N'Không khuyến mãi', '', 0, '2025-01-01', '2030-01-01')
                KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();

                String CodeKhuyenMai = jTextField13.getText();
                if (CodeKhuyenMai.isEmpty()) {
                    CodeKhuyenMai = "";
                }

                KhuyenMai khuyenMai = khuyenMaiDAO.findKhuyenMai(CodeKhuyenMai);

                if (khuyenMai == null) {
                    JOptionPane.showMessageDialog(this, "Mã khuyến mãi không tồn tại!");
                    return;
                }

                double TongTienSP = Double.parseDouble(jTextField14.getText());
                double TongTienKhuyenMai = (TongTienSP * khuyenMai.getGoiGiamGia()) / 100;
                double TongTienThanhToan = TongTienSP - TongTienKhuyenMai;

                jTextField16.setText(String.valueOf(TongTienKhuyenMai));
                jTextField17.setText(String.valueOf(TongTienThanhToan));

                ChiTietHD chiTietHD = new ChiTietHD(MaHDMoi, MaSP, SoLuong, khuyenMai.getMaKM());
                ChiTietHDDAO chiTietHDDAO = new ChiTietHDDAO();
                chiTietHDDAO.createChiTietHD(chiTietHD);
            }
            GlobalState.MaHDChon = MaHDMoi;
            this.readHoaDonCho();
            JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Tạo hóa đơn thất bại!");
        }


    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int MaSP = GlobalState.MaSPChon;

        switch (MaSP) {
            case 0:
                this.sayMessage("Vui lòng chọn một dòng sản phẩm muốn thêm ở bảng dưới");
                break;
            default:
                SanPhamDAO sanPhamDAO = new SanPhamDAO();
                List<ChiTietHDDTO> sanPhamChonLst = sanPhamDAO.readSanPhamChon(MaSP);

                DefaultTableModel tableSanPhamChon = (DefaultTableModel) this.rSTableMetro3.getModel();

                for (ChiTietHDDTO sanPham : sanPhamChonLst) {
                    tableSanPhamChon.addRow(new Object[]{
                        GlobalState.MaSPChon,
                        sanPham.getTenSP(),
                        sanPham.getTenDM(),
                        sanPham.getTenMS(),
                        sanPham.getTenKT(),
                        GlobalState.SoLuongSPChon,
                        sanPham.getGia() * GlobalState.SoLuongSPChon,});
                }
                this.thayDoiThongTinHoaDon();
                break;
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        this.resetTableChiTietHD();

        jTextField9.setText("");
        jTextField10.setText("");
        jDateChooser10.setDate(null);
        jTextField12.setText("");
        jTextField14.setText("");
        jTextField17.setText("");
        jTextField18.setText("");
        jTextField19.setText("");
        jTextField13.setText("");
        jTextField16.setText("");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // Điền SĐT -> Thêm -> Nếu SĐT trùng thì ra luôn Khách Hàng, nếu chưa có thì tạo mới 
        String TenKH = jTextField9.getText();
        String SDT = jTextField10.getText();

        if (SDT.isBlank() == true) {
            this.sayMessage("Vui lòng không để trống các ô nhập SDT");
        } else {
            KhachHangDAO khachHangDAO = new KhachHangDAO();
            KhachHang ketQua = khachHangDAO.findKhachHang(TenKH, SDT);

            if (ketQua == null) {
                KhachHang khachHang = new KhachHang(TenKH, SDT);
                int khachHangMoi = khachHangDAO.createKhachHang(khachHang);
                if (khachHangMoi == 1) {
                    KhachHang idKhachHangMoi = khachHangDAO.findKhachHang(TenKH, SDT);
                    GlobalState.MaKH = idKhachHangMoi.getID();
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công");
                    readKhachHang();
                    // code
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!");
                }
            } else {
                GlobalState.MaKH = ketQua.getID();
                jTextField9.setText(ketQua.getTenKH());
            }
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void rSComboMetro4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSComboMetro4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rSComboMetro4ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        if (jTextField15.getText().isEmpty()) {
            readSanPham();
            return;
        }

        int MaSP = Integer.parseInt(jTextField15.getText());

        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        List<SanPhamDTO> sanPhamLst = sanPhamDAO.readSanPhamQLY();

        DefaultTableModel tableSanPham = (DefaultTableModel) this.rSTableMetro2.getModel();
        tableSanPham.setRowCount(0);

        for (SanPhamDTO sanPham : sanPhamLst) {
            if (sanPham.getID() == MaSP) {
                tableSanPham.addRow(new Object[]{
                    sanPham.getID(),
                    sanPham.getTenSP(),
                    sanPham.getTenDM(),
                    sanPham.getTenMS(),
                    sanPham.getTenKT(),
                    sanPham.getSoLuong(),
                    sanPham.getGia(),});
                return;
            }
        }

        if (tableSanPham.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!");
        }

    }//GEN-LAST:event_jButton15ActionPerformed

    private void rSTableMetro2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSTableMetro2MouseClicked
        // TODO add your handling code here:
        int soDongChon = rSTableMetro2.getSelectedRow();
        String MaSPChon = rSTableMetro2.getValueAt(soDongChon, 0).toString();
        String GiaSanPhamChon = rSTableMetro2.getValueAt(soDongChon, 6).toString();

        GlobalState.MaSPChon = Integer.parseInt(MaSPChon);
        GlobalState.GiaSanPhamChon = Double.parseDouble(GiaSanPhamChon);
    }//GEN-LAST:event_rSTableMetro2MouseClicked

    private void rSTableMetro3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_rSTableMetro3PropertyChange
        this.thayDoiThongTinHoaDon();
    }//GEN-LAST:event_rSTableMetro3PropertyChange

    private void rSTableMetro1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_rSTableMetro1PropertyChange


    }//GEN-LAST:event_rSTableMetro1PropertyChange

    private void rSTableMetro1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSTableMetro1MouseClicked
        int soDongChon = rSTableMetro1.getSelectedRow();
        int MaHD = Integer.parseInt(rSTableMetro1.getValueAt(soDongChon, 0).toString());

        GlobalState.MaHDChoChon = MaHD;
    }//GEN-LAST:event_rSTableMetro1MouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel QRScanPanel;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private com.toedter.calendar.JDateChooser jDateChooser10;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField9;
    private rojerusan.RSComboMetro rSComboMetro4;
    private rojerusan.RSTableMetro rSTableMetro1;
    private rojerusan.RSTableMetro rSTableMetro2;
    private rojerusan.RSTableMetro rSTableMetro3;
    private rojerusan.RSTableMetro rSTableMetro4;
    // End of variables declaration//GEN-END:variables

}
