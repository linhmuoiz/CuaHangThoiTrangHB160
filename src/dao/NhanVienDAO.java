/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enity.NhanVien;
import utils.KetNoiDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Thuy SCTV
 */
public class NhanVienDAO {
    public boolean checkLogin(String sdt, String pass){
        try(Connection conn = KetNoiDB.getConnectDB()){
            PreparedStatement ppStm = conn.prepareStatement("SELECT * FROM NhanVien WHERE SDT = ? AND MatKhauDN = ?");
            ppStm.setString(1, sdt);
            ppStm.setString(2, pass);
            ResultSet rs = ppStm.executeQuery();
            if (rs.next() == true){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // HienThiNhanVien
    public List<NhanVien> readNhanVien() {
        String sql = "SELECT * FROM NhanVien;";
        List<NhanVien> nhanVienLst = new ArrayList<NhanVien>();
        
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String TenNV = rs.getString("TenNV");
                String SDT = rs.getString("SDT");
                String GioiTinh = rs.getString("GioiTinh");
                String DiaChi = rs.getString("DiaChi");
                
                NhanVien nhanVien = new NhanVien(ID, TenNV, SDT, GioiTinh, DiaChi);
                nhanVienLst.add(nhanVien);
            }
            return nhanVienLst;
        }
        catch (SQLException e) {
            return nhanVienLst;
        }
    }
    public int createNhanVien(NhanVien nhanVien) {
        String sql = "INSERT INTO NhanVien VALUES (?, ?, ?, ?, ?);";
        
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            
            ps.setString(1, nhanVien.getTenNV());
            ps.setString(2, nhanVien.getSDT());
            ps.setString(3, nhanVien.getMatKhauDN());
            ps.setString(4, nhanVien.getDiaChi());
            ps.setString(5, nhanVien.getGioiTinh());
            
            int ketQua = ps.executeUpdate();
            return ketQua;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
