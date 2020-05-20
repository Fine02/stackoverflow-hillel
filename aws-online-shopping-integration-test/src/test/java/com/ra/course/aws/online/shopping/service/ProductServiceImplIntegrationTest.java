package com.ra.course.aws.online.shopping.service;

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
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes ={AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("test")
@Sql(scripts= {"classpath:schema.sql", "classpath:data.sql"})
public class ProductServiceImplIntegrationTest {

    private List<Product> mockProductsListFromDao;
    private Product productWithReview;
    private Product newProduct;

    @Autowired
    private ProductService productService;

    @BeforeEach
    public void before() {

        newProduct = createProduct();
    }

    @Test
    @DisplayName("whenAddNewProductThenReturnSavedId")
    void shouldAddNewProduct() throws SQLException {
        Long addedProductID = productService.save(newProduct);
        assertNotNull(addedProductID);
    }

    @Test
    @DisplayName("whenAddNewProductThenReturnSavedId")
    void shouldUpdateProduct() {
        Product foundProduct = productService.findByID(1L);
        foundProduct.setName("changed - name");
        foundProduct.setPrice(150.00);
        productService.update(foundProduct);
        Product changed = productService.findByID(1L);

        Assertions.assertAll(
                ()-> assertEquals("changed - name", changed.getName()),
                ()-> assertEquals(150.00, changed.getPrice())
        );
    }

    @Test
    @DisplayName("whenRemoveProductIfTryToFindByDeletedIdThenReturnNull")
    void shouldRemoveProduct() {
        productService.remove(1L);
        Product found = productService.findByID(1L);
        assertNull(found);
    }
    @Test
    @DisplayName("shouldReturnProductsWhenSearchingById")
    void shouldSearchProductsByID() throws SQLException {
        Long savedId = productService.save(newProduct);
        Product foundProduct = productService.findByID(savedId);

        Assertions.assertAll(
                ()-> assertEquals(newProduct.getName(), foundProduct.getName() ),
                ()-> assertEquals(newProduct.getDescription(), foundProduct.getDescription()),
                ()-> assertEquals(newProduct.getPrice(), foundProduct.getPrice()),
                ()-> assertEquals(newProduct.getAvailableItemCount(), foundProduct.getAvailableItemCount()),
                ()-> assertEquals(newProduct.getCategory().getName(), foundProduct.getCategory().getName())
        );
    }
    @Test
    @DisplayName("shouldReturnListProductsWhenSearchingByName")
    void shouldSearchProductsByName() {
        List<Product> actualProductsList = productService.searchByName("apple iphone6s");
        for (Product pr : actualProductsList) {
            assertEquals("apple iphone6s", pr.getName());
        }
        assertThat(actualProductsList.size() == 2);
    }
    @Test
    @DisplayName("shouldReturnListProductsWhenSearchingByCategory")
    void shouldSearchProductsByCategory() {
        List<Product> actualProductsList = productService.searchByCategory(createCategory());
        for (Product pr : actualProductsList) {
            assertEquals(pr.getCategory().getName(), "Cell_phones");
        }
        assertThat(actualProductsList.size() == 3);
    }
    @Test
    @DisplayName("shouldReturnAllProductsList")
    void shouldReturnAllProductsList() {
        List<Product> actualAllProducts = productService.getAll();
        assertEquals( 6 , actualAllProducts.size());
    }

    private ProductCategory createCategory(){
        return new ProductCategory("Cell_phones", "");
    }

    private Product createProduct(){
        Product product =  new Product();
        product.setName("Sony");
        product.setDescription("smartphone sony");
        product.setAvailableItemCount(10);
        product.setPrice(180.00);
        product.setCategory(createCategory());
        return product;
    }

}



