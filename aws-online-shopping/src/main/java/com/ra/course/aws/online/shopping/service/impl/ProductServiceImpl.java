package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.exceptions.ProductNotFoundException;
import com.ra.course.aws.online.shopping.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private transient final ProductDao productDao;

    public ProductServiceImpl(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Long save(final Product newProduct) {
        return productDao.save(newProduct);
    }

    @Override
    public boolean update(final Product productToUpdate) {
        Optional.ofNullable(productDao.findById(productToUpdate.getId()))
                .ifPresentOrElse(product -> productDao.update(productToUpdate), () -> {
                    throw new ProductNotFoundException("Product with id=" + productToUpdate.getId() + " not found");
                });
        return true;
    }

    @Override
    public boolean remove(final Long productId) {
        Optional.ofNullable(productDao.findById(productId))
                .ifPresentOrElse(product -> productDao.remove(productId), () -> {
                    throw new ProductNotFoundException("Product with id = " + productId + " not found.");
                });
        return true;
    }

    @Override
    public Product findByID(final Long productID) {
        return productDao.findById(productID);
    }
}
