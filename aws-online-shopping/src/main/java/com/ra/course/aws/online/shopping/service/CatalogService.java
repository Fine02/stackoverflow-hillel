package com.ra.course.aws.online.shopping.service;

public interface CatalogService {

    void updateCatalogWithModifiedProduct(Long productId);

    void updateCatalogWithNewProduct(Long productId);

    void removeProductFromCatalog(final Long productId);
}
