/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Thuy SCTV
 */
public class ChiTietHDDTO {
     private int ID;
    private int MaSP;
    private String TenSP;
    private String TenDM;
    private String TenMS;
    private String TenKT;
    private int SoLuong;
    private double Gia;
    private int GioiGiamGia;

    public ChiTietHDDTO() {
    }

    public ChiTietHDDTO(int ID, String TenSP, String TenDM, String TenMS, String TenKT, double Gia) {
        this.ID = ID;
        this.TenSP = TenSP;
        this.TenDM = TenDM;
        this.TenMS = TenMS;
        this.TenKT = TenKT;
        this.Gia = Gia;
    }
    
    

    public ChiTietHDDTO(int MaSP, String TenSP, String TenDM, String TenMS, String TenKT, int SoLuong, double Gia, int GioiGiamGia) {
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.TenDM = TenDM;
        this.TenMS = TenMS;
        this.TenKT = TenKT;
        this.SoLuong = SoLuong;
        this.Gia = Gia;
        this.GioiGiamGia = GioiGiamGia;
    }

    public ChiTietHDDTO(int ID, int MaSP, String TenSP, String TenDM, String TenMS, String TenKT, int SoLuong, double Gia, int GioiGiamGia) {
        this.ID = ID;
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.TenDM = TenDM;
        this.TenMS = TenMS;
        this.TenKT = TenKT;
        this.SoLuong = SoLuong;
        this.Gia = Gia;
        this.GioiGiamGia = GioiGiamGia;
    }
    
    

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int MaSP) {
        this.MaSP = MaSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public String getTenDM() {
        return TenDM;
    }

    public void setTenDM(String TenDM) {
        this.TenDM = TenDM;
    }

    public String getTenMS() {
        return TenMS;
    }

    public void setTenMS(String TenMS) {
        this.TenMS = TenMS;
    }

    public String getTenKT() {
        return TenKT;
    }

    public void setTenKT(String TenKT) {
        this.TenKT = TenKT;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public double getGia() {
        return Gia;
    }

    public void setGia(double Gia) {
        this.Gia = Gia;
    }

    public int getGioiGiamGia() {
        return GioiGiamGia;
    }

    public void setGioiGiamGia(int GioiGiamGia) {
        this.GioiGiamGia = GioiGiamGia;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
    
}
