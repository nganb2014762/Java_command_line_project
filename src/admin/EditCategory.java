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

/**
 *
 * @author MSI
 */
public class EditCategory {

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("----- Sửa danh mục -----");
            System.out.println("Nhập ID danh mục cần chỉnh sửa: ");
            int ID = scanner.nextInt();
            scanner.nextLine();

            String newName = getCategoryInput(scanner, "Nhập tên danh mục mới (Nhấn Enter để giữ nguyên giá trị cũ): ", conn, ID, "name");
            boolean isUpdated = updateCategory(conn, ID, newName);

            if (isUpdated) {
                System.out.println("Thông tin danh mục đã được cập nhật thành công!");
            } else {
                System.out.println("Cập nhật thông tin danh mục không thành công. Vui lòng thử lại sau!");
            }
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static String getCategoryInput(Scanner scanner, String message, Connection conn, int ID, String columnName) {
        System.out.println(message);
        String categoryInput = scanner.nextLine();
        return categoryInput.isEmpty() ? getCurrentCategoryData(conn, ID, columnName) : categoryInput;
    }

    public static String getCurrentCategoryData(Connection conn, int ID, String columnName) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String currentValue = "";

        try {
            String query = "SELECT " + columnName + " FROM category WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,  ID);

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

    public static boolean updateCategory(Connection conn, int ID, String newName) {
        PreparedStatement stmt = null;
        boolean isUpdated = false;

        try {
            String query = "UPDATE category SET name = ? WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, newName);
            stmt.setInt(2,  ID);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                isUpdated = true; 
            }
        } catch (SQLException e) {
            System.out.println("Update failed! Error: " + e.getMessage());
        } finally {
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
