package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ShoppingCartDao;
import com.ra.course.aws.online.shopping.entity.Item;
import com.ra.course.aws.online.shopping.entity.payment.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.exception.ElementNotFoundException;
import com.ra.course.aws.online.shopping.exception.ObjectRequireNotBeNullException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class ShoppingCartServiceImplTest {
    private ShoppingCartServiceImpl shoppingCartService;
    private ShoppingCartDao shoppingCartDao = mock(ShoppingCartDao.class);
    private final Long productID = 1L;



    @BeforeEach
    public void before(){
        shoppingCartService = new ShoppingCartServiceImpl(shoppingCartDao);
    }

    @Test
    public void WhenAddProductShouldBeMinOneNumberOfInvocation(){
        //given
        Product mockProduct = mockProduct(productID);
        //when
        shoppingCartService.addProductToCart(mockProduct);
        //then
        verify(shoppingCartDao).addItemToCart(Mockito.any());

    }
    @Test
    public void WhenAddProductGivenProductMustNotBeNull(){
        //given
        Product productForDel = null;
        //when
        Throwable exception = Assertions.assertThrows(NullPointerException.class, () -> {
            shoppingCartService.addProductToCart(productForDel);
        });
        //then
        assertEquals(exception.getMessage(), "given product must not be Null!");
        assertEquals(exception.getClass(), ObjectRequireNotBeNullException.class);
    }

    @Test
    public void WhenAddProductToCartThenReturnProductID(){
        //given
        Long expectedID = 1L;
        Item newItem = mockItem(1L,1);
        Product newProduct = mockProduct(productID);
        when(shoppingCartDao.addItemToCart(newItem)).thenReturn(expectedID);
        //when
        Long productIDActual = shoppingCartService.addProductToCart(newProduct);
        //then
        Assertions.assertEquals(expectedID, productIDActual);
    }

    @Test
    public void RemoveProductFromCartShouldBeMinOneNumberOfInvocation(){
        //given
        Product mockProduct = mockProduct(productID);
        Item itemFromDB = mockItem(1L,1);
        when(shoppingCartDao.getItemFromCart(mockProduct.getProductID())).thenReturn(itemFromDB);
        //when
        shoppingCartService.removeProductFromCart(mockProduct);
        //then
        verify(shoppingCartDao, times(1)).getItemFromCart(1L);
        verify(shoppingCartDao).removeItemFromCart(Mockito.any());

    }
    @Test
    public void WhenRemoveProductFromCartWhenQuantityMoreThenOneShouldBeMinOneNumberOfInvocation(){
        //given
        Product productForDel = mockProduct(productID);
        Item itemFromDB2 = new Item(1L, 2, 12.5);
        when(shoppingCartDao.getItemFromCart(productForDel.getProductID())).thenReturn(itemFromDB2);
        //when
        shoppingCartService.removeProductFromCart(productForDel);
        //then
        verify(shoppingCartDao, atLeastOnce()).updateItemInCart(itemFromDB2);
    }
    @Test
    public void WhenRemoveProductFromCartGivenProductMustNotBeNull(){
        //given
        Product productForDel = null;
        //when
        Throwable exception = Assertions.assertThrows(NullPointerException.class, () -> {
            shoppingCartService.removeProductFromCart(productForDel);
        });
        //then
        assertEquals(exception.getMessage(), "given productToRemove must not be Null!");
        assertEquals(exception.getClass(), ObjectRequireNotBeNullException.class);
    }
    @Test
    public void WhenRemoveProductFromCartIfCatchExceptionThenThrowsElementNotFoundException(){
        //given
        Product productForDel = mockProduct(productID);
        when(shoppingCartDao.getItemFromCart(productForDel.getProductID())).thenThrow(new IllegalArgumentException());
        //when
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            shoppingCartService.removeProductFromCart(productForDel);
        });
        //then
        assertEquals(exception.getClass(), ElementNotFoundException.class);
    }
    @Test
    public void WhenCheckoutItemInCartIfPresentsThenReturnTrue(){
        //given
        Item itemFromDB = mockItem(1L, 1);
        Item searchItem = mockItem(1L, 1);
        when(shoppingCartDao.getItemFromCart(1L)).thenReturn(itemFromDB);
        //when
        boolean actualResult = shoppingCartService.checkoutItemInCart(searchItem);
        //then
        Assertions.assertTrue(actualResult);
    }
    @Test
    public void WhenCheckoutItemInCartIfNotPresentsThenReturnFalse(){
        //given
        Item itemFromDB = mockItem(5L, 0);
        Item searchItem = mockItem(5L, 1);
        List<Item> items = mockItems();
        when(shoppingCartDao.getItemFromCart(searchItem.getProductID())).thenReturn(itemFromDB);
        //when
        boolean actualResult = shoppingCartService.checkoutItemInCart(searchItem);
        //then
        Assertions.assertFalse(actualResult);
    }

    @Test
    public void WhenCheckoutItemInCartShouldBeMinOneNumberOfInvocation(){
        //given
        Item itemToCheck = mockItem(1L, 1);
        Item itemFromDB = mockItem(1L, 1);
        when(shoppingCartDao.getItemFromCart(1L)).thenReturn(itemFromDB);
        //when
        shoppingCartService.checkoutItemInCart(itemToCheck);
        //then
        verify(shoppingCartDao,atLeast(1)).getItemFromCart(Mockito.any());

    }
    @Test
    public void WhenCheckoutItemInCartGivenItemMustNotBeNull(){
        //given
        Item itemToCheck = null;
        //when
        Throwable exception = Assertions.assertThrows(NullPointerException.class, () -> {
            shoppingCartService.checkoutItemInCart(itemToCheck);
        });
        //then
        assertEquals(exception.getMessage(), "given item must not be Null!");


    }
    @Test
    public void WhenCheckoutItemInCartIfCatchExceptionThenThrowsElementNotFoundException(){
        //given
        Item item = mockItem(5L, 2);
        when(shoppingCartDao.getItemFromCart(item.getProductID())).thenThrow(new IllegalArgumentException());
        //when
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            shoppingCartService.checkoutItemInCart(item);
        });
        //then
        assertEquals(exception.getClass(), ElementNotFoundException.class);
    }
    @Test
    public void WhenBuyItemsInCartShouldBeMinOneNumberOfInvocation(){
        //given
        List<Item> itemsInCart = mockItems();
        when(shoppingCartDao.getAllItems()).thenReturn(itemsInCart);
        //when
        shoppingCartService.buyItems();
        //then
        verify(shoppingCartDao,atLeast(1)).makePayment(Mockito.any());
    }
    @Test
    public void WhenBuyItemsInCartThenReturnedPaymentStatusCOMPLETED(){
        //given
        PaymentStatus expected = PaymentStatus.COMPLETED;
        List<Item> itemsInCart = mockItems();
        when(shoppingCartDao.getAllItems()).thenReturn(itemsInCart);
        when(shoppingCartDao.makePayment(itemsInCart)).thenReturn(PaymentStatus.COMPLETED);
        //when
        PaymentStatus actual = shoppingCartService.buyItems();
        //then
        Assertions.assertEquals(expected,actual);
    }

    private Product mockProduct(Long productID){
        return new Product(productID,"pen", "device", 12.5,5, new ProductCategory("office", "office aquipment"));
    }
    private Product mockProduct2(Long productID_InDB2){
        return new Product(productID_InDB2,"pencil", "device", 5,5, new ProductCategory("office", "office aquipment"));
    }
    private Item mockItem(Long ID , int quantity){
        return new Item(ID, quantity, 12.5);
    }
    private List<Item> mockItems(){
        return  new ArrayList<>(){{
            add(new Item(1L, 1, 12.5));
            add(new Item(2L, 2, 22.6));
            add(new Item(5L, 2, 25.0));
        }};
    }

}