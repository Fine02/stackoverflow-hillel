package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.product.Product;


public interface ProductService {
  void addProductReview(Product product);
  void addProductRating(Product product, int rating);
}
