/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import dao.ChiTietHDDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.KhuyenMaiDAO;
import dao.SanPhamDAO;
import dto.ChiTietHDDTO;
import dto.HoaDonDTO;
import dto.SanPhamDTO;
import enity.ChiTietHD;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import enity.HoaDon;
import enity.KhachHang;
import enity.KhuyenMai;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import utils.GlobalState;
import utils.readDetailOrderToForm;

public class QuanLyBanHangJpanel extends javax.swing.JPanel implements Runnable, ThreadFactory {

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

    private WebcamPanel camPanel = null;
    private Webcam webcam = null;
    private ExecutorService executor = null;
    private volatile boolean isShowingInputDialog = false;

    private void moWebcam() {
        // Kiểm tra xem executor (luồng quản lý webcam) đã tồn tại chưa hoặc đã bị tắt chưa
        if (executor == null || executor.isShutdown()) {
            // Nếu executor chưa tồn tại hoặc đã bị tắt, tạo một executor mới
            executor = Executors.newSingleThreadScheduledExecutor(this); // Tạo một executor đơn luồng
        }

        // Tạo một SwingWorker để khởi tạo webcam trong một luồng nền
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            // Phương thức doInBackground() được thực thi trong một luồng nền
            @Override
            protected Void doInBackground() throws Exception {
                // Khởi tạo webcam ở đây, trong background thread
                Dimension size = WebcamResolution.QVGA.getSize(); // Lấy kích thước QVGA cho webcam
                webcam = Webcam.getWebcams().get(0); // Lấy webcam đầu tiên từ danh sách
                webcam.setViewSize(size); // Đặt kích thước cho webcam

                camPanel = new WebcamPanel(webcam); // Tạo một WebcamPanel để hiển thị hình ảnh từ webcam
                camPanel.setPreferredSize(size); // Đặt kích thước ưu tiên cho WebcamPanel
                camPanel.setFPSDisplayed(true); // Hiển thị số khung hình trên giây (FPS) trên WebcamPanel
                return null; // Không trả về giá trị gì từ tác vụ nền
            }

            // Phương thức done() được thực thi trên luồng giao diện người dùng (Event Dispatch Thread) sau khi doInBackground() hoàn thành
            @Override
            protected void done() {
                // Cập nhật giao diện người dùng sau khi webcam đã sẵn sàng
                QRScanPanel.removeAll(); // Xóa tất cả các thành phần con hiện có trong QRScanPanel (để loại bỏ WebcamPanel cũ)
                QRScanPanel.add(camPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 410, 150)); // Thêm WebcamPanel mới vào QRScanPanel với vị trí và kích thước cố định
                QRScanPanel.revalidate(); // Yêu cầu QRScanPanel bố trí lại các thành phần con của nó (đảm bảo WebcamPanel được hiển thị đúng cách)
                QRScanPanel.repaint(); // Yêu cầu QRScanPanel vẽ lại nội dung của nó (đảm bảo hình ảnh từ webcam được hiển thị)
                executor.execute(QuanLyBanHangJpanel.this); // Bắt đầu luồng quét mã QR (QuanLyBanHangJpanel.this là đối tượng QuanLyBanHangJpanel hiện tại, implement Runnable)
            }
        };

        worker.execute(); // Chạy SwingWorker (bắt đầu thực thi doInBackground() trong một luồng nền)
    }

    public void run() {
        try {
            do {
                try {
                    Thread.sleep(5); // Tạm dừng luồng 5ms để giảm tải CPU, giúp chương trình mượt mà hơn
                } catch (InterruptedException ex) {
                    // Xử lý InterruptedException: được ném ra khi một luồng đang chờ bị gián đoạn.
                    System.out.println("Webcam thread interrupted. Stopping."); // In ra thông báo lỗi
                    Thread.currentThread().interrupt(); // Đặt lại cờ ngắt cho luồng hiện tại để các phương thức gọi có thể phản ứng với sự gián đoạn.
                    return; // Kết thúc phương thức run() để dừng thread
                }

                Result rs = null; // Khai báo biến rs kiểu Result để lưu trữ kết quả giải mã QR code.
                BufferedImage image = null; // Khai báo biến image kiểu BufferedImage để lưu trữ hình ảnh từ webcam.

                if (webcam != null && webcam.isOpen()) { // Kiểm tra webcam có tồn tại và đang mở không
                    if ((image = webcam.getImage()) == null) { // Lấy hình ảnh từ webcam. Nếu không có hình ảnh,
                        continue; // Bỏ qua các bước còn lại của vòng lặp và tiếp tục vòng lặp tiếp theo.
                    }
                }

                if (image == null) { // Kiểm tra nếu hình ảnh vẫn null
                    continue; // Bỏ qua các bước còn lại của vòng lặp và tiếp tục vòng lặp tiếp theo.
                }
                LuminanceSource source = new BufferedImageLuminanceSource(image); // Tạo một đối tượng LuminanceSource từ hình ảnh để chuyển đổi thành dữ liệu có thể đọc được bởi ZXing.
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source)); // Chuyển đổi LuminanceSource thành BinaryBitmap, một ảnh nhị phân mà ZXing có thể giải mã.

                try {
                    rs = new MultiFormatReader().decode(bitmap); // Cố gắng giải mã BinaryBitmap để tìm QR code.
                } catch (NotFoundException ex) {
                    // Logger.getLogger(QuanLyBanHangJpanel.class.getName()).log(Level.SEVERE, null, ex);
                    //Không cần log vì việc không tìm thấy QR là bình thường
                    // Xử lý NotFoundException: được ném ra khi không tìm thấy QR code trong hình ảnh.
                    // Trong trường hợp này, không cần ghi log vì việc không tìm thấy QR code là bình thường.
                }

                if (rs != null) { // Kiểm tra nếu đã giải mã thành công QR code
                    String resultText = rs.getText(); // Lấy nội dung của QR code.
                    SwingUtilities.invokeLater(() -> { // Chạy code trong luồng sự kiện đồ họa để cập nhật giao diện người dùng một cách an toàn.
                        if (!isShowingInputDialog) { // Kiểm tra xem hộp thoại nhập liệu có đang hiển thị hay không. Biến isShowingInputDialog là một cờ (flag) để tránh việc mở nhiều hộp thoại cùng lúc.
                            isShowingInputDialog = true; // Đặt cờ thành true trước khi hiển thị hộp thoại để ngăn việc mở thêm hộp thoại khác.
                            try {
                                int productID = Integer.parseInt(resultText); // Chuyển đổi nội dung QR code (resultText) thành một số nguyên (productID) - mã sản phẩm.
                                System.out.println("Đã quét ID sản phẩm: " + productID); // In ra mã sản phẩm đã quét được.

                                SanPhamDAO sanPhamDAO = new SanPhamDAO(); // Tạo một đối tượng SanPhamDAO để truy cập dữ liệu sản phẩm.
                                List<SanPhamDTO> sanPhamLst = sanPhamDAO.readSanPhamQLY(); // Lấy danh sách tất cả các sản phẩm từ cơ sở dữ liệu.

                                SanPhamDTO sanPhamDaTimThay = null; // Khai báo biến sanPhamDaTimThay để lưu trữ sản phẩm tìm thấy.
                                for (SanPhamDTO sanPham : sanPhamLst) { // Duyệt qua danh sách sản phẩm để tìm sản phẩm có mã trùng với productID.
                                    if (sanPham.getID() == productID) { // Nếu tìm thấy sản phẩm có mã trùng với productID,
                                        sanPhamDaTimThay = sanPham; // Gán sản phẩm đó cho biến sanPhamDaTimThay.
                                        break; // Thoát khỏi vòng lặp vì đã tìm thấy sản phẩm.
                                    }
                                }

                                if (sanPhamDaTimThay == null) { // Nếu không tìm thấy sản phẩm,
                                    JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!"); // Hiển thị một thông báo lỗi.
                                    return; // Thoát khỏi khối mã này.
                                }

                                String inputslsp = JOptionPane.showInputDialog(camPanel, "Nhập số lượng sản phẩm:"); // Hiển thị một hộp thoại để người dùng nhập số lượng sản phẩm.
                                if (inputslsp != null && !inputslsp.isEmpty()) { // Kiểm tra nếu người dùng đã nhập số lượng và không nhấn Cancel.
                                    try {
                                        int quantity = Integer.parseInt(inputslsp); // Chuyển đổi số lượng đã nhập thành một số nguyên.
                                        if (quantity > 0) { // Kiểm tra nếu số lượng lớn hơn 0.
                                            GlobalState.MaSPChon = productID; // Lưu mã sản phẩm đã chọn vào biến toàn cục GlobalState.MaSPChon.
                                            GlobalState.SoLuongSPChon = quantity; // Lưu số lượng sản phẩm đã chọn vào biến toàn cục GlobalState.SoLuongSPChon.
                                            double ThanhTien = GlobalState.SoLuongSPChon * sanPhamDaTimThay.getGia(); // Tính thành tiền bằng cách nhân số lượng với giá sản phẩm.

                                            DefaultTableModel tableSanPhamChon = (DefaultTableModel) this.rSTableMetro3.getModel(); // Lấy mô hình bảng (table model) của bảng rSTableMetro3.

                                            tableSanPhamChon.addRow(new Object[]{ // Thêm một hàng mới vào bảng với thông tin sản phẩm đã chọn.
                                                sanPhamDaTimThay.getID(), // Mã sản phẩm.
                                                sanPhamDaTimThay.getTenSP(), // Tên sản phẩm.
                                                sanPhamDaTimThay.getTenDM(), // Tên danh mục.
                                                sanPhamDaTimThay.getTenMS(), // Tên màu sắc.
                                                sanPhamDaTimThay.getTenKT(), // Tên kích thước.
                                                GlobalState.SoLuongSPChon, // Số lượng đã chọn.
                                                ThanhTien // Thành tiền.
                                            });
                                            thayDoiThongTinHoaDonTheoMaQR();
                                        } else { // Nếu số lượng không lớn hơn 0,
                                            JOptionPane.showMessageDialog(camPanel, "Số lượng phải lớn hơn 0.", "Lỗi", JOptionPane.ERROR_MESSAGE); // Hiển thị một thông báo lỗi.
                                        }
                                    } catch (NumberFormatException e) { // Xử lý NumberFormatException: được ném ra khi không thể chuyển đổi chuỗi thành số.
                                        JOptionPane.showMessageDialog(camPanel, "Số lượng không hợp lệ. Vui lòng nhập một số.", "Lỗi", JOptionPane.ERROR_MESSAGE); // Hiển thị một thông báo lỗi.
                                    }
                                }
                            } catch (NumberFormatException e) { // Xử lý NumberFormatException: được ném ra khi không thể chuyển đổi nội dung QR code thành số (mã sản phẩm).
                                JOptionPane.showMessageDialog(camPanel, "Mã QR không hợp lệ. Vui lòng quét một mã QR chứa ID sản phẩm (số).", "Lỗi", JOptionPane.ERROR_MESSAGE); // Hiển thị một thông báo lỗi.
                            } finally {
                                isShowingInputDialog = false; // Đặt cờ thành false sau khi hộp thoại đã đóng, cho phép quét mã QR và mở hộp thoại khác. Đảm bảo cờ luôn được đặt lại, ngay cả khi có lỗi xảy ra.
                            }
                        }
                    });
                }
            } while (!Thread.currentThread().isInterrupted()); // Kiểm tra cờ ngắt trong điều kiện vòng lặp
        } catch (Exception e) {
            System.err.println("Error in webcam thread: " + e.getMessage()); // In ra thông báo lỗi nếu có bất kỳ lỗi nào xảy ra trong quá trình quét QR code.
            e.printStackTrace(); // In ra dấu vết ngăn xếp của lỗi.
        } finally {
            // Đảm bảo đóng webcam trong khối finally
            dongWebcam(); // Gọi phương thức dongWebcam() để đảm bảo webcam được đóng khi luồng kết thúc, ngay cả khi có lỗi xảy ra.
        }
    }

    public void dongWebcam() {
        if (webcam != null) { // Kiểm tra nếu webcam tồn tại

            if (executor != null && !executor.isShutdown()) { // Kiểm tra nếu executor tồn tại và chưa bị tắt

                executor.shutdownNow(); // Tắt executor một cách mạnh mẽ, ngăn chặn các tác vụ đang chạy.
                try {

                    executor.awaitTermination(5, TimeUnit.NANOSECONDS); // Chờ tối đa 5 nano giây để executor tắt hoàn toàn.
                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt(); // Đặt lại cờ ngắt cho luồng hiện tại.
                    System.err.println("Interrupted while waiting for webcam thread to terminate."); // In ra thông báo lỗi nếu bị gián đoạn trong khi chờ executor tắt.
                }
                executor = null; // Đặt executor về null sau khi tắt.
            }

            if (webcam.isOpen()) { // Kiểm tra nếu webcam đang mở
                webcam.close(); // Đóng webcam.
            }
        }

        // Remove camPanel from QRScanPanel *before* setting it to null
        if (camPanel != null) { // Kiểm tra nếu camPanel tồn tại

            //Stop the camPanel before removing it
            camPanel.stop(); // Dừng camPanel trước khi gỡ bỏ nó.

            QRScanPanel.remove(camPanel); // Remove camPanel from QRScanPanel
            QRScanPanel.revalidate(); // Xác nhận lại bố cục của QRScanPanel.
            QRScanPanel.repaint(); // Vẽ lại QRScanPanel.
            camPanel = null; // Set camPanel to null
        }
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }

    private void ScanRsTable3() {
        SanPhamDAO spDAO = new SanPhamDAO();
        int soLuongHienTai;
        int i;
        int ID1;
        int ID2;
        DefaultTableModel Tmodel = (DefaultTableModel) rSTableMetro3.getModel();
        for (i = 0; i < rSTableMetro3.getRowCount(); i++) {
            ID1 = (int) rSTableMetro3.getValueAt(i, 0);

            for (int j = i + 1; j < rSTableMetro3.getRowCount(); j++) {
                ID2 = (int) rSTableMetro3.getValueAt(j, 0);
                if (ID1 == ID2) {
                    String soLuongStr = String.valueOf(rSTableMetro3.getValueAt(i, 5));
                    soLuongHienTai = Integer.parseInt(soLuongStr) + 1;
                    rSTableMetro3.setValueAt(soLuongHienTai, i, 5);
                    double soLuong = Double.parseDouble(String.valueOf(rSTableMetro3.getValueAt(i, 5)));
                    double gia = spDAO.getGiaSP(Integer.parseInt(String.valueOf(rSTableMetro3.getValueAt(i, 0))));
                    double thanhTien = soLuong * gia;
                    rSTableMetro3.setValueAt(thanhTien, i, 6);
                    Tmodel.removeRow(j);
                    rSTableMetro3.setModel(Tmodel);
                }
            }
        }
    }

    public void thayDoiThongTinHoaDonTheoMaQR() {
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

        // Thay đổi Số lượng bán sẽ thay đổi Thông tin hóa đơn
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date NgayThanhLap = java.sql.Date.valueOf(sdf.format(new java.util.Date()));

        int SoLuongSP = 0;
        double TongTienSP = 0.0;
        double TongTienKhuyenMai = 0.0;
        double TongThanhToan;
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

        // Thay đổi Số lượng bán sẽ thay đổi Thông tin hóa đơn
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date NgayThanhLap = java.sql.Date.valueOf(sdf.format(new java.util.Date()));

        int SoLuongSP = 0;
        double TongTienSP = 0.0;
        double TongTienKhuyenMai = 0.0;
        double TongThanhToan;
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
        //Select MaKM
        HoaDonDAO hdDAO = new HoaDonDAO();
        jTextField13.setText(hdDAO.getMaGG(GlobalState.MaHDChoChon));
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
                    sanPhamDAO.updateSanPhamMua(chiTietHD.getMaSP(), chiTietHD.getSoLuong());
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

    private void refundAmountByDeleteRow(int currentRow) {
        try {
            // Lấy mã sản phẩm và số lượng cần hoàn lại từ bảng chi tiết hóa đơn
            int maSP = Integer.parseInt(rSTableMetro3.getValueAt(currentRow, 0).toString());
            int refundAmount = Integer.parseInt(rSTableMetro3.getValueAt(currentRow, 5).toString());
            
            // Lấy số lượng hiện tại của sản phẩm từ database
            SanPhamDAO sanPhamDAO = new SanPhamDAO();
            int currentAmount = sanPhamDAO.getSoLuongByID(maSP);

            // Cập nhật số lượng mới bằng cách cộng thêm số lượng đã hoàn lại
            int updatedAmount = currentAmount + refundAmount;
            System.out.println("Update Amount: "+updatedAmount);
            sanPhamDAO.updateSoLuongByID(maSP, updatedAmount);
            this.readSanPham();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi hoàn lại số lượng sản phẩm!");
        }
    }

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
        jButton17 = new javax.swing.JButton();

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
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
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

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
                .addContainerGap(14, Short.MAX_VALUE))
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
                .addContainerGap(22, Short.MAX_VALUE))
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
                .addGap(135, 135, 135)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(246, 225, 225));
        jPanel11.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel11.setPreferredSize(new java.awt.Dimension(1300, 300));

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(128, 0, 0));
        jLabel16.setText("Quét mã vạch sản phẩm");

        QRScanPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
                .addGap(0, 20, Short.MAX_VALUE))
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
                .addComponent(QRScanPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(246, 225, 225));
        jPanel12.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(128, 0, 0)));
        jPanel12.setPreferredSize(new java.awt.Dimension(1300, 300));

        rSTableMetro3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
                .addContainerGap(13, Short.MAX_VALUE))
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

        jButton17.setBackground(new java.awt.Color(255, 255, 255));
        jButton17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton17.setForeground(new java.awt.Color(128, 0, 0));
        jButton17.setText("Xóa");
        jButton17.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(128, 0, 0)));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
            .addComponent(jScrollPane2)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21))))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton10)
                            .addComponent(jButton17)
                            .addComponent(jButton9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
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
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addGap(29, 29, 29))
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
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

        // Chọn hóa đơn cần xóa
        int MaHD = GlobalState.MaHDChoChon;

        if (MaHD == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xóa!");
            return;
        }

        HoaDonDAO hoaDonDAO = new HoaDonDAO();
        ChiTietHDDAO chiTietHDDAO = new ChiTietHDDAO();
        SanPhamDAO sanPhamDAO = new SanPhamDAO();

        // Cập nhật trạng thái hóa đơn thành "Hủy"
        int ketQuaHoaDon = hoaDonDAO.updateHoaDonHuy(MaHD);

        if (ketQuaHoaDon == 1) {
            // Lấy danh sách sản phẩm trong chi tiết hóa đơn
            List<ChiTietHD> danhSachChiTiet = chiTietHDDAO.findChiTietHD(MaHD);

            for (ChiTietHD chiTiet : danhSachChiTiet) {
                int MaSP = chiTiet.getMaSP();
                int SoLuongDaHuy = chiTiet.getSoLuong();

                // Hoàn lại số lượng sản phẩm trong database
                sanPhamDAO.updateSanPhamHuy(MaSP, SoLuongDaHuy);
            }
            this.readSanPham();
            JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công");
            this.readHoaDonCho(); // Làm mới lại danh sách hóa đơn
        } else {
            JOptionPane.showMessageDialog(this, "Xóa hóa đơn thất bại!");
        }
    }//GEN-LAST:event_jButton6ActionPerformed
    int MaHD;
    String HinhThucThanhToan;
    String TrangThai;
    String amount;
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        MaHD = GlobalState.MaHDChoChon;
        System.out.println("MaHD: " + MaHD);
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
                sanPhamDAO.updateSanPhamMua(chiTietHD.getMaSP(), chiTietHD.getSoLuong());
            }
            System.out.println(ketQua);
            if (ketQua == 1) {
                this.readSanPham();
                this.readHoaDonCho();
                this.clean();
                JOptionPane.showMessageDialog(this, "Thanh toán thành công");
            } else {
                System.out.println("KQ: " + ketQua);
                JOptionPane.showMessageDialog(this, "Thanh toán thất bại!");
            }
        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // Tạo hóa đơn
        int MaKH = GlobalState.MaKH;
        double ThanhTien = Double.parseDouble(jTextField17.getText());
        String HinhThucThanhToan = rSComboMetro4.getSelectedItem().toString();
        Date NgayTao = jDateChooser10.getDate();
        String TrangThai = "Đang xử lý";
        String MaKM = jTextField13.getText();
        HoaDon hoaDon = new HoaDon(MaKH, ThanhTien, HinhThucThanhToan, NgayTao, TrangThai, MaKM);

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

                SanPhamDAO sanPhamDAO = new SanPhamDAO();
                sanPhamDAO.updateSanPhamMua(MaSP, SoLuong);

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
                // Hiển thị số lượng trừ tạm thời trên bảng sản phẩm
                for (int i = 0; i < rSTableMetro2.getRowCount(); i++) {
                    int idSanPham = Integer.parseInt(rSTableMetro2.getValueAt(i, 0).toString());
                    if (idSanPham == MaSP) {
                        int SoLuongHienTai = Integer.parseInt(rSTableMetro2.getValueAt(i, 5).toString());
                        int soLuongFinal = SoLuongHienTai - GlobalState.SoLuongSPChon;
                        rSTableMetro2.setValueAt(soLuongFinal, i, 5);
                        break;
                    }
                }

                this.ScanRsTable3();
                this.thayDoiThongTinHoaDon();
                break;
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        ChiTietHDDAO chiTietHDDAO = new ChiTietHDDAO();
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        // Lấy danh sách sản phẩm trong chi tiết hóa đơn
        List<ChiTietHD> danhSachChiTiet = chiTietHDDAO.findChiTietHD(MaHD);

        for (ChiTietHD chiTiet : danhSachChiTiet) {
            int MaSP = chiTiet.getMaSP();
            int SoLuongDaHuy = chiTiet.getSoLuong();

            // Hoàn lại số lượng sản phẩm trong database
            sanPhamDAO.updateSanPhamHuy(MaSP, SoLuongDaHuy);
        }
        this.readSanPham();
        this.clean();
    }//GEN-LAST:event_jButton10ActionPerformed
    private void clean() {
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
    }
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

    private void LoadUp2Form() {
        DefaultTableModel Tmodel;
        Tmodel = (DefaultTableModel) rSTableMetro3.getModel();
        Tmodel.setRowCount(0);

        readDetailOrderToForm readFunction = new readDetailOrderToForm();
        HoaDonDTO info = readFunction.readHoaDonByID(GlobalState.MaHDChoChon);
        //
        jTextField9.setText(info.getTenKH());
        jTextField10.setText(info.getSDT());
        rSComboMetro4.setSelectedItem(info.getHinhThucTT());
        jDateChooser10.setDate(info.getNgayTao());

        List<ChiTietHDDTO> lstChiTietDTO = new ArrayList<>();
        lstChiTietDTO = readFunction.readChiTietHD(GlobalState.MaHDChoChon);
        for (ChiTietHDDTO chiTietHDDTO : lstChiTietDTO) {
            Tmodel.addRow(new Object[]{
                chiTietHDDTO.getMaSP(),
                chiTietHDDTO.getTenSP(),
                chiTietHDDTO.getTenDM(),
                chiTietHDDTO.getTenMS(),
                chiTietHDDTO.getTenKT(),
                chiTietHDDTO.getSoLuong(),
                chiTietHDDTO.getGia() * chiTietHDDTO.getSoLuong()
            });
        }
        this.thayDoiThongTinHoaDon();
    }
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
        int currentID = Integer.parseInt((String.valueOf(rSTableMetro1.getValueAt(soDongChon, 0))));
        GlobalState.MaHDChoChon = currentID;
        this.LoadUp2Form();
        this.ScanRsTable3();
        this.triggerDiscount();
    }//GEN-LAST:event_rSTableMetro1MouseClicked


    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here
        this.triggerDiscount();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void triggerDiscount() {
        String discountCode = jTextField13.getText();
        KhuyenMaiDAO kmDAO = new KhuyenMaiDAO();
        int discountPack = kmDAO.getDiscountPack(discountCode);
        if (discountPack == 0) {
            jTextField16.setText("");
        } else {
            double TongTienSP = Double.parseDouble(jTextField14.getText());
            double TongTienKhuyenMai = (TongTienSP * discountPack) / 100;
            double TongTienThanhToan = TongTienSP - TongTienKhuyenMai;

            jTextField16.setText(String.valueOf(TongTienKhuyenMai));
            jTextField17.setText(String.valueOf(TongTienThanhToan));
        }
    }
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        moWebcam();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        dongWebcam();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tableChiTietHD = (DefaultTableModel) rSTableMetro3.getModel();
        int currentRow = rSTableMetro3.getSelectedRow();
        System.out.println("ROw: "+currentRow);
        if (currentRow != -1) {
            // Hoàn lại số lượng sản phẩm về danh sách sản phẩm
            refundAmountByDeleteRow(currentRow);

            // Xóa dòng khỏi bảng chi tiết hóa đơn
            tableChiTietHD.removeRow(currentRow);

            JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!");
        }

    }//GEN-LAST:event_jButton17ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel QRScanPanel;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
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
