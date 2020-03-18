package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;

import java.util.List;

public interface ProductDao {

    Long save(Product product);

    void update(Product product);

    void remove(Long productID);

    Product findById(Long ID);

    List<Product> searchProductsByName(String productName);

    List<Product> searchProductsByCategory(ProductCategory productCategory);

    List<Product> getAll();
}
