package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.dao.NotificationDao;
import com.ra.course.aws.online.shopping.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
//@Sql(scripts= "classpath:schema.sql")
//@Sql(scripts= "classpath:test-data.sql")
public class OrderServiceImplIntegrationTest {

// @Autowired
// private JdbcTemplate template;
//
//@Test
//    public void test (){
//    System.out.println(template.getDataSource());
//    }

    @Autowired
    private OrderDao orderDao;

}
