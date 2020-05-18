package com.ra.course.aws.online.shopping;

import com.ra.course.aws.online.shopping.entity.Item;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;


@Configuration
public class TestConfig {
    @Primary
    @Bean
    public BeanPropertyRowMapper mockedBeanPropertyRowMapper() {
        return BeanPropertyRowMapper.newInstance(Item.class);
    }
}