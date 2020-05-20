package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.keyholder.KeyHolderFactory;
import com.ra.course.aws.online.shopping.mapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JDBCProductDaoImpl implements ProductDao {
    public static final String INSERT_SQL = "insert into product (name, description, price, availableItemCount, product_category_id)" +
            " values (?, ?, ?, ?, (select pr_cat_id from product_category where cat_name = ?))";
    public static final String UPDATE_SQL = "update product set name=?,description=?,price=?,availableItemCount=?, product_category_id=?  where pr_id=?";
    public static final String SELECT_ALL_SQL = "select * from product inner join product_category pc on product.product_category_id = pc.pr_cat_id left join product_review pr on product.pr_id = pr.product_id";
    public static final String DELETE_SQL = "delete from product where pr_id=?";
    public static final String SELECT_ONE_SQL = "select pr_id, availableItemCount, name, description, price, pr_cat_id, cat_name, cat_description, pr_rev_id, rating, review" +
            " from product p inner join product_category pc on p.product_category_id = pc.pr_cat_id " +
            "left join product_review pr on p.pr_id = pr.product_id where p.pr_id = ?";
    public static final String SEL_PR_BY_NAME = "select * from product inner join product_category pc on product.product_category_id = pc.pr_cat_id left join product_review pr on product.pr_id = pr.product_id where product.name = ?";
    public static final String SEL_PR_BY_CAT = "select * from product inner join product_category pc on product.product_category_id = pc.pr_cat_id left join product_review pr on product.pr_id = pr.product_id where pc.cat_name = ?";
    private transient final JdbcTemplate jdbcTemplate;
    private transient final ProductRowMapper productRowMapper;
    private transient final KeyHolderFactory factory;


    @Autowired
    public JDBCProductDaoImpl(final JdbcTemplate jdbcTemplate, final ProductRowMapper productRowMapper, final KeyHolderFactory factory) {
        this.jdbcTemplate = jdbcTemplate;
        this.productRowMapper = productRowMapper;
        this.factory = factory;
    }

    @Override
    public Long save(final Product product)  {
        final KeyHolder keyHolder = factory.newKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection con) throws SQLException {
                final PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"pr_id"});
                ps.setString(1, product.getName());
                ps.setString(2, product.getDescription());
                ps.setDouble(3, product.getPrice());
                ps.setInt(4, product.getAvailableItemCount());
                ps.setString(5, product.getCategory().getName());
                return ps; }
        }, keyHolder);

        product.setId(keyHolder.getKey().longValue());
        return product.getId();
    }


    @Override
    public boolean update(final Product product) {
        final int updatedRow = jdbcTemplate.update(UPDATE_SQL, product.getName(), product.getDescription(), product.getPrice(), product.getAvailableItemCount(), product.getCategory().getId(), product.getId());
        return updatedRow == 1;
    }

    @Override
    public boolean remove(final Long productID) {
        final int updatedRow = jdbcTemplate.update(DELETE_SQL, productID);
        return updatedRow == 1;
    }

    @Override
    public Product findById(final Long ID) {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE_SQL, productRowMapper, ID);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Product> searchProductsByName(final String productName) {
        return jdbcTemplate.query(SEL_PR_BY_NAME, productRowMapper, productName);
    }

    @Override
    public List<Product> searchProductsByCategory(final ProductCategory productCategory) {
        final String categoryName = productCategory.getName();
        return jdbcTemplate.query(SEL_PR_BY_CAT, productRowMapper, categoryName);
    }

    @Override
    public List<Product> getAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, productRowMapper);
    }

}
