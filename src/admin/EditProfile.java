///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */

package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EditProfile {

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Nhập ID người dùng cần chỉnh sửa: ");
            int userID = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống sau khi nhập số

            String newUsername = getUserInput(scanner, "Nhập tên người dùng mới (Nhấn Enter để giữ nguyên giá trị cũ): ", conn, userID, "name");
            String newAddress = getUserInput(scanner, "Nhập địa chỉ mới (Nhấn Enter để giữ nguyên giá trị cũ): ", conn, userID, "address");
            String newPhone = getUserInput(scanner, "Nhập SĐT mới (Nhấn Enter để giữ nguyên giá trị cũ): ", conn, userID, "phone");
            String newEmail = getUserInput(scanner, "Nhập Email mới (Nhấn Enter để giữ nguyên giá trị cũ): ", conn, userID, "email");
            
            // Cập nhật thông tin người dùng
            boolean isUpdated = updateUserInfo(conn, userID, newUsername, newAddress, newPhone, newEmail);

            if (isUpdated) {
                System.out.println("Thông tin người dùng đã được cập nhật thành công!");
                // Thực hiện các hành động sau khi cập nhật thông tin thành công
            } else {
                System.out.println("Cập nhật thông tin người dùng không thành công. Vui lòng thử lại sau!");
                // Hiển thị thông báo lỗi hoặc xử lý khi cập nhật thông tin không thành công
            }

        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static String getUserInput(Scanner scanner, String message, Connection conn, int userID, String columnName) {
        System.out.println(message);
        String userInput = scanner.nextLine();
        return userInput.isEmpty() ? getCurrentUserData(conn, userID, columnName) : userInput;
    }

    public static String getCurrentUserData(Connection conn, int userID, String columnName) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String currentValue = "";

        try {
            String query = "SELECT " + columnName + " FROM user WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userID);

            rs = stmt.executeQuery();
            if (rs.next()) {
                currentValue = rs.getString(columnName);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving current " + columnName + ": " + e.getMessage());
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
        return currentValue;
    }

    public static boolean updateUserInfo(Connection conn, int userID, String newUsername, String newAddress, String newPhone, String newEmail) {
        PreparedStatement stmt = null;
        boolean isUpdated = false;

        try {
            String query = "UPDATE user SET name = ?, address = ?, phone = ?, email = ? WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, newUsername);
            stmt.setString(2, newAddress);
            stmt.setString(3, newPhone);
            stmt.setString(4, newEmail);
            
            stmt.setInt(5, userID);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                isUpdated = true; // Cập nhật thành công
            }
        } catch (SQLException e) {
            System.out.println("Update failed! Error: " + e.getMessage());
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
        return isUpdated;
    }
}
