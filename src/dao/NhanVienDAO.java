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
import utils.GlobalState;
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
    // 4.1 Xóa Nhân viên
    public int XoaNhanVien(int ID) {
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "DELETE FROM NhanVien WHERE ID = ?";
            PreparedStatement ppStm = conn.prepareStatement(sql);

            ppStm.setInt(1, ID);
            int ketQua = ppStm.executeUpdate();
            return ketQua;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi");
            return 0;
        }
    }

    // 4.2 Sửa nhân viên 
    public int SuaNhanVien(NhanVien nhanvien, int id) {
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "UPDATE NhanVien SET TenNV = ?, SDT = ?, MatKhauDN = ?, DiaChi = ?, GioiTinh = ? WHERE ID = ?";
            PreparedStatement ppStm = conn.prepareStatement(sql);

            ppStm.setString(1, nhanvien.getTenNV());
            ppStm.setString(2, nhanvien.getSDT());
            ppStm.setString(3, nhanvien.getMatKhauDN());
            ppStm.setString(4, nhanvien.getDiaChi());
            ppStm.setString(5, nhanvien.getGioiTinh());
            ppStm.setInt(6, id);
            int ketQua = ppStm.executeUpdate();
            return ketQua;
        } catch (Exception e) {
            System.out.println("Lỗi");
            return 0;

        }
    }

    public NhanVien getNhanVienById(int id) {
        String sql = "SELECT * FROM NhanVien WHERE id = ?";
        int ID = 0;
        String tenNV = null;
        String gioi = null;
        String SDT = null;
        String diaChi = null;
        String matKhau = null;
        try (Connection conn = KetNoiDB.getConnectDB(); // Sử dụng kết nối từ KetNoiDB
                 PreparedStatement ps = conn.prepareStatement(sql)) {
            // Đặt giá trị cho dấu hỏi trong câu lệnh SQL
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ID = rs.getInt("id");
                tenNV = rs.getString("TenNV");
                gioi = rs.getString("GioiTinh");
                SDT = rs.getString("SDT");
                diaChi = rs.getString("DiaChi");
                matKhau = rs.getString("MatKhauDN");
            }
            return new NhanVien(ID, tenNV, SDT, matKhau, diaChi, gioi);
        } catch (SQLException e) {
            e.printStackTrace();
            // Trả về đối tượng NhanVien nếu tìm thấy, ngược lại trả về null
            return null;
        }
    }
    public int updateMatKhauDN(String matKhauHienTai, String matKhauMoi) {
        String sql = "UPDATE NhanVien SET MatKhauDN = ? WHERE SDT = ? AND MatKhauDN = ?;";
        
        try (Connection con = KetNoiDB.getConnectDB(); 
                PreparedStatement ps = con.prepareStatement(sql);) {
            
            ps.setString(1, matKhauMoi);
            ps.setString(2, GlobalState.SDT);
            ps.setString(3, matKhauHienTai);
            
            int ketQua = ps.executeUpdate();
            return ketQua;
        }
        catch (SQLException e) {
            return 0;
        }
    }
    
    
    
    public List<NhanVien> TimKiemNhanVien(String TimKiem){
        List<NhanVien> nhanVienLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT * FROM NhanVien WHERE TenNV LIKE ? OR SDT LIKE ?";
            PreparedStatement ppStm = conn.prepareStatement(sql);
            
            ppStm.setString(1, "%" + TimKiem + "%");
            ppStm.setString(2, "%" + TimKiem + "%");
           ResultSet rs = ppStm.executeQuery();
           
           while (rs.next()) {
                int ID = rs.getInt("ID");
                String tenNV = rs.getString("TenNV");
                String SDT = rs.getString("SDT");
                String diaChi = rs.getString("DiaChi");
                String GioiTinh = rs.getString("GioiTinh");
                
                NhanVien nhanVien = new NhanVien(ID, tenNV, SDT, GioiTinh, diaChi);
                nhanVienLst.add(nhanVien);
            }
           return nhanVienLst;
        } catch (Exception e) {
            e.printStackTrace();
            return nhanVienLst;
        }
    }
}
