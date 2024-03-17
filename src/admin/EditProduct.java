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
public class EditProduct {

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("----- Chỉnh sửa thông tin sản phẩm -----");
            System.out.println("Nhập ID sản phẩm cần chỉnh sửa: ");
            int productID = scanner.nextInt();
            scanner.nextLine();

            String newName = getProductInput(scanner, "Nhập tên sản phẩm mới (Nhấn Enter để giữ nguyên giá trị cũ): ", conn, productID, "name");
            int newCategoryID = Integer.parseInt(getProductInput(scanner, "Nhập ID danh mục mới (Nhấn Enter để giữ nguyên giá trị cũ): ", conn, productID, "category_id"));
            int newQuantity = Integer.parseInt(getProductInput(scanner, "Nhập số lượng sản phẩm mới (Nhấn Enter để giữ nguyên giá trị cũ): ", conn, productID, "quantity"));
            float newPrice = Float.parseFloat(getProductInput(scanner, "Nhập giá sản phẩm mới (Nhấn Enter để giữ nguyên giá trị cũ): ", conn, productID, "price"));

            boolean isUpdated = updateProduct(conn, productID, newName, newCategoryID, newQuantity, newPrice);

            if (isUpdated) {
                System.out.println("Thông tin sản phẩm đã được cập nhật thành công!");
            } else {
                System.out.println("Cập nhật thông tin sản phẩm không thành công. Vui lòng thử lại sau!");
            }
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static String getProductInput(Scanner scanner, String message, Connection conn, int productID, String columnName) {
        System.out.println(message);
        String productInput = scanner.nextLine();
        return productInput.isEmpty() ? getCurrentProductData(conn, productID, columnName) : productInput;
    }

    public static String getCurrentProductData(Connection conn, int productID, String columnName) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String currentValue = "";

        try {
            String query = "SELECT " + columnName + " FROM products WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, productID);

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

    public static boolean updateProduct(Connection conn, int productID, String newName, int newCategoryID, int newQuantity, float newPrice) {
        PreparedStatement stmt = null;
        boolean isUpdated = false;

        try {
            String sql = "SELECT * FROM products WHERE id = ?";
            PreparedStatement checkIdStatement = conn.prepareStatement(sql);
            checkIdStatement.setInt(1, productID);
            ResultSet checkIdResult = checkIdStatement.executeQuery();

            if (checkIdResult.next()) {
                sql = "SELECT * FROM category WHERE id = ?";
                PreparedStatement checkCategoryStatement = conn.prepareStatement(sql);
                checkCategoryStatement.setInt(1, newCategoryID);
                ResultSet checkCategoryResult = checkCategoryStatement.executeQuery();

                if (checkCategoryResult.next()) {
                    String query = "UPDATE products SET name = ?, category_id = ?, quantity = ?, price = ? WHERE id = ?";
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, newName);
                    stmt.setInt(2, newCategoryID);
                    stmt.setInt(3, newQuantity);
                    stmt.setFloat(4, newPrice);
                    stmt.setInt(5, productID);

                    int affectedRows = stmt.executeUpdate();

                    if (affectedRows > 0) {
                        isUpdated = true;
                    }

                } else {
                    System.out.println("Category_ID không tồn tại trong danh sách!");
                }
            } else {
                System.out.println("ID không tồn tại trong danh sách!");
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

//    public void EditProductMethod() {
//        Connection conn = null;
//        try {
//            conn = Connected.getConnection();
//            Scanner l = new Scanner(System.in);
//            String newname = new String();
//            int id, newcategory_id, newquantity, newprice, updated;
//            String sql;
//            AddProduct returnaddProduct;
//            System.out.println("----- Sửa sản phẩm -----");
//            System.out.println("Nhập id: ");
//            id = l.nextInt();
//            l.nextLine();
//
//            sql = "SELECT * FROM products WHERE id = ?";
//            PreparedStatement checkIdStatement = conn.prepareStatement(sql);
//            checkIdStatement.setInt(1, id);
//            ResultSet checkIdResult = checkIdStatement.executeQuery();
//
//            if (checkIdResult.next()) {
//                System.out.println("Nhập tên mới: ");
//                newname = l.nextLine();
//                newname = newname.isEmpty() ? checkIdResult.getString("name") : newname;
//                
//                System.out.println("Nhập mã danh mục: ");
//                newcategory_id = l.nextInt();
//                
//                sql = "SELECT * FROM category WHERE id = ?";
//                PreparedStatement checkCategoryStatement = conn.prepareStatement(sql);
//                checkCategoryStatement.setInt(1, newcategory_id);
//                ResultSet checkCategoryResult = checkCategoryStatement.executeQuery();
//
//                if (checkCategoryResult.next()) {
//                    System.out.println("Nhập số lượng sản phẩm: ");
//                    newquantity = l.nextInt();
//                    System.out.println("Nhập giá sản phẩm: ");
//                    newprice = l.nextInt();
//
//                    sql = "UPDATE products SET name = ?, category_id = ?, quantity = ?, price = ?  WHERE id = ?";
//                    PreparedStatement psmt;
//                    psmt = conn.prepareStatement(sql);
//                    psmt.setString(1, newname);
//                    psmt.setInt(2, newcategory_id);
//                    psmt.setInt(3, newquantity);
//                    psmt.setInt(4, newprice);
//                    psmt.setInt(5, id);
//                    updated = psmt.executeUpdate();
//                    try {
//                        int rowsInserted = psmt.executeUpdate();
//                        if (rowsInserted > 0) {
//                            System.out.println("Sửa sản phẩm thành công!");
//                        } else {
//                            System.out.println("Sửa sản phẩm thất bại");
//                        }
//                        returnaddProduct = new AddProduct();
//                        returnaddProduct.returnAddProductMethod();
//                    } catch (SQLException ex) {
//                        System.out.println("Truy vấn dữ liệu không thành công!\n" + ex.getMessage());
//                    }
//                } else {
//                    System.out.println("Category_ID không tồn tại trong danh sách!");
//                    returnaddProduct = new AddProduct();
//                    returnaddProduct.returnAddProductMethod();
//                }
//            } else {
//                System.out.println("ID không tồn tại trong danh sách!");
//
//                returnaddProduct = new AddProduct();
//                returnaddProduct.returnAddProductMethod();
//            }
//        } catch (SQLException ex) {
//            System.out.println("Lỗi!\n" + ex.getMessage());
//        }
//    }
}
