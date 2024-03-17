/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FindUser {
    
    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Nhập ID người dùng cần tìm kiếm: ");
            int userID = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống sau khi nhập số

            findUserByID(conn, userID);

//            scanner.close();
//            MySQLConnect.closeConnection(conn);
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static void findUserByID(Connection conn, int userID) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM user WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userID);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Lấy thông tin người dùng từ ResultSet
                String username = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");

                System.out.println("Thông tin người dùng:");
                System.out.println("ID: " + userID);
                System.out.println("Tên người dùng: " + username);
                System.out.println("Địa chỉ: " + address);
                System.out.println("SĐT: " + phone);
            } else {
                System.out.println("Không tìm thấy người dùng có ID " + userID);
            }
        } catch (SQLException e) {
            System.out.println("Tìm kiếm người dùng thất bại! Lỗi: " + e.getMessage());
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


