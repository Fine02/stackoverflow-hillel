package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.entity.product.ProductReview;
import com.ra.course.aws.online.shopping.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {


    private ProductServiceImpl productService;
    private ProductDao productDao = mock(ProductDao.class);
    private Product newProduct;
    private List<Product> mockProductsListFromDao;
    private Product productWithReview;
    private Product productFromDb;

    @BeforeEach
    void before() {
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
    void shouldAddNewProduct() {
        Long expectedNewProductId = 4L;
        //given
        when(productDao.save(newProduct)).thenReturn(expectedNewProductId);
        //when
        Long addedProductID = productService.addNewProduct(newProduct);
        //then
        assertEquals(expectedNewProductId, addedProductID);
    }

    @Test
    void shouldUpdateProduct() {
        newProduct.setAvailableItemCount(0);
        //when
        productService.updateProduct(newProduct);
        //then
        verify(productDao).update(newProduct);
    }

    @Test
    void shouldRemoveProduct() {
        Long productID = 1L;
        //when
        productService.removeProduct(1L);
        //then
        verify(productDao).remove(productID);
    }

    @Test
    void shouldSearchProductsByID() {
        Long productID = 1L;
        //when
        productService.searchProductById(1L);
        //then
        verify(productDao).findById(productID);
    }

    @Test
    void shouldSearchProductsByName() {
        //given
        String productName = "Sunglasses";
        when(productDao.searchProductsByName(productName)).thenReturn(mockProductsListFromDao);
        //when
        List<Product> expectedProductsList = productService.searchProductsByName(productName);
        //then
        for (Product pr : expectedProductsList) {
            Assertions.assertTrue(pr.getName().contains(productName));
        }
    }

    @Test
    void shouldSearchProductsByCategory() {
        //given
        ProductCategory productCategory = new ProductCategory("Accessories", "Accessories description");
        when(productDao.searchProductsByCategory(productCategory)).thenReturn(mockProductsListFromDao);
        //when
        List<Product> expectedProductsList = productService.searchProductsByCategory(productCategory);
        //then
        for (Product pr : expectedProductsList) {
            Assertions.assertEquals(pr.getCategory(), productCategory);
        }
    }

    @Test
    void shouldReturnAllProductsList() {
        //given
        when(productDao.getAll()).thenReturn(mockProductsListFromDao);
        //when
        List<Product> expectedAllProducts = productService.getAllProducts();
        //then
        Assertions.assertEquals(expectedAllProducts.size(), mockProductsListFromDao.size());
    }

    /*------------------------------------*/


    @Test
    void WhenAddProductReviewShouldBeMinOneNumberOfInvocationForUpdateProduct() {
        //given
        when(productDao.findById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductReview(productWithReview);
        //then
        verify(productDao, times(1)).update(productFromDb);

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
        when(productDao.findById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductReview(productWithReview);
        //then
        verify(productDao).findById(Mockito.any());
    }

    @Test
    void WhenAddProductReviewProductInDBShouldBeWithNewReview() {
        //given
        when(productDao.findById(5L)).thenReturn(productFromDb);
        //when
        productService.addProductReview(productWithReview);
        //then
        Assertions.assertEquals(productWithReview, productFromDb);
    }

    @Test
    void WhenAddProductReviewShouldReturnNothing() {
        //given
        when(productDao.findById(productWithReview.getId())).thenReturn(productFromDb);
        doNothing().when(productDao).update(isA(Product.class));
        //when
        productService.addProductReview(productWithReview);
        //then
        verify(productDao, times(1)).update(productFromDb);

    }

    @Test
    void WhenAddProductRatingShouldBeMinOneNumberOfInvocationForUpdateProduct() {
        //given
        when(productDao.findById(5L)).thenReturn(productWithReview);
        //when
        productService.addProductRating(productWithReview, 9);
        //then
        verify(productDao, times(1)).update(productWithReview);

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
        when(productDao.findById(5L)).thenReturn(productWithReview);
        //when
        productService.addProductRating(productWithReview, 9);
        //then
        Assertions.assertEquals(9, productWithReview.getProductReview().getRating());

    }

    @Test
    void WhenAddProductRatingIfReturnedProductHasNoReviewAddingDefaultReviewWithNewRating() {
        //given
        ProductReview defaultProductReview = new ProductReview(5L, 9, "");
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
