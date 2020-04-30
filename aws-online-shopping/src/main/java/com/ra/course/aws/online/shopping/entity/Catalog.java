package com.ra.course.aws.online.shopping.entity;
import com.ra.course.aws.online.shopping.entity.product.Product;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Catalog  {
    private LocalDateTime lastUpdated;
    private Map<String, List<Product>> productsByNames;
    private Map<String, List<Product>> productsByCategories;

    public Catalog() {
    }

    public Catalog(LocalDateTime lastUpdated, Map<String, List<Product>> productsByNames,
                   Map<String, List<Product>> productsByCategories) {
        this.lastUpdated = lastUpdated;
        this.productsByNames = productsByNames;
        this.productsByCategories = productsByCategories;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Map<String, List<Product>> getProductsByNames() {
        return productsByNames;
    }

    public void setProductsByNames(Map<String, List<Product>> productsByNames) {
        this.productsByNames = productsByNames;
    }

    public Map<String, List<Product>> getProductsByCategories() {
        return productsByCategories;
    }

    public void setProductsByCategories(Map<String, List<Product>> productsByCategories) {
        this.productsByCategories = productsByCategories;
    }
}
