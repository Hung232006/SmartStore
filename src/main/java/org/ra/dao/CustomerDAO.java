package org.ra.dao;

import org.ra.config.DBConnection;
import org.ra.model.entity.Customer;
import org.ra.model.entity.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements ICustomerDAO {


    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers";
        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getInt("id"));
                    customer.setName(rs.getString("name"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPassword(rs.getString("password"));
                    customer.setRole(UserRole.valueOf(rs.getString("role")));
                    customer.setAddress(rs.getString("address"));
                    customers.add(customer);

                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    @Override
    public Customer findById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();

                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setRole(UserRole.valueOf(rs.getString("role")));
                customer.setAddress(rs.getString("address"));

                return customer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    // thêm khách hàng
    @Override
    public boolean addCustomer(Customer customer) {
        String sql = """
                INSERT INTO Customers (name, phone, email, password, role, address)
                VALUES (?, ?, ?,?, ?::user_role, ?)
                """;
        try(
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ){
            ps.setString(1,customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPassword());
            ps.setString(5, customer.getRole().name());
            ps.setString(6, customer.getAddress());
            int result = ps.executeUpdate();
            return (result > 0);
        } catch (SQLException e) {
                e.printStackTrace();

        }
        return false;
    }
    // hàm kiểm tra trùng mail
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM customers WHERE email = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // có dữ liệu -> email đã tồn tại

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String sql = """
    UPDATE customers
    SET name = ?, phone = ?, email = ?, password = ?,
        role = ?::user_role,
        address = ?
    WHERE id = ?
    """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPassword());
            ps.setString(5, customer.getRole().name());
            ps.setString(6, customer.getAddress());
            ps.setInt(7, customer.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteCustomer(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    // show toàn bộ khách hàng
    @Override
    public boolean showCustomer(int id){
        String sql = " SELECT * FROM Customers WHERE id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Tên: " + rs.getString("name"));
                System.out.println("Số Điện Thoại: " + rs.getString("Phone"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Role: " + rs.getString("role"));
                System.out.println("Mật khẩu: " + rs.getString("password"));

            } else {
                System.out.println("Không tìm thấy sản phẩm!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
}

