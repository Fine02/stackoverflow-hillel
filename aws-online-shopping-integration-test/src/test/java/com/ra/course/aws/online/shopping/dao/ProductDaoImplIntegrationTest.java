package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.product.Product;
import com.ra.course.aws.online.shopping.entity.product.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
@Sql(scripts = {"classpath:schema.sql"/*, "classpath:test-data.sql"*/})
public class ProductDaoImplIntegrationTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void saveTest() {
        Product product = new Product("WwWax", "Reusel Red", 129.9,
                40, new ProductCategory("Hair styling products", "hs"));
        Long savedProductId = productDao.save(product);
        System.out.println("id:    : " + savedProductId);
    }

    @Test
    public void findByIDTest() {
        Product result = productDao.findById(1L);
        System.out.println(result);
    }

    @Test
    public void findAllTest() {
        List<Product> result = productDao.getAll();
        System.out.println(result);
    }
}
