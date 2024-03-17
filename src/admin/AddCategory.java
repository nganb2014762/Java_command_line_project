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
public class AddCategory {

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("----- Thêm danh mục -----");
            System.out.println("Nhập tên danh mục: ");
            String name = scanner.nextLine();

            boolean addCategory = addCategory(conn, name);

            if (addCategory) {
                System.out.println("Thêm danh mục thành công!");
            } else {
                System.out.println("Thêm danh mục không thành công. Vui lòng thử lại sau!");
            }
            AddCategory returnAddCategoryMethod = new AddCategory();
            returnAddCategoryMethod.returnAddCategoryMethod();
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static boolean addCategory(Connection conn, String name) {
        PreparedStatement stmt = null;
        boolean addCategory = false;

        try {
            String query = "INSERT INTO category (name) VALUES (?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, name);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                addCategory = true;
            }
        } catch (SQLException e) {
            System.out.println("Add category failed! Error: " + e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return addCategory;
    }

    public void returnAddCategoryMethod() {
        Scanner l = new Scanner(System.in);
        int y;
        System.out.println(" 0. Trở về 1. Tiếp tục thêm 2. Xem danh mục");
        System.out.println("Nhập lựa chọn của bạn: ");
        y = l.nextInt();
        if (y == 1) {
            AddCategory.main(new String[0]);
        } else if (y == 2) {
            ListCategory.main(new String[0]);
        } else {
            return;
        }
    }
}
