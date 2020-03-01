package com.ra.course.aws.online.shopping.entity.user;

import com.ra.course.aws.online.shopping.entity.ShoppingCart;
import com.ra.course.aws.online.shopping.entity.order.Order;
import java.util.List;

public class Customer {
    private ShoppingCart cart;
    private Order order;

    public Customer() {
    }

    public Customer(ShoppingCart cart, Order order) {
        this.cart = cart;
        this.order = order;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
