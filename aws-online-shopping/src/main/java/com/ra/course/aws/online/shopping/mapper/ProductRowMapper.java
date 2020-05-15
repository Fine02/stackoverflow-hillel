package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.entity.product.ProductReview;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final ProductCategory category = mapToCategory(rs);
        final ProductReview review = mapToReview(rs);
        final Product product = new Product();
        product.setId(rs.getLong("pr_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getDouble("price"));
        product.setAvailableItemCount(rs.getInt("availableItemCount"));
        product.setCategory(category);
        product.setProductReview(review);
        return product;
    }
    private ProductCategory mapToCategory(final ResultSet rs) throws SQLException {
        final ProductCategory category = new ProductCategory();
        category.setId(rs.getLong("pr_cat_id"));
        category.setName(rs.getString("cat_name"));
        category.setDescription(rs.getString("cat_description"));
        return category;
    }
    private ProductReview mapToReview(final ResultSet rs) throws SQLException {
        final ProductReview review = new ProductReview();
        review.setId(rs.getLong("pr_rev_id"));
        review.setRating(rs.getInt("rating"));
        review.setReview(rs.getString("review"));
        return review;
    }
}

