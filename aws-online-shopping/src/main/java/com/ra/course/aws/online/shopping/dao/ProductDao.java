package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;

import java.util.List;

public interface ProductDao {

    Long addNewProduct(Product product);

    void updateProduct(Product product);

    void removeProduct(Long productID);

    Product searchProductById(Long ID);

    List<Product> searchProductsByName(String productName);

    List<Product> searchProductsByCategory(ProductCategory productCategory);

    List<Product> getAllProducts();
}
