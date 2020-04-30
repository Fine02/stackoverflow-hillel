package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.mapper.ProductRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {
    private static final String INSERT_INTO_PRODUCTS = "INSERT INTO products (name, description, price, availableItemCount) " +
            "VALUES (?, ?, ?, ?) RETURNING products.id";
//
    private final JdbcTemplate jdbcTemplate;
    private final ProductRowMapper productRowMapper;

    public ProductDaoImpl(JdbcTemplate jdbcTemplate, ProductRowMapper productRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.productRowMapper = productRowMapper;
    }

    @Override
    public Long save(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

       int i = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_INTO_PRODUCTS);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getAvailableItemCount());
            return ps;
        }, keyHolder);

        return (long) keyHolder.getKey();
    }

//    public Long save(Product product) {
//        try {
//            return (long) jdbcTemplate.update(INSERT_INTO_PRODUCTS, product.getName(),
//                    product.getDescription(), product.getPrice(), product.getAvailableItemCount());
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }

    @Override
    public boolean update(Product product) {
        return false;
    }

    @Override
    public boolean remove(Long productID) {
        return false;
    }

    @Override
    public Product findById(Long ID) {
        String sql = "SELECT * FROM products INNER JOIN product_categories ON products.id = product_categories.id WHERE products.id=?";
        try {
            return jdbcTemplate.queryForObject(sql, productRowMapper, ID);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Product> getAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class));
    }
}
