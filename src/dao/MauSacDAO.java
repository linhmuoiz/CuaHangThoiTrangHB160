/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import enity.MauSac;
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
public class MauSacDAO {
    public List<MauSac> loadComboBox() {
//        String SQL = "SELECT TenDM FROM DanhMuc";
        List<MauSac> TenMauSacLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT TenMS FROM MauSac";

            System.out.println("Executing SQL Query: " + sql);

            PreparedStatement ppStm = conn.prepareStatement(sql);
            ResultSet rs = ppStm.executeQuery();

            while (rs.next()) {
                String TenMS = rs.getString("TenMS");
                MauSac mauSac = new MauSac(0, TenMS);
                
                TenMauSacLst.add(mauSac);
            }
            return TenMauSacLst;
        } catch (Exception e) {
            System.err.println("Error reading products from the database:");
            e.printStackTrace();
            return TenMauSacLst;
        }
    }
}
