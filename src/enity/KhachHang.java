/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enity;

/**
 *
 * @author Thuy SCTV
 */
public class KhachHang {
    private int ID;
    private String TenKH;
    private String SDT;

    public KhachHang() {
    }

    public KhachHang(String TenKH, String SDT) {
        this.TenKH = TenKH;
        this.SDT = SDT;
    }

    public KhachHang(int ID, String TenKH, String SDT) {
        this.ID = ID;
        this.TenKH = TenKH;
        this.SDT = SDT;
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
    
}
