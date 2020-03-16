package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.Catalog;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.exceptions.ProductNotFoundException;
import com.ra.course.aws.online.shopping.service.impl.CatalogServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CatalogServiceImplTest {

    private CatalogServiceImpl catalogService;
    private ProductDao productDao = mock(ProductDao.class);

    @BeforeEach
    void before() {
        List<Product> mockProductsListFromDao = new ArrayList<>(Arrays.asList(
                new Product(1L, "Sunglasses", "RayBan Wayfarer", 199.9,
                        11, new ProductCategory("Accessories", "Accessories description")),
                new Product(2L, "Sunglasses", "RayBan Aviator", 119.9,
                        15, new ProductCategory("Accessories", "Accessories description")),
                new Product(3L, "Sunglasses", "RayBan Clubmaster", 249.9,
                        5, new ProductCategory("Accessories", "Accessories description")),
                new Product(4L, "Perfume", "Amouage Interlude", 299.9,
                        100, new ProductCategory("Fragnances", "Fragnanses description")),
                new Product(5L, "Perfume", "Tom Ford Oud Wood", 199.9,
                        45, new ProductCategory("Fragnances", "Fragnanses description")),
                new Product(6L, "Perfume", "Nasomatto Black Afgano", 169.9,
                        8, new ProductCategory("Fragnances", "Fragnanses description")),
                new Product(7L, "Trainers", "Adidas Yung 1", 129.9,
                        55, new ProductCategory("Shoes", "Shoes description")),
                new Product(8L, "Trainers", "Nike AirForce", 119.9,
                        180, new ProductCategory("Shoes", "Shoes description")),
                new Product(9L, "Trainers", "Asics Gel Lyte V", 139.9,
                        161, new ProductCategory("Shoes", "Shoes description"))
        ));
        when(productDao.getAllProducts()).thenReturn(mockProductsListFromDao);
        catalogService = new CatalogServiceImpl(productDao);
    }

    @Test
    void shouldCreateCatalog() {
        //when
        Catalog catalog = catalogService.getCatalog();
        //then
        Assertions.assertAll(
                () -> assertTrue(catalog.getProductNames().keySet().containsAll(Arrays.asList("Sunglasses", "Trainers", "Perfume"))),
                () -> assertTrue(catalog.getProductCategories().keySet().containsAll(Arrays.asList("Accessories", "Fragnances", "Shoes")))
        );
    }

    @Test
    void shouldUpdateCatalogWithNewProduct() {
        //given
        Product newProduct = new Product(10L, "Jeans", "Levis 501", 119.9,
                10, new ProductCategory("Clothes", "Clothes description"));
        when(productDao.searchProductById(newProduct.getProductID())).thenReturn(newProduct);
        //when
        catalogService.updateCatalogWithNewProduct(newProduct.getProductID());
        //then
        Assertions.assertAll(
                () -> assertTrue(catalogService.getCatalog().getProductNames().containsKey(newProduct.getName())),
                () -> assertTrue(catalogService.getCatalog().getProductNames().get(newProduct.getName()).contains(newProduct)),
                () -> assertTrue(catalogService.getCatalog().getProductCategories().containsKey(newProduct.getCategory().getName())),
                () -> assertTrue(catalogService.getCatalog().getProductCategories().get(newProduct.getCategory().getName()).contains(newProduct))
        );
    }

    @Test
    void shouldUpdateCatalogWithModifiedProduct() {
        Product modifiedProduct = new Product(1L, "Polarized Sunglasses", "RayBan Wayfarer P", 225.9,
                29, new ProductCategory("Accessories", "Accessories description"));
        //given
        when(productDao.searchProductById(modifiedProduct.getProductID())).thenReturn(modifiedProduct);
        //when
        catalogService.updateCatalogWithModifiedProduct(modifiedProduct.getProductID());
        //then
        Product updatedProductInProductNamesMap = catalogService.getCatalog()
                .getProductNames()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(product -> product.getProductID().equals(modifiedProduct.getProductID())).findAny().orElse(null);

        Product updatedProductInProductCategoriesMap = catalogService.getCatalog()
                .getProductCategories()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(product -> product.getProductID().equals(modifiedProduct.getProductID())).findAny().orElse(null);
        Assertions.assertAll(
                () -> assertEquals(updatedProductInProductNamesMap, modifiedProduct),
                () -> assertEquals(updatedProductInProductCategoriesMap, modifiedProduct)
        );
    }

    @Test
    void shouldRemoveProduct() {
        final Long productToRemoveId = 1L;
        //when
        catalogService.removeProductFromCatalog(productToRemoveId);
        //then
        Assertions.assertAll(
                () -> assertNull(catalogService.getCatalog()
                        .getProductNames()
                        .values()
                        .stream()
                        .flatMap(Collection::stream)
                        .filter(product -> product.getProductID().equals(productToRemoveId)).findAny().orElse(null)),

                () -> assertNull(catalogService.getCatalog()
                        .getProductCategories()
                        .values()
                        .stream()
                        .flatMap(Collection::stream)
                        .filter(product -> product.getProductID().equals(productToRemoveId)).findAny().orElse(null))
        );
    }

    @Test
    void shouldThrowExceptionIfProductToRemoveDoesNotExist() {
        final Long absentID = 111L;
        Exception exception = assertThrows(ProductNotFoundException.class, () ->
                catalogService.removeProductFromCatalog(absentID)
        );
        String expectedMessage = "Product with id = " + absentID + " not found";
        String actualMessage = exception.getMessage();
        //then
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}

