package com.ra.course.aws.online.shopping.entity.product;

import java.util.Objects;

public class Product {
    private Long productID;
    private String name;
    private String description;
    private double price;
    private int availableItemCount;
    private ProductCategory category;
    private ProductReview productReview;

    public Product() {
    }

    public Product(String name, String description, double price, int availableItemCount, ProductCategory category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableItemCount = availableItemCount;
        this.category = category;
    }

    public Product(Long productID, String name, String description, double price, int availableItemCount, ProductCategory category) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableItemCount = availableItemCount;
        this.category = category;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
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

    public ProductReview getProductReview() {
        return productReview;
    }

    public void setProductReview(ProductReview productReview) {
        this.productReview = productReview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                availableItemCount == product.availableItemCount &&
                Objects.equals(productID, product.productID) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(category, product.category) &&
                Objects.equals(productReview, product.productReview);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, name, description, price, availableItemCount, category, productReview);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", availableItemCount=" + availableItemCount +
                ", category=" + category +
                ", productReview=" + productReview +
                '}';
    }
}
