package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductReview;

import java.util.Objects;

public class ProductServiceImpl implements ProductService {
    private final transient ProductDao productDao;

    public ProductServiceImpl(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void addProductReview(final Product product) {
        if(product != null){
            final Product productFromDao = productDao.searchProductById(product.getProductID());
            Objects.requireNonNull(productFromDao);
            productFromDao.setProductReview(product.getProductReview());
            productDao.updateProduct(productFromDao);
        }

    }

    @Override
    public void addProductRating(final Product product, final int rating) {
        if(product != null){
            final Product productFromDao = productDao.searchProductById(product.getProductID());
            Objects.requireNonNull(productFromDao);
            final ProductReview productReview = productFromDao.getProductReview() == null ? new ProductReview(productFromDao.getProductID(), rating, "") : productFromDao.getProductReview();
            productReview.setRating(rating);
            productFromDao.setProductReview(productReview);
            productDao.updateProduct(productFromDao);
        }
    }
}
