/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enity;

/**
 *
 * @author admim
 */
public class KichThuoc {
    int ID;
    String TenKT;

    public KichThuoc() {
    }

    public KichThuoc(int ID, String TenKT) {
        this.ID = ID;
        this.TenKT = TenKT;
    }

    public int getID() {
        return ID;
    }

    public String getTenKT() {
        return TenKT;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTenKT(String TenKT) {
        this.TenKT = TenKT;
    }
    
    
}
