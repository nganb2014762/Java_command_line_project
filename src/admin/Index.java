/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author MSI
 */
public class Index {

    public static void main(String args[]) {
        Connection conn = null;
        try {
            conn = Connected.getConnection();
            Scanner l = new Scanner(System.in);
            int x, y, z, d = 1;

            do {
                System.out.println("---------------");
                System.out.println("1 Quản lý danh mục sản phẩm");
                System.out.println("2 Quản lý sản phẩm");
                System.out.println("3 Quản lý người dùng");
                System.out.println("4 Quản lý đơn hàng");
                System.out.println("5 Quản lý doanh thu");
                System.out.println("0 Thoát");
                System.out.println("---------------");
                System.out.println("Nhập lựa chọn của bạn: ");
                x = l.nextInt();
                switch (x) {
                    case 0:
                        System.out.println("Bạn đã thoát");
                        conn.close();
                        d = 0;
                        break;
                    case 1:
                        int c = 1;
                        do {
                            System.out.println("---------------");
                            System.out.println("1 Thêm danh mục");
                            System.out.println("2 Xem danh mục");
                            System.out.println("3 Sửa danh mục");
                            System.out.println("4 Xóa danh mục");
                            System.out.println("0 Trở về");
                            System.out.println("---------------");
                            System.out.println("Nhập lựa chọn của bạn: ");
                            x = l.nextInt();

                            switch (x) {
                                case 0:
                                    System.out.println("Bạn đã thoát");
                                    c = 0;
                                    break;
                                case 1:
                                    AddCategory.main(new String[0]);
                                    break;
                                case 2:
                                    ListCategory.main(new String[0]);
                                    break;
                                case 3:
                                    EditCategory.main(new String[0]);
                                    break;
                                case 4:
                                    DeleteCategory.main(new String[0]);
                                    break;
                            }
                        } while (c == 1);
                        break;
                    case 2:
                        int p = 1;
                        do {
                            System.out.println("---------------");
                            System.out.println("1 Thêm sản phẩm");
                            System.out.println("2 Xem sản phẩm");
                            System.out.println("3 Sửa sản phẩm");
                            System.out.println("4 Xóa sản phẩm");
                            System.out.println("0 Trở về");
                            System.out.println("---------------");
                            System.out.println("Nhập lựa chọn của bạn: ");
                            y = l.nextInt();
                            switch (y) {
                                case 0:
                                    System.out.println("Bạn đã thoát");
                                    p = 0;
                                    break;
                                case 1:
                                    AddProduct.main(new String[0]);
                                    break;
                                case 2:
                                    ListProduct.main(new String[0]);
                                    break;
                                case 3:
                                    EditProduct.main(new String[0]);
                                    break;
                                case 4:
                                    DeleteProduct.main(new String[0]);
                                    break;
                            }
                        } while (p == 1);
                        break;
                    case 3:
                        int u = 1;
                        do {
                            System.out.println("---------------");
                            System.out.println("1. Thêm thông tin cá nhân");
                            System.out.println("2. Sửa thông tin cá nhân");
                            System.out.println("3. Xem danh sách tài khoản");
                            System.out.println("4. Xóa tài khoản");
                            System.out.println("5. Tìm tài khoản");
                            System.out.println("0 Trở về");
                            System.out.println("---------------");
                            System.out.println("Nhập lựa chọn của bạn: ");
                            z = l.nextInt();
                            switch (z) {
                                case 0:
                                    System.out.println("Bạn đã thoát");
                                    u = 0;
                                    break;
                                case 1:
                                    Register.main(new String[0]);
                                    break;
                                case 2:
                                    EditProfile.main(new String[0]);
                                    break;
                                case 3:
                                    ListUser.main(new String[0]);
                                    break;
                                case 4:
                                    DeleteUser.main(new String[0]);
                                    break;
                                case 5:
                                    FindUser.main(new String[0]);
                                    break;
                            }
                        } while (u == 1);
                        break;
                    case 4:
                        int o = 1;
                        do {
                            System.out.println("---------------");
                            System.out.println("1. Xem danh sách đơn hàng ");
                            System.out.println("2. Tìm đơn hàng ");
                            System.out.println("3. Sửa thông tin đơn hàng ");
                            System.out.println("4. Xóa đơn hàng ");
                            System.out.println("0 Trở về");
                            System.out.println("---------------");
                            System.out.println("Nhập lựa chọn của bạn: ");
                            z = l.nextInt();
                            switch (z) {
                                case 0:
                                    System.out.println("Bạn đã thoát");
                                    o = 0;
                                    break;
                                case 1:
                                    ListOrder.main(new String[0]);
                                    break;
                                case 2:
                                    FindOrder.main(new String[0]);
                                    break;
                                case 3:
                                    EditOrder.main(new String[0]);
                                    break;
                                case 4:
                                    DeleteOrder.main(new String[0]);
                                    break;
                            }
                        } while (o == 1);
                        break;
                    case 5:
                        RevenueCalculator.main(new String[0]);
                        break;
                }
            } while (d == 1);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
