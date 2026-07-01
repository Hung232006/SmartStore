package org.ra.presentation;
import org.mindrot.jbcrypt.BCrypt;
import org.ra.business.CustomerBUS;
import org.ra.business.ProductBUS;
import org.ra.dao.CustomerDAO;
import org.ra.dao.ICustomerDAO;
import org.ra.dao.ProductDAO;
import org.ra.model.entity.Customer;
import org.ra.model.entity.Product;
import org.ra.model.entity.UserRole;
import org.ra.util.Validation;

import java.util.List;
import java.util.Scanner;

public class CustomerMenu {
    private final Scanner sc = new Scanner(System.in);
    private final CustomerBUS customerBUS = new CustomerBUS();
    private final ProductBUS productBUS = new ProductBUS();

    public void menu() {
        int choice;

        do {
            System.out.println("\n===== QUẢN LÝ KHÁCH HÀNG =====");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm mới khách hàng");
            System.out.println("3. Cập nhật thông tin");
            System.out.println("4. Xóa khách hàng");
            System.out.println("5. Quay lại");
            System.out.print("Chọn chức năng: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    showAllCustomers();
                    break;

                case 2:
                    addCustomer();
                    break;

                case 3:
                    updateCustomer();
                    break;

                case 4:
                    deleteCustomer();
                    break;

                case 5:
                    System.out.println("Quay lại menu chính...");
                    new MainMenu().menu();
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }

        } while (choice != 5);
    }

    private void showAllCustomers() {
        List<Customer> customers = customerBUS.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống!");
            return;
        }

        System.out.printf("%-5s %-20s %-15s %-25s %-30s %-12s %-20s%n",
                "ID", "Tên", "SĐT", "Email", "Password", "Role", "Address");

        for (Customer p : customers) {
            System.out.printf("%-5d %-20s %-15s %-25s %-30s %-12s %-20s%n",
                    p.getId(),
                    p.getName(),
                    p.getPhone(),
                    p.getEmail(),
                    p.getPassword(),
                    p.getRole(),
                    p.getAddress());
        }
    }

    private void addCustomer() {
        CustomerBUS customerBUS = new CustomerBUS();
        Customer customer = new Customer();
        System.out.print("Tên người mua: ");
        String name = sc.nextLine();

        System.out.print("số điện thoại: ");
        String phone = sc.nextLine();

        String email;

        while (true) {
            System.out.print("Địa chỉ email: ");
            email = sc.nextLine();

            if (customerBUS.existsByEmail(email)) {
                System.out.println("Email đã tồn tại, vui lòng nhập lại!");
            } else {
                break;
            }
        }

        System.out.print("nhập password: ");
        String password = sc.nextLine();
        String hashedPassword = Validation.hashPassword(password);
        customer.setPassword(hashedPassword);


        System.out.print("Đỉa chỉ nhà: ");
        String address = sc.nextLine();


        customer.setName(name);
        customer.setPhone(String.valueOf(phone));
        customer.setEmail(email);
        customer.setAddress(String.valueOf(address));
        customer.setRole(UserRole.CUSTOMER);

        if (customerBUS.addCustomer(customer)) {
            System.out.println("Thêm thành công!");
        } else {
            System.out.println("Thêm thất bại!");
        }
    }

    private void updateCustomer() {
        System.out.println("Cập nhật thông tin khách hàng...");
        Scanner sc = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAO();

        System.out.print("Nhập ID khách hàng: ");
        int id = Integer.parseInt(sc.nextLine());

        Customer customer = customerDAO.findById(id);

        if (customer == null) {
            System.out.println("ID khách hàng không tồn tại!");
            return;
        }

        System.out.println("===== Nhập thông tin mới =====");

        System.out.print("Tên: ");
        customer.setName(sc.nextLine());

        System.out.print("SĐT: ");
        customer.setPhone(sc.nextLine());

        System.out.print("Email: ");
        customer.setEmail(sc.nextLine());

        System.out.print("Password: ");
        String password = sc.nextLine();
        customer.setPassword(Validation.hashPassword(password));


        while (true) {
            System.out.print("Nhập role (ADMIN/CUSTOMER): ");
            String role = sc.nextLine().trim().toUpperCase();

            try {
                customer.setRole(UserRole.valueOf(role));
                break; // nhập đúng thì thoát vòng lặp
            } catch (IllegalArgumentException e) {
                System.out.println("Role không hợp lệ! Vui lòng nhập ADMIN hoặc CUSTOMER.");
            }
        }

        System.out.print("Địa chỉ: ");
        customer.setAddress(sc.nextLine());

        if (customerBUS.updateCustomer(customer)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    private void deleteCustomer() {
        CustomerDAO customerDAO = new CustomerDAO();
        System.out.print("Nhập ID khách hàng cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());

        Customer customer = customerDAO.findById(id);

        if (customer == null) {
            System.out.println("ID khách hàng không tồn tại!");
            return;
        }

        System.out.print("Bạn có chắc chắn muốn xóa? (Y/N): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {

            if (customerBUS.deleteCustomer(id)) {
                System.out.println("Xóa thành công!");
            } else {
                System.out.println("Xóa thất bại!");
            }

        } else {
            System.out.println("Đã hủy thao tác xóa.");
        }
    }
}
