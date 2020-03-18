package com.ra.course.aws.online.shopping.entity.product;

import java.util.Objects;

public class Product {
    private Long id;
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

    public Product(Long id, String name, String description, double price, int availableItemCount, ProductCategory category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableItemCount = availableItemCount;
        this.category = category;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(category, product.category) &&
                Objects.equals(productReview, product.productReview);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, availableItemCount, category, productReview);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", availableItemCount=" + availableItemCount +
                ", category=" + category +
                ", productReview=" + productReview +
                '}';
    }
}
