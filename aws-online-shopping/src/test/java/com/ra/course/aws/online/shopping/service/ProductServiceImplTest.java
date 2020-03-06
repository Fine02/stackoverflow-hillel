package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.entity.product.ProductReview;
import com.ra.course.aws.online.shopping.exception.ObjectRequireNotBeNullException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductServiceImpl productService;
    private ProductDao productDao = mock(ProductDao.class);

    @BeforeEach
    public void before(){
        productService = new ProductServiceImpl(productDao);
    }

    @Test
    void WhenAddProductReviewShouldBeMinOneNumberOfInvocationForUpdateProduct() {
        //given
        Product newProduct = getMockProduct(5L);
        Product productFromDb = getProductFromDbt(5L);
        when(productDao.searchProductById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductReview(newProduct);
        //then
        verify(productDao, times(1)).updateProduct(productFromDb);

    }
    @Test
    void WhenAddProductReviewShouldBeAnyInvocationForMethod() {
        //given
        Product newProduct = getMockProduct(5L);
        Product productFromDb = getProductFromDbt(5L);
        when(productDao.searchProductById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductReview(newProduct);
        //then
        verify(productDao).searchProductById(Mockito.any());
    }
    @Test
    void WhenAddProductReviewProductInDBShouldBeWithNewReview() {
        //given
        Product newProduct = getMockProduct(5L);
        Product productFromDb = getProductFromDbt(5L);
        when(productDao.searchProductById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductReview(newProduct);
        //then
        Assertions.assertEquals(newProduct, productFromDb);
    }

    @Test
    void WhenAddProductReviewShouldReturnNothing() {
        //given
        Product newProduct = getMockProduct(5L);
        Product productFromDb = getProductFromDbt(5L);
        when(productDao.searchProductById(newProduct.getProductID())).thenReturn(productFromDb);
        doNothing().when(productDao).updateProduct(isA(Product.class));
        //when
        productService.addProductReview(newProduct);
        //then
        verify(productDao, times(1)).updateProduct(productFromDb);

    }
    @Test
    void WhenAddProductReviewGivenProductMustNotBeNull() {
        //given
        Product productForDel = null;
        //when
        Throwable exception = Assertions.assertThrows(NullPointerException.class, () -> {
            productService.addProductReview(productForDel);
        });
        //then
        assertEquals(exception.getMessage(), "given product must not be Null!");
        assertEquals(exception.getClass(), ObjectRequireNotBeNullException.class);

    }



    private Product getMockProduct(Long productID) {
        Product productWithReview = new Product(productID, "pen", "device", 12.5, 5, new ProductCategory("office", "office aquipment"));
        productWithReview.setProductReview(new ProductReview(10, "someReview"));

        return productWithReview;
    }
    private Product getProductFromDbt(Long productID) {
        Product product = new Product(productID, "pen", "device", 12.5, 5, new ProductCategory("office", "office aquipment"));

        return product;
    }
}