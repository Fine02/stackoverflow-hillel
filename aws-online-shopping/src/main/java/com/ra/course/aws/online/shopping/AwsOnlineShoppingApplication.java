package com.ra.course.aws.online.shopping;

import com.ra.course.aws.online.shopping.dao.impl.DaoConfiguration;
import com.ra.course.aws.online.shopping.dao.impl.JdbcNotificationDaoImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@SuppressWarnings("PMD")
public class AwsOnlineShoppingApplication {
    public static void main(String[] args) {
        SpringApplication.run(AwsOnlineShoppingApplication.class, args);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoConfiguration.class);
        JdbcNotificationDaoImpl jdbcNotDao = context.getBean(JdbcNotificationDaoImpl.class);

    }
}
