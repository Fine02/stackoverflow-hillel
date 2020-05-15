package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.Item;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.user.Member;

public interface ShoppingCartService {
    Long addProductToCart(Product product, Member member);

    void removeItemFromCart(Item item);

    boolean checkoutItemInCart(Long itemId);

    Item findItemById(Long productId);
}
