package com.ra.course.aws.online.shopping.entity;

import java.util.Map;

public class ShoppingCart {
    private Map<Product, Integer> productList;

    public ShoppingCart(Map<Product, Integer> productList) {
        this.productList = productList;
    }

    public Map<Product, Integer> getProductList() {
        return productList;
    }

    public void setProductList(Map<Product, Integer> productList) {
        this.productList = productList;
    }

}
