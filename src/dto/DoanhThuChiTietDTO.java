/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author admim
 */
public class DoanhThuChiTietDTO {
    int MaSP;
    String TenSP;
    int TongSoLuongBanRa;
    double TongTienSanPhamBanRa;

    public DoanhThuChiTietDTO() {
    }

    public DoanhThuChiTietDTO(int MaSP, String TenSP, int TongSoLuongBanRa, double TongTienSanPhamBanRa) {
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.TongSoLuongBanRa = TongSoLuongBanRa;
        this.TongTienSanPhamBanRa = TongTienSanPhamBanRa;
    }

    public int getMaSP() {
        return MaSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public int getTongSoLuongBanRa() {
        return TongSoLuongBanRa;
    }

    public double getTongTienSanPhamBanRa() {
        return TongTienSanPhamBanRa;
    }

    public void setMaSP(int MaSP) {
        this.MaSP = MaSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public void setTongSoLuongBanRa(int TongSoLuongBanRa) {
        this.TongSoLuongBanRa = TongSoLuongBanRa;
    }

    public void setTongTienSanPhamBanRa(double TongTienSanPhamBanRa) {
        this.TongTienSanPhamBanRa = TongTienSanPhamBanRa;
    }
    
    
}
