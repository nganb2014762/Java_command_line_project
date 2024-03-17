/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author MSI
 */
public class ListProduct {

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            viewProducts(conn);
            Connected.closeConnection(conn);
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static void viewProducts(Connection conn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM products";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int Id = rs.getInt("id");
                String name = rs.getString("name");
                int category_id = rs.getInt("category_id");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                System.out.println("ID: " + Id);
                System.out.println("Name: " + name);
                System.out.println("Category_ID: " + category_id);
                System.out.println("Quantity: " + quantity);
                System.out.println("Price: " + price);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xem danh sách sản phẩm: " + e.getMessage());
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
    }

//    public void ListProductMethod() {
//        Connection conn = null;
//        try {
//            conn = Connected.getConnection();
//            Scanner l = new Scanner(System.in);
//            String name = new String();
//            int id, category_id, quantity, price, x, y, d = 1;
//            String sql;
//            Statement smt;
//            ResultSet rs;
//            AddProduct addProduct;
//            ListProduct listProduct;
//
//            System.out.println("----- Xem sản phẩm -----");
//            sql = "SELECT * FROM products";
//            smt = conn.createStatement();
//
//            try {
//                rs = smt.executeQuery(sql);
//                while (rs.next()) {
//                    System.out.println("id: " + rs.getString("id"));
//                    System.out.println("name:" + rs.getString("name"));
//                    System.out.println("category_id:" + rs.getString("category_id"));
//                    System.out.println("quantity:" + rs.getString("quantity"));
//                    System.out.println("price:" + rs.getString("price"));
//                }
//                System.out.println("Nhập (0)-trở về (1)-thêm sản phẩm ");
//                y = l.nextInt();
//                if (y == 1) {
//                    addProduct = new AddProduct();
//                    addProduct.AddProductMethod();
//                } else {
//                    return;
//                }
//            } catch (SQLException ex) {
//                System.out.println("Truy vấn dữ liệu không thành công!" + ex.getMessage());
//            }
//        } catch (SQLException ex) {
//            System.out.println("Lỗi!\n" + ex.getMessage());
//        }
//
//    }
}
