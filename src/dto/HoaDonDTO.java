/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.Date;

/**
 *
 * @author Thuy SCTV
 */
public class HoaDonDTO {
    private int ID;
    private String TenKH;
    private String SDT;
    private double ThanhTien;
    private String HinhThucTT;
    private String TenNV;
    private Date NgayTao;
    private String TrangThai;

    public HoaDonDTO() {
    }

    public HoaDonDTO(int ID, String TenKH, String SDT, double ThanhTien, String HinhThucTT, String TenNV, Date NgayTao, String TrangThai) {
        this.ID = ID;
        this.TenKH = TenKH;
        this.SDT = SDT;
        this.ThanhTien = ThanhTien;
        this.HinhThucTT = HinhThucTT;
        this.TenNV = TenNV;
        this.NgayTao = NgayTao;
        this.TrangThai = TrangThai;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public double getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(double ThanhTien) {
        this.ThanhTien = ThanhTien;
    }

    public String getHinhThucTT() {
        return HinhThucTT;
    }

    public void setHinhThucTT(String HinhThucTT) {
        this.HinhThucTT = HinhThucTT;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }
    
}
