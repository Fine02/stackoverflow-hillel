package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.Item;
import com.ra.course.aws.online.shopping.entity.ShoppingCart;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.entity.user.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes ={AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("test")
@Sql(scripts= {"classpath:schema.sql", "classpath:data.sql"})
public class ShoppingCartServiceImplIntegrationTest {

    private Product product;
    private Member member;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @BeforeEach
    public void before() {
        product = createProduct();
        member = createMember();
    }

    @Test
    @DisplayName("whenAddProductToCartThenReturnSavedId")
    public void addProductToCartTest(){
        Product product = productService.findByID(1L);
        Long savedItemId = shoppingCartService.addProductToCart(product, member);
        assertNotNull(savedItemId);
    }

    @Test
    @DisplayName("WhenAddProductIfProductOrMemberIsNullThenNotThrowsException")
    public void addProductToCartWithGivenNullTest(){
        Assertions.assertAll(
                () -> assertDoesNotThrow(()-> shoppingCartService.addProductToCart(null, member)),
                () -> assertDoesNotThrow(()-> shoppingCartService.addProductToCart(product, null)),
                () -> assertDoesNotThrow(()-> shoppingCartService.addProductToCart(null, null))
        );
    }

    @Test
    @DisplayName("whenRemoveItemFromCartIfItemIsNullThenNotThrowsException")
    public void RemoveProductFromCartWithGivenNullTest(){
        assertDoesNotThrow(()-> shoppingCartService.removeItemFromCart(null));
    }

    @Test
    @DisplayName("WhenCheckoutItemInCartIfPresentsThenReturnTrue")
    public void checkoutItemInCartTest(){
        boolean actualResult = shoppingCartService.checkoutItemInCart(1L);
        Assertions.assertTrue(actualResult);
    }

    @Test
    @DisplayName("whenRemoveItemFromCartIfQuantityMoreThenOneThenChangeQuantity")
    public void RemoveProductFromCart(){
        Product jeans = productService.findByID(5L);
        Item foundItem = shoppingCartService.findItemById(jeans.getId());
        shoppingCartService.removeItemFromCart(foundItem);
        Item changed = shoppingCartService.findItemById(jeans.getId());
        Assertions.assertAll(
                ()-> assertNotEquals(foundItem.getQuantity(), changed.getQuantity()),
                ()-> assertEquals(5, foundItem.getQuantity()),
                ()-> assertEquals(4, changed.getQuantity()),
                ()-> assertEquals(150, foundItem.getPrice()),
                ()-> assertEquals(120, changed.getPrice())
        );

    }
    @Test
    @DisplayName("shouldReturnItemWhenSearchingById")
    public void findItemTest() throws SQLException {
        Long save = productService.save(product);
        Product productInDB = productService.findByID(save);
        Long savedItemId = shoppingCartService.addProductToCart(productInDB, member);
        Item foundItem = shoppingCartService.findItemById(productInDB.getId());

        assertEquals(savedItemId, foundItem.getItemId());
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
    private Member createMember(){
        Member member = new Member();
        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        member.setCart(cart);
        return member;
    }


}


