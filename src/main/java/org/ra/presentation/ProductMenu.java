package org.ra.presentation;

import org.ra.business.ProductBUS;
import org.ra.dao.ProductDAO;
import org.ra.model.entity.Product;

import java.util.List;
import java.util.Scanner;

public class ProductMenu {

    private final ProductBUS productBUS = new ProductBUS();
    private final Scanner sc = new Scanner(System.in);

    public void menu() {
        int choice;

        do {
            System.out.println("\n===== QUẢN LÝ SẢN PHẨM =====");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Hiển thị danh sách");
            System.out.println("3. Tìm sản phẩm");
            System.out.println("4. Cập nhật sản phẩm");
            System.out.println("5. Xóa sản phẩm");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    showProducts();
                    break;
                case 3:
                    findProduct();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 0:
                    System.out.println("Quay lại menu chính...");
                    new MainMenu().menu();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }

        } while (choice != 0);
    }

    private void addProduct() {
        System.out.print("Tên sản phẩm: ");
        String name = sc.nextLine();

        System.out.print("Nhãn hiểu: ");
        String brand = sc.nextLine();

        System.out.print("Giá: ");
        double price = Double.parseDouble(sc.nextLine());

        System.out.print("Số lượng: ");
        int stock = Integer.parseInt(sc.nextLine());

        Product product = new Product();
        product.setName(name);
        product.setBrand(brand);
        product.setPrice(price);
        product.setStock(stock);

        if (productBUS.addProduct(product)) {
            System.out.println("Thêm thành công!");
        } else {
            System.out.println("Thêm thất bại!");
        }
    }

    private void showProducts() {
        List<Product> products = productBUS.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống!");
            return;
        }

        System.out.printf("%-5s %-25s %-15s %-15s %-10s%n",
                "ID", "Tên", "Hãng", "Giá", "Tồn kho");

        for (Product p : products) {
            System.out.printf("%-5d %-25s %-15s %-15.2f %-10d%n",
                    p.getId(),
                    p.getName(),
                    p.getBrand(),
                    p.getPrice(),
                    p.getStock());
        }
    }

    private void findProduct() {
        Scanner sc = new Scanner(System.in);

        int id;

        while (true) {
            try {
                System.out.print("Nhập ID sản phẩm: ");
                id = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("ID phải là số, vui lòng nhập lại!");
            }
        }
        ProductDAO productDAO = new ProductDAO();

        Product product = productDAO.findById(id);

        if (product == null) {
            System.out.println(
                    "ID sản phẩm không tồn tại, vui lòng kiểm tra lại");
            return;
        }
        System.out.println("Thông tin sản phẩm:");
        System.out.println(product);
    }

    private void updateProduct() {
        Scanner sc = new Scanner(System.in);
        ProductDAO productDAO = new ProductDAO();

        System.out.print("Nhập ID sản phẩm: ");
        int id = Integer.parseInt(sc.nextLine());

        Product product = productDAO.findById(id);

        if (product == null) {
            System.out.println("Không tìm thấy sản phẩm!");
            return;
        }



        System.out.println("1. Sửa tên");
        System.out.println("2. Sửa hãng");
        System.out.println("3. Sửa giá");
        System.out.println("4. Sửa tồn kho");

        System.out.print("Chọn: ");
        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1:
                System.out.print("Tên mới: ");
                product.setName(sc.nextLine());
                break;

            case 2:
                System.out.print("Hãng mới: ");
                product.setBrand(sc.nextLine());
                break;

            case 3:
                System.out.print("Giá mới: ");
                product.setPrice(Double.parseDouble(sc.nextLine()));
                break;

            case 4:
                System.out.print("Tồn kho mới: ");
                product.setStock(Integer.parseInt(sc.nextLine()));
                break;

            default:
                System.out.println("Lựa chọn không hợp lệ!");
                return;
        }

        if (productDAO.updateProduct(product)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    private void deleteProduct() {
        Scanner sc = new Scanner(System.in);
        ProductDAO productDAO = new ProductDAO();

        System.out.print("Nhập ID sản phẩm cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());

        Product product = productDAO.findById(id);

        if (product == null) {
            System.out.println("Không tìm thấy sản phẩm!");
            return;
        }

        System.out.println("Thông tin sản phẩm:");
        System.out.println(product);

        System.out.print("Bạn có chắc chắn muốn xóa (Y/N)? ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {

            if (productDAO.deleteProduct(id)) {
                System.out.println("Xóa thành công!");
            } else {
                System.out.println("Xóa thất bại!");
            }

        } else {
            System.out.println("Đã hủy thao tác xóa.");
        }
    }
}