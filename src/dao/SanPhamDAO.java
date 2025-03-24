/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.SanPhamDanhMucMauSacKichThuocDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.KetNoiDB;

/**
 *
 * @author admim
 */
public class SanPhamDAO {

    public List<SanPhamDanhMucMauSacKichThuocDTO> readSanPham() {
        List<SanPhamDanhMucMauSacKichThuocDTO> sanPhamLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT\n"
                    + "    SP.ID AS MaSP,\n"
                    + "    SP.TenSP,\n"
                    + "    SP.Gia AS Gia, \n"
                    + "    SP.SoLuong,\n"
                    + "    SP.TrangThai,\n"
                    + "    DM.TenDM AS DanhMuc,\n"
                    + "    MS.TenMS AS MauSac,\n"
                    + "    KT.TenKT AS KichThuoc\n"
                    + "FROM SanPham AS SP\n"
                    + "JOIN DanhMuc AS DM ON SP.MaDM = DM.MaDM\n"
                    + "JOIN MauSac AS MS ON SP.MaMS = MS.MaMS\n"
                    + "JOIN KichThuoc AS KT ON SP.MaKT = KT.MaKT\n"
                    + "ORDER BY SP.ID;";

            System.out.println("Executing SQL Query: " + sql);

            PreparedStatement ppStm = conn.prepareStatement(sql);
            ResultSet rs = ppStm.executeQuery();

            while (rs.next()) {
                int maSP = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSp");
                double Gia = rs.getDouble("Gia");
                int SoLuong = rs.getInt("SoLuong");
                String TrangThai = rs.getString("TrangThai");
                String TenDM = rs.getString("DanhMuc");
                String TenMS = rs.getString("MauSac");
                String TenKT = rs.getString("KichThuoc");
//                int MaKT = rs.getInt("MaKT");

                SanPhamDanhMucMauSacKichThuocDTO sanPhamDTO = new SanPhamDanhMucMauSacKichThuocDTO(maSP, TenSP, Gia, SoLuong, TrangThai, TenDM, TenMS, TenKT);
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
