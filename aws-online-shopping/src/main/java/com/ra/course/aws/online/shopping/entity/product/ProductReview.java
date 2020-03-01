package com.ra.course.aws.online.shopping.entity.product;

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
}
