package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.entity.product.ProductReview;
import com.ra.course.aws.online.shopping.service.ProductService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    private transient final ProductDao productDao;

    public ProductServiceImpl(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Long save(final Product newProduct) throws SQLException {

        return productDao.save(newProduct);
    }

    @Override
    public void update(final Product productToUpdate) {

        productDao.update(productToUpdate);
    }

    @Override
    public void remove(final Long productId) {

        productDao.remove(productId);
    }

    @Override
    public Product findByID(final Long productID) {
        return productDao.findById(productID);
    }

    @Override
    public List<Product> searchByName(final String productName) {
        return productDao.searchProductsByName(productName);
    }

    @Override
    public List<Product> searchByCategory(final ProductCategory productCategory) {
        return productDao.searchProductsByCategory(productCategory);
    }

    @Override
    public List<Product> getAll() {

        return productDao.getAll();
    }

    @Override
    public void addProductReview(final Product product) {
        if(product != null){
            final Product productFromDao = productDao.findById(product.getId());
            Objects.requireNonNull(productFromDao);
            productFromDao.setProductReview(product.getProductReview());
            productDao.update(productFromDao);
        }

    }

    @Override
    public void addProductRating(final Product product, final int rating) {
        if(product != null){
            final Product productFromDao = productDao.findById(product.getId());
            Objects.requireNonNull(productFromDao);
            final ProductReview productReview = productFromDao.getProductReview() == null ? new ProductReview(productFromDao.getId(), rating, "") : productFromDao.getProductReview();
            productReview.setRating(rating);
            productFromDao.setProductReview(productReview);
            productDao.update(productFromDao);
        }
    }
}
