/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import dto.ChiTietHDDTO;
import dto.HoaDonDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class readDetailOrderToForm {

    public HoaDonDTO readHoaDonByID(int id) {
        int ID = 0;
        String TenKH = null;
        String SDT = null;
        double ThanhTien = 0;
        String HinhThucTT = null;
        Date NgayTao = null;
        String TrangThai = null;
        String sql = "SELECT hd.ID, kh.TenKH, kh.SDT, hd.ThanhTien, "
                + "hd.HinhThucTT, hd.NgayTao, hd.TrangThai "
                + "FROM HoaDon hd "
                + "INNER JOIN KhachHang kh ON kh.ID = hd.MaKH WHERE hd.ID = ?;";

        try (Connection con = KetNoiDB.getConnectDB()) {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ID = rs.getInt("ID");
                TenKH = rs.getString("TenKH");
                SDT = rs.getString("SDT");
                ThanhTien = rs.getDouble("ThanhTien");
                HinhThucTT = rs.getString("HinhThucTT");
                NgayTao = rs.getDate("NgayTao");
                TrangThai = rs.getString("TrangThai");
            }
            return new HoaDonDTO(ID, TenKH, SDT, ThanhTien, HinhThucTT, NgayTao, TrangThai);
        } catch (SQLException e) {
            e.printStackTrace();  // In lỗi ra console
            return null;
        }
    }
        public List<ChiTietHDDTO> readChiTietHD(int MaHD) {
    String sql = "SELECT \n" +
            "    cthd.MaSP, \n" +
            "    sp.TenSP, \n" +
            "    dm.TenDM, \n" +
            "    ms.TenMS, \n" +
            "    kt.TenKT, \n" +
            "    cthd.SoLuong, \n" +
            "    sp.Gia, \n" +
            "    sp.TrangThai, \n" +
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
            String TrangThai = rs.getString("TrangThai"); 

            ChiTietHDDTO chiTietHoaDon = new ChiTietHDDTO(MaSP, TenSP, TenDM, TenMS, TenKT, SoLuong, Gia, TrangThai);
            chiTietHoaDonLst.add(chiTietHoaDon);
        }
        return chiTietHoaDonLst;
    } catch (SQLException e) {
        e.printStackTrace(); 
        return chiTietHoaDonLst;
    }
        }
}