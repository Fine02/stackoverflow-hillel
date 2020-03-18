package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.Catalog;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.exceptions.ProductNotFoundException;
import com.ra.course.aws.online.shopping.service.CatalogService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CatalogServiceImpl implements CatalogService {

    private transient Catalog catalog;
    private transient final ProductDao productDao;

    public CatalogServiceImpl(final ProductDao productDao) {
        this.productDao = productDao;
        createCatalog();
    }

    public Catalog getCatalog() {
        return catalog;
    }

    private void createCatalog() {
        catalog = new Catalog(
                LocalDateTime.now(),
                getProductNamesMap(),
                getProductCategoriesMap());
    }

    @Override
    public void updateCatalogWithNewProduct(final Long newProductId) {
        final Product newProduct = productDao.findById(newProductId);

        /*Putting new product into productNames map*/
        catalog.getProductNames().computeIfAbsent(newProduct.getName(),
                pr -> new ArrayList<>()).add(newProduct);

        /*Putting new product into productCategories map*/
        catalog.getProductCategories().computeIfAbsent(newProduct.getCategory().getName(),
                pr -> new ArrayList<>()).add(newProduct);

        catalog.setLastUpdated(LocalDateTime.now());
    }

    @Override
    public void updateCatalogWithModifiedProduct(final Long modifiedProductId) {
        removeProductFromCatalog(modifiedProductId);

        updateCatalogWithNewProduct(modifiedProductId);

        catalog.setLastUpdated(LocalDateTime.now());
    }

    @Override
    public void removeProductFromCatalog(final Long productId) {
        /*Finding product to remove by ID*/
        final Optional<Product> optProduct = catalog.getProductNames()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(product -> product.getId().equals(productId))
                .findAny();
        /*Removing product from maps*/
        if (optProduct.isPresent()) {
            final Product productToRemove = optProduct.get();
            catalog.getProductNames().get(productToRemove.getName()).remove(productToRemove);
            catalog.getProductCategories().get(productToRemove.getCategory().getName()).remove(productToRemove);
        } else {
            throw new ProductNotFoundException("Product with id = " + productId + " not found");
        }
        catalog.setLastUpdated(LocalDateTime.now());
    }

    private Map<String, List<Product>> getProductNamesMap() {
        return productDao
                .getAll()
                .stream()
                .collect(Collectors.groupingBy(Product::getName));
    }

    private Map<String, List<Product>> getProductCategoriesMap() {
        return productDao
                .getAll()
                .stream()
                .collect(Collectors.groupingBy(pr -> pr.getCategory().getName()));
    }
}


