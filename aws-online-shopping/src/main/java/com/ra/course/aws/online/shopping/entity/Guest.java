package com.ra.course.aws.online.shopping.entity;

import com.ra.course.aws.online.shopping.entity.interfaces.User;

public class Guest implements User {
    private Long id;
    private ShoppingCart shoppingCart;

    public Guest(Long id, ShoppingCart shoppingCart) {
        this.id = id;
        this.shoppingCart = shoppingCart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
