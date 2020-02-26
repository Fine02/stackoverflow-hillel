package com.ra.course.aws.online.shopping.entity;

import interfaces.Search;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Catalog implements Search {
    private LocalDate lastUpdated;
    private Map<String, List<Product>> productNames;
    private Map<String, List<Product>> productCategories;
    private  String name;

    public Catalog(LocalDate lastUpdated, Map<String, List<Product>> productNames,
                   Map<String, List<Product>> productCategories, String name) {
        this.lastUpdated = lastUpdated;
        this.productNames = productNames;
        this.productCategories = productCategories;
        this.name = name;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Product> searchProductByName(String name) {
        return null;
    }

    @Override
    public List<Product> searchProductByCategory(String category) {
        return null;
    }
}
