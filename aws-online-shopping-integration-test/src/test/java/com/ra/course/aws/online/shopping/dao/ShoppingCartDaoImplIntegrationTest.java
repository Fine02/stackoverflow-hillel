package com.ra.course.aws.online.shopping.dao;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes ={AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("test")
@Sql(scripts= {"classpath:schema.sql", "classpath:data.sql"})
public class ShoppingCartDaoImplIntegrationTest {

    private Product product;
    private Item itemToCart;

    @BeforeEach
    public void before(){
        product = createProduct();
        itemToCart = createItem();
    }

    @Autowired
    private ShoppingCartDao shoppingCartDao;


    @Test
    @DisplayName("WhenSaveNewItemThenReturnIdFromDB")
    public void saveItemTest (){
        Long savedId = shoppingCartDao.addItemToCart(itemToCart);
        assertThat(savedId > 0).isTrue();
    }

    @Test
    @DisplayName("WhenFindItemInCartThenReturnSavedItem")
    public void findItemTest (){
        Item foundItem = shoppingCartDao.findItem(1L);
        assertEquals(4L, foundItem.getItemId());

    }

    @Test
    @DisplayName("whenGetAllItemsFromDatabaseThenReturnListItems")
    public void getAllItemsFromCartTest (){
        List<Item> allItems = shoppingCartDao.getAllItems();
        assertThat(allItems.size() > 0).isTrue();

    }
    @Test
    @DisplayName("whenRemoveItemIfTryToFindByIdThenReturnNull")
    public void removeItemFromCartTest (){
        Item foundItem = shoppingCartDao.findItem(5L);
        shoppingCartDao.removeItemFromCart(foundItem);
        Item deleted = shoppingCartDao.findItem(5L);
        assertNull(deleted);
    }

    @Test
    @DisplayName("whenUpdateItemThenGetUpdatedItem")
    public void updateItemInCartTest (){
        Item itemFromCart = shoppingCartDao.findItem(2L);
        itemFromCart.setPrice(450.00);
        itemFromCart.setQuantity(3);
        shoppingCartDao.updateItemInCart(itemFromCart);
        Item changedItem = shoppingCartDao.findItem(2L);
        Assertions.assertAll(
                ()-> assertEquals(450.00, changedItem.getPrice()),
                ()-> assertEquals(3, changedItem.getQuantity())
        );
    }

    private Item createItem(){
        ShoppingCart cart = new ShoppingCart();
        cart.setId(2L);
        Member member = new Member();
        member.setCart(cart);

        return new Item(1, 155.00, member.getCart().getId(), product.getId());
    }
    private Product createProduct(){
        return new Product(1L,"samsung smart camera", "description for camera", 155.00,10, new ProductCategory(1L,"Cameras", "some description for cameras"));
    }

}
