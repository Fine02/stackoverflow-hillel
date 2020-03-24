package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.product.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {

    Long save(Product product);

    boolean update(Product product);

    boolean remove(Long productID);

    Product findById(Long ID);

    List<Product> getAll();

}
