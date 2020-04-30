package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.entity.Catalog;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import com.ra.course.aws.online.shopping.exceptions.ProductNotFoundException;
import com.ra.course.aws.online.shopping.service.impl.CatalogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CatalogServiceImplIntegrationTest {

    private CatalogServiceImpl catalogService;
    private ProductDao productDao = mock(ProductDao.class);
    private List<Product> mockProductsListFromDao;

    @BeforeEach
    void before() {
        mockProductsListFromDao = new ArrayList<>(Arrays.asList(
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
        when(productDao.getAll()).thenReturn(mockProductsListFromDao);
        catalogService = new CatalogServiceImpl(productDao);
        catalogService.createCatalog();
    }

    @Test
    @DisplayName("Should return catalog filled with data from DB when CatalogService instance created")
    void shouldCreateCatalog() {
        //when
        Catalog catalog = catalogService.getCatalog();
        //then
        assertAll(
                () -> assertTrue(catalog.getProductsByNames().keySet().containsAll(Arrays.asList("Sunglasses", "Trainers", "Perfume"))),
                () -> assertTrue(catalog.getProductsByCategories().keySet().containsAll(Arrays.asList("Accessories", "Fragnances", "Shoes")))
        );
    }

    @Test
    @DisplayName("Catalog should contain new product when updated with it, method should return true")
    void shouldUpdateCatalogWithNewProduct() {
        //given
        Product newProduct = new Product(10L, "Jeans", "Levis 501", 119.9,
                10, new ProductCategory("Clothes", "Clothes description"));
        when(productDao.findById(newProduct.getId())).thenReturn(newProduct);
        //when
        boolean result = catalogService.updateCatalogWithNewProduct(newProduct.getId());
        //then
        assertAll(
                () -> assertTrue(result),
                () -> assertTrue(catalogService.getCatalog().getProductsByNames().containsKey(newProduct.getName())),
                () -> assertTrue(catalogService.getCatalog().getProductsByNames().get(newProduct.getName()).contains(newProduct)),
                () -> assertTrue(catalogService.getCatalog().getProductsByCategories().containsKey(newProduct.getCategory().getName())),
                () -> assertTrue(catalogService.getCatalog().getProductsByCategories().get(newProduct.getCategory().getName()).contains(newProduct))
        );
    }

    @Test
    @DisplayName("Modified product`s fields in catalog should be updated, method should return true")
    void shouldUpdateCatalogWithModifiedProduct() {
        Product modifiedProduct = new Product(1L, "Polarized Sunglasses", "RayBan Wayfarer P", 225.9,
                29, new ProductCategory("Accessories", "Accessories description"));
        //given
        when(productDao.findById(modifiedProduct.getId())).thenReturn(modifiedProduct);
        //when
        boolean result = catalogService.updateCatalogWithModifiedProduct(modifiedProduct.getId());
        //then
        Product updatedProductInProductNamesMap = catalogService.getCatalog()
                .getProductsByNames()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(product -> product.getId().equals(modifiedProduct.getId())).findAny().orElse(null);

        Product updatedProductInProductCategoriesMap = catalogService.getCatalog()
                .getProductsByCategories()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(product -> product.getId().equals(modifiedProduct.getId())).findAny().orElse(null);

        assertAll(
                () -> assertTrue(result),
                () -> assertEquals(updatedProductInProductNamesMap, modifiedProduct),
                () -> assertEquals(updatedProductInProductCategoriesMap, modifiedProduct)
        );
    }

    @Test
    @DisplayName("Catalog should not contain removed product, method should return true")
    void shouldRemoveProduct() {
        final Long productToRemoveId = 1L;
        //when
        boolean result = catalogService.removeProductFromCatalog(productToRemoveId);
        //then
        assertAll(
                () -> assertTrue(result),

                () -> assertNull(catalogService.getCatalog()
                        .getProductsByNames()
                        .values()
                        .stream()
                        .flatMap(Collection::stream)
                        .filter(product -> product.getId().equals(productToRemoveId)).findAny().orElse(null)),

                () -> assertNull(catalogService.getCatalog()
                        .getProductsByCategories()
                        .values()
                        .stream()
                        .flatMap(Collection::stream)
                        .filter(product -> product.getId().equals(productToRemoveId)).findAny().orElse(null))
        );
    }

    @Test
    @DisplayName("Should throw exception when product to remove does not exist")
    void shouldThrowExceptionIfProductToRemoveDoesNotExist() {
        //given
        final Long absentID = 111L;
        //when
        Exception exception = assertThrows(ProductNotFoundException.class, () ->
                catalogService.removeProductFromCatalog(absentID)
        );
        String expectedMessage = "Product with id = " + absentID + " not found";
        String actualMessage = exception.getMessage();
        //then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Products returned should have following names")
    void shouldSearchProductsByName() {
        //given
        String productName = "Sunglasses";
        //when
        List<Product> expectedProductsList = catalogService.searchProductsByName(productName);
        //then
        for (Product pr : expectedProductsList) {
            assertTrue(pr.getName().contains(productName));
        }
    }

    @Test
    @DisplayName("Products returned should belong to following category")
    void shouldSearchProductsByCategory() {
        //given
        ProductCategory productCategory = new ProductCategory("Accessories", "Accessories description");
        //when
        List<Product> expectedProductsList = catalogService.searchProductsByCategory(productCategory);
        //then
        assertAll(
                () -> assertEquals(expectedProductsList.size(), 3),
                () -> {
                    for (Product pr : expectedProductsList) {
                        assertEquals(pr.getCategory(), productCategory);
                    }
                }
        );
    }

    @Test
    @DisplayName("Returned products list size should be equal to expected")
    void shouldReturnAllProductsList() {
        //when
        List<Product> expectedAllProducts = catalogService.getAll();
        //then
        assertEquals(expectedAllProducts.size(), mockProductsListFromDao.size());
    }
}

