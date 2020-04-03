package com.ra.course.aws.online.shopping;

import com.ra.course.aws.online.shopping.dao.AccountDao;
import com.ra.course.aws.online.shopping.dao.OrderDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@SuppressWarnings("PMD")
public class AwsOnlineShoppingApplication {
    public static void main(String[] args) {
//        SpringApplication.run(AwsOnlineShoppingApplication.class, args);
//
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        JdbcNotificationDaoImpl jdbcNotDao = context.getBean(JdbcNotificationDaoImpl.class);

        ConfigurableApplicationContext context = SpringApplication.run (AwsOnlineShoppingApplication.class);

        OrderDao orderDao = context.getBean(OrderDao.class);
        AccountDao accountDao = context.getBean(AccountDao.class);


    }
}
