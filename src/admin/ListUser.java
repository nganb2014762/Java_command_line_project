/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListUser {
    
    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            viewUsers(conn);
            Connected.closeConnection(conn);
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static void viewUsers(Connection conn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM user";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("id");
                String username = rs.getString("name");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String phone = rs.getString("phone");

                System.out.println("ID: " + userId);
                System.out.println("Username: " + username);
                System.out.println("Email: " + email);
                System.out.println("Address: " + address);
                System.out.println("Phone: " + phone);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xem danh sách người dùng: " + e.getMessage());
        } finally {
            // Đóng tất cả các kết nối và tài nguyên
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
