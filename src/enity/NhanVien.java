/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enity;

/**
 *
 * @author Thuy SCTV
 */
public class NhanVien {
    private String TenNV;
    private String SDT;
    private String MatKhauDN;
    private String DiaChi;
    private String GioiTinh;

    public NhanVien() {
    }

    public NhanVien(String TenNV, String SDT, String MatKhauDN, String DiaChi, String GioiTinh) {
        this.TenNV = TenNV;
        this.SDT = SDT;
        this.MatKhauDN = MatKhauDN;
        this.DiaChi = DiaChi;
        this.GioiTinh = GioiTinh;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getMatKhauDN() {
        return MatKhauDN;
    }

    public void setMatKhauDN(String MatKhauDN) {
        this.MatKhauDN = MatKhauDN;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }
    
    
}
