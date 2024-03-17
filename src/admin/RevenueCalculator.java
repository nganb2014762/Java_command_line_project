package admin;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.sql.CallableStatement;

public class RevenueCalculator {

    public static void main(String[] args) {
        Connection conn = Connected.getConnection();

        if (conn != null) {
            Scanner scanner = new Scanner(System.in);
            boolean validDate = false;

            do {
                System.out.println("---------------");
                System.out.println("1. Tính doanh thu theo ngày");
                System.out.println("2. Tính doanh thu theo tháng");
                System.out.println("0 Trở về");
                System.out.println("---------------");
                System.out.println("Nhập lựa chọn của bạn: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    scanner.nextLine(); // Đọc bỏ dòng trống sau khi nhập số
                    System.out.println("Nhập ngày tháng năm (yyyy-MM-dd): ");
                    String inputDate = scanner.nextLine();

                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = sdf.parse(inputDate);
                        calculateRevenueForDate(conn, date);
                        validDate = true;
                    } catch (Exception e) {
                        System.out.println("Định dạng ngày không hợp lệ! Vui lòng nhập lại.");
                    }
                } else if (choice == 2) {
                    System.out.println("Nhập tháng (1-12): ");
                    int month = scanner.nextInt();

                    System.out.println("Nhập năm: ");
                    int year = scanner.nextInt();

                    if (isValidMonth(month) && isValidYear(year)) {
                        calculateRevenueForMonth(conn, month, year);
                        validDate = true;
                    } else {
                        System.out.println("Tháng hoặc năm không hợp lệ! Vui lòng nhập lại.");
                    }
                } else if (choice == 0) {
                    validDate = false;
                    break;
                } else {
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
                }
            } while (!validDate);

        } else {
            System.out.println("Không thể kết nối đến CSDL.");
        }

    }

    public static void calculateRevenueForDate(Connection conn, Date date) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(date);

            String query = "{ CALL CalculateDailyRevenue(?) }";
            stmt = conn.prepareCall(query);
            stmt.setString(1, formattedDate);

            rs = stmt.executeQuery();

            if (rs.next()) {
                double totalRevenue = rs.getDouble("daily_revenue");
                System.out.println("Doanh thu ngày " + formattedDate + ": " + totalRevenue);
                RevenueCalculator.main(new String[0]);
            } else {
                System.out.println("Không có dữ liệu doanh thu cho ngày " + formattedDate);
            }
        } catch (SQLException e) {
            System.out.println("Tính toán doanh thu thất bại! Lỗi: " + e.getMessage());
        } finally {
            // Đóng tất cả các kết nối và tài nguyên
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

    public static void calculateRevenueForMonth(Connection conn, int month, int year) {
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "{ CALL CalculateMonthlyRevenue(?, ?) }";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, month);
            stmt.setInt(2, year);

            rs = stmt.executeQuery();

            if (rs.next()) {
                double totalRevenue = rs.getDouble("monthly_revenue");
                System.out.println("Doanh thu tháng " + month + "/" + year + ": " + totalRevenue);
                RevenueCalculator.main(new String[0]);
            } else {
                System.out.println("Không có dữ liệu doanh thu cho tháng " + month + "/" + year);
            }
        } catch (SQLException e) {
            System.out.println("Tính toán doanh thu thất bại! Lỗi: " + e.getMessage());
        } finally {
            // Đóng tất cả các kết nối và tài nguyên
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

    public static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    public static boolean isValidYear(int year) {
        return year >= 1900; // Giả sử năm không nhỏ hơn 1900
    }

}
