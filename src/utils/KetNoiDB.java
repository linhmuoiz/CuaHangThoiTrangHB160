/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import java.sql.*;
/**
 *
 * @author Thuy SCTV
 */
public class KetNoiDB {
    public static Connection getConnectDB() throws SQLException{
        String urlSQL = "jdbc:sqlserver://26.130.250.210:1433;databaseName=DUANTOTNGHIEP;"
                + "user=sa;password=1234;encrypt=true;"
                + "trustServerCertificate=true";
        return DriverManager.getConnection(urlSQL);
    }
}
