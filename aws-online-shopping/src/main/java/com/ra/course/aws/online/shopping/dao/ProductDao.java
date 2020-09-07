package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.product.Product;

import java.util.List;

public interface ProductDao {

    Long save(Product product);

    boolean update(Product product);

    boolean remove(Long productID);

    Product findById(Long ID);

    List<Product> getAll();

}
