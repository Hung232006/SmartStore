package org.ra.presentation;

import org.ra.model.entity.Product;

import java.util.Scanner;

public class MainMenu {

    private final Scanner sc = new Scanner(System.in);

    public void menu() {
        int choice;

        do {
            System.out.println("\n=================================");
            System.out.println("      HỆ THỐNG QUẢN LÝ SHOP");
            System.out.println("=================================");
            System.out.println("1. Quản lý điện thoại");
            System.out.println("2. Quản lý khách hàng");
            System.out.println("3. Quản lý thông tin mua bán");
            System.out.println("4. Đăng xuất");
            System.out.println("=================================");
            System.out.print("Nhập lựa chọn: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Chuyển đến menu quản lý điện thoại...");
                     new ProductMenu().menu();
                    break;

                case 2:
                    System.out.println("Chuyển đến menu quản lý khách hàng...");
                     new CustomerMenu().menu();
                    break;

                case 3:
                    System.out.println("Chuyển đến menu quản lý thông tin mua bán...");
                     new InvoiceMenu().menu();
                    break;

                case 4:
                    System.out.println("Đăng xuất thành công!");
                    new LoginMenu().menu();
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }

        } while (choice != 4);
    }
}
