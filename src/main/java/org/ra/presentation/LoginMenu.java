package org.ra.presentation;

import org.ra.business.CustomerBUS;
import org.ra.model.entity.Customer;
import org.ra.model.entity.UserRole;

import java.util.Scanner;


public class LoginMenu {

    private final Scanner sc = new Scanner(System.in);
    private final CustomerBUS customerBUS = new CustomerBUS();

    public void menu() {

        int choice;

        do {

            System.out.println("\n========== MENU ĐĂNG NHẬP ==========");
            System.out.println("[1] Đăng nhập");
            System.out.println("[2] Đăng ký");
            System.out.println("[3] Thoát");
            System.out.print("Chọn chức năng: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        login();
                        break;

                    case 2:
                        register();
                        break;

                    case 3:
                        System.out.println("Đã thoát chương trình!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
            }

        } while (true);
    }

    private void login() {

        while (true) {

            String email;

            while (true) {
                System.out.print("Email: ");
                email = sc.nextLine();

                if (!email.trim().isEmpty()) {
                    break;
                }

                System.out.println("Không được để trống!");
            }

            String password;

            while (true) {
                System.out.print("Password: ");
                password = sc.nextLine();

                if (!password.trim().isEmpty()) {
                    break;
                }

                System.out.println("Không được để trống!");
            }

            Customer customer = customerBUS.login(email, password);


            if (customer == null) {
                System.out.println("Tài khoản hoặc mật khẩu không chính xác!");
            } else if (customer.getRole() != UserRole.ADMIN) {
                System.out.println("Tài khoản khách hàng không có quyền truy cập!");
            } else {
                System.out.println("Đăng nhập thành công!");
                new MainMenu().menu();
                return;
            }
        }
    }

    // Tạm thời để trống, sau này viết đăng ký
    private void register() {
        System.out.println("Chức năng đăng ký đang phát triển...");
    }
}