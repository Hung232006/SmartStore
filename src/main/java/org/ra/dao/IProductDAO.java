package org.ra.dao;


import org.ra.model.entity.Product;
import java.util.List;

public interface IProductDAO {

    List<Product> getAllProducts();

    Product findById(int id);

    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(int id);
    boolean showById(int id);
}
