package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.product.Product;

import java.util.List;

public interface Dao<T> {

    Long save(T t);

    boolean update(T t);

    boolean remove(Long id);

    Product findById(Long id);

}
