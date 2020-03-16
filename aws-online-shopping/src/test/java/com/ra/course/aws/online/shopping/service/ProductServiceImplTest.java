package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.entity.product.ProductReview;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.mockito.Mockito;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductServiceImpl productService;
    private ProductDao productDao = mock(ProductDao.class);
    private Product productWithReview;
    private Product productFromDb;

    @BeforeEach
    public void before(){
        productService = new ProductServiceImpl(productDao);
        productWithReview  = getMockProductWithReview(5L);
        productFromDb = getMockProductFromDbt(5L);
    }

    @Test
    void WhenAddProductReviewShouldBeMinOneNumberOfInvocationForUpdateProduct() {
        //given
        when(productDao.searchProductById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductReview(productWithReview);
        //then
        verify(productDao, times(1)).updateProduct(productFromDb);

    }
    @Test
    void WhenAddProductReviewGivenProductShouldNotBeNull() {
        //when
        productService.addProductReview(null);
        //then
        Assertions.assertDoesNotThrow((ThrowingSupplier<NullPointerException>) NullPointerException::new);

    }
    @Test
    void WhenAddProductReviewShouldBeAnyInvocationForMethod() {
        //given
        when(productDao.searchProductById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductReview(productWithReview);
        //then
        verify(productDao).searchProductById(Mockito.any());
    }
    @Test
    void WhenAddProductReviewProductInDBShouldBeWithNewReview() {
        //given
        when(productDao.searchProductById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductReview(productWithReview);
        //then
        Assertions.assertEquals(productWithReview, productFromDb);
    }

    @Test
    void WhenAddProductReviewShouldReturnNothing() {
        //given
        when(productDao.searchProductById(productWithReview.getProductID())).thenReturn(productFromDb);
        doNothing().when(productDao).updateProduct(isA(Product.class));
        //when
        productService.addProductReview(productWithReview);
        //then
        verify(productDao, times(1)).updateProduct(productFromDb);

    }
    @Test
    void WhenAddProductRatingShouldBeMinOneNumberOfInvocationForUpdateProduct() {
        //given
        when(productDao.searchProductById(5L)).thenReturn(productWithReview);
        //when
        productService.addProductRating(productWithReview, 9);
        //then
        verify(productDao, times(1)).updateProduct(productWithReview);

    }
    @Test
    void WhenAddProductRatingGivenProductShouldNotBeNull() {
        //when
        productService.addProductRating(null, 1);
        //then
        Assertions.assertDoesNotThrow((ThrowingSupplier<NullPointerException>) NullPointerException::new);

    }
    @Test
    void WhenAddProductRatingShouldBeSavedWithNewRating() {
        //given
        when(productDao.searchProductById(5L)).thenReturn(productWithReview);
        //when
        productService.addProductRating(productWithReview, 9);
        //then
        Assertions.assertEquals(9, productWithReview.getProductReview().getRating());

    }
    @Test
    void WhenAddProductRatingIfReturnedProductHasNoReviewAddingDefaultReviewWithNewRating() {
        //given
        ProductReview defaultProductReview = new ProductReview(5L, 9,"");
        when(productDao.searchProductById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductRating(productFromDb, 9);
        //then
        Assertions.assertEquals(defaultProductReview, productFromDb.getProductReview());

    }

    private Product getMockProductWithReview(Long productID) {
        Product productWithReview = new Product(productID, "pen", "device", 12.5, 5, new ProductCategory(1L,"office", "office aquipment"));
        productWithReview.setProductReview(new ProductReview(1L,10, "someReview"));

        return productWithReview;
    }
    private Product getMockProductFromDbt(Long productID) {
        return new Product(productID, "pen", "device", 12.5, 5, new ProductCategory(1L,"office", "office aquipment"));

    }
}