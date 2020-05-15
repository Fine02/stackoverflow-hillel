package com.ra.course.aws.online.shopping.entity.product;

import java.util.Objects;

public class ProductReview {
    private Long id;
    private int rating;
    private String review;

    public ProductReview() {
    }

    public ProductReview(int rating, String review) {
        this.rating = rating;
        this.review = review;
    }

    public ProductReview(Long id, int rating, String s) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                Objects.equals(review, that.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, review);
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "id=" + id +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                '}';
    }
}
