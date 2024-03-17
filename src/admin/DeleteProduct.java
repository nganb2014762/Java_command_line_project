/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author MSI
 */
public class DeleteProduct {

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("----- Xóa sản phẩm -----");
            System.out.println("Nhập ID sản phẩm muốn xóa: ");
            int productIdToDelete = scanner.nextInt();

            boolean isDeleted = deleteProduct(conn, productIdToDelete);

            if (isDeleted) {
                System.out.println("Sản phẩm đã được xóa thành công!");
            } else {
                System.out.println("Không thể xóa sản phẩm. Vui lòng kiểm tra lại ID.");
            }
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static boolean deleteProduct(Connection conn, int Id) {
        PreparedStatement stmt = null;
        boolean isDeleted = false;

        try {
            String query = "DELETE FROM products WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, Id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
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
