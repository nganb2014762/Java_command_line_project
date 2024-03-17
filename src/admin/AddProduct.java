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
public class AddProduct {

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("----- Thêm sản phẩm -----");
            System.out.println("Nhập tên sản phẩm: ");
            String name = scanner.nextLine();

            System.out.println("Nhập mã danh mục: ");
            int category_id = scanner.nextInt();

            System.out.println("Nhập số lượng sản phẩm: ");
            int quantity = scanner.nextInt();

            System.out.println("Nhập giá sản phẩm: ");
            float price = scanner.nextFloat();

            boolean addProduct = addProduct(conn, name, category_id, quantity, price);

            if (addProduct) {
                System.out.println("Thêm sản phẩm thành công!");
            } else {
                System.out.println("Thêm sản phẩm không thành công. Vui lòng thử lại sau!");
            }
            AddProduct returnaddProduct = new AddProduct();
            returnaddProduct.returnAddProductMethod();
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static boolean addProduct(Connection conn, String name, int category_id, int quantity, float price) {
        PreparedStatement stmt = null;
        boolean addProduct = false;

        try {
            String sql = "SELECT * FROM category WHERE id = ?";
            PreparedStatement checkProductStatement = conn.prepareStatement(sql);
            checkProductStatement.setInt(1, category_id);
            ResultSet checkProductResult = checkProductStatement.executeQuery();

            if (checkProductResult.next()) {
                String query = "INSERT INTO products (name, category_id, quantity, price) VALUES (?, ?, ?, ?)";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setInt(2, category_id);
                stmt.setInt(3, quantity);
                stmt.setFloat(4, price);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    addProduct = true;
                }
            } else {
                System.out.println("Product_ID không tồn tại trong danh sách!");
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
        return addProduct;
    }

    public void returnAddProductMethod() {
        Scanner l = new Scanner(System.in);
        int y;
        System.out.println(" 0. Trở về 1. Tiếp tục thêm 2. Xem sản phẩm");
        System.out.println("Nhập lựa chọn của bạn: ");
        y = l.nextInt();
        if (y == 1) {
            AddProduct.main(new String[0]);
        } else if (y == 2) {
            ListProduct.main(new String[0]);
        } else {
            return;
        }
    }
}
