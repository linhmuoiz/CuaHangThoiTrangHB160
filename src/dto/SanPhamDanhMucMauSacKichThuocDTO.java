/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author admim
 */
public class SanPhamDanhMucMauSacKichThuocDTO {

    int ID;
    String TenSp;
    double Gia;
    int SoLuong;
    String TrangThai;
    String TenDM;
    String TenMS;
    String TenKT;
    String HinhAnh;

    public SanPhamDanhMucMauSacKichThuocDTO() {
    }

    public SanPhamDanhMucMauSacKichThuocDTO(int ID, String TenSp, double Gia, int SoLuong, String TrangThai, String TenDM, String TenMS, String TenKT, String HinhAnh) {
        this.ID = ID;
        this.TenSp = TenSp;
        this.Gia = Gia;
        this.SoLuong = SoLuong;
        this.TrangThai = TrangThai;
        this.TenDM = TenDM;
        this.TenMS = TenMS;
        this.TenKT = TenKT;
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

    public String getTenDM() {
        return TenDM;
    }

    public String getTenMS() {
        return TenMS;
    }

    public String getTenKT() {
        return TenKT;
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

    public void setTenDM(String TenDM) {
        this.TenDM = TenDM;
    }

    public void setTenMS(String TenMS) {
        this.TenMS = TenMS;
    }

    public void setTenKT(String TenKT) {
        this.TenKT = TenKT;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    
}
