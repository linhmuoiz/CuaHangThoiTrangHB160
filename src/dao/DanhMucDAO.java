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
    
    public List<DanhMuc> readDanhMuc(){
        List<DanhMuc> DanhMucLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()){
            String sql = "SELECT * FROM DanhMuc";
            System.out.println("Executing SQL Query: " + sql);
            
            PreparedStatement ppStm = conn.prepareStatement(sql);
            ResultSet rs = ppStm.executeQuery();
            
            while(rs.next()){
                int MaDM = rs.getInt("MaDM");
                String TenDM = rs.getString("TenDM");
                
                DanhMuc danhMuc = new DanhMuc(MaDM, TenDM);
                DanhMucLst.add(danhMuc);
            }
            return DanhMucLst;
        } catch (Exception e) {
            return DanhMucLst;
        }
    }
    
    public int ThemDanhMuc(DanhMuc danhMuc){
        try (Connection conn = KetNoiDB.getConnectDB()){
            String sql = "INSERT INTO DanhMuc (TenDM) VALUES (?)";
            PreparedStatement ppStm = conn.prepareStatement(sql);
            
            ppStm.setString(1, danhMuc.getTenDM());
            
            int ketQua = ppStm.executeUpdate();
            return ketQua;
        } catch (Exception e) {
             System.out.println("Lỗi");
            return 0;
        }
    }
    
    public int SuaDanhMuc(DanhMuc danhMuc){
        try (Connection conn = KetNoiDB.getConnectDB()){
            String sql = "UPDATE DanhMuc SET TenDM = ? WHERE MaDM = ?";
            PreparedStatement ppStm = conn.prepareStatement(sql);
            
            ppStm.setString(1, danhMuc.getTenDM());
            ppStm.setInt(2, danhMuc.getID());
            
            int ketQua = ppStm.executeUpdate();
            return ketQua;
        } catch (Exception e) {
             System.out.println("Lỗi");
            return 0;
        }
    }
    
    public int XoaDanhMuc(int MaDM){
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "DELETE FROM DanhMuc Where MaDM = ?";
            PreparedStatement ppStm = conn.prepareCall(sql);
            
            ppStm.setInt(1, MaDM);
            
            int ketQua = ppStm.executeUpdate();
            return ketQua;
        } catch (Exception e) {
            System.out.println("Lỗi");
            return 0;
        }
    }
}
