package com.ra.course.aws.online.shopping.entity;

import java.util.List;

public class ShoppingCart {
    private List<Item> items;

    public ShoppingCart() {
    }

    public ShoppingCart(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
