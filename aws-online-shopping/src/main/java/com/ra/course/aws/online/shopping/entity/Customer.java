package com.ra.course.aws.online.shopping.entity;

import interfaces.Search;

import java.util.List;

public class Customer implements Search {
    @Override
    public List<Product> searchProductByName(String name) {
        return null;
    }

    @Override
    public List<Product> searchProductByCategory(String category) {
        return null;
    }
}
