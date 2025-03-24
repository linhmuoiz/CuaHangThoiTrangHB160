/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enity;

/**
 *
 * @author admim
 */
public class DanhMuc {
    int ID;
    String TenDM;

    public DanhMuc() {
    }

    public DanhMuc(int ID, String TenDM) {
        this.ID = ID;
        this.TenDM = TenDM;
    }

    public int getID() {
        return ID;
    }

    public String getTenDM() {
        return TenDM;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTenDM(String TenDM) {
        this.TenDM = TenDM;
    }
    
}
