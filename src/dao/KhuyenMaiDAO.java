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
}
