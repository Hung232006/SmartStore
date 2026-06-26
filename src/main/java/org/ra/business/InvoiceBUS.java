package org.ra.business;

import org.ra.dao.CustomerDAO;
import org.ra.dao.InvoiceDAO;
import org.ra.dao.ProductDAO;
import org.ra.model.entity.Customer;
import org.ra.model.entity.Invoice;
import org.ra.model.entity.InvoiceDetail;
import org.ra.model.entity.Product;

import java.util.List;

public class InvoiceBUS {

    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final ProductDAO productDAO = new ProductDAO();

    // ====================== Tạo hóa đơn ======================

    public boolean createInvoice(Invoice invoice, InvoiceDetail detail) {

        // Kiểm tra khách hàng
        Customer customer = customerDAO.findById(invoice.getCustomerId());

        if (customer == null) {
            System.out.println("Khách hàng không tồn tại!");
            return false;
        }

        // Kiểm tra sản phẩm
        Product product = productDAO.findById(detail.getProductId());

        if (product == null) {
            System.out.println("Sản phẩm không tồn tại!");
            return false;
        }

        // Kiểm tra số lượng
        if (detail.getQuantity() <= 0) {
            System.out.println("Số lượng phải lớn hơn 0!");
            return false;
        }

        // Kiểm tra tồn kho
        if (product.getStock() < detail.getQuantity()) {
            System.out.println("Không đủ số lượng trong kho!");
            return false;
        }

        // Tính tổng tiền
        double totalAmount = product.getPrice() * detail.getQuantity();

        invoice.setTotalAmount(totalAmount);
        detail.setUnitPrice(product.getPrice());

        // Gọi DAO lưu dữ liệu
        return invoiceDAO.createInvoice(invoice, detail);
    }

    // ====================== Hiển thị tất cả hóa đơn ======================

    public List<Invoice> showAllInvoices() {
        return invoiceDAO.showAllInvoices();
    }

    // ====================== Tìm kiếm hóa đơn ======================

    public void searchInvoice(int id) {
        invoiceDAO.searchInvoice(id);
    }

    // ====================== Thống kê doanh thu ======================

    public void revenueStatistics() {

        double totalRevenue = invoiceDAO.getTotalRevenue();

        System.out.println("========================================");
        System.out.printf("TỔNG DOANH THU: %,.2f VNĐ%n", totalRevenue);
        System.out.println("========================================");
    }

}