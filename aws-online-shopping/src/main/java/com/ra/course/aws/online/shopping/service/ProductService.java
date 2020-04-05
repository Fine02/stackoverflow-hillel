package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.product.Product;

public interface ProductService {

    Long save(Product product);

    boolean update(Product product);

    boolean remove(Long productId);

    Product findByID(Long productID);

}

