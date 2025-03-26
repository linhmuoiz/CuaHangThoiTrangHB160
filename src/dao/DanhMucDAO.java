/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enity.DanhMuc;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import utils.KetNoiDB;

/**
 *
 * @author admim
 */
public class DanhMucDAO {

    public List<DanhMuc> loadComboBox() {
//        String SQL = "SELECT TenDM FROM DanhMuc";
        List<DanhMuc> TenDanhMucLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT TenDM FROM DanhMuc";

            System.out.println("Executing SQL Query: " + sql);

            PreparedStatement ppStm = conn.prepareStatement(sql);
            ResultSet rs = ppStm.executeQuery();

            while (rs.next()) {
                String TenDM = rs.getString("TenDM");

                DanhMuc danhMuc = new DanhMuc(0, TenDM);
                TenDanhMucLst.add(danhMuc);
            }
            return TenDanhMucLst;
        } catch (Exception e) {
            System.err.println("Error reading products from the database:");
            e.printStackTrace();
            return TenDanhMucLst;
        }
    }
}
