package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */ /**
 *
 * @author MSI
 */

public class ListCategory {

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            viewCategorys(conn);
            Connected.closeConnection(conn);
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static void viewCategorys(Connection conn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM category";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int Id = rs.getInt("id");
                String username = rs.getString("name");

                System.out.println("ID: " + Id);
                System.out.println("Name: " + username);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xem danh sách người dùng: " + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
