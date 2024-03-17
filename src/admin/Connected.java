/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ngân
 */
package admin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connected {
    
    private static final String URL = "jdbc:mysql://localhost:3306/project";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (conn != null) {
//                System.out.println("Connected to the database!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed! Error: " + e.getMessage());
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
//                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.out.println("Failed to close connection! Error: " + e.getMessage());
            }
        }
    }
}






