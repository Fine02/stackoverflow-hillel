package com.ra.course.aws.online.shopping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;


@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = "classpath:schema.sql")
public class MainCreateDB {

    @Autowired
    private JdbcTemplate template;

    @Test
    public void test() {
        System.out.println(template.getDataSource());
    }

}
