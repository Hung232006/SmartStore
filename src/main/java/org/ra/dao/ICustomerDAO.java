package org.ra.dao;

import org.ra.model.entity.Customer;

import java.util.List;

public interface ICustomerDAO {
    List<Customer> getAllCustomers();

    Customer findById(int id);

    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(int id);

    boolean showCustomer(int id);

    boolean existsByEmail(String email);
}
