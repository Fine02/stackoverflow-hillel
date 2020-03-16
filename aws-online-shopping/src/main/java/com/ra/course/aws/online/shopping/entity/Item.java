package com.ra.course.aws.online.shopping.entity;

import java.util.Objects;

public class Item {
    private Long productID;
    private int quantity;
    private double price;

    public Item() {
    }

    public Item(int quantity, double price) {
        this.quantity = quantity;
        this.price = price;
    }

    public Item(Long productID, int quantity, double price) {
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return quantity == item.quantity &&
                Double.compare(item.price, price) == 0 &&
                Objects.equals(productID, item.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, quantity, price);
    }

    @Override
    public String toString() {
        return "Item{" +
                "productID=" + productID +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
