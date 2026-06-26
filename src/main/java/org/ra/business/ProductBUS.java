package org.ra.business;

import org.ra.dao.IProductDAO;
import org.ra.dao.ProductDAO;
import org.ra.model.entity.Product;

import java.util.List;

public class ProductBUS {

    private IProductDAO productDAO = new ProductDAO();


    public List<Product> searchByName(String keyword) {
        return productDAO.getAllProducts()
                .stream()
                .filter(p -> p.getName()
                        .toLowerCase()
                        .contains(keyword.toLowerCase()))
                .toList();
    }


    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public List<Product> searchByBrand(String brand) {
        return productDAO.getAllProducts()
                .stream()
                .filter(p -> p.getBrand()
                        .toLowerCase()
                        .contains(brand.toLowerCase()))
                .toList();
    }

    public List<Product> searchByPrice(double min, double max) {
        return productDAO.getAllProducts()
                .stream()
                .filter(p -> p.getPrice() >= min
                        && p.getPrice() <= max)
                .toList();
    }


    public boolean addProduct(Product product) {

        if (product.getPrice() <= 0) {
            System.out.println("Giá phải lớn hơn 0");
            return false;
        }

        if (product.getStock() < 0) {
            System.out.println("Tồn kho không hợp lệ");
            return false;
        }

        return productDAO.addProduct(product);
    }
}
