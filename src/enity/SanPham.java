/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enity;

/**
 *
 * @author admim
 */
public class SanPham {
    int ID;
    String TenSp;
    double Gia;
    int SoLuong;
    String TrangThai;
    int MaDM;
    int MaMS;
    int MaKT;
    String HinhAnh;

    public SanPham() {
    }

    public SanPham(int ID, String TenSp, double Gia, int SoLuong, String TrangThai, int MaDM, int MaMS, int MaKT, String HinhAnh) {
        this.ID = ID;
        this.TenSp = TenSp;
        this.Gia = Gia;
        this.SoLuong = SoLuong;
        this.TrangThai = TrangThai;
        this.MaDM = MaDM;
        this.MaMS = MaMS;
        this.MaKT = MaKT;
        this.HinhAnh = HinhAnh;
    }

    public int getID() {
        return ID;
    }

    public String getTenSp() {
        return TenSp;
    }

    public double getGia() {
        return Gia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public int getMaDM() {
        return MaDM;
    }

    public int getMaMS() {
        return MaMS;
    }

    public int getMaKT() {
        return MaKT;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTenSp(String TenSp) {
        this.TenSp = TenSp;
    }

    public void setGia(double Gia) {
        this.Gia = Gia;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public void setMaDM(int MaDM) {
        this.MaDM = MaDM;
    }

    public void setMaMS(int MaMS) {
        this.MaMS = MaMS;
    }

    public void setMaKT(int MaKT) {
        this.MaKT = MaKT;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }
    
    
}
