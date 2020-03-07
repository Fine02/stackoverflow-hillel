package com.ra.course.aws.online.shopping.entity;

public class Product {
    private String name;
    private String description;
    private double price;
    private int availableItemCount;
    private ProductCategory category;

    public Product(String name, String description, double price, int availableItemCount, ProductCategory category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableItemCount = availableItemCount;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableItemCount() {
        return availableItemCount;
    }

    public void setAvailableItemCount(int availableItemCount) {
        this.availableItemCount = availableItemCount;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}
