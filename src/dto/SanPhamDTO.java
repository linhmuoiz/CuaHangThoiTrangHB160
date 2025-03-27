/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Thuy SCTV
 */
public class SanPhamDTO {
    private int ID;
    private String TenSP;
    private double Gia;
    private int SoLuong;
    private String TrangThai;
    private String TenDM;
    private String TenMS;
    private String TenKT;

    public SanPhamDTO() {
    }

    public SanPhamDTO(int ID, String TenSP, double Gia, int SoLuong, String TrangThai, String TenDM, String TenMS, String TenKT) {
        this.ID = ID;
        this.TenSP = TenSP;
        this.Gia = Gia;
        this.SoLuong = SoLuong;
        this.TrangThai = TrangThai;
        this.TenDM = TenDM;
        this.TenMS = TenMS;
        this.TenKT = TenKT;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public double getGia() {
        return Gia;
    }

    public void setGia(double Gia) {
        this.Gia = Gia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
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
    
}
