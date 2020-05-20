package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.JDBCProductDaoImpl;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.keyholder.KeyHolderFactory;
import com.ra.course.aws.online.shopping.mapper.ProductRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class ProductDaoImplTest {
    private ProductDao productDao;
    private JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private ProductRowMapper productRowMapper = mock(ProductRowMapper.class);
    private KeyHolderFactory factory = mock(KeyHolderFactory.class);
    private Connection connection;
    private Integer prefId = 55;
    private Product product;
    private Product productWithId;
    private List<Product> testList;
    private PreparedStatement prepareStatement;

    @BeforeEach
    public void before() {
        productDao = new JDBCProductDaoImpl(jdbcTemplate, productRowMapper, factory);
        product = createProduct();
        productWithId = createProductWithId();
        testList = createProductList();
        prepareStatement = mock(PreparedStatement.class);
        connection = mock(Connection.class);
    }

    @Test
    @DisplayName("WhenSaveNewProductTHenShouldReturnId")
    public void saveTest() throws SQLException {
        //when
        KeyHolder keyHolder = mock(GeneratedKeyHolder.class);
        when(factory.newKeyHolder()).thenReturn(keyHolder);
        when(keyHolder.getKey()).thenReturn(prefId.longValue());
        when(connection.prepareStatement(
                eq(JDBCProductDaoImpl.INSERT_SQL), eq(new String[]{"pr_id"}))).thenReturn(prepareStatement);
        doAnswer(invocation -> {
                    ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(connection);
                    verify(prepareStatement).setString(1, product.getName());
                    verify(prepareStatement).setString(2, product.getDescription());
                    verify(prepareStatement).setDouble(3, product.getPrice());
                    verify(prepareStatement).setInt(4, product.getAvailableItemCount());
                    verify(prepareStatement).setString(5, product.getCategory().getName());
                    verify(prepareStatement, times(3)).setString(any(Integer.class), any(String.class));
                    verify(prepareStatement, times(1)).setInt(any(Integer.class), any(Integer.class));
                    verify(prepareStatement, times(1)).setDouble(any(Integer.class), any(Double.class));
                    return null;
                }
        ).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        //then
        assertEquals(prefId.longValue(), productDao.save(product));

    }

    @Test
    @DisplayName("WhenUpdateProductTHenShouldMinOneInvocation")
    public void updateTest() {
        //given
        productWithId.setName("newName");
        //when
        when(jdbcTemplate.update(any(), anyString(), anyString(), anyDouble(), anyInt(), anyLong(), anyLong())).thenReturn(1);
        //then
        assertTrue(productDao.update(productWithId));

    }

    @Test
    @DisplayName("WhenUpdateProductIfDidNotUpdateThenReturnFalse")
    public void updateWithNegativeResultTest() {
        //given
        productWithId.setName("newName");
        when(jdbcTemplate.update(any(), anyString(), anyString(), anyDouble(), anyInt(), anyLong(), anyLong())).thenReturn(0);
        //when
        productDao.update(productWithId);
        //then
        assertFalse(productDao.update(productWithId));
    }

    @Test
    @DisplayName("WhenRemoveProductThenShouldMinOneInvocation")
    public void removeTest(){
        //when
        when(jdbcTemplate.update(any(),any(Long.class))).thenReturn(1);
        //then
        assertTrue(productDao.remove(1L));
        verify(jdbcTemplate).update(any(), eq(1L));
    }

    @Test
    @DisplayName("WhenRemoveProductIfDidNotUpdateThenReturnFalse")
    public void removeWithNegativeResultTest(){
        //when
        when(jdbcTemplate.update(any(),any(Long.class))).thenReturn(0);
        //then
        assertFalse(productDao.remove(1L));
    }

    @Test
    @DisplayName("WhenFindProductByIdThenShouldReturnProduct")
    public void findByIdTest(){
        //given
        var prodId = 55L;
        Product product = new Product();
        product.setId(prodId);
        product.setName("test");
        when(jdbcTemplate.queryForObject(JDBCProductDaoImpl.SELECT_ONE_SQL, productRowMapper, prodId)).thenReturn(product);
        //when
        Product byId = productDao.findById(55L);
        //then
        assertEquals(product.getName(),byId.getName());
    }

    @Test
    @DisplayName("WhenFindProductIfThrownExceptionThenReturnNull")
    public void findItemTest2(){
        //given
        var prodId = 55L;
        Product product = new Product();
        product.setId(prodId);
        product.setName("test");
        when(jdbcTemplate.queryForObject(JDBCProductDaoImpl.SELECT_ONE_SQL, productRowMapper, prodId)).thenThrow(EmptyResultDataAccessException.class);
        //when
        Product byId = productDao.findById(55L);
        //then
        assertNull(byId);
    }

    @Test
    @DisplayName("WhenSearchProductsByNameThenReturnListProducts")
    public void searchProductsByNameTest(){
        //given
        String productName = "iPhone10";
        when(jdbcTemplate.query(JDBCProductDaoImpl.SEL_PR_BY_NAME, productRowMapper, productName)).thenReturn(testList);
        //when
        List<Product> actualList = productDao.searchProductsByName("iPhone10");
        //then
        assertAll(
                ()-> assertEquals(testList.size(), actualList.size()),
                ()-> assertEquals("iPhone10", actualList.get(0).getName())
        );
    }

    @Test
    @DisplayName("WhenSearchProductsByCategoryThenReturnListProducts")
    public void searchProductsByCategoryTest(){
        //given
        ProductCategory category = new ProductCategory();
        category.setName("Cell_phones");
        category.setDescription("");
        when(jdbcTemplate.query(JDBCProductDaoImpl.SEL_PR_BY_CAT, productRowMapper, category.getName())).thenReturn(testList);
        //when
        List<Product> actualList = productDao.searchProductsByCategory(category);
        //then
        assertAll(
                ()-> assertEquals(testList.size(), actualList.size()),
                ()-> assertEquals(category, actualList.get(1).getCategory()),
                ()-> assertEquals("Cell_phones", actualList.get(1).getCategory().getName())
        );
    }
    @Test
    @DisplayName("WhenSearchAllProductsThenReturnListOfAllProducts")
    public void getAllProductsTest(){
        //given
        when(jdbcTemplate.query(JDBCProductDaoImpl.SELECT_ALL_SQL, productRowMapper)).thenReturn(testList);
        //when
        List<Product> actualList = productDao.getAll();
        //then
        assertEquals(testList.size(), actualList.size());
    }


    private Product createProduct(){
        return new Product("Sunglasses", "RayBan Wayfarer", 199.9,
                10, new ProductCategory("Accessories", "Accessories description"));
    }

    private Product createProductWithId(){
        return new Product(1L,"Sunglasses", "RayBan Wayfarer", 199.9,
                10, new ProductCategory(1L,"Accessories", "Accessories description"));
    }

    private ProductCategory createCategory(){
        return new ProductCategory("Cell_phones", "");
    }
    private List<Product> createProductList(){
        List<Product> list = new ArrayList<>();
        Product product1 = new Product(); product1.setName("iPhone10"); product1.setPrice(150.00);product1.setCategory(createCategory());
        Product product2 = new Product(); product2.setName("iPhone10"); product2.setPrice(300.00);product2.setCategory(createCategory());
        Product product3 = new Product(); product3.setName("iPhone10"); product3.setPrice(450.00);product3.setCategory(createCategory());
        list.add(product1);
        list.add(product2);
        list.add(product3);
        return list;
    }
}
