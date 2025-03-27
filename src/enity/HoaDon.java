/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enity;

import java.util.Date;

/**
 *
 * @author Thuy SCTV
 */
public class HoaDon {private int ID;
    private int MaNV;
    private int MaKH;
    private double ThanhTien;
    private String HinhThucTT;
    private Date NgayTao;
    private String TrangThai;

    public HoaDon() {
    }

    public HoaDon(int MaNV, int MaKH, double ThanhTien, String HinhThucTT, Date NgayTao, String TrangThai) {
        this.MaNV = MaNV;
        this.MaKH = MaKH;
        this.ThanhTien = ThanhTien;
        this.HinhThucTT = HinhThucTT;
        this.NgayTao = NgayTao;
        this.TrangThai = TrangThai;
    }
    
    

    public HoaDon(int ID, Date NgayTao, String TrangThai) {
        this.ID = ID;
        this.NgayTao = NgayTao;
        this.TrangThai = TrangThai;
    } 

    public HoaDon(int ID, int MaNV, int MaKH, double ThanhTien, String HinhThucTT, Date NgayTao, String TrangThai) {
        this.ID = ID;
        this.MaNV = MaNV;
        this.MaKH = MaKH;
        this.ThanhTien = ThanhTien;
        this.HinhThucTT = HinhThucTT;
        this.NgayTao = NgayTao;
        this.TrangThai = TrangThai;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int MaNV) {
        this.MaNV = MaNV;
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int MaKH) {
        this.MaKH = MaKH;
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
