package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.exceptions.ProductNotFoundException;
import com.ra.course.aws.online.shopping.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    private ProductServiceImpl productService;
    private ProductDao productDao = mock(ProductDao.class);
    private Product someProduct;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productDao);
        someProduct = new Product(1L, "Sunglasses", "RayBan Wayfarer", 199.9,
                10, new ProductCategory("Accessories", "Accessories description"));
    }

    @Test
    @DisplayName("Should return saved product id")
    void shouldAddNewProduct() {
        Long expectedNewProductId = 1L;
        //given
        when(productDao.save(someProduct)).thenReturn(expectedNewProductId);
        //when
        Long addedProductID = productService.save(someProduct);
        //then
        assertEquals(expectedNewProductId, addedProductID);
    }

    @Test
    @DisplayName("Should return true when product updated")
    void shouldUpdateExistingProduct() {
        //given
        Product productToUpdate = new Product(someProduct);
        productToUpdate.setAvailableItemCount(0);
        when(productDao.findById(productToUpdate.getId())).thenReturn(someProduct);
        //when
        boolean result = productService.update(productToUpdate);
        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should throw an exception when product to update does not exist")
    void shouldNotUpdateProduct() {
        //given
        when(productDao.findById(someProduct.getId())).thenReturn(null);
        //when
        Exception exception = assertThrows(ProductNotFoundException.class, () -> productService.update(someProduct));
        //then
        String expectedMessage = "Product with id=" + someProduct.getId() + " not found";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should return true when product removed")
    void shouldRemoveProduct() {
        Long productToRemoveId = 1L;
        when(productDao.findById(productToRemoveId)).thenReturn(someProduct);
        //when
        boolean result = productService.remove(productToRemoveId);
        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should throw an exception when product to remove does not exist")
    void shouldNotRemoveProduct() {
        Long productToRemoveId = 999L;
        //given
        when(productDao.findById(productToRemoveId)).thenReturn(null);
        //when
        Exception exception = assertThrows(ProductNotFoundException.class, () -> productService.remove(productToRemoveId));
        //then
        String expectedMessage = "Product with id = " + productToRemoveId + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Finded product should be equal to expected")
    void shouldSearchProductsByID() {
        //given
        Long productId = 1L;
        Product expectedProduct = someProduct;
        when(productDao.findById(productId)).thenReturn(expectedProduct);
        //when
        Product findedProduct = productService.findByID(productId);
        //then
        assertEquals(expectedProduct, findedProduct);
    }
}


