package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ShoppingCartDao;
import com.ra.course.aws.online.shopping.entity.Item;
import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
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
    private final Long productID = 1L;
    Product newProduct;
    Item newItem;



    @BeforeEach
    public void before(){
        shoppingCartService = new ShoppingCartServiceImpl(shoppingCartDao);
        newProduct = mockProduct(productID);
        newItem = mockItem(1L,1);
    }

    @Test
    public void WhenAddProductShouldBeMinOneNumberOfInvocation(){
        //when
        shoppingCartService.addProductToCart(newProduct);
        //then
        verify(shoppingCartDao).addItemToCart(Mockito.any());

    }
    @Test
    public void WhenAddProductGivenProductMustNotBeNull(){
        //when
        Product productForDel = null;
        //then
        Assertions.assertThrows(NullPointerException.class, () -> shoppingCartService.addProductToCart(productForDel));
    }

    @Test
    public void WhenAddProductToCartThenReturnProductID(){
        //given
        Long expectedID = 1L;
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
        when(shoppingCartDao.getItemFromCart(mockProduct.getId())).thenReturn(newItem);
        //when
        shoppingCartService.removeProductFromCart(mockProduct);
        //then
        verify(shoppingCartDao, times(1)).getItemFromCart(1L);
        verify(shoppingCartDao).removeItemFromCart(Mockito.any());

    }
    @Test
    public void WhenRemoveProductFromCartWhenQuantityMoreThenOneShouldBeMinOneNumberOfInvocation(){
        //given
        Item itemFromDB2 = new Item(1L, 2, 12.5);
        when(shoppingCartDao.getItemFromCart(newProduct.getId())).thenReturn(itemFromDB2);
        //when
        shoppingCartService.removeProductFromCart(newProduct);
        //then
        verify(shoppingCartDao, atLeastOnce()).updateItemInCart(itemFromDB2);
    }
    @Test
    public void WhenRemoveProductFromCartGivenProductMustNotBeNull() {
        //when
        shoppingCartService.removeProductFromCart(null);
        //then
        Assertions.assertDoesNotThrow((ThrowingSupplier<NullPointerException>) NullPointerException::new);
    }

    @Test
    public void WhenRemoveProductFromCartIfCatchExceptionThenThrowsElementNotFoundException(){
        //given
        when(shoppingCartDao.getItemFromCart(newProduct.getId())).thenThrow(new IllegalArgumentException());
        //when
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> shoppingCartService.removeProductFromCart(newProduct));
        //then
        assertEquals(exception.getClass(), ElementNotFoundException.class);
    }
    @Test
    public void WhenCheckoutItemInCartIfPresentsThenReturnTrue(){
        //given
        Item searchItem = mockItem(1L, 1);
        when(shoppingCartDao.getItemFromCart(1L)).thenReturn(newItem);
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
        when(shoppingCartDao.getItemFromCart(1L)).thenReturn(newItem);
        //when
        shoppingCartService.checkoutItemInCart(itemToCheck);
        //then
        verify(shoppingCartDao,atLeast(1)).getItemFromCart(Mockito.any());

    }

    @Test
    public void WhenCheckoutItemInCartGivenItemMustNotBeNull(){
        //when
        Item itemToCheck = null;
        //then
        Assertions.assertThrows(NullPointerException.class, () -> shoppingCartService.checkoutItemInCart(itemToCheck));

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
        return new Product(productID,"pen", "device", 12.5,5, new ProductCategory(1L, "office", "office aquipment"));
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