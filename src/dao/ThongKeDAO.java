package dao;

import dto.DoanhThuChiTietDTO;
import dto.SanPhamBanChayDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.KetNoiDB;

public class ThongKeDAO {

    public double tinhDoanhThu() {
        String sql = "SELECT SUM(ThanhTien) AS doanh_thu FROM HoaDon WHERE TrangThai = N'Hoàn thành' AND NgayTao = CAST(GETDATE() AS DATE);";

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement ps = con.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("doanh_thu");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int tinhSoHoaDon() {
        String sql = "SELECT COUNT(*) AS so_hoa_don FROM HoaDon WHERE TrangThai = N'Hoàn thành' AND NgayTao = CAST(GETDATE() AS DATE);";

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement ps = con.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("so_hoa_don");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int tinhSoSanPhamBanRa() {
        String sql = "SELECT SUM(cthd.SoLuong) AS TongSoSanPhamBanRa\n"
                + "FROM ChiTietHD AS cthd\n"
                + "INNER JOIN HoaDon AS hd ON hd.ID = cthd.MaHD\n"
                + "WHERE hd.NgayTao = CAST(GETDATE() AS DATE)\n"
                + "AND hd.TrangThai = N'Hoàn thành';";

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement ps = con.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("TongSoSanPhamBanRa");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<SanPhamBanChayDTO> tinhTopSanPhamBanChay() {
        String sql = "SELECT TOP 10 \n"
                + "	sp.ID AS MaSP, \n"
                + "	sp.TenSP, \n"
                + "	SUM(cthd.SoLuong) AS SoLuongBanRa\n"
                + "FROM HoaDon hd\n"
                + "INNER JOIN ChiTietHD cthd ON cthd.MaHD = hd.ID\n"
                + "INNER JOIN SanPham sp ON sp.ID = cthd.MaSP\n"
                + "WHERE hd.TrangThai = N'Hoàn thành'\n"
                + "GROUP BY sp.ID, sp.TenSP\n"
                + "ORDER BY SoLuongBanRa DESC;";

        List<SanPhamBanChayDTO> sanPhamLst = new ArrayList<SanPhamBanChayDTO>();

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement ps = con.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int MaSP = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                int SoLuongSanPhamBanRa = rs.getInt("SoLuongBanRa");

                SanPhamBanChayDTO sanPhamBanChayDTO = new SanPhamBanChayDTO(MaSP, TenSP, SoLuongSanPhamBanRa);
                sanPhamLst.add(sanPhamBanChayDTO);
            }
            return sanPhamLst;
        } catch (SQLException e) {
            e.printStackTrace();
            return sanPhamLst;
        }
    }

    public List<DoanhThuChiTietDTO> doanhThuChiTietHomNay() {
        String sql = "SELECT SP.ID AS MaSanPham,\n"
                + "    SP.TenSP AS TenSanPham,\n"
                + "    SUM(CTHD.SoLuong) AS TongSoLuongBanRa,\n"
                + "    SUM(CTHD.SoLuong * SP.Gia) AS TongTienSanPhamBanRa\n"
                + "FROM ChiTietHD AS CTHD\n"
                + "JOIN SanPham AS SP ON CTHD.MaSP = SP.ID\n"
                + "JOIN HoaDon AS HD ON CTHD.MaHD = HD.ID\n"
                + "WHERE HD.NgayTao = CAST(GETDATE() AS DATE) \n"
                + "AND HD.TrangThai = N'Hoàn thành'"
                + "GROUP BY SP.ID, SP.TenSP\n"
                + "ORDER BY SP.ID;";

        List<DoanhThuChiTietDTO> doanhThuLst = new ArrayList<DoanhThuChiTietDTO>();

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement ps = con.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int MaSP = rs.getInt("MaSanPham");
                String TenSP = rs.getString("TenSanPham");
                int TongSoLuongBanRa = rs.getInt("TongSoLuongBanRa");
                double TongTienSanPhamBanRa = rs.getDouble("TongTienSanPhamBanRa");

                DoanhThuChiTietDTO doanhThuChiTietDTO = new DoanhThuChiTietDTO(MaSP, TenSP, TongSoLuongBanRa, TongTienSanPhamBanRa);
                doanhThuLst.add(doanhThuChiTietDTO);
//                SanPhamBanChayDTO sanPhamBanChayDTO = new SanPhamBanChayDTO(MaSP, TenSP, SoLuongSanPhamBanRa);
//                doanhThuLst.add(sanPhamBanChayDTO);
            }
            return doanhThuLst;
        } catch (SQLException e) {
            e.printStackTrace();
            return doanhThuLst;
        }
    }
}
