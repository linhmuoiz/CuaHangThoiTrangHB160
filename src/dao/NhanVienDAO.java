/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import utils.KetNoiDB;
import java.sql.*;
/**
 *
 * @author Thuy SCTV
 */
public class NhanVienDAO {
    public boolean checkLogin(String sdt, String pass){
        try(Connection conn = KetNoiDB.getConnectDB()){
            PreparedStatement ppStm = conn.prepareStatement("SELECT * FROM NhanVien WHERE SDT = ? AND MatKhauDN = ?");
            ppStm.setString(1, sdt);
            ppStm.setString(2, pass);
            ResultSet rs = ppStm.executeQuery();
            if (rs.next() == true){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
