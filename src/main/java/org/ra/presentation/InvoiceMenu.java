package org.ra.presentation;

import org.ra.business.InvoiceBUS;
import org.ra.model.entity.Invoice;
import org.ra.model.entity.InvoiceDetail;

import java.util.List;
import java.util.Scanner;

public class InvoiceMenu {

    private final Scanner sc = new Scanner(System.in);
    private final InvoiceBUS invoiceBUS = new InvoiceBUS();

    public void menu() {

        while (true) {

            System.out.println("\n========== QUẢN LÝ HÓA ĐƠN ==========");
            System.out.println("1. Tạo hóa đơn bán hàng");
            System.out.println("2. Xem danh sách hóa đơn");
            System.out.println("3. Tìm kiếm hóa đơn");
            System.out.println("4. Thống kê doanh thu");
            System.out.println("5. Quay lại");
            System.out.print("Chọn chức năng: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
                continue;
            }

            switch (choice) {

                case 1:
                    createInvoice();
                    break;

                case 2:
                    showAllInvoices();
                    break;

                case 3:
                    searchInvoice();
                    break;

                case 4:
                    revenueStatistics();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // Tạo hóa đơn

    private void createInvoice() {

        try {

            Invoice invoice = new Invoice();
            InvoiceDetail detail = new InvoiceDetail();

            System.out.print("Nhập ID khách hàng: ");
            invoice.setCustomerId(Integer.parseInt(sc.nextLine()));

            System.out.print("Nhập ID sản phẩm: ");
            detail.setProductId(Integer.parseInt(sc.nextLine()));

            System.out.print("Nhập số lượng mua: ");
            detail.setQuantity(Integer.parseInt(sc.nextLine()));

            boolean result = invoiceBUS.createInvoice(invoice, detail);

            if (result) {
                System.out.println("Tạo hóa đơn thành công!");
            } else {
                System.out.println("Tạo hóa đơn thất bại!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Dữ liệu nhập không hợp lệ!");
        }
    }

    //  Hiển thị hóa đơn

    private void showAllInvoices() {

        List<Invoice> invoices = invoiceBUS.showAllInvoices();

        if (invoices.isEmpty()) {
            System.out.println("Chưa có hóa đơn nào!");
            return;
        }

        System.out.printf("%-8s %-20s %-35s %-20s%n",
                "ID", "Customer ID", "Ngày tạo", "Tổng tiền");

        for (Invoice invoice : invoices) {

            System.out.printf("%-8d %-20d %-35s %-20.2f%n",
                    invoice.getId(),
                    invoice.getCustomerId(),
                    invoice.getCreatedAt(),
                    invoice.getTotalAmount());
        }
    }

    //Tìm kiếm hóa đơn

    private void searchInvoice() {

        try {

            System.out.print("Nhập ID hóa đơn: ");
            int id = Integer.parseInt(sc.nextLine());

            invoiceBUS.searchInvoice(id);

        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
        }
    }

    // Thống kê doanh thu

    private void revenueStatistics() {
        invoiceBUS.revenueStatistics();
    }

}