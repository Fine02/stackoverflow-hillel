package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.Item;
import com.ra.course.aws.online.shopping.entity.payment.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.product.Product;

public interface ShoppingCartService {
    Long addProductToCart(Product product);
    void removeProductFromCart(Product product);
    boolean checkoutItemInCart(Item item);
    PaymentStatus buyItems();
}
