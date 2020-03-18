package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.entity.product.Product;
import java.util.List;

public interface ProductService {

    Long save(Product product);

    void update(Product product);

    void remove(Long productId);

    Product findByID(Long productID);

    List<Product> searchByName(String productName);

    List<Product> searchByCategory(ProductCategory productCategory);

    List<Product> getAll();

    void addProductReview(Product product);

    void addProductRating(Product product, int rating);

}


