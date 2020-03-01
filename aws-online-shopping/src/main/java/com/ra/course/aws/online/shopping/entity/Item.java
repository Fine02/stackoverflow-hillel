package com.ra.course.aws.online.shopping.entity;

public class Item {
    private String productID;
    private int quantity;
    private double price;

    public Item() {
    }

    public Item(int quantity, double price) {
        this.quantity = quantity;
        this.price = price;
    }

    public Item(String productID, int quantity, double price) {
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
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
}
