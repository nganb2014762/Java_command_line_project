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

public class FindOrder{

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Nhập ID đơn hàng cần tìm: ");
            int orderIdToFind = scanner.nextInt();

            // Tìm kiếm đơn hàng theo ID
            findOrderById(conn, orderIdToFind);

            // Đóng scanner nếu không sử dụng nữa
//            scanner.close();
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static void findOrderById(Connection conn, int orderId) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM orders WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Lấy thông tin từ ResultSet
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                double totalPrice = rs.getDouble("total_price");
                String totalProducts = rs.getString("total_products");
                String placedOn = rs.getString("placed_on");
                String paymentStatus = rs.getString("payment_status");

                // In thông tin đơn hàng
                System.out.println("Thông tin đơn hàng:");
                System.out.println("ID: " + id);
                System.out.println("User ID: " + userId);
                System.out.println("Total Price: " + totalPrice);
                System.out.println("Total Products: " + totalProducts);
                System.out.println("Placed On: " + placedOn);
                System.out.println("Payment Status: " + paymentStatus);
            } else {
                System.out.println("Không tìm thấy đơn hàng có ID: " + orderId);
            }
        } catch (SQLException e) {
            System.out.println("Tìm đơn hàng thất bại! Lỗi: " + e.getMessage());
        } finally {
            // Đóng ResultSet và PreparedStatement sau khi sử dụng
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
}
