/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Register {
    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Nhập tên người dùng: ");
            String username = scanner.nextLine();

            System.out.println("Nhập địa chỉ email: ");
            String email = scanner.nextLine();

            System.out.println("Nhập mật khẩu: ");
            String password = scanner.nextLine();

            boolean isRegistered = registerUser(conn, username, email, password);

            if (isRegistered) {
                System.out.println("Đăng ký thành công!");
                // Thực hiện các hành động sau khi đăng ký thành công
            } else {
                System.out.println("Đăng ký không thành công. Vui lòng thử lại sau!");
                // Hiển thị thông báo lỗi hoặc xử lý khi đăng ký không thành công
            }
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static boolean registerUser(Connection conn, String username, String email, String password) {
        PreparedStatement stmt = null;
        boolean isRegistered = false;

        try {
            String query = "INSERT INTO user (name, email, password) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                isRegistered = true; // Đăng ký thành công
            }
        } catch (SQLException e) {
            System.out.println("Registration failed! Error: " + e.getMessage());
        } finally {
            // Đóng tất cả các kết nối và tài nguyên
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isRegistered;
    }
}
