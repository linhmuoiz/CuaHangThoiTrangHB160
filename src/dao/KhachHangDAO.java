/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enity.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.KetNoiDB;

/**
 *
 * @author Thuy SCTV
 */
public class KhachHangDAO {
    public List<KhachHang> readKhachHang() {
        String sql = "SELECT * FROM KhachHang;";
        List<KhachHang> khachHangLst = new ArrayList<KhachHang>();
        
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String TenKH = rs.getString("TenKH");
                String SDT = rs.getString("SDT");
                
                KhachHang khachHang = new KhachHang(ID, TenKH, SDT);
                khachHangLst.add(khachHang);
            }
            return khachHangLst;
        }
        catch (SQLException e) {
            return khachHangLst;
        }
    }
    
    public int createKhachHang(KhachHang khachHang) {
        String sql = "INSERT INTO KhachHang VALUES (?, ?);";
        
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            
            ps.setString(1, khachHang.getTenKH());
            ps.setString(2, khachHang.getSDT());
            
            int ketQua = ps.executeUpdate();
            return ketQua;
        }
        catch (SQLException e) {
            return 0;
        }
    }
    
    public KhachHang findKhachHang(String tenKH, String sdt) {
        String sql = "SELECT * FROM KhachHang WHERE SDT = (?);";
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            
            ps.setString(1, sdt);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String TenKH  = rs.getString("TenKH");
                String SDT = rs.getString("SDT");
                System.out.println("SDT");
                System.out.println(SDT);
                return new KhachHang(ID, TenKH, SDT);
            }
            return null;
        }
        catch (SQLException e) {
            return null;
        }
    }
}
