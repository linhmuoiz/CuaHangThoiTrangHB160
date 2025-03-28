/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enity.KhuyenMai;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import utils.KetNoiDB;

/**
 *
 * @author Hp
 */
public class KhuyenMaiDAO {
    public List<KhuyenMai> readKhuyenMai() {
        List<KhuyenMai> khuyenMaiLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT * FROM KhuyenMai";
            System.out.println("Executing SQL Query: " + sql);

            PreparedStatement ppStm = conn.prepareStatement(sql);
            ResultSet rs = ppStm.executeQuery();

            while (rs.next()) {
                int MaKM = rs.getInt("MaKM");
                String TenKM = rs.getString("TenKM");
                String CodeGiamGia = rs.getString("CodeGiamGia");
                int GoiGiamGia = rs.getInt("GoiGiamGia");
                String NgayBatDau = rs.getString("NgayBD");
                String NgayKetThuc = rs.getString("NgayKT");

                KhuyenMai khuyenMai = new KhuyenMai(MaKM, TenKM, CodeGiamGia, GoiGiamGia, NgayBatDau, NgayKetThuc);
                khuyenMaiLst.add(khuyenMai);
            }
            return khuyenMaiLst;
        } catch (Exception e) {
            return khuyenMaiLst;
        }
    }
    
    public List<KhuyenMai> TimKiemKhuyenMai(String TimKiem) {
        List<KhuyenMai> khuyenMaiLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT * FROM KhuyenMai WHERE MaKM = ? OR TenKM LIKE ?";
            System.out.println("Executing SQL Query: " + sql);

            PreparedStatement ppStm = conn.prepareStatement(sql);

            // Thiết lập Tham số thứ 1 (WHERE MaKM = ?)
            try {
                int idTimKiem = Integer.parseInt(TimKiem); // chuyển đổi TimKiem thành số nguyên
                ppStm.setInt(1, idTimKiem);
            } catch (NumberFormatException e) {
                ppStm.setInt(1, 0); // Nếu không phải là số thì mặc định là 0 để tránh bị lỗi
            }

            // Thiết lập tham số thứ 2 (OR TenKM LIKE ?)
            ppStm.setString(2, "%" + TimKiem + "%");

            ResultSet rs = ppStm.executeQuery();

            while (rs.next()) {
                int MaKM = rs.getInt("MaKM");
                String TenKM = rs.getString("TenKM");
                String CodeGiamGia = rs.getString("CodeGiamGia");
                int GoiGiamGia = rs.getInt("GoiGiamGia");
                String NgayBatDau = rs.getString("NgayBD");
                String NgayKetThuc = rs.getString("NgayKT");

                KhuyenMai khuyenMai = new KhuyenMai(MaKM, TenKM, CodeGiamGia, GoiGiamGia, NgayBatDau, NgayKetThuc);
                khuyenMaiLst.add(khuyenMai);
            }
            return khuyenMaiLst;
        } catch (SQLException e) { 
            System.err.println("Lỗi TimKiemKhuyenMai: " + e.getMessage());
            e.printStackTrace();
            return khuyenMaiLst;
        }
    }
        
        public List<KhuyenMai>  TimKiemSanPhamTheoNgay(String ngayBatDau, String ngayKetThuc){
        List<KhuyenMai> khuyenMaiLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT * FROM KhuyenMai WHERE NgayBD >= ? AND NgayKT <= ?";
            
            System.out.println("Executing SQL Query: " + sql);
            PreparedStatement ppStm = conn.prepareStatement(sql);
            
            ppStm.setString(1, ngayBatDau);
            ppStm.setString(2, ngayKetThuc);
            
            ResultSet rs = ppStm.executeQuery();
            
            while (rs.next()) {
                int MaKM = rs.getInt("MaKM");
                String TenKM = rs.getString("TenKM");
                String CodeGiamGia = rs.getString("CodeGiamGia");
                int GoiGiamGia = rs.getInt("GoiGiamGia");
                String NgayBatDau = rs.getString("NgayBD");
                String NgayKetThuc = rs.getString("NgayKT");

                KhuyenMai khuyenMai = new KhuyenMai(MaKM, TenKM, CodeGiamGia, GoiGiamGia, NgayBatDau, NgayKetThuc);
                khuyenMaiLst.add(khuyenMai);
            }
            return khuyenMaiLst;
        } catch (Exception e) {
            System.err.println("Lỗi TimKiemKhuyenMai: " + e.getMessage());
            e.printStackTrace();
            return khuyenMaiLst;
        }
    }

}
