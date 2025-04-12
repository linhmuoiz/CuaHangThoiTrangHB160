/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.ChiTietHDDTO;
import dto.SanPhamDTO;
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

    //read quan ly san pham
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
//                String HinhAnh = rs.getString("HinhAnh");
//                int MaKT = rs.getInt("MaKT");

                SanPhamDanhMucMauSacKichThuocDTO spdmkt = new SanPhamDanhMucMauSacKichThuocDTO(maSP, TenSP, Gia, SoLuong, TrangThai, TenDM, TenMS, TenKT, TenKT);
                sanPhamLst.add(spdmkt);
            }

            return sanPhamLst;

        } catch (Exception e) {
            System.err.println("Error reading products from the database:");
            e.printStackTrace();
            return sanPhamLst;
        }
    }

    //readSanPham cua Quan ly ban hang tai quay
    public List<SanPhamDTO> readSanPhamQLY() {
        String sql = "SELECT ID, TenSP, Gia, SoLuong, TrangThai, dm.TenDM, ms.TenMS, kt.TenKT  \n"
                + "FROM SanPham sp\n"
                + "INNER JOIN DanhMuc dm ON dm.MaDM = sp.MaDM\n"
                + "INNER JOIN MauSac ms ON ms.MaMS = sp.MaMS\n"
                + "INNER JOIN KichThuoc kt ON kt.MaKT = sp.MaKT WHERE sp.TrangThai = N'Đang Hoạt Động'";
        List<SanPhamDTO> sanPhamLst = new ArrayList<SanPhamDTO>();

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement ps = con.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String TenNV = rs.getString("TenSP");
                double Gia = rs.getDouble("Gia");
                int SoLuong = rs.getInt("SoLuong");
                String TrangThai = rs.getString("TrangThai");
                String MaDM = rs.getString("TenDM");
                String MaMS = rs.getString("TenMS");
                String MaKT = rs.getString("TenKT");

                SanPhamDTO sanPham = new SanPhamDTO(ID, TenNV, Gia, SoLuong, TrangThai, MaDM, MaMS, MaKT);
                sanPhamLst.add(sanPham);
            }
            return sanPhamLst;
        } catch (SQLException e) {
            e.printStackTrace();
            return sanPhamLst;
        }
    }

    public List<ChiTietHDDTO> readSanPhamChon(int MaSPChon) {
        String sql = "SELECT \n"
                + "    sp.ID, \n"
                + "    sp.TenSP, \n"
                + "    dm.TenDM, \n"
                + "    ms.TenMS, \n"
                + "    kt.TenKT, \n"
                + "    sp.Gia\n"
                + "FROM SanPham sp\n"
                + "INNER JOIN DanhMuc dm ON dm.MaDM = sp.MaDM\n"
                + "INNER JOIN MauSac ms ON ms.MaMS = sp.MaMS\n"
                + "INNER JOIN KichThuoc kt ON kt.MaKT = sp.MaKT\n"
                + "WHERE sp.ID = (?);";

        List<ChiTietHDDTO> sanPhamChonLst = new ArrayList<ChiTietHDDTO>();

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setInt(1, MaSPChon);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int MaSP = rs.getInt("ID");
                String TenSP = rs.getString("TenSP");
                String TenDM = rs.getString("TenDM");
                String TenMS = rs.getString("TenMS");
                String TenKT = rs.getString("TenKT");
                double Gia = rs.getDouble("Gia");

                ChiTietHDDTO sanPhamChon = new ChiTietHDDTO(MaSP, TenSP, TenDM, TenMS, TenKT, Gia);
                sanPhamChonLst.add(sanPhamChon);
            }
            return sanPhamChonLst;
        } catch (SQLException e) {
            return sanPhamChonLst;
        }
    }

    public String getHinhAnhSanPham(int ID) {
        String HinhAnh = null;
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT HinhAnh FROM SanPham WHERE ID = ?";
            System.out.println("Executing SQL Query: " + sql);

            PreparedStatement ppStm = conn.prepareStatement(sql);
            ppStm.setInt(1, ID);
            ResultSet rs = ppStm.executeQuery();

            while (rs.next()) {
                HinhAnh = rs.getString("HinhAnh");
            }
        } catch (Exception e) {
            System.err.println("Error reading products from the database:");
            e.printStackTrace();
        }
        return HinhAnh;
    }

    public List<SanPhamDanhMucMauSacKichThuocDTO> TimKiemSanPham(String TimKiem) {
        List<SanPhamDanhMucMauSacKichThuocDTO> sanPhamLst = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "SELECT\n"
                    + "    SP.ID AS MaSP,\n"
                    + "    SP.TenSP,\n"
                    + "    SP.Gia AS Gia,\n"
                    + "    SP.SoLuong,\n"
                    + "    SP.TrangThai,\n"
                    + "    DM.TenDM AS DanhMuc,\n"
                    + "    MS.TenMS AS MauSac,\n"
                    + "    KT.TenKT AS KichThuoc\n"
                    + "FROM SanPham AS SP\n"
                    + "JOIN DanhMuc AS DM ON SP.MaDM = DM.MaDM\n"
                    + "JOIN MauSac AS MS ON SP.MaMS = MS.MaMS\n"
                    + "JOIN KichThuoc AS KT ON SP.MaKT = KT.MaKT\n"
                    + "WHERE SP.ID = ? OR SP.TenSP LIKE ?\n"
                    + "ORDER BY SP.ID;";
            System.out.println("Executing SQL Query: " + sql);

            PreparedStatement ppStm = conn.prepareStatement(sql);

            //Thiết lập Tham số thứ 1 (WHERE SP.ID = ?)
            try {
                int idTimKiem = Integer.parseInt(TimKiem); //chuyển đổi TimKiem thành số nguyên
                ppStm.setInt(1, idTimKiem);
            } catch (Exception e) {
                ppStm.setInt(1, 0); //Nếu không phải là số thì mặc định là 0 để tránh bị lỗi
            }

            //Thiết lập tham số thứ 2(SP.TenSP LIKE ?)
            ppStm.setString(2, "%" + TimKiem + "%");

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

                SanPhamDanhMucMauSacKichThuocDTO sanPhamDTO = new SanPhamDanhMucMauSacKichThuocDTO(maSP, TenSP, Gia, SoLuong, TrangThai, TenDM, TenMS, TenKT, "");
                sanPhamLst.add(sanPhamDTO);
            }

            return sanPhamLst;
        } catch (Exception e) {
            System.err.println("Error search  products from the database:");
            e.printStackTrace();
            return sanPhamLst;
        }
    }

    public int ThemSanPham(SanPhamDanhMucMauSacKichThuocDTO sanPham) {
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "INSERT INTO SanPham (TenSP, Gia, SoLuong, TrangThai, MaDM, MaMS, MaKT, HinhAnh) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ppStm = conn.prepareStatement(sql);

            ppStm.setString(1, sanPham.getTenSp());
            ppStm.setDouble(2, sanPham.getGia());
            ppStm.setInt(3, sanPham.getSoLuong());
            ppStm.setString(4, sanPham.getTrangThai());
            ppStm.setInt(5, getMaDM(sanPham.getTenDM(), conn)); //Thiết lập tham số thứ 5 (MaDM) bằng cách gọi hàm getMaDM với tên danh mục và kết nối CSDL
            ppStm.setInt(6, getMaMS(sanPham.getTenMS(), conn));
            ppStm.setInt(7, getMaKT(sanPham.getTenKT(), conn));
            ppStm.setString(8, sanPham.getHinhAnh());

            int ketQua = ppStm.executeUpdate();
            return ketQua;
        } catch (Exception e) {
            System.out.println("Lỗi");
            return 0;

        }
    }

    public int SuaSanPham(SanPhamDanhMucMauSacKichThuocDTO sanPham) {
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "UPDATE SanPham SET TenSP = ?, Gia = ?, SoLuong = ?, TrangThai = ?, MaDM = ?, MaMS = ?, MaKT = ?, HinhAnh = ? WHERE ID = ?";
            PreparedStatement ppStm = conn.prepareStatement(sql);

            ppStm.setString(1, sanPham.getTenSp());
            ppStm.setDouble(2, sanPham.getGia());
            ppStm.setInt(3, sanPham.getSoLuong());
            ppStm.setString(4, sanPham.getTrangThai());
            ppStm.setInt(5, getMaDM(sanPham.getTenDM(), conn)); //Thiết lập tham số thứ 5 (MaDM) bằng cách gọi hàm getMaDM với tên danh mục và kết nối CSDL
            ppStm.setInt(6, getMaMS(sanPham.getTenMS(), conn));
            ppStm.setInt(7, getMaKT(sanPham.getTenKT(), conn));
            ppStm.setString(8, sanPham.getHinhAnh());
            ppStm.setInt(9, sanPham.getID());

            int ketQua = ppStm.executeUpdate();
            return ketQua;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int XoaSanPham(int ID) {
        try (Connection conn = KetNoiDB.getConnectDB()) {
            String sql = "DELETE FROM SanPham WHERE ID = ?";
            PreparedStatement ppStm = conn.prepareStatement(sql);

            ppStm.setInt(1, ID);
            int ketQua = ppStm.executeUpdate();
            return ketQua;
        } catch (Exception e) {
            System.out.println("Lỗi");
            return 0;
        }
    }

    private int getMaDM(String TenDM, Connection conn) {   // Phương thức lấy MaDM từ TenDM
        int MaDM = 0; // Khởi tạo MaDM
        try {
            String sql = "SELECT MaDM FROM DanhMuc WHERE TenDM = ?"; // Truy vấn SQL SELECT để lấy MaDM
            PreparedStatement ppStm = conn.prepareStatement(sql);   // Tạo PreparedStatement từ truy vấn SQL
            ppStm.setString(1, TenDM);// Thiết lập tham số 1 (TenDM)
            ResultSet rs = ppStm.executeQuery(); // Thực thi truy vấn SELECT và lấy ResultSet
            if (rs.next()) {  // Kiểm tra xem có kết quả không
                MaDM = rs.getInt("MaDM");// Lấy giá trị MaDM từ ResultSet
            }
        } catch (SQLException e) {
            System.err.println("Error getting MaDM from database:"); // In ra thông báo lỗi nếu có lỗi SQL
            e.printStackTrace();  // In rFa stack trace của lỗi
        }
        return MaDM;  // Trả về MaDM
    }

    private int getMaMS(String TenMS, Connection conn) {
        int MaMS = 0;
        try {
            String sql = "SELECT MaMS FROM MauSac WHERE TenMS = ?";
            PreparedStatement ppStm = conn.prepareStatement(sql);
            ppStm.setString(1, TenMS);
            ResultSet rs = ppStm.executeQuery();
            if (rs.next()) {
                MaMS = rs.getInt("MaMS");
            }
        } catch (Exception e) {
            System.err.println("Error getting MaDM from database:");
            e.printStackTrace();
        }
        return MaMS;
    }

    private int getMaKT(String TenKT, Connection conn) {
        int MaKT = 0;
        try {
            String sql = "SELECT MaKT FROM KichThuoc WHERE TenKT = ?";
            PreparedStatement ppStm = conn.prepareStatement(sql);
            ppStm.setString(1, TenKT);
            ResultSet rs = ppStm.executeQuery();
            if (rs.next()) {
                MaKT = rs.getInt("MaKT");
            }
        } catch (Exception e) {
            System.err.println("Error getting MaDM from database:");
            e.printStackTrace();
        }
        return MaKT;
    }

    // Cập nhật số lượng sản phẩm khi đã thanh toán
    public int updateSanPhamMua(int ID, int SoLuongDaBan) {
        String selectSQL = "SELECT SoLuong FROM SanPham WHERE ID = (?)";
        String updateSQL = "UPDATE SanPham SET SoLuong = (?) WHERE ID = (?);";

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement psSelect = con.prepareStatement(selectSQL); 
                PreparedStatement psUpdate = con.prepareStatement(updateSQL);) {

            psSelect.setInt(1, ID);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                int SoLuongHienTai = rs.getInt("SoLuong");
                int SoLuongMoi = SoLuongHienTai - SoLuongDaBan;

                psUpdate.setInt(1, SoLuongMoi);
                psUpdate.setInt(2, ID);
            }

            int ketQua = psUpdate.executeUpdate();
            System.out.println("ketQua: "+ketQua);
            return ketQua;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    // Cập nhật số lượng sản phẩm khi đã thanh toán
    public int updateSanPhamHuy(int ID, int SoLuongDaHuy) {
        String selectSQL = "SELECT SoLuong FROM SanPham WHERE ID = (?)";
        String updateSQL = "UPDATE SanPham SET SoLuong = (?) WHERE ID = (?);";

        try (Connection con = KetNoiDB.getConnectDB(); PreparedStatement psSelect = con.prepareStatement(selectSQL); 
                PreparedStatement psUpdate = con.prepareStatement(updateSQL);) {

            psSelect.setInt(1, ID);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                int SoLuongHienTai = rs.getInt("SoLuong");
                int SoLuongMoi = SoLuongHienTai + SoLuongDaHuy;

                psUpdate.setInt(1, SoLuongMoi);
                psUpdate.setInt(2, ID);
            }

            int ketQua = psUpdate.executeUpdate();
            System.out.println("ketQua: "+ketQua);
            return ketQua;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public double getGiaSP(int MaSP){
        String sql = "select Gia from SanPham where ID = ?";
        double gia = 0;
        try(Connection conn = KetNoiDB.getConnectDB()){
            PreparedStatement ppStm = conn.prepareStatement(sql);
            ppStm.setInt(1, MaSP);
            ResultSet rs = ppStm.executeQuery();
            if (rs.next()){
                gia = rs.getDouble("Gia");
            }
            return gia;
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public void updateSoLuongByID(int maSP, int soLuong){
        String sql = "Update SanPham SET SoLuong = ? Where ID = ?";
        try(Connection conn = KetNoiDB.getConnectDB()){
            PreparedStatement ppStm = conn.prepareStatement(sql);
            ppStm.setInt(1, maSP);
            ppStm.setInt(2, soLuong);
            ppStm.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getSoLuongByID(int maSP) {
    String sql = "SELECT SoLuong FROM SanPham WHERE ID = ?";
    try (Connection conn = KetNoiDB.getConnectDB();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, maSP);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("SoLuong");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}


}

