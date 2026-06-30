package org.ra.business;

import org.ra.dao.CustomerDAO;
import org.ra.dao.ICustomerDAO;
import org.ra.model.entity.Customer;
import org.ra.model.entity.Product;
import org.ra.util.Validation;

import java.util.List;


public class CustomerBUS {

        private  ICustomerDAO customerDAO =  new CustomerDAO();

    public List<Customer> searchByName(String keyword) {
        return customerDAO.getAllCustomers()
                .stream()
                .filter(p -> p.getName()
                        .toLowerCase()
                        .contains(keyword.toLowerCase()))
                .toList();
    }
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }
        // Thêm khách hàng
        public boolean addCustomer(Customer customer) {

            if (customer.getName() == null || customer.getName().trim().isEmpty()) {
                System.out.println("Tên khách hàng không được để trống!");
                return false;
            }

            return customerDAO.addCustomer(customer);
        }

        // Cập nhật khách hàng
        public boolean updateCustomer(Customer customer) {

            if (customer.getName().trim().isEmpty()) {
                System.out.println("Tên không được để trống!");
                return false;
            }

            return customerDAO.updateCustomer(customer);
        }

        // Xóa khách hàng
        public boolean deleteCustomer(int id) {

            Customer customer = customerDAO.findById(id);

            if (customer == null) {
                System.out.println("ID khách hàng không tồn tại!");
                return false;
            }

            return customerDAO.deleteCustomer(id);
        }
    public boolean existsByEmail(String email) {
        return customerDAO.existsByEmail(email);
    }

        /*// Tìm khách hàng theo ID
        public Customer findById(int id) {
            return customerDAO.findById(id);
        }*/
    // đăng nhập ccuar admin
        public Customer login(String email, String password) {

            Customer customer = customerDAO.login(email ,  password);

            if (customer == null) {
                return null;
            }

            if (Validation.checkPassword(password, customer.getPassword())) {
                return customer;
            }

            return null;
        }
}
