package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CatalogService {

    boolean updateCatalogWithModifiedProduct(Long productId);

    boolean updateCatalogWithNewProduct(Long productId);

    boolean removeProductFromCatalog(Long productId);

    List<Product> searchProductsByName(String productName);

    List<Product> searchProductsByCategory(ProductCategory productCategory);

    List<Product> getAll();
}
