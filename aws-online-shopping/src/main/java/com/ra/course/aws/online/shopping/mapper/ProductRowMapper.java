package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
@Service
public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        ProductCategory category = mapToProductCatrgory(rs, rowNum);
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getDouble("price"));
        product.setAvailableItemCount(rs.getInt("availableItemCount"));
        product.setCategory(category);
        return product;
    }
    private ProductCategory mapToProductCatrgory (ResultSet rs, int rowNum) throws SQLException {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(rs.getLong("id"));
        productCategory.setName(rs.getString("name"));
        productCategory.setDescription(rs.getString("description"));
        return productCategory;
    }
}
