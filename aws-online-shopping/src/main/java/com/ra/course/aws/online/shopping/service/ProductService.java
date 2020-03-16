package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;

import java.util.List;

public interface ProductService {

    Long addNewProduct(Product product);

    void updateProduct(Product product);

    void removeProduct(Long productId);

    Product searchProductById(Long productID);

    List<Product> searchProductsByName(String productName);

    List<Product> searchProductsByCategory(ProductCategory productCategory);

    List<Product> getAllProducts();

    void addProductReview(Product product);

    void addProductRating(Product product, int rating);

}


