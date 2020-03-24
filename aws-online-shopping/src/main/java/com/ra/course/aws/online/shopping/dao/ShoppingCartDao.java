package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.Item;
import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartDao {
    Long addItemToCart(Item item);
    Item getItemFromCart(Long productId);
    List<Item> getAllItems();
    void removeItemFromCart(Item item);
    PaymentStatus makePayment(List<Item> items);
    void updateItemInCart(Item item);
}
