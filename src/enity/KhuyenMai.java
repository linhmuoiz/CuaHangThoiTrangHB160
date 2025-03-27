/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enity;

/**
 *
 * @author admim
 */
public class KhuyenMai {
    int MaKM;
    String TenKM;
    String CodeGiamGia;
    int GoiGiamGia;
    String NgayBatDau;
    String NgayKetThuc;

    public KhuyenMai() {
    }

    public KhuyenMai(int MaKM, String TenKM, String CodeGiamGia, int GoiGiamGia, String NgayBatDau, String NgayKetThuc) {
        this.MaKM = MaKM;
        this.TenKM = TenKM;
        this.CodeGiamGia = CodeGiamGia;
        this.GoiGiamGia = GoiGiamGia;
        this.NgayBatDau = NgayBatDau;
        this.NgayKetThuc = NgayKetThuc;
    }

    public int getMaKM() {
        return MaKM;
    }

    public String getTenKM() {
        return TenKM;
    }

    public String getCodeGiamGia() {
        return CodeGiamGia;
    }

    public int getGoiGiamGia() {
        return GoiGiamGia;
    }

    public String getNgayBatDau() {
        return NgayBatDau;
    }

    public String getNgayKetThuc() {
        return NgayKetThuc;
    }

    public void setMaKM(int MaKM) {
        this.MaKM = MaKM;
    }

    public void setTenKM(String TenKM) {
        this.TenKM = TenKM;
    }

    public void setCodeGiamGia(String CodeGiamGia) {
        this.CodeGiamGia = CodeGiamGia;
    }

    public void setGoiGiamGia(int GoiGiamGia) {
        this.GoiGiamGia = GoiGiamGia;
    }

    public void setNgayBatDau(String NgayBatDau) {
        this.NgayBatDau = NgayBatDau;
    }

    public void setNgayKetThuc(String NgayKetThuc) {
        this.NgayKetThuc = NgayKetThuc;
    }
    
    
}
