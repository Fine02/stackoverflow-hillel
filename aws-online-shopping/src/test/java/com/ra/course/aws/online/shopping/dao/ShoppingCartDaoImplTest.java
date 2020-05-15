package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.JDBCShoppingCartDaoImpl;
import com.ra.course.aws.online.shopping.entity.Item;
import com.ra.course.aws.online.shopping.entity.ShoppingCart;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.keyholder.KeyHolderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class ShoppingCartDaoImplTest {
    private ShoppingCartDao shoppingCartDao;
    private JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private KeyHolderFactory factory = mock(KeyHolderFactory.class);
    BeanPropertyRowMapper<Item> itemBeanPropertyRowMapper;
    private Product product;
    private Item item;
    private Integer itemId = 21;
    private List<Item> itemList;
    private Connection connection;
    private PreparedStatement prepareStatement;



    @BeforeEach
    public void before() {
        itemBeanPropertyRowMapper = BeanPropertyRowMapper.newInstance(Item.class);
        shoppingCartDao = new JDBCShoppingCartDaoImpl(jdbcTemplate, itemBeanPropertyRowMapper, factory);
        prepareStatement = mock(PreparedStatement.class);
        connection = mock(Connection.class);
        product = createProduct();
        item = createItem();
        itemList = createItemsList();
    }

    @Test
    @DisplayName("WhenSaveNewItemTHenShouldReturnItemId")
    public void addItemToCartTest() throws SQLException {
        //when
        KeyHolder keyHolder = mock(GeneratedKeyHolder.class);
        when(factory.newKeyHolder()).thenReturn(keyHolder);
        when(keyHolder.getKey()).thenReturn(itemId.longValue());
        when(connection.prepareStatement(
                eq(JDBCShoppingCartDaoImpl.INSERT_SQL), eq(new String[]{"item_id"}))).thenReturn(prepareStatement);
        doAnswer(invocation -> {
                    ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(connection);
                    verify(prepareStatement).setDouble(1, item.getPrice());
                    verify(prepareStatement).setInt(2, item.getQuantity());
                    verify(prepareStatement).setLong(3, item.getShoppingCartId());
                    verify(prepareStatement).setLong(4, item.getProductId());
                    verify(prepareStatement, times(1)).setDouble(any(Integer.class), any(Double.class));
                    verify(prepareStatement, times(1)).setInt(any(Integer.class), any(Integer.class));
                    verify(prepareStatement, times(2)).setLong(any(Integer.class), any(Long.class));
                    return null;
                }
        ).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        //then
        assertEquals(itemId.longValue(), shoppingCartDao.addItemToCart(item));
    }

    @Test
    @DisplayName("WhenFindItemByProductIdThenShouldReturnItem")
    public void findItemTest(){
        //given
        var prodId = 55L;
        when(jdbcTemplate.queryForObject(JDBCShoppingCartDaoImpl.SELECT_ONE_SQL, itemBeanPropertyRowMapper, prodId)).thenReturn(item);
        //when
        Item foundItem = shoppingCartDao.findItem(prodId);
        //then
        assertEquals(prodId, foundItem.getProductId());
    }

    @Test
    @DisplayName("WhenFindItemIfThrownExceptionThenReturnNull")
    public void findItemTest2(){
        //given
        var prodId = 55L;
        when(jdbcTemplate.queryForObject(JDBCShoppingCartDaoImpl.SELECT_ONE_SQL, itemBeanPropertyRowMapper, prodId)).thenThrow(EmptyResultDataAccessException.class);
        //when
        Item foundItem = shoppingCartDao.findItem(prodId);
        //then
        assertNull(foundItem);
    }

    @Test
    @DisplayName("WhenFindAllItemsThenShouldReturnListItems")
    public void getAllItemsTest(){
        when(jdbcTemplate.query(JDBCShoppingCartDaoImpl.SELECT_ALL_SQL, itemBeanPropertyRowMapper)).thenReturn(itemList);
        //when
        List<Item> allItems = shoppingCartDao.getAllItems();
        //then
        assertEquals(itemList.size(), allItems.size());
    }

    @Test
    @DisplayName("WhenRemoveItemFromCartThenShouldReturnTrue")
    public void removeItemFromCartTest(){
        //given
        item.setItemId(1L);
        //when
        when(jdbcTemplate.update(anyString(), any(Long.class))).thenReturn(1);
        //then
        assertTrue(shoppingCartDao.removeItemFromCart(item));
        verify(jdbcTemplate).update(any(), eq(1L));

    }

    @Test
    @DisplayName("WhenRemoveItemFromCartIfDidNotRemovedThenShouldReturnFalse")
    public void removeItemFromCartWithNegativeResultTest(){
        //given
        item.setItemId(1L);
        //when
        when(jdbcTemplate.update(anyString(), any(Long.class))).thenReturn(0);
        //then
        assertFalse(shoppingCartDao.removeItemFromCart(item));

    }

    @Test
    @DisplayName("WhenUpdateItemsInCartThenShouldReturnTrue")
    public void updateItemInCartTest(){
        //given
        item.setItemId(1L);
        item.setPrice(275.00);
        //when
        when(jdbcTemplate.update(any(),any(Double.class), any(Integer.class), any(Long.class), any(Long.class), any(Long.class))).thenReturn(1);
        //then
        assertTrue(shoppingCartDao.updateItemInCart(item));

    }

    @Test
    @DisplayName("WhenUpdateItemsInCartIfDidNotUpdatedThenShouldReturnFalse")
    public void updateItemInCartWithNegativeResultTest(){
        //given
        item.setItemId(1L);
        item.setPrice(275.00);
        //when
        when(jdbcTemplate.update(any(),any(Double.class), any(Integer.class), any(Long.class), any(Long.class), any(Long.class))).thenReturn(0);
        //then
        assertFalse(shoppingCartDao.updateItemInCart(item));

    }


    private Product createProduct(){
        return new Product(55L,"samsung smart camera", "description for camera", 155.00,10, new ProductCategory(1L,"Cameras", "some description for cameras"));
    }

    private Item createItem(){
        ShoppingCart cart = new ShoppingCart();
        cart.setId(2L);
        Member member = new Member();
        member.setCart(cart);
        return new Item(1, 155.00, member.getCart().getId(), product.getId());
    }
    private List<Item> createItemsList(){

        ShoppingCart cart = new ShoppingCart();
        cart.setId(2L);
        Member member = new Member();
        member.setCart(cart);
        return new ArrayList<>(){{
            add(new Item(1, 155.00, member.getCart().getId(), 1L));
            add(new Item(1, 155.00, member.getCart().getId(), 2L));
            add(new Item(1, 155.00, member.getCart().getId(), 3L));
        }};
    }



}

