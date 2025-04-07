/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.ChiTietHDDTO;
import enity.ChiTietHD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.KetNoiDB;

/**
 *
 * @author Admin
 */
public class ChiTietHDDAO {
     public List<ChiTietHDDTO> readChiTietHD(int MaHD) {
        String sql = "SELECT \n" +
                "    cthd.MaSP, \n" +
                "    sp.TenSP, \n" +
                "    dm.TenDM, \n" +
                "    ms.TenMS, \n" +
                "    kt.TenKT, \n" +
                "    cthd.SoLuong, \n" +
                "    sp.Gia, \n" +
                "    km.GoiGiamGia\n" +
                "FROM ChiTietHD cthd\n" +
                "INNER JOIN SanPham sp ON sp.ID = cthd.MaSP\n" +
                "INNER JOIN DanhMuc dm ON dm.MaDM = sp.MaDM\n" +
                "INNER JOIN MauSac ms ON ms.MaMS = sp.MaMS\n" +
                "INNER JOIN KichThuoc kt ON kt.MaKT = sp.MaKT\n" +
                "LEFT JOIN KhuyenMai km ON km.MaKM = cthd.MaKM\n" +
                "WHERE cthd.MaHD = ?;";
        
        List<ChiTietHDDTO> chiTietHoaDonLst = new ArrayList<ChiTietHDDTO>();
        
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            
            ps.setInt(1, MaHD);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int MaSP = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                String TenDM = rs.getString("TenDM");
                String TenMS = rs.getString("TenMS");
                String TenKT = rs.getString("TenKT");
                int SoLuong = rs.getInt("SoLuong");
                double Gia = rs.getDouble("Gia");
                int GoiGiamGia = rs.getInt("GoiGiamGia");
                
                ChiTietHDDTO chiTietHoaDon = new ChiTietHDDTO(MaSP, TenSP, TenDM, TenMS, TenKT, SoLuong, Gia);
                chiTietHoaDonLst.add(chiTietHoaDon);
            }
            return chiTietHoaDonLst;
        }
        catch (SQLException e) {
            return chiTietHoaDonLst;
        }
    }
     public int createChiTietHD(ChiTietHD chiTietHD) {
        String sql = "INSERT INTO ChiTietHD VALUES (?, ?, ?, ?)";
       
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            
            ps.setInt(1, chiTietHD.getMaHD());
            ps.setInt(2, chiTietHD.getMaSP());
            ps.setInt(3, chiTietHD.getSoLuong());
            ps.setInt(4, chiTietHD.getMaKM());
            
            int ketQua = ps.executeUpdate();
            return ketQua;
        }
        catch (SQLException e) {
            return 0;
        }
     }
     public int deletePrevOrderDetail(int MaHD){
         String sql = "DELETE * FROM ChiTietHD WHERE MaHD = ?";
         
         try(Connection conn = KetNoiDB.getConnectDB()){
             PreparedStatement ppStm = conn.prepareStatement(sql);
             ppStm.setInt(1, MaHD);
             return 1;
         }
         catch(Exception e){
             e.printStackTrace();
             return 0;
         }
     }
    
    public List<ChiTietHD> findChiTietHD(int maHoaDon) {
        String sql = "SELECT * FROM ChiTietHD WHERE MaHD = (?);";
        List<ChiTietHD> chiTietHDLst = new ArrayList<ChiTietHD>();
        
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            
            ps.setInt(1, maHoaDon);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int MaHD = rs.getInt("MaHD");
                int MaSP = rs.getInt("MaSP");
                int SoLuong = rs.getInt("SoLuong");
                int MaKM = rs.getInt("MaKM");
                
                ChiTietHD chiTietHD = new ChiTietHD(MaHD, MaSP, SoLuong, MaKM);
                chiTietHDLst.add(chiTietHD);
            }
            return chiTietHDLst;
        }
        catch (SQLException e) {
            return chiTietHDLst;
        }
    }
}
