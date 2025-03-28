package dao;

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
        String sql = "SELECT SUM(ThanhTien) AS doanh_thu FROM HoaDon WHERE TrangThai = N'Hoàn thành';";
        
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("doanh_thu"); 
            }
            return 0;
        }
        catch (SQLException e) {
            return 0;
        }
    }
    
    public int tinhSoHoaDon() {
        String sql = "SELECT COUNT(*) AS so_hoa_don FROM HoaDon WHERE TrangThai = N'Hoàn thành';";
        
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("so_hoa_don"); 
            }
            return 0;
        }
        catch (SQLException e) {
            return 0;
        }
    }
    
    public int tinhSoSanPhamBanRa() {
        String sql = "SELECT SUM(cthd.SoLuong) AS so_san_pham_ban_ra \n" +
                    "FROM ChiTietHD cthd\n" +
                    "INNER JOIN HoaDon hd ON hd.ID = cthd.MaHD\n" +
                    "WHERE hd.TrangThai = N'Hoàn thành';";
        
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("so_san_pham_ban_ra"); 
            }
            return 0;
        }
        catch (SQLException e) {
            return 0;
        }
    }
    
    public List<SanPhamBanChayDTO> tinhTopSanPhamBanChay() {
        String sql = "SELECT TOP 10 \n" +
                    "	sp.ID AS MaSP, \n" +
                    "	sp.TenSP, \n" +
                    "	SUM(cthd.SoLuong) AS SoLuongBanRa\n" +
                    "FROM HoaDon hd\n" +
                    "INNER JOIN ChiTietHD cthd ON cthd.MaHD = hd.ID\n" +
                    "INNER JOIN SanPham sp ON sp.ID = cthd.MaSP\n" +
                    "WHERE hd.TrangThai = N'Hoàn thành'\n" +
                    "GROUP BY sp.ID, sp.TenSP\n" +
                    "ORDER BY SoLuongBanRa DESC;";
        
        List<SanPhamBanChayDTO> sanPhamLst = new ArrayList<SanPhamBanChayDTO>();
        
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {int MaSP = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                int SoLuongSanPhamBanRa = rs.getInt("SoLuongBanRa");
                
                SanPhamBanChayDTO sanPhamBanChayDTO = new SanPhamBanChayDTO(MaSP, TenSP, SoLuongSanPhamBanRa);
                sanPhamLst.add(sanPhamBanChayDTO);
            }
            return sanPhamLst;
        }
        catch (SQLException e) {
            return sanPhamLst;
        }
    }
}