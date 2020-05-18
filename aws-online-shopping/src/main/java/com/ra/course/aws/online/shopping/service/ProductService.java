package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    Long save(Product product) throws SQLException;

    void update(Product product);

    void remove(Long productId);

    Product findByID(Long productID);

    List<Product> searchByName(String productName);

    List<Product> searchByCategory(ProductCategory productCategory);

    List<Product> getAll();

    void addProductReview(Product product);

    void addProductRating(Product product, int rating);

}


