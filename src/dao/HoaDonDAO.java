package dao;

import dto.HoaDonDTO;
import enity.HoaDon;
import enity.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import utils.KetNoiDB;

public class HoaDonDAO {

    public List<HoaDonDTO> readHoaDon() {
        String sql = "SELECT hd.ID, kh.TenKH, kh.SDT, hd.ThanhTien, "
                + "hd.HinhThucTT, hd.NgayTao, hd.TrangThai "
                + "FROM HoaDon hd "
                + "INNER JOIN KhachHang kh ON kh.ID = hd.MaKH;";

        List<HoaDonDTO> hoaDonLst = new ArrayList<>();

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String TenKH = rs.getString("TenKH");
                String SDT = rs.getString("SDT");
                double ThanhTien = rs.getDouble("ThanhTien");
                String HinhThucTT = rs.getString("HinhThucTT");
                Date NgayTao = rs.getDate("NgayTao");
                String TrangThai = rs.getString("TrangThai");

                HoaDonDTO hoaDon = new HoaDonDTO(ID, TenKH, SDT, ThanhTien, HinhThucTT, NgayTao, TrangThai);
                hoaDonLst.add(hoaDon);
            }

            if (hoaDonLst.isEmpty()) {
                System.out.println("Không có hóa đơn nào trong database.");
            } else {
                System.out.println("Số hóa đơn lấy được: " + hoaDonLst.size());
            }

        } catch (SQLException e) {
            e.printStackTrace();  // In lỗi ra console
            return null;
        }
        return hoaDonLst;
    }

    public List<HoaDon> readHoaDonCho() {
        String sql = "SELECT ID, NgayTao, TrangThai FROM HoaDon WHERE TrangThai = N'Đang xử lý'";
        List<HoaDon> hoaDonLst = new ArrayList<HoaDon>();

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement ps = con.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                Date NgayTao = rs.getDate("NgayTao");
                String TrangThai = rs.getString("TrangThai");

                HoaDon hoaDon = new HoaDon(ID, NgayTao, TrangThai);
                hoaDonLst.add(hoaDon);
            }
            return hoaDonLst;
        } catch (SQLException e) {
            return hoaDonLst;
        }
    }

    public int createHoaDon(HoaDon hoaDon) {
        String sql = "INSERT INTO HoaDon VALUES (?, ?, ?, ?, ?);";

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setInt(1, hoaDon.getMaKH());
            ps.setDouble(2, hoaDon.getThanhTien());
            ps.setString(3, hoaDon.getHinhThucTT());

            ps.setDate(4, new java.sql.Date(hoaDon.getNgayTao().getTime()));
            ps.setString(5, hoaDon.getTrangThai());

            int ketQua = ps.executeUpdate();
            return ketQua;
        } catch (SQLException e) {
            return 0;
        }
    }

    public int findHoaDonMoi() {
        String sql = "SELECT TOP 1 ID FROM HoaDon ORDER BY ID DESC;";

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement ps = con.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("ID");
            }
            return 0;
        } catch (SQLException e) {
            return 0;
        }
    }

    public int updateHoaDon(int MaHD, String HinhThucThanhToan, String TrangThai) {
        String sql = "UPDATE HoaDon SET HinhThucTT = (?), TrangThai = (?) WHERE ID = (?);";

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setString(1, HinhThucThanhToan);
            ps.setString(2, TrangThai);
            ps.setInt(3, MaHD);

            int ketQua = ps.executeUpdate();
            return ketQua;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateHoaDonHuy(int MaHD) {
        String sql = "UPDATE HoaDon SET TrangThai = N'Hủy' WHERE ID = ?;";

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setInt(1, MaHD);

            int ketQua = ps.executeUpdate();
            return ketQua;
        } catch (SQLException e) {
            return 0;
        }
    }

    public List<HoaDonDTO> TimKiemHoaDon(String TimKiem) {
        List<HoaDonDTO> hoaDonLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT hd.ID, kh.TenKH, kh.SDT, hd.ThanhTien,\n"
                    + "       hd.HinhThucTT, hd.NgayTao, hd.TrangThai\n"
                    + "FROM HoaDon hd\n"
                    + "INNER JOIN KhachHang kh ON kh.ID = hd.MaKH\n"
                    + "WHERE hd.ID LIKE ? OR kh.SDT LIKE ?;";
            PreparedStatement ppStm = conn.prepareStatement(sql);

            ppStm.setString(1, "%" + TimKiem + "%");
            ppStm.setString(2, "%" + TimKiem + "%");
            ResultSet rs = ppStm.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String TenKH = rs.getString("TenKH");
                String SDT = rs.getString("SDT");
                double ThanhTien = rs.getDouble("ThanhTien");
                String HinhThucTT = rs.getString("HinhThucTT");
                Date NgayTao = rs.getDate("NgayTao");
                String TrangThai = rs.getString("TrangThai");

                HoaDonDTO hoaDon = new HoaDonDTO(ID, TenKH, SDT, ThanhTien, HinhThucTT, NgayTao, TrangThai);
                hoaDonLst.add(hoaDon);
            }
            return hoaDonLst;
        } catch (Exception e) {
            e.printStackTrace();
            return hoaDonLst;
        }
    }
    
    
    public List<HoaDonDTO> TimKiemHoaDonTheoNgay(String ngayTimKiem) {
        List<HoaDonDTO> hoaDonLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT hd.ID, kh.TenKH, kh.SDT, hd.ThanhTien,\n"
                    + "       hd.HinhThucTT, hd.NgayTao, hd.TrangThai\n"
                    + "FROM HoaDon hd\n"
                    + "INNER JOIN KhachHang kh ON kh.ID = hd.MaKH\n"
                    + "WHERE hd.NgayTao = ?";

            System.out.println("Executing SQL Query: " + sql);
            PreparedStatement ppStm = conn.prepareStatement(sql);

            ppStm.setString(1, ngayTimKiem);

            ResultSet rs = ppStm.executeQuery();
            
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String TenKH = rs.getString("TenKH");
                String SDT = rs.getString("SDT");
                double ThanhTien = rs.getDouble("ThanhTien");
                String HinhThucTT = rs.getString("HinhThucTT");
                Date NgayTao = rs.getDate("NgayTao");
                String TrangThai = rs.getString("TrangThai");

                HoaDonDTO hoaDon = new HoaDonDTO(ID, TenKH, SDT, ThanhTien, HinhThucTT, NgayTao, TrangThai);
                hoaDonLst.add(hoaDon);
            }
            return hoaDonLst;
        } catch (Exception e) {
            System.err.println("Lỗi TimKiemKhuyenMai: " + e.getMessage());
            e.printStackTrace();
            return hoaDonLst;
        }
    }
}
