package com.ra.course.aws.online.shopping.entity;

import java.util.List;

public class Product {
    private int productId;
    private String productName;
    private String productDescription;
    private int productPrice;
    private List<String> productImageList;
    private String productImagePath;
    private String productCategory;
    private String productSeller;

    public Product(int productId, String productName, String productDescription, int productPrice, List<String> productImageList, String productImagePath, String productCategory, String productSeller) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productImageList = productImageList;
        this.productImagePath = productImagePath;
        this.productCategory = productCategory;
        this.productSeller = productSeller;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public List<String> getProductImageList() {
        return productImageList;
    }

    public void setProductImageList(List<String> productImageList) {
        this.productImageList = productImageList;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductSeller() {
        return productSeller;
    }

    public void setProductSeller(String productSeller) {
        this.productSeller = productSeller;
    }
}
