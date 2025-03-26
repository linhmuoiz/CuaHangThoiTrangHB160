/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enity.KichThuoc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.KetNoiDB;

/**
 *
 * @author admim
 */
public class KichThuocDAO {
    public List<KichThuoc> loadComboBox() {
//        String SQL = "SELECT TenDM FROM DanhMuc";
        List<KichThuoc> TenKichThuocLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT TenKT FROM KichThuoc";

            System.out.println("Executing SQL Query: " + sql);

            PreparedStatement ppStm = conn.prepareStatement(sql);
            ResultSet rs = ppStm.executeQuery();

            while (rs.next()) {
                String TenKT = rs.getString("TenKT");
                KichThuoc kichThuoc = new KichThuoc(0, TenKT);
                
                TenKichThuocLst.add(kichThuoc);
            }
            return TenKichThuocLst;
        } catch (Exception e) {
            System.err.println("Error reading products from the database:");
            e.printStackTrace();
            return TenKichThuocLst;
        }
    }
}
