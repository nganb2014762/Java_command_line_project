/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteOrder {

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Nhập ID đơn hàng cần xóa: ");
            int orderIdToDelete = scanner.nextInt();

            boolean isDeleted = deleteOrder(conn, orderIdToDelete);

            if (isDeleted) {
                System.out.println("Đơn hàng có ID " + orderIdToDelete + " đã được xóa thành công!");
                // Thực hiện các hành động sau khi xóa thành công
            } else {
                System.out.println("Xóa đơn hàng không thành công. Vui lòng thử lại sau!");
                // Hiển thị thông báo lỗi hoặc xử lý khi xóa không thành công
            }

        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }

    public static boolean deleteOrder(Connection conn, int orderId) {
        PreparedStatement stmt = null;
        boolean isDeleted = false;

        try {
            String query = "DELETE FROM orders WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                isDeleted = true; // Xóa thành công
            }
        } catch (SQLException e) {
            System.out.println("Xóa đơn hàng thất bại! Lỗi: " + e.getMessage());
        } finally {
            // Đóng PreparedStatement sau khi sử dụng
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
