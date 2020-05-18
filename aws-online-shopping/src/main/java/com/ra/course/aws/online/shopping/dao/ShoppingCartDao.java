package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.Item;

import java.util.List;

public interface ShoppingCartDao {
    Long addItemToCart(Item item);
    Item findItem(Long productId);
    List<Item> getAllItems();
    boolean removeItemFromCart(Item item);
    boolean updateItemInCart(Item item);
}
