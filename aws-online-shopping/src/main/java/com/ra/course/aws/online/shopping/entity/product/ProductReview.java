package com.ra.course.aws.online.shopping.entity.product;

import java.util.Objects;

public class ProductReview {
    private int rating;
    private String review;

    public ProductReview() {
    }

    public ProductReview(int rating, String review) {
        this.rating = rating;
        this.review = review;
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
                review.equals(that.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, review);
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "rating=" + rating +
                ", review='" + review + '\'' +
                '}';
    }
}
