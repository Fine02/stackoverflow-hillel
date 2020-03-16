package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.product.Product;


public interface ProductDao {
    Product searchProductById(Long ID);
    void updateProduct(Product product);
}
