package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.entity.product.ProductReview;
import com.ra.course.aws.online.shopping.service.ProductService;

import java.util.List;
import java.util.Objects;

public class ProductServiceImpl implements ProductService {

    private transient final ProductDao productDao;

    public ProductServiceImpl(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Long addNewProduct(final Product newProduct) {
        return productDao.addNewProduct(newProduct);
    }

    @Override
    public void updateProduct(final Product productToUpdate) {
        productDao.updateProduct(productToUpdate);
    }

    @Override
    public void removeProduct(final Long productId) {
        productDao.removeProduct(productId);
    }

    @Override
    public Product searchProductById(final Long productID) {
        return  productDao.searchProductById(productID);
    }

    @Override
    public List<Product> searchProductsByName(final String productName) {
        return productDao.searchProductsByName(productName);
    }

    @Override
    public List<Product> searchProductsByCategory(final ProductCategory productCategory) {
        return productDao.searchProductsByCategory(productCategory);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
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
