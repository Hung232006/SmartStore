package org.ra.dao;

import org.ra.config.DBConnection;
import org.ra.model.entity.Invoice;
import org.ra.model.entity.InvoiceDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO implements IInvoiceDAO {

    @Override
    public boolean createInvoice(Invoice invoice, InvoiceDetail detail) {

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Thêm hóa đơn
            String sqlInvoice = "INSERT INTO invoices(customer_id, total_amount) VALUES (?, ?)";

            PreparedStatement psInvoice = conn.prepareStatement(
                    sqlInvoice,
                    Statement.RETURN_GENERATED_KEYS
            );

            psInvoice.setInt(1, invoice.getCustomerId());
            psInvoice.setDouble(2, invoice.getTotalAmount());

            psInvoice.executeUpdate();

            ResultSet rs = psInvoice.getGeneratedKeys();

            int invoiceId = 0;
            if (rs.next()) {
                invoiceId = rs.getInt(1);
            }

            // Thêm chi tiết hóa đơn
            String sqlDetail = "INSERT INTO invoice_details(invoice_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

            PreparedStatement psDetail = conn.prepareStatement(sqlDetail);

            psDetail.setInt(1, invoiceId);
            psDetail.setInt(2, detail.getProductId());
            psDetail.setInt(3, detail.getQuantity());
            psDetail.setDouble(4, detail.getUnitPrice());

            psDetail.executeUpdate();

            // Cập nhật tồn kho
            String sqlUpdate = "UPDATE products SET stock = stock - ? WHERE id = ?";

            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);

            psUpdate.setInt(1, detail.getQuantity());
            psUpdate.setInt(2, detail.getProductId());

            psUpdate.executeUpdate();

            conn.commit();

            return true;

        } catch (Exception e) {

            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            return false;

        } finally {

            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean createInvoice(int customerId, int productId, int quantity) {
        return false;
    }

    @Override
    public List<Invoice> showAllInvoices() {

        List<Invoice> invoices = new ArrayList<>();

        String sql = "SELECT * FROM invoices ORDER BY id";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {

                Invoice invoice = new Invoice();

                invoice.setId(rs.getInt("id"));
                invoice.setCustomerId(rs.getInt("customer_id"));
                invoice.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                invoice.setTotalAmount(rs.getDouble("total_amount"));

                invoices.add(invoice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoices;
    }

    @Override
    public void searchInvoice(int id) {

        String sql = "SELECT * FROM invoices WHERE id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("========== THÔNG TIN HÓA ĐƠN ==========");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("ID Khách hàng: " + rs.getInt("customer_id"));
                System.out.println("Ngày tạo: " + rs.getTimestamp("created_at"));
                System.out.println("Tổng tiền: " + rs.getDouble("total_amount"));

            } else {
                System.out.println("Không tìm thấy hóa đơn!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double revenueStatistics() {

        String sql = "SELECT SUM(total_amount) AS revenue FROM invoices";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {

            if (rs.next()) {
                double revenue = rs.getDouble("revenue");

                System.out.println("========== THỐNG KÊ DOANH THU ==========");
                System.out.printf("Tổng doanh thu: %,.2f VNĐ%n", revenue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}