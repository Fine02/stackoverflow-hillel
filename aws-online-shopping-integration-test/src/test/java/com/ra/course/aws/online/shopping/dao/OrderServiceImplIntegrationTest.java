package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest (classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
//@Sql(scripts= "classpath:schema.sql")
@Sql(scripts= "classpath:test-data.sql")
public class OrderServiceImplIntegrationTest {

// @Autowired
// private JdbcTemplate template;
//
//@Test
//    public void test (){
//    System.out.println(template.getDataSource());
//    }

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private OrderDao orderDao;

//    @Test
//    public void findOrderLogByIdTest(){
//
//    }

    @Test
    public void findOrderLogByIdTest(){

    }
}
