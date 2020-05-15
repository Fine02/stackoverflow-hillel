package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.entity.product.ProductReview;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductRowMapperTest {
    private Product product;


    @BeforeEach
    public void before() {
        product = createProduct();

    }

    @Test
    void mapRowTest() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong("pr_id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("testName");
        when(resultSet.getString("description")).thenReturn("some description");
        when(resultSet.getDouble("price")).thenReturn(150.00);
        when(resultSet.getInt("availableItemCount")).thenReturn(10);
        when(resultSet.getLong("pr_cat_id")).thenReturn(1L);
        when(resultSet.getString("cat_name")).thenReturn("catName");
        when(resultSet.getString("cat_description")).thenReturn("---");
        when(resultSet.getLong("pr_rev_id")).thenReturn(1L);
        when(resultSet.getInt("rating")).thenReturn(10);
        when(resultSet.getString("review")).thenReturn("---");

        Product productFromMapper = new ProductRowMapper().mapRow(resultSet, 0);

        assertEquals(product.toString(), productFromMapper.toString());
    }


    private Product createProduct(){
        Product product = new Product();
        ProductCategory category = new ProductCategory();
        ProductReview review = new ProductReview();
        category.setId(1L);
        category.setName("catName");
        category.setDescription("---");
        review.setId(1L);
        review.setRating(10);
        review.setReview("---");
        product.setId(1L);
        product.setName("testName");
        product.setDescription("some description");
        product.setPrice(150.00);
        product.setAvailableItemCount(10);
        product.setCategory(category);
        product.setProductReview(review);

        return product;
    }

}
