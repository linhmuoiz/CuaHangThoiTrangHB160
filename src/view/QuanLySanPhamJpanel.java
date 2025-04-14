/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import dao.DanhMucDAO;
import dao.KichThuocDAO;
import dao.MauSacDAO;
import dao.SanPhamDAO;
import dto.SanPhamDanhMucMauSacKichThuocDTO;
import enity.DanhMuc;
import enity.KichThuoc;
import enity.MauSac;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admim
 */
public class QuanLySanPhamJpanel extends javax.swing.JPanel {

    /**
     * Creates new form QuanLySanPhamJpanel
     */
    public QuanLySanPhamJpanel() {
        initComponents();
        FillTable();
        loadCBODanhMuc();
        loadCBOMauSac();
        loadCBOKichThuoc();
        lblHinhAnh.setText("Chọn Ảnh");
    }
    private String imagePath;
    private String showImage;

    private void FillTable() {
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        List<SanPhamDanhMucMauSacKichThuocDTO> sanPhamLst = sanPhamDAO.readSanPham();
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();

        model.setRowCount(0);
        for (SanPhamDanhMucMauSacKichThuocDTO sanPham : sanPhamLst) {
            System.out.println("Adding product: " + sanPham.getTenSp());

            Object[] rowData = {
                sanPham.getID(),
                sanPham.getTenSp(),
                sanPham.getGia(),
                sanPham.getSoLuong(),
                sanPham.getTrangThai(),
                sanPham.getTenDM(),
                sanPham.getTenMS(),
                sanPham.getTenKT(),
                sanPham.getHinhAnh()
            };
            model.addRow(rowData);
        }
    }

    private void loadCBODanhMuc() {
        DanhMucDAO danhMucDAO = new DanhMucDAO();
        List<DanhMuc> TenDanhMucLst = danhMucDAO.loadComboBox();

        for (DanhMuc danhMuc : TenDanhMucLst) {
            cboDanhMuc.addItem(danhMuc.getTenDM());
        }
    }

    private void loadCBOMauSac() {
        MauSacDAO mauSacDAO = new MauSacDAO();
        List<MauSac> TenMauSacLst = mauSacDAO.loadComboBox();

        for (MauSac mauSac : TenMauSacLst) {
            cboMauSac.addItem(mauSac.getTenMS());
        }
    }

    private void loadCBOKichThuoc() {
        KichThuocDAO kichThuocDAO = new KichThuocDAO();
        List<KichThuoc> TenKichThuocLst = kichThuocDAO.loadComboBox();

        for (KichThuoc kichThuoc : TenKichThuocLst) {
            cboKichThuoc.addItem(kichThuoc.getTenKT());
        }
    }

    private void displayImage(String imagePath) {
        try {
            // 1. Lấy đường dẫn tuyệt đối đến thư mục dự án
            String projectPath = System.getProperty("user.dir");

            // 2. Tạo đường dẫn tuyệt đối đầy đủ đến hình ảnh
            String absoluteImagePath = projectPath + "\\src\\png\\" + imagePath;

            // 3. Tạo ImageIcon từ đường dẫn tuyệt đối
            ImageIcon imageIcon = new ImageIcon(absoluteImagePath);

            //Kiểm tra xem ảnh có load được không
            if (imageIcon == null || imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.out.println("Không load được ảnh, đường dẫn không hợp lệ");
                System.out.println(absoluteImagePath);
            }

            // 4. Thay đổi kích thước hình ảnh để vừa với kích thước JLabel
            int width = 480;
            int height = 340;
            Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(image);

            // 5. Đặt ảnh đã resize vào JLabel
            lblHinhAnh.setIcon(scaledImageIcon);
            lblHinhAnh.setText("");
        } catch (Exception e) {
            System.out.println("Không load được, lỗi: " + e.getMessage());
            System.out.println(imagePath); //In ra đường dẫn để debug
        }
    }

    private void XoaNoiDungNhapLieu() {
        txtGia.setText("");
        txtSoLuong.setText("");
        txtTenSanPham.setText("");
        cboDanhMuc.setSelectedIndex(0);
        cboKichThuoc.setSelectedIndex(0);
        cboMauSac.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
        lblHinhAnh.setIcon(null); //xoá ảnh
        imagePath = null; // đặt lại đường dẫn
    }

