package com.ra.course.aws.online.shopping.entity.product;

import java.util.Objects;

public class ProductReview {
    private Long productID;
    private int rating;
    private String review;

    public ProductReview() {
    }

    public ProductReview(Long productID, int rating, String review) {
        this.productID = productID;
        this.rating = rating;
        this.review = review;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductReview that = (ProductReview) o;
        return rating == that.rating &&
                productID.equals(that.productID) &&
                review.equals(that.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, rating, review);
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "productID=" + productID +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                '}';
    }
}
