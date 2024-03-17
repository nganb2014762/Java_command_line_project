/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteUser {
    
    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Nhập ID người dùng muốn xóa: ");
            int userIdToDelete = scanner.nextInt();

            boolean isDeleted = deleteUser(conn, userIdToDelete);

            if (isDeleted) {
                System.out.println("Người dùng đã được xóa thành công!");
            } else {
                System.out.println("Không thể xóa người dùng. Vui lòng kiểm tra lại ID.");
            }

//            MySQLConnect.closeConnection(conn);
//            scanner.close();
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static boolean deleteUser(Connection conn, int userId) {
        PreparedStatement stmt = null;
        boolean isDeleted = false;

        try {
            String query = "DELETE FROM user WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                isDeleted = true; // Người dùng đã được xóa thành công
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa người dùng: " + e.getMessage());
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
        return isDeleted;
    }
}
