package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.exception.ObjectRequireNotBeNullException;

import java.util.Objects;

public class ProductServiceImpl implements ProductService {
    private final transient ProductDao productDao;

    public ProductServiceImpl(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void addProductReview(final Product product) {
        if(product == null){
            throw new ObjectRequireNotBeNullException("given product must not be Null!");
        }
        final Product productFromDao = productDao.searchProductById(product.getProductID());
        Objects.requireNonNull(productFromDao);
        productFromDao.setProductReview(product.getProductReview());
        productDao.updateProduct(productFromDao);
    }
}
