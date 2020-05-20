package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.ShoppingCartDao;
import com.ra.course.aws.online.shopping.entity.Item;
import com.ra.course.aws.online.shopping.keyholder.KeyHolderFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JDBCShoppingCartDaoImpl implements ShoppingCartDao {

    public static final String INSERT_SQL = "insert into item (price, quantity, shopping_cart_id, product_id) values (?, ?, ?, ?)";
    public static final String SELECT_ONE_SQL = "select * from item where product_id = ?";
    public static final String SELECT_ALL_SQL = "select * from item";
    public static final String DELETE_SQL = "delete from item where item_id=?";
    public static final String UPDATE_SQL = "update item set price=?,quantity=?,shopping_cart_id=?,product_id=? where item_id=?";
    private transient final JdbcTemplate jdbcTemplate;
    private transient final BeanPropertyRowMapper<Item> itemRowMapper;
    private transient final KeyHolderFactory factory;

    public JDBCShoppingCartDaoImpl(final JdbcTemplate jdbcTemplate, final BeanPropertyRowMapper<Item> itemRowMapper, final KeyHolderFactory factory) {
        this.jdbcTemplate = jdbcTemplate;
        this.itemRowMapper = itemRowMapper;
        this.factory = factory;
    }

    @Override
    public Long addItemToCart(final Item item) {
        final KeyHolder keyHolder = factory.newKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection con) throws SQLException {
                final PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"item_id"});
                ps.setDouble(1, item.getPrice());
                ps.setInt(2, item.getQuantity());
                ps.setLong(3, item.getShoppingCartId());
                ps.setLong(4, item.getProductId());
                return ps;
            }
        }, keyHolder);

        item.setItemId(keyHolder.getKey().longValue());
        return item.getItemId();
    }

    @Override
    public Item findItem(final Long productId) {
        try{
            return jdbcTemplate.queryForObject(SELECT_ONE_SQL, itemRowMapper, productId);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Item> getAllItems() {
        return jdbcTemplate.query(SELECT_ALL_SQL, itemRowMapper);
    }

    @Override
    public boolean removeItemFromCart(final Item item) {
        final int updated = jdbcTemplate.update(DELETE_SQL, item.getItemId());
        return updated == 1;
    }


    @Override
    public boolean updateItemInCart(final Item item) {
        final int updated = jdbcTemplate.update(UPDATE_SQL, item.getPrice(), item.getQuantity(), item.getShoppingCartId(), item.getProductId(), item.getItemId());
        return updated == 1;
    }

}
