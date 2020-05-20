package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes ={AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("test")
@Sql(scripts= {"classpath:schema.sql", "classpath:data.sql"})
public class ProductDaoImplIntegrationTest {
    private Product product;
    private ProductCategory category;

    @BeforeEach
    public void before(){
        product = createProduct();
        category = createCategory();
    }

    @Autowired
    private ProductDao productDao;

    @Test
    @DisplayName("WhenSaveNewProductThenReturnIdFromDB")
    public void saveTest () throws SQLException {
        Long returnedId = productDao.save(product);
        assertThat(returnedId > 0).isTrue();
    }

    @Test
    @DisplayName("whenSearchProductByIDThenReturnProduct")
    public void findByIdTest (){
        Product foundProd = productDao.findById(6L);
        assertEquals(foundProd.getId(), 6L);
    }
    @Test
    @DisplayName("whenSearchProductByIDThatIsNotInDBThenReturnNull")
    public void findByIdNotINDBTest (){
        Product foundProd = productDao.findById(60L);
        assertNull(foundProd);
    }

    @Test
    @DisplayName("whenUpdateProductInDatabaseThenGetUpdatedProduct")
    public void updateTest (){
        Product foundProd = productDao.findById(6L);
        foundProd.setName("Pink Floyd");
        foundProd.setDescription("the best Album");
        productDao.update(foundProd);
        Product changedProduct = productDao.findById(6L);
        Assertions.assertAll(
                ()-> assertEquals("Pink Floyd", changedProduct.getName()),
                ()-> assertEquals("the best Album", changedProduct.getDescription())
        );
    }

    @Test
    @DisplayName("whenGetAllProductsInDatabaseThenReturnListProducts")
    public void getAllTest (){
        List<Product> allProducts = productDao.getAll();
        assertThat(allProducts.size() > 0).isTrue();

    }
    @Test
    @DisplayName("whenSearchProductsByNameThenReturnListProducts")
    public void searchProductsByNameTest(){
        List<Product> productList = productDao.searchProductsByName("apple iphone6s");
        assertThat(productList.size() > 1).isTrue();

    }
    @Test
    @DisplayName("whenSearchProductsByCategoryThenReturnListProducts")
    public void searchProductsByCategoryTest(){
        List<Product> productList = productDao.searchProductsByCategory(category);
        assertThat(productList.size() == 3).isTrue();
    }

    @Test
    @DisplayName("whenRemoveProductIfTryToFindByIdThenReturnNull")
    public void removeTest (){
        productDao.remove(1L);
        Product foundProd = productDao.findById(1L);
        assertNull(foundProd);

    }

    private Product createProduct(){
        return new Product("Red Hot Chili Peppers", "some description", 110.00,10, new ProductCategory(4L,"Music", "description for category"));
    }
    private ProductCategory createCategory(){
        ProductCategory category = new ProductCategory();
        category.setName("Cell_phones");
        return category;
    }

}
