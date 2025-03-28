/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.ChiTietHDDTO;
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
                
                ChiTietHDDTO chiTietHoaDon = new ChiTietHDDTO(MaSP, TenSP, TenDM, TenMS, TenKT, SoLuong, Gia, GoiGiamGia);
                chiTietHoaDonLst.add(chiTietHoaDon);
            }
            return chiTietHoaDonLst;
        }
        catch (SQLException e) {
            return chiTietHoaDonLst;
        }
    }}
