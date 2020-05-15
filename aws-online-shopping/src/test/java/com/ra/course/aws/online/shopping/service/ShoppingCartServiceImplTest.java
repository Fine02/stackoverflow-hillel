package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.dao.ShoppingCartDao;
import com.ra.course.aws.online.shopping.entity.Item;
import com.ra.course.aws.online.shopping.entity.ShoppingCart;
import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.ElementNotFoundException;
import com.ra.course.aws.online.shopping.service.impl.ShoppingCartServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class ShoppingCartServiceImplTest {
    private ShoppingCartServiceImpl shoppingCartService;
    private ShoppingCartDao shoppingCartDao = mock(ShoppingCartDao.class);
    private ProductDao productDao = mock(ProductDao.class);
    private Product newProduct;
    private Member member;
    private Item newItem;



    @BeforeEach
    public void before(){
        shoppingCartService = new ShoppingCartServiceImpl(shoppingCartDao, productDao);
        Long productID = 1L;
        newProduct = mockProduct(productID);
        member = mockMember();
        newItem = mockItem(1L,1);
    }

    @Test
    public void WhenAddProductShouldBeMinOneNumberOfInvocation(){
        //when
        shoppingCartService.addProductToCart(newProduct, member);
        //then
        verify(shoppingCartDao).addItemToCart(Mockito.any());

    }
    @Test
    public void WhenAddProductGivenProductMustNotBeNull(){
        Assertions.assertAll(
                () -> assertDoesNotThrow(()-> shoppingCartService.addProductToCart(null, member)),
                () -> assertDoesNotThrow(()-> shoppingCartService.addProductToCart(newProduct, null)),
                () -> assertDoesNotThrow(()-> shoppingCartService.addProductToCart(null, null))

        );
    }

    @Test
    public void WhenAddProductToCartThenReturnItemID(){
        //given
        Long expectedID = 1L;
        when(shoppingCartDao.addItemToCart(newItem)).thenReturn(expectedID);
        //when
        Long itemIDActual = shoppingCartService.addProductToCart(newProduct, member);
        //then
        Assertions.assertEquals(expectedID, itemIDActual);
    }
    @Test
    public void WhenAddProductToCartIfItemExistThenUpdateItemData(){
        //given
        Long expectedID = 1L;
        when(shoppingCartDao.findItem(1L)).thenReturn(newItem);
        when(shoppingCartDao.updateItemInCart(newItem)).thenReturn(true);
        //when
        Long itemIDActual = shoppingCartService.addProductToCart(newProduct, member);
        //then
        assertAll(
                ()-> assertEquals(expectedID, itemIDActual),
                ()-> assertEquals(2, newItem.getQuantity()),
                ()-> assertEquals(25, newItem.getPrice())
        );

    }

    @Test
    public void RemoveProductFromCartShouldBeMinOneNumberOfInvocation(){
        //given
        Item itemToDel = mockItem(1L, 1);
        when(shoppingCartDao.findItem(newItem.getProductId())).thenReturn(newItem);
        when(productDao.findById(newProduct.getId())).thenReturn(newProduct);
        //when
        shoppingCartService.removeItemFromCart(itemToDel);
        //then
        verify(shoppingCartDao, times(1)).findItem(1L);
        verify(shoppingCartDao).removeItemFromCart(Mockito.any());

    }
    @Test
    public void WhenRemoveProductFromCartWhenQuantityMoreThenOneShouldBeMinOneNumberOfInvocation(){
        //given
        Item item = new Item(1L, 2, 12.50, 1L,1L);
        when(shoppingCartDao.findItem(newItem.getItemId())).thenReturn(item);
        when(productDao.findById(newProduct.getId())).thenReturn(newProduct);
        when(shoppingCartDao.updateItemInCart(item)).thenReturn(true);
        //when
        shoppingCartService.removeItemFromCart(item);
        //then
        verify(shoppingCartDao, atLeastOnce()).updateItemInCart(item);
    }
    @Test
    public void WhenRemoveProductFromCartGivenProductMustNotBeNull() {
        //when
        shoppingCartService.removeItemFromCart(null);
        //then
        Assertions.assertDoesNotThrow((ThrowingSupplier<NullPointerException>) NullPointerException::new);
    }

    @Test
    public void WhenRemoveProductFromCartIfReturnNullThenThrowsNullPointerException(){
        //when
        when(shoppingCartDao.findItem(newProduct.getId())).thenReturn(null);
        //then
        assertThrows(NullPointerException.class, ()-> shoppingCartService.removeItemFromCart(newItem));

    }
    @Test
    public void WhenRemoveProductFromCartIfReturnedProductIsNullThenThrowsNullPointerException(){
        //when
        when(productDao.findById(newProduct.getId())).thenReturn(null);
        //then
        assertThrows(NullPointerException.class, ()-> shoppingCartService.removeItemFromCart(newItem));
    }
    @Test
    public void WhenCheckoutItemInCartIfPresentsThenReturnTrue(){
        //given
        Item searchItem = mockItem(1L, 1);
        when(shoppingCartDao.findItem(1L)).thenReturn(newItem);
        //when
        boolean actualResult = shoppingCartService.checkoutItemInCart(searchItem.getItemId());
        //then
        Assertions.assertTrue(actualResult);
    }
    @Test
    public void WhenCheckoutItemInCartIfNotPresentsThenReturnFalse(){
        //given
        Item searchItem = mockItem(5L, 1);
        when(shoppingCartDao.findItem(searchItem.getItemId())).thenReturn(null);
        //when
        boolean actualResult = shoppingCartService.checkoutItemInCart(searchItem.getItemId());
        //then
        Assertions.assertFalse(actualResult);
    }

    @Test
    public void WhenCheckoutItemInCartShouldBeMinOneNumberOfInvocation(){
        //given
        Item itemToCheck = mockItem(1L, 1);
        when(shoppingCartDao.findItem(1L)).thenReturn(newItem);
        //when
        shoppingCartService.checkoutItemInCart(itemToCheck.getItemId());
        //then
        verify(shoppingCartDao,atLeast(1)).findItem(Mockito.any());

    }
    @Test
    public void shouldFindItemByProductID() {
        Long productID = 1L;
        //when
        shoppingCartService.findItemById(1L);
        //then
        verify(shoppingCartDao).findItem(productID);
    }


    private Product mockProduct(Long productID){
        return new Product(productID,"pen", "device", 12.5,5, new ProductCategory(1L, "office", "office aquipment"));
    }
    private Member mockMember(){
        Member member = new Member();
        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        member.setCart(cart);
        return member;
    }

    private Item mockItem(Long ID , int quantity){

        return new Item(ID, quantity, 12.50, 1L, 1L   );
    }
    private List<Item> mockItems(){
        return  new ArrayList<>(){{
            add(new Item(1L, 1, 12.50, 1L, 1L));
            add(new Item(2L, 2, 22.60, 1L, 1L));
            add(new Item(5L, 2, 25.00, 1L,1L));
        }};
    }

}