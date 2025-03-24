/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enity.SanPhamDanhMucMauSacKichThuocDTO;
import java.util.ArrayList;
import java.util.List;
import utils.KetNoiDB;
import java.sql.*;
import utils.KetNoiDB;
import java.sql.*;

/**
 *
 * @author admim
 */
public class SanPhamDAO {

    public List<SanPhamDanhMucMauSacKichThuocDTO> readSanPham() {
        List<SanPhamDanhMucMauSacKichThuocDTO> sanPhamLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT " +
                         "    SP.ID AS MaSP, " +
                         "    SP.TenSP, " +
                         "    SP.Gia AS DonGia, " +
                         "    SP.SoLuong, " +
                         "    SP.TrangThai, " +
                         "    SP.MaDM, " +
                         "    SP.MaMS, " +
                         "    SP.MaKT " +
                         "FROM SanPham AS SP " +
                         "JOIN DanhMuc AS DM ON SP.MaDM = DM.MaDM " +
                         "JOIN MauSac AS MS ON SP.MaMS = MS.MaMS " +
                         "JOIN KichThuoc AS KT ON SP.MaKT = KT.MaKT " +
                         "ORDER BY SP.ID;";

            System.out.println("Executing SQL Query: " + sql); 

            PreparedStatement ppStm = conn.prepareStatement(sql);
            ResultSet rs = ppStm.executeQuery();

            while (rs.next()) {
                int maSP = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSp");
                double Gia = rs.getDouble("DonGia");
                int SoLuong = rs.getInt("SoLuong");
                String TrangThai = rs.getString("TrangThai");
                int MaDM = rs.getInt("MaDM");
                int MaMS = rs.getInt("MaMS");
                int MaKT = rs.getInt("MaKT");

                SanPhamDanhMucMauSacKichThuocDTO sanPhamDTO = new SanPhamDanhMucMauSacKichThuocDTO(maSP, TenSP, Gia, SoLuong, TrangThai, MaDM, MaMS, MaKT);
                sanPhamLst.add(sanPhamDTO);
                System.out.println("Added Product: " + sanPhamDTO.getTenSp()); 
            }

            System.out.println("Total Products Retrieved: " + sanPhamLst.size()); 
            return sanPhamLst;

        } catch (Exception e) {
            System.err.println("Error reading products from the database:");
            e.printStackTrace();
            return sanPhamLst;
        }
    }
}