package com.ra.course.aws.online.shopping.entity.product;

import java.util.Objects;

public class ProductCategory {
    private Long categoryID;
    private String name;
    private String description;

    public ProductCategory() {
    }

    public ProductCategory(Long categoryID, String name, String description) {
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
    }

    public Long getCategoryID() {return categoryID;}

    public void setCategoryID(Long categoryID) {this.categoryID = categoryID;}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategory that = (ProductCategory) o;
        return categoryID.equals(that.categoryID) &&
                name.equals(that.name) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryID, name, description);
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryID=" + categoryID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
