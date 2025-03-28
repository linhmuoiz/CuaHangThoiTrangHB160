/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.HoaDonDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.KetNoiDB;
import enity.HoaDon;

/**
 *
 * @author Thuy SCTV
 */
public class HoaDonDAO {
    public List<HoaDonDTO> readHoaDon() {
    String sql = "SELECT hd.ID, kh.TenKH, kh.SDT, hd.ThanhTien, " +
                 "hd.HinhThucTT, hd.NgayTao, hd.TrangThai " +
                 "FROM HoaDon hd " +
                 "INNER JOIN KhachHang kh ON kh.ID = hd.MaKH;";
                 
    
    List<HoaDonDTO> hoaDonLst = new ArrayList<>();
    
    try (Connection con = KetNoiDB.getConnectDB();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

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
        String sql = "SELECT ID, NgayTao, TrangThai FROM HoaDon;";
        List<HoaDon> hoaDonLst = new ArrayList<HoaDon>();
        
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                Date NgayTao = rs.getDate("NgayTao");
                String TrangThai = rs.getString("TrangThai");
                
                HoaDon hoaDon = new HoaDon(ID, NgayTao, TrangThai);
                hoaDonLst.add(hoaDon);
            }
            return hoaDonLst;
        }
        catch (SQLException e) {
            return hoaDonLst;
        }
    }
    
    public int createHoaDon(HoaDon hoaDon) {
        String sql = "INSERT INTO HoaDon VALUES ( ?, ?, ?, ?, ?);";
       
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            
            
            ps.setInt(1, hoaDon.getMaKH());
            ps.setDouble(2, hoaDon.getThanhTien());
            ps.setString(3, hoaDon.getHinhThucTT());        
            
            ps.setDate(4, new java.sql.Date(hoaDon.getNgayTao().getTime()));
            ps.setString(5, hoaDon.getTrangThai());
            
            int ketQua = ps.executeUpdate();
            return ketQua;
        }
        catch (SQLException e) {
            System.out.println("SQLException");
            System.out.println(e);
            return 0;
        }
    }
}
