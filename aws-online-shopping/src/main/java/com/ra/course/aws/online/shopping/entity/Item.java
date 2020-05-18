package com.ra.course.aws.online.shopping.entity;

import java.util.Objects;

public class Item {

    private Long itemId;
    private int quantity;
    private double price;
    private Long shoppingCartId;
    private Long productId;


    public Item() {
    }

    public Item(int quantity, double price, Long shoppingCartId, Long productId) {
        this.quantity = quantity;
        this.price = price;
        this.shoppingCartId = shoppingCartId;
        this.productId = productId;
    }

    public Item(Long itemId, int quantity, double price, Long shoppingCartId, Long productId) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.shoppingCartId = shoppingCartId;
        this.productId = productId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getShoppingCartId() {return shoppingCartId;}

    public void setShoppingCartId(Long shoppingCartId) {this.shoppingCartId = shoppingCartId;}

    public Long getProductId() {return productId;}

    public void setProductId(Long productId) {this.productId = productId;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return quantity == item.quantity &&
                Double.compare(item.price, price) == 0 &&
                shoppingCartId.equals(item.shoppingCartId) &&
                productId.equals(item.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, price, shoppingCartId, productId);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", shoppingCartId=" + shoppingCartId +
                ", productId=" + productId +
                '}';
    }
}
