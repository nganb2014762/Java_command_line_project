///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EditOrder {

    // Phương thức kiểm tra xem user_id có tồn tại trong bảng user hay không
    public static boolean isUserIdExists(Connection conn, int userId) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isExists = false;

        try {
            String query = "SELECT id FROM user WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);

            rs = stmt.executeQuery();
            if (rs.next()) {
                isExists = true; // user_id tồn tại trong bảng user
            }
        } catch (SQLException e) {
            System.out.println("Error checking user ID: " + e.getMessage());
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
        return isExists;
    }

    public static int getCurrentUserId(Connection conn, int orderID) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int currentUserId = 0;

        try {
            String query = "SELECT user_id FROM orders WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderID);

            rs = stmt.executeQuery();
            if (rs.next()) {
                currentUserId = rs.getInt("user_id");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving current user ID: " + e.getMessage());
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
        return currentUserId;
    }

    // Phương thức lấy giá trị cũ của total_price
    public static double getCurrentTotalPrice(Connection conn, int orderID) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        double currentTotalPrice = 0.0;

        try {
            String query = "SELECT total_price FROM orders WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderID);

            rs = stmt.executeQuery();
            if (rs.next()) {
                currentTotalPrice = rs.getDouble("total_price");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving current total price: " + e.getMessage());
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
        return currentTotalPrice;
    }

    // Phương thức cập nhật thông tin đơn hàng
    public static boolean updateOrderInfo(Connection conn, int orderID, int newUserID, double newTotalPrice, String newTotalProducts, String newPlacedOn, String newPaymentStatus) {
        PreparedStatement stmt = null;
        boolean isUpdated = false;

        try {
            String query = "UPDATE orders SET user_id = ?, total_price = ?, total_products = ?, placed_on = ?, payment_status = ? WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, newUserID);
            stmt.setDouble(2, newTotalPrice);
            stmt.setString(3, newTotalProducts);
            stmt.setString(4, newPlacedOn);
            stmt.setString(5, newPaymentStatus);
            stmt.setInt(6, orderID);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                isUpdated = true; // Cập nhật thành công
            }
        } catch (SQLException e) {
            System.out.println("Update failed! Error: " + e.getMessage());
        } finally {
            // Đóng tất cả các kết nối và tài nguyên
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

    public static String getCurrentTotalProducts(Connection conn, int orderID) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String currentTotalProducts = "";

        try {
            String query = "SELECT total_products FROM orders WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderID);

            rs = stmt.executeQuery();
            if (rs.next()) {
                currentTotalProducts = rs.getString("total_products");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving current total products: " + e.getMessage());
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
        return currentTotalProducts;
    }

    public static String getCurrentPlacedOn(Connection conn, int orderID) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String currentPlacedOn = "";

        try {
            String query = "SELECT placed_on FROM orders WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderID);

            rs = stmt.executeQuery();
            if (rs.next()) {
                currentPlacedOn = rs.getString("placed_on");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving current placed on date: " + e.getMessage());
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
        return currentPlacedOn;
    }

    public static String getCurrentPaymentStatus(Connection conn, int orderID) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String currentPaymentStatus = "";

        try {
            String query = "SELECT payment_status FROM orders WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, orderID);

            rs = stmt.executeQuery();
            if (rs.next()) {
                currentPaymentStatus = rs.getString("payment_status");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving current payment status: " + e.getMessage());
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
        return currentPaymentStatus;
    }

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Nhập ID đơn hàng cần chỉnh sửa: ");
            int orderID = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống sau khi nhập số            

            System.out.println("Nhập giá trị mới của user_id (Nhấn Enter để giữ nguyên giá trị cũ): ");
            String newUserIDInput = scanner.nextLine();
            int newUserID = (newUserIDInput.isEmpty()) ? getCurrentUserId(conn, orderID) : Integer.parseInt(newUserIDInput);

            boolean isUserIdValid = isUserIdExists(conn, newUserID);
            if (!isUserIdValid) {
                System.out.println("User ID không tồn tại. Vui lòng nhập lại User ID hợp lệ.");
                return;
            }

            // Nhập giá trị mới của total_price hoặc giữ nguyên giá trị cũ nếu không nhập
            System.out.println("Nhập giá trị mới của total_price (Nhấn Enter để giữ nguyên giá trị cũ): ");
            String newTotalPriceInput = scanner.nextLine();
            double newTotalPrice = (newTotalPriceInput.isEmpty()) ? getCurrentTotalPrice(conn, orderID) : Double.parseDouble(newTotalPriceInput);

            // Nhập số lượng sản phẩm mới hoặc giữ nguyên giá trị cũ nếu không nhập
            System.out.println("Nhập số lượng sản phẩm mới (Nhấn Enter để giữ nguyên giá trị cũ): ");
            String newTotalProductsInput = scanner.nextLine();
            String newTotalProducts = newTotalProductsInput.isEmpty() ? getCurrentTotalProducts(conn, orderID) : newTotalProductsInput;

            // Nhập ngày đặt hàng mới hoặc giữ nguyên giá trị cũ nếu không nhập
            System.out.println("Nhập ngày đặt hàng mới (Nhấn Enter để giữ nguyên giá trị cũ - yyyy-MM-dd): ");
            String newPlacedOnInput = scanner.nextLine();
            String newPlacedOn = newPlacedOnInput.isEmpty() ? getCurrentPlacedOn(conn, orderID) : newPlacedOnInput;

            // Nhập trạng thái thanh toán mới hoặc giữ nguyên giá trị cũ nếu không nhập
            System.out.println("Nhập trạng thái thanh toán mới (Nhấn Enter để giữ nguyên giá trị cũ): ");
            String newPaymentStatusInput = scanner.nextLine();
            String newPaymentStatus = newPaymentStatusInput.isEmpty() ? getCurrentPaymentStatus(conn, orderID) : newPaymentStatusInput;

            // Cập nhật thông tin đơn hàng
            boolean isUpdated = updateOrderInfo(conn, orderID, newUserID, newTotalPrice, newTotalProducts, newPlacedOn, newPaymentStatus);

            if (isUpdated) {
                System.out.println("Thông tin đơn hàng đã được cập nhật thành công!");
                // Thực hiện các hành động sau khi cập nhật thông tin thành công
            } else {
                System.out.println("Cập nhật thông tin đơn hàng không thành công. Vui lòng thử lại sau!");
                // Hiển thị thông báo lỗi hoặc xử lý khi cập nhật thông tin không thành công
            }

        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }
    }
}