    private boolean showPasswordDialog() {
        java.awt.Frame parentFrame = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        DoiMatKhauJdialog dialog = new DoiMatKhauJdialog(parentFrame, true);
        dialog.setLocationRelativeTo(this); // Center relative to the JPanel
        dialog.setVisible(true);
        return dialog.isAuthenticated();
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
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtTenSanPham = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cboDanhMuc = new rojerusan.RSComboMetro();
        cboMauSac = new rojerusan.RSComboMetro();
        jLabel18 = new javax.swing.JLabel();
        cboTrangThai = new rojerusan.RSComboMetro();
        jLabel19 = new javax.swing.JLabel();
        cboKichThuoc = new rojerusan.RSComboMetro();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        lblHinhAnh = new javax.swing.JLabel();
        btnThemAnh = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSach = new rojerusan.RSTableMetro();
        jLabel23 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        rdoMa = new javax.swing.JRadioButton();
        rdoTen = new javax.swing.JRadioButton();
        jLabel22 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1500, 1000));
        setMinimumSize(new java.awt.Dimension(1500, 1000));

        jPanel6.setBackground(new java.awt.Color(246, 225, 225));
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel6.setPreferredSize(new java.awt.Dimension(1300, 400));

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(128, 0, 0));
        jLabel13.setText("Tên Sản Phẩm :");

        txtTenSanPham.setBackground(new java.awt.Color(246, 225, 225));
        txtTenSanPham.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txtTenSanPham.setForeground(new java.awt.Color(128, 0, 0));
        txtTenSanPham.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        txtTenSanPham.setMinimumSize(new java.awt.Dimension(300, 30));
        txtTenSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSanPhamActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(128, 0, 0));
        jLabel14.setText("Đơn Giá:");

        txtGia.setBackground(new java.awt.Color(246, 225, 225));
        txtGia.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txtGia.setForeground(new java.awt.Color(128, 0, 0));
        txtGia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        txtGia.setMinimumSize(new java.awt.Dimension(300, 30));
        txtGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(128, 0, 0));
        jLabel15.setText("Số Lượng: ");

        txtSoLuong.setBackground(new java.awt.Color(246, 225, 225));
        txtSoLuong.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txtSoLuong.setForeground(new java.awt.Color(128, 0, 0));
        txtSoLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(128, 0, 0)));
        txtSoLuong.setMinimumSize(new java.awt.Dimension(300, 30));
        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(128, 0, 0));
        jLabel16.setText("Trạng Thái: ");

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(128, 0, 0));
        jLabel17.setText("Danh Mục:");

        cboDanhMuc.setBackground(new java.awt.Color(246, 225, 225));
        cboDanhMuc.setForeground(new java.awt.Color(128, 0, 0));
        cboDanhMuc.setColorArrow(new java.awt.Color(128, 0, 0));
        cboDanhMuc.setColorBorde(new java.awt.Color(128, 0, 0));
        cboDanhMuc.setColorFondo(new java.awt.Color(246, 225, 225));
        cboDanhMuc.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        cboDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDanhMucActionPerformed(evt);
            }
        });

        cboMauSac.setBackground(new java.awt.Color(246, 225, 225));
        cboMauSac.setForeground(new java.awt.Color(128, 0, 0));
        cboMauSac.setColorArrow(new java.awt.Color(128, 0, 0));
        cboMauSac.setColorBorde(new java.awt.Color(128, 0, 0));
        cboMauSac.setColorFondo(new java.awt.Color(246, 225, 225));
        cboMauSac.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        cboMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauSacActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(128, 0, 0));
        jLabel18.setText("Màu Sắc:");

        cboTrangThai.setBackground(new java.awt.Color(246, 225, 225));
        cboTrangThai.setForeground(new java.awt.Color(128, 0, 0));
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Đang Hoạt Động", "Không Hoạt Động" }));
        cboTrangThai.setColorArrow(new java.awt.Color(128, 0, 0));
        cboTrangThai.setColorBorde(new java.awt.Color(128, 0, 0));
        cboTrangThai.setColorFondo(new java.awt.Color(246, 225, 225));
        cboTrangThai.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(128, 0, 0));
        jLabel19.setText("Kích Thước: ");

        cboKichThuoc.setBackground(new java.awt.Color(246, 225, 225));
        cboKichThuoc.setForeground(new java.awt.Color(128, 0, 0));
        cboKichThuoc.setColorArrow(new java.awt.Color(128, 0, 0));
        cboKichThuoc.setColorBorde(new java.awt.Color(128, 0, 0));
        cboKichThuoc.setColorFondo(new java.awt.Color(246, 225, 225));
        cboKichThuoc.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        cboKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKichThuocActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(255, 255, 255));
        btnThem.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        btnThem.setForeground(new java.awt.Color(128, 0, 0));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add-user-3-48.png"))); // NOI18N
        btnThem.setText("Thêm ");
        btnThem.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 255, 255));
        btnSua.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        btnSua.setForeground(new java.awt.Color(128, 0, 0));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/available-updates-48.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 255, 255));
        btnXoa.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(128, 0, 0));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-48.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(246, 225, 225));
        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel7.setPreferredSize(new java.awt.Dimension(1300, 400));
        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        lblHinhAnh.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jPanel7.add(lblHinhAnh);

        btnThemAnh.setBackground(new java.awt.Color(255, 255, 255));
        btnThemAnh.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        btnThemAnh.setForeground(new java.awt.Color(128, 0, 0));
        btnThemAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/camera-48.png"))); // NOI18N
        btnThemAnh.setText("Thêm Ảnh");
        btnThemAnh.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        btnThemAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemAnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel15)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(cboDanhMuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(cboTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboKichThuoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnThemAnh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 4, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(btnThem))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(btnSua))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(24, 24, 24))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                .addComponent(jLabel15)
                                                .addGap(18, 18, 18)))
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel16))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel17)
                                            .addComponent(cboDanhMuc, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel18)
                                            .addComponent(cboMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel19)
                                            .addComponent(cboKichThuoc, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(btnXoa)
                                        .addGap(29, 29, 29)
                                        .addComponent(btnThemAnh)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 28)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(128, 0, 0));
        jLabel2.setText("Điều Chỉnh Thông Tin Sản Phẩm");

        jPanel15.setBackground(new java.awt.Color(246, 225, 225));
        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel15.setPreferredSize(new java.awt.Dimension(1300, 400));

        tblDanhSach.setBackground(new java.awt.Color(246, 225, 225));
        tblDanhSach.setForeground(new java.awt.Color(255, 255, 255));
        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng", "Trạng Thái", "Danh Mục", "Màu Sắc", "Kích Thước"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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
        tblDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSach);

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(128, 0, 0));
        jLabel23.setText("Tìm Kiếm:");

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

        buttonGroup1.add(rdoMa);
        rdoMa.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        rdoMa.setForeground(new java.awt.Color(128, 0, 0));
        rdoMa.setSelected(true);
        rdoMa.setText("Theo Mã");

        buttonGroup1.add(rdoTen);
        rdoTen.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        rdoTen.setForeground(new java.awt.Color(128, 0, 0));
        rdoTen.setText("Theo Tên");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(rdoMa)
                .addGap(36, 36, 36)
                .addComponent(rdoTen)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdoMa)
                        .addComponent(rdoTen)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 28)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(128, 0, 0));
        jLabel22.setText("Thông Tin Sản Phẩm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel2))
                        .addGap(862, 862, 862)))
                .addGap(112, 112, 112))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSanPhamActionPerformed

    private void txtGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void cboDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDanhMucActionPerformed

    private void cboMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMauSacActionPerformed

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void cboKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKichThuocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboKichThuocActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        String TenSP = txtTenSanPham.getText();
        String Gia = txtGia.getText();
        String SoLuong = txtSoLuong.getText();
        String TrangThai = (String) cboTrangThai.getSelectedItem();
        String DanhMuc = (String) cboDanhMuc.getSelectedItem();
        String MauSac = (String) cboMauSac.getSelectedItem();
        String KichThuoc = (String) cboKichThuoc.getSelectedItem();

        if (TenSP.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên Sản Phẩm Không Được Để Trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Gia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá Không Được Để Trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (SoLuong.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số Lượng Không Được Để Trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double giaDouble = 0;
        try {
            giaDouble = Double.parseDouble(Gia);
            if (giaDouble < 0 || giaDouble == 0) {
                JOptionPane.showMessageDialog(this, "Vui Lòng Nhập Lại");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá phải là một số thực hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int SLint = 0;
        try {
            SLint = Integer.parseInt(SoLuong);
            if (SLint <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là một số nguyên dương lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là một số nguyên hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SanPhamDanhMucMauSacKichThuocDTO sanPham = new SanPhamDanhMucMauSacKichThuocDTO();
        sanPham.setTenSp(TenSP);
        sanPham.setGia(giaDouble);
        sanPham.setSoLuong(SLint);
        sanPham.setTrangThai(TrangThai);
        sanPham.setTenDM(DanhMuc);
        sanPham.setTenMS(MauSac);
        sanPham.setTenKT(KichThuoc);
        sanPham.setHinhAnh(imagePath);

        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        int ketQua = sanPhamDAO.ThemSanPham(sanPham);

        if (ketQua == 1) {
            JOptionPane.showMessageDialog(this, "Thêm Sản Phẩm Thành Công");
            XoaNoiDungNhapLieu();
            FillTable();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm Thất Bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        if (showPasswordDialog()) {
            int dongDangChon = tblDanhSach.getSelectedRow();
            if (dongDangChon != -1) {
                DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
                int ID = (int) model.getValueAt(dongDangChon, 0);

                SanPhamDAO sanPham = new SanPhamDAO();
                int ketQua = sanPham.CapNhatTrangThaiSanPham(ID, "Đã Xóa");

                if (ketQua == 1) {
                    JOptionPane.showMessageDialog(this, "Sản phẩm đã được xóa thành công");
                    model.removeRow(dongDangChon);
                    FillTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Ẩn sản phẩm thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng dữ liệu để ẩn", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:

        int dongDangChon = tblDanhSach.getSelectedRow();
        if (dongDangChon != -1) {
            String TenSP = txtTenSanPham.getText();
            String Gia = txtGia.getText();
            String SoLuong = txtSoLuong.getText();
            String TrangThai = (String) cboTrangThai.getSelectedItem();
            String DanhMuc = (String) cboDanhMuc.getSelectedItem();
            String MauSac = (String) cboMauSac.getSelectedItem();
            String KichThuoc = (String) cboKichThuoc.getSelectedItem();
            DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
            int ID = (int) model.getValueAt(dongDangChon, 0);

            if (TenSP.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên Sản Phẩm Không Được Để Trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (Gia.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Giá Không Được Để Trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (SoLuong.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Số Lượng Không Được Để Trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double giaDouble = 0;
            try {
                giaDouble = Double.parseDouble(Gia);
                if (giaDouble < 0 || giaDouble == 0) {
                    JOptionPane.showMessageDialog(this, "Vui Lòng Nhập Lại");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá phải là một số thực hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int SLint = 0;
            try {
                SLint = Integer.parseInt(SoLuong);
                if (SLint <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải là một số nguyên dương lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là một số nguyên hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SanPhamDanhMucMauSacKichThuocDTO sanPham = new SanPhamDanhMucMauSacKichThuocDTO();
            sanPham.setID(ID);
            sanPham.setTenSp(TenSP);
            sanPham.setGia(giaDouble);
            sanPham.setSoLuong(SLint);
            sanPham.setTrangThai(TrangThai);
            sanPham.setTenDM(DanhMuc);
            sanPham.setTenMS(MauSac);
            sanPham.setTenKT(KichThuoc);
            sanPham.setHinhAnh(imagePath);

            SanPhamDAO sanPhamDAO = new SanPhamDAO();
            int ketQua = sanPhamDAO.SuaSanPham(sanPham);

            if (ketQua == 1) {
                JOptionPane.showMessageDialog(this, "Sửa Sản Phẩm Thành Công");
                XoaNoiDungNhapLieu();
                FillTable();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa Thất Bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng dữ liệu để sửa", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemAnhActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(imageFilter);

        int returnVal = fileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String absolutePath = selectedFile.getAbsolutePath();

            // 1. Lấy đường dẫn tuyệt đối đến thư mục dự án (thay đổi phần này phù hợp với dự án của bạn)
            String projectPath = System.getProperty("user.dir"); // Thường thì user.dir sẽ trỏ đến thư mục dự án

            // 2. Tạo đường dẫn tuyệt đối đến thư mục icon
            String iconFolderPath = projectPath + "\\src\\png\\"; //hoặc "/src/icon/" tuỳ hệ điều hành

            // 3. Tạo đường dẫn tương đối
            try {
                File iconFolderFile = new File(iconFolderPath);
                String relativePath = iconFolderFile.toURI().relativize(new File(absolutePath).toURI()).getPath();
                imagePath = relativePath; // Lưu đường dẫn tương đối

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi chuyển đổi đường dẫn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            displayImage(imagePath);
        }
    }//GEN-LAST:event_btnThemAnhActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        String theo = rdoMa.isSelected() ? "Theo Mã" : "Theo Tên";
        String TimKiem = txtTimKiem.getText();

        if ("Theo Mã".equals(theo)) {
            if (TimKiem.isBlank() == true) {
                FillTable();
            } else {
                SanPhamDAO sanPhamDAO = new SanPhamDAO();
                List<SanPhamDanhMucMauSacKichThuocDTO> sanPhamLst = sanPhamDAO.TimKiemSanPhamTheoMa(TimKiem);
                DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();

                model.setRowCount(0);
                for (SanPhamDanhMucMauSacKichThuocDTO sanPham : sanPhamLst) {
                    System.out.println("Adding product: " + sanPham.getTenSp());

                    Object[] rowData = {
                        sanPham.getID(),
                        sanPham.getTenSp(),
                        sanPham.getGia(),
                        sanPham.getSoLuong(),
                        sanPham.getTrangThai(),
                        sanPham.getTenDM(),
                        sanPham.getTenMS(),
                        sanPham.getTenKT()
                    };
                    model.addRow(rowData);
                }
            }

        } else if ("Theo Tên".equals(theo)) {
            if (TimKiem.isBlank() == true) {
                FillTable();
            } else {
                SanPhamDAO sanPhamDAO = new SanPhamDAO();
                List<SanPhamDanhMucMauSacKichThuocDTO> sanPhamLst = sanPhamDAO.TimKiemSanPhamTheoTen(TimKiem);
                DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();

                model.setRowCount(0);
                for (SanPhamDanhMucMauSacKichThuocDTO sanPham : sanPhamLst) {
                    System.out.println("Adding product: " + sanPham.getTenSp());

                    Object[] rowData = {
                        sanPham.getID(),
                        sanPham.getTenSp(),
                        sanPham.getGia(),
                        sanPham.getSoLuong(),
                        sanPham.getTrangThai(),
                        sanPham.getTenDM(),
                        sanPham.getTenMS(),
                        sanPham.getTenKT()
                    };
                    model.addRow(rowData);
                }
            }
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked
        // TODO add your handling code here:
        int dongDangChon = tblDanhSach.getSelectedRow();

        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        int ID = (int) tblDanhSach.getValueAt(dongDangChon, 0);
        imagePath = sanPhamDAO.getHinhAnhSanPham(ID);

        String TenSanPham = model.getValueAt(dongDangChon, 1).toString();
        System.out.println("TenSP: " + TenSanPham);
        String Gia = model.getValueAt(dongDangChon, 2).toString();
        String SoLuong = model.getValueAt(dongDangChon, 3).toString();
        String TrangThai = model.getValueAt(dongDangChon, 4).toString();
        String DanhMuc = model.getValueAt(dongDangChon, 5).toString();
        String MauSac = model.getValueAt(dongDangChon, 6).toString();
        String KichThuoc = model.getValueAt(dongDangChon, 7).toString();

        txtTenSanPham.setText(TenSanPham);
        txtGia.setText(String.valueOf(Gia));
        txtSoLuong.setText(String.valueOf(SoLuong));
        cboTrangThai.setSelectedItem(TrangThai);
        cboDanhMuc.setSelectedItem(DanhMuc);
        cboMauSac.setSelectedItem(MauSac);
        cboKichThuoc.setSelectedItem(KichThuoc);

        System.out.println("Đường dẫn hiển thị ảnh :" + imagePath);
        displayImage(imagePath);

    }//GEN-LAST:event_tblDanhSachMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemAnh;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private rojerusan.RSComboMetro cboDanhMuc;
    private rojerusan.RSComboMetro cboKichThuoc;
    private rojerusan.RSComboMetro cboMauSac;
    private rojerusan.RSComboMetro cboTrangThai;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JRadioButton rdoMa;
    private javax.swing.JRadioButton rdoTen;
    private rojerusan.RSTableMetro tblDanhSach;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
