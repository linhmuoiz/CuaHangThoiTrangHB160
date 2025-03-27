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
        String sql = "SELECT hd.ID, kh.TenKH, kh.SDT, hd.ThanhTien, \n" +
                    "hd.HinhThucTT, nv.TenNV, hd.NgayTao, hd.TrangThai\n" +
                    "FROM HoaDon hd\n" +
                    "INNER JOIN KhachHang kh ON kh.ID = hd.MaKH\n" +
                    "INNER JOIN NhanVien nv ON nv.ID = hd.MaNV;";
        List<HoaDonDTO> hoaDonLst = new ArrayList<HoaDonDTO>();
        
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String TenKH = rs.getString("TenKH");
                String SDT = rs.getString("SDT");
                double ThanhTien = rs.getDouble("ThanhTien");
                String HinhThucTT = rs.getString("HinhThucTT");
                String TenNV = rs.getString("TenNV");
                Date NgayTao = rs.getDate("NgayTao");
                String TrangThai = rs.getString("TrangThai");
                
                HoaDonDTO hoaDon = new HoaDonDTO(ID, TenKH, SDT, ThanhTien, HinhThucTT, TenNV, NgayTao, TrangThai);
                hoaDonLst.add(hoaDon);
            }
            return hoaDonLst;
        }
        catch (SQLException e) {
            return hoaDonLst;
        }
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
        String sql = "INSERT INTO HoaDon VALUES (?, ?, ?, ?, ?, ?);";
       
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            
            ps.setInt(1, hoaDon.getMaNV());
            ps.setInt(2, hoaDon.getMaKH());
            ps.setDouble(3, hoaDon.getThanhTien());
            ps.setString(4, hoaDon.getHinhThucTT());        
            
            ps.setDate(5, new java.sql.Date(hoaDon.getNgayTao().getTime()));
            ps.setString(6, hoaDon.getTrangThai());
            
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
