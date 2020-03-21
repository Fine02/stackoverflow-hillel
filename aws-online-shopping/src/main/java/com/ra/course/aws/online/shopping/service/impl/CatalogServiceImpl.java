package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.Catalog;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.exceptions.ProductNotFoundException;
import com.ra.course.aws.online.shopping.service.CatalogService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CatalogServiceImpl implements CatalogService {

    private transient final Catalog catalog;
    private transient final ProductDao productDao;

    public CatalogServiceImpl(final ProductDao productDao) {
        this.productDao = productDao;
        this.catalog = createCatalog();
    }

    public Catalog getCatalog() {
        return catalog;
    }

    private Catalog createCatalog() {
        final Supplier<Stream<Product>> streamSupplier = () -> productDao.getAll().stream();

        final Map<String, List<Product>> productsByNames = streamSupplier
                .get()
                .collect(Collectors.groupingBy(Product::getName));

        final Map<String, List<Product>> productsByCat = streamSupplier
                .get()
                .collect(Collectors.groupingBy(pr -> pr.getCategory().getName()));

        return new Catalog(
                LocalDateTime.now(),
                productsByNames,
                productsByCat);
    }

    @Override
    public boolean updateCatalogWithNewProduct(final Long newProductId) {
        final Product newProduct = productDao.findById(newProductId);

        /*Putting new product into productNames map*/
        catalog.getProductsByNames().computeIfAbsent(newProduct.getName(),
                pr -> new ArrayList<>()).add(newProduct);

        /*Putting new product into productCategories map*/
        catalog.getProductsByCategories().computeIfAbsent(newProduct.getCategory().getName(),
                pr -> new ArrayList<>()).add(newProduct);

        catalog.setLastUpdated(LocalDateTime.now());

        return true;
    }

    @Override
    public boolean updateCatalogWithModifiedProduct(final Long modifiedProductId) {
        removeProductFromCatalog(modifiedProductId);

        updateCatalogWithNewProduct(modifiedProductId);

        catalog.setLastUpdated(LocalDateTime.now());

        return true;
    }

    @Override
    public boolean removeProductFromCatalog(final Long productId) {
        /*Finding product to remove by ID*/
        final Optional<Product> optProduct = catalog.getProductsByNames()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(product -> product.getId().equals(productId))
                .findAny();
        /*Removing product from maps*/
        if (optProduct.isPresent()) {
            final Product productToRemove = optProduct.get();
            catalog.getProductsByNames().get(productToRemove.getName()).remove(productToRemove);
            catalog.getProductsByCategories().get(productToRemove.getCategory().getName()).remove(productToRemove);
        } else {
            throw new ProductNotFoundException("Product with id = " + productId + " not found");
        }
        catalog.setLastUpdated(LocalDateTime.now());

        return true;
    }

    @Override
    public List<Product> searchProductsByName(final String productName) {
        return catalog.getProductsByNames().get(productName);
    }

    @Override
    public List<Product> searchProductsByCategory(final ProductCategory productCategory) {
        return catalog.getProductsByCategories().get(productCategory.getName());
    }

    @Override
    public List<Product> getAll() {
        return catalog.getProductsByNames()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}


