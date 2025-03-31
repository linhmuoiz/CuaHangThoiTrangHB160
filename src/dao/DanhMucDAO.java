/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enity.DanhMuc;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import utils.KetNoiDB;

/**
 *
 * @author admim
 */
public class DanhMucDAO {

    public List<DanhMuc> loadComboBox() {
//        String SQL = "SELECT TenDM FROM DanhMuc";
        List<DanhMuc> TenDanhMucLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT TenDM FROM DanhMuc";

            System.out.println("Executing SQL Query: " + sql);

            PreparedStatement ppStm = conn.prepareStatement(sql);
            ResultSet rs = ppStm.executeQuery();

            while (rs.next()) {
                String TenDM = rs.getString("TenDM");

                DanhMuc danhMuc = new DanhMuc(0, TenDM);
                TenDanhMucLst.add(danhMuc);
            }
            return TenDanhMucLst;
        } catch (Exception e) {
            System.err.println("Error reading products from the database:");
            e.printStackTrace();
            return TenDanhMucLst;
        }
    }

    public List<DanhMuc> readDanhMuc() {
        List<DanhMuc> DanhMucLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT * FROM DanhMuc";
            System.out.println("Executing SQL Query: " + sql);

            PreparedStatement ppStm = conn.prepareStatement(sql);
            ResultSet rs = ppStm.executeQuery();

            while (rs.next()) {
                int MaDM = rs.getInt("MaDM");
                String TenDM = rs.getString("TenDM");

                DanhMuc danhMuc = new DanhMuc(MaDM, TenDM);
                DanhMucLst.add(danhMuc);
            }
            return DanhMucLst;
        } catch (Exception e) {
            return DanhMucLst;
        }
    }

    public int ThemDanhMuc(DanhMuc danhMuc) {
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "INSERT INTO DanhMuc (TenDM) VALUES (?)";
            PreparedStatement ppStm = conn.prepareStatement(sql);

            ppStm.setString(1, danhMuc.getTenDM());

            int ketQua = ppStm.executeUpdate();
            return ketQua;
        } catch (Exception e) {
            System.out.println("Lỗi");
            return 0;
        }
    }

    public int SuaDanhMuc(DanhMuc danhMuc) {
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "UPDATE DanhMuc SET TenDM = ? WHERE MaDM = ?";
            PreparedStatement ppStm = conn.prepareStatement(sql);

            ppStm.setString(1, danhMuc.getTenDM());
            ppStm.setInt(2, danhMuc.getID());

            int ketQua = ppStm.executeUpdate();
            return ketQua;
        } catch (Exception e) {
            System.out.println("Lỗi");
            return 0;
        }
    }

    public int XoaDanhMuc(int MaDM) {
        // Khai báo ở ngoài khối try để có thể truy cập trong khối finally (để đóng kết nối).
        Connection conn = null;
        PreparedStatement ppStmChiTietHD = null;
        PreparedStatement ppStmSanPham = null;
        PreparedStatement ppStmDanhMuc = null;
        try {
            //Kết nối đến cơ sở dữ liệu
            conn = KetNoiDB.getConnectDB();
            
            // 2. Vô hiệu hóa cơ chế tự động commit của JDBC. Để quản lý transaction một cách rõ ràng.
            conn.setAutoCommit(false);

            // 1. Xóa các bản ghi liên quan trong bảng ChiTietHD (Chi tiết Hóa đơn).
            //    Tìm các sản phẩm thuộc danh mục cần xóa (MaDM) và xóa các bản ghi ChiTietHD liên quan.
            String sqlChiTietHD = "DELETE FROM ChiTietHD WHERE MaSP IN (SELECT ID FROM SanPham WHERE MaDM = ?)";
            ppStmChiTietHD = conn.prepareStatement(sqlChiTietHD);
            ppStmChiTietHD.setInt(1, MaDM);
            ppStmChiTietHD.executeUpdate();

            // 2. Xóa các sản phẩm liên quan trong bảng SanPham.
            //    Xóa tất cả các sản phẩm có MaDM trùng với MaDM cần xóa.
            String sqlSanPham = "DELETE FROM SanPham WHERE MaDM = ?";
            ppStmSanPham = conn.prepareStatement(sqlSanPham);
            ppStmSanPham.setInt(1, MaDM);
            ppStmSanPham.executeUpdate();

            // 3. Xóa danh mục trong bảng DanhMuc.
            //    Xóa danh mục có MaDM trùng với MaDM cần xóa.
            String sqlDanhMuc = "DELETE FROM DanhMuc WHERE MaDM = ?";
            ppStmDanhMuc = conn.prepareStatement(sqlDanhMuc);
            ppStmDanhMuc.setInt(1, MaDM);
            int ketQua = ppStmDanhMuc.executeUpdate();
            
            // 6. Commit transaction.
            //    Lưu tất cả các thay đổi vào cơ sở dữ liệu.
            //    Nếu không có lỗi nào xảy ra, các thao tác xóa sẽ được thực hiện vĩnh viễn.
            //  như kiểu khi mày xoá ct hoá đơn thì nó lưu luôn và dữ liệu và nó sẽ làm cái tiếp theo để ko bị lỗi
            conn.commit();
            return ketQua;
        } catch (SQLException e) {
            System.err.println("Lỗi xóa danh mục: " + e.getMessage());
            e.printStackTrace();
            
            // 9. Rollback transaction.
            //    Hoàn tác tất cả các thay đổi đã thực hiện trong transaction.
            //    Nếu có lỗi xảy ra trong quá trình xóa, tất cả các thao tác sẽ được hủy bỏ.
            
            //cái rollback như kiểu là mày xoá đc chi tiết hoá đơn nhưng đến xoá sản phẩm thì nó lại lỗi nên nó huỷ luôn thao tác xáo chi tiết hoá đơn 
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Đã rollback transaction");
                } catch (SQLException rollbackEx) {
                    System.err.println("Lỗi rollback: " + rollbackEx.getMessage());
                    rollbackEx.printStackTrace();
                }
            }
            
            return 0;
        } finally {
            // 11. Đảm bảo đóng tất cả các connection và statement.
            //     Trong khối finally, chúng ta sẽ luôn luôn đóng các kết nối và statement,
            //     bất kể có lỗi xảy ra hay không.
            // Nếu đã tạo PreparedStatement, hãy đóng nó để giải phóng tài nguyên
            try {
                if (ppStmChiTietHD != null) {
                    ppStmChiTietHD.close();
                }
                if (ppStmSanPham != null) {
                    ppStmSanPham.close();
                }
                if (ppStmDanhMuc != null) {
                    ppStmDanhMuc.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true);  // Bật lại auto-commit để tránh ảnh hưởng đến các thao tác khác
                    conn.close(); // Đóng Connection để trả lại cho pool hoặc giải phóng
                }
            } catch (SQLException closeEx) {
                System.err.println("Lỗi đóng connection/statement: " + closeEx.getMessage());
                closeEx.printStackTrace();
            }
        }
    }
}
