package com.ra.course.aws.online.shopping.entity;
import com.ra.course.aws.online.shopping.entity.product.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Catalog  {
    private LocalDateTime lastUpdated;
    private Map<String, List<Product>> productNames;
    private Map<String, List<Product>> productCategories;

    public Catalog() {
    }

    public Catalog(LocalDateTime lastUpdated, Map<String, List<Product>> productNames,
                   Map<String, List<Product>> productCategories) {
        this.lastUpdated = lastUpdated;
        this.productNames = productNames;
        this.productCategories = productCategories;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Map<String, List<Product>> getProductNames() {
        return productNames;
    }

    public void setProductNames(Map<String, List<Product>> productNames) {
        this.productNames = productNames;
    }

    public Map<String, List<Product>> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(Map<String, List<Product>> productCategories) {
        this.productCategories = productCategories;
    }


}
