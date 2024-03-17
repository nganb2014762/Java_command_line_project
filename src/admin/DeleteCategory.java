/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author MSI
 */
public class DeleteCategory {

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("----- Xóa danh mục -----");
            System.out.println("Nhập ID danh mục muốn xóa: ");
            int categoryIdToDelete = scanner.nextInt();

            boolean isDeleted = deleteCategory(conn, categoryIdToDelete);

            if (isDeleted) {
                System.out.println("Danh mục đã được xóa thành công!");
            } else {
                System.out.println("Không thể xóa danh mục. Vui lòng kiểm tra lại ID.");
            }
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static boolean deleteCategory(Connection conn, int Id) {
        PreparedStatement stmt = null;
        boolean isDeleted = false;

        try {
            String query = "DELETE FROM category WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,Id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                isDeleted = true; 
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa danh mục: " + e.getMessage());
        } finally {
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
