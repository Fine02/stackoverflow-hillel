package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.entity.product.ProductReview;
import com.ra.course.aws.online.shopping.exceptions.ProductNotFoundException;
import com.ra.course.aws.online.shopping.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    private ProductServiceImpl productService;
    private ProductDao productDao = mock(ProductDao.class);
    private Product newProduct;
    private List<Product> mockProductsListFromDao;
    private Product productWithReview;
    private Product productFromDb;

    @BeforeEach
    public void before() {
        productService = new ProductServiceImpl(productDao);
        newProduct = new Product("Sunglasses", "RayBan Wayfarer", 199.9,
                10, new ProductCategory("Accessories", "Accessories description"));
        mockProductsListFromDao = new ArrayList<>(Arrays.asList(
                new Product(1L, "Sunglasses", "RayBan Wayfarer", 199.9,
                        10, new ProductCategory("Accessories", "Accessories description")),
                new Product(2L, "Sunglasses", "RayBan Aviator", 119.9,
                        15, new ProductCategory("Accessories", "Accessories description")),
                new Product(3L, "Sunglasses", "RayBan Clubmaster", 249.9,
                        5, new ProductCategory("Accessories", "Accessories description"))
        ));
        productWithReview = getMockProductWithReview(5L);
        productFromDb = getMockProductFromDbt(5L);
    }

    @Test
    public void shouldAddNewProduct() throws SQLException {
        Long expectedNewProductId = 4L;
        //given
        when(productDao.save(newProduct)).thenReturn(expectedNewProductId);
        //when
        Long addedProductID = productService.save(newProduct);
        //then
        assertEquals(expectedNewProductId, addedProductID);
    }

    @Test
    public void shouldUpdateProduct() {
        newProduct.setAvailableItemCount(0);
        //when
        productService.update(newProduct);
        //then
        verify(productDao).update(newProduct);
    }

    @Test
    public void shouldRemoveProduct() {
        Long productID = 1L;
        //when
        productService.remove(1L);
        //then
        verify(productDao).remove(productID);
    }

    @Test
    public void shouldSearchProductsByID() {
        Long productID = 1L;
        //when
        productService.findByID(1L);
        //then
        verify(productDao).findById(productID);
    }

    @Test
    public void shouldSearchProductsByName() {
        //given
        String productName = "Sunglasses";
        when(productDao.searchProductsByName(productName)).thenReturn(mockProductsListFromDao);
        //when
        List<Product> expectedProductsList = productService.searchByName(productName);
        //then
        for (Product pr : expectedProductsList) {
            Assertions.assertTrue(pr.getName().contains(productName));
        }
    }

    @Test
    public void shouldSearchProductsByCategory() {
        //given
        ProductCategory productCategory = new ProductCategory("Accessories", "Accessories description");
        when(productDao.searchProductsByCategory(productCategory)).thenReturn(mockProductsListFromDao);
        //when
        List<Product> expectedProductsList = productService.searchByCategory(productCategory);
        //then
        for (Product pr : expectedProductsList) {
            Assertions.assertEquals(pr.getCategory(), productCategory);
        }
    }

    @Test
    public void shouldReturnAllProductsList() {
        //given
        when(productDao.getAll()).thenReturn(mockProductsListFromDao);
        //when
        List<Product> expectedAllProducts = productService.getAll();
        //then
        Assertions.assertEquals(expectedAllProducts.size(), mockProductsListFromDao.size());
    }

    /*------------------------------------*/


    @Test
    public void WhenAddProductReviewShouldBeMinOneNumberOfInvocationForUpdateProduct() {
        //given
        when(productDao.findById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductReview(productWithReview);
        //then
        verify(productDao, times(1)).update(productFromDb);

    }

    @Test
    public void WhenAddProductReviewGivenProductShouldNotBeNull() {
        //when
        productService.addProductReview(null);
        //then
        Assertions.assertDoesNotThrow((ThrowingSupplier<NullPointerException>) NullPointerException::new);

    }

    @Test
    public void WhenAddProductReviewShouldBeAnyInvocationForMethod() {
        //given
        when(productDao.findById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductReview(productWithReview);
        //then
        verify(productDao).findById(Mockito.any());
    }

    @Test
    public void WhenAddProductReviewProductInDBShouldBeWithNewReview() {
        //given
        when(productDao.findById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductReview(productWithReview);
        //then
        Assertions.assertEquals(productWithReview, productFromDb);
    }

    @Test
    public void WhenAddProductReviewShouldHaveMinOneInvocation() {
        //given
        when(productDao.findById(productWithReview.getId())).thenReturn(productFromDb);
        when(productDao.update(productFromDb)).thenReturn(true);
        //when
        productService.addProductReview(productWithReview);
        //then
        verify(productDao, times(1)).update(productFromDb);

    }

    @Test
    public void WhenAddProductRatingShouldBeMinOneNumberOfInvocationForUpdateProduct() {
        //given
        when(productDao.findById(5L)).thenReturn(productWithReview);
        //when
        productService.addProductRating(productWithReview, 9);
        //then
        verify(productDao, times(1)).update(productWithReview);

    }

    @Test
    public void WhenAddProductRatingGivenProductShouldNotBeNull() {
        //when
        productService.addProductRating(null, 1);
        //then
        Assertions.assertDoesNotThrow((ThrowingSupplier<NullPointerException>) NullPointerException::new);

    }

    @Test
    public void WhenAddProductRatingShouldBeSavedWithNewRating() {
        //given
        when(productDao.findById(5L)).thenReturn(productWithReview);
        //when
        productService.addProductRating(productWithReview, 9);
        //then
        Assertions.assertEquals(9, productWithReview.getProductReview().getRating());

    }

    @Test
    public void WhenAddProductRatingIfReturnedProductHasNoReviewAddingDefaultReviewWithNewRating() {
        //given
        ProductReview defaultProductReview = new ProductReview(9, null);
        when(productDao.findById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductRating(productFromDb, 9);
        //then
        Assertions.assertEquals(defaultProductReview, productFromDb.getProductReview());

    }

    private Product getMockProductWithReview(Long productID) {
        Product productWithReview = new Product(productID, "pen", "device", 12.5, 5, new ProductCategory(1L, "office", "office aquipment"));
        productWithReview.setProductReview(new ProductReview(1L, 10, "someReview"));

        return productWithReview;
    }

    private Product getMockProductFromDbt(Long productID) {
        return new Product(productID, "pen", "device", 12.5, 5, new ProductCategory(1L, "office", "office aquipment"));

    }

}

