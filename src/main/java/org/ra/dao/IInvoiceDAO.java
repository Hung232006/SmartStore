package org.ra.dao;


import org.ra.model.entity.Invoice;
import org.ra.model.entity.InvoiceDetail;

import java.util.List;

public interface IInvoiceDAO {

    boolean createInvoice(int customerId, int productId, int quantity);

    List<Invoice> showAllInvoices();

    void searchInvoice(int id);

    double getTotalRevenue();

    boolean createInvoice(Invoice invoice, InvoiceDetail detail);
}