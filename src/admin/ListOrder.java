/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListOrder {

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            printOrderList(conn);
        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static void printOrderList(Connection conn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM orders";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            // In ra thông tin từ ResultSet
            while (rs.next()) {
                int orderId = rs.getInt("id");
                int userId = rs.getInt("user_id");
                double totalPrice = rs.getDouble("total_price");
                String totalProducts = rs.getString("total_products");
                String placedOn = rs.getString("placed_on");
                String paymentStatus = rs.getString("payment_status");

                // In thông tin của từng đơn hàng
                System.out.println("ID đơn hàng: " + orderId);
                System.out.println("ID người dùng: " + userId);
                System.out.println("Tổng giá tiền: " + totalPrice);
                System.out.println("Tổng sản phẩm: " + totalProducts);
                System.out.println("Ngày đặt hàng: " + placedOn);
                System.out.println("Trạng thái thanh toán: " + paymentStatus);
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi truy vấn danh sách đơn hàng: " + e.getMessage());
        } finally {
            // Đóng ResultSet, PreparedStatement và Connection sau khi sử dụng
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }
    }
}
