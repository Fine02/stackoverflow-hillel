package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.NotificationDao;
import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.dao.PaymentDao;
import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public class DaoConfiguration {
    @Bean
    public ShippingDao shippingDao(JdbcTemplate jdbcTemplate){
        return new JdbcShippingDaoImpl(jdbcTemplate);
    }

    @Bean
    public PaymentDao paymentDao(JdbcTemplate jdbcTemplate){
        return new JdbcPaymentDaoImpl(jdbcTemplate);
    }

    @Bean
    public OrderDao orderDao (JdbcTemplate jdbcTemplate){
        return new JdbcOrderDaoImpl(jdbcTemplate);
    }

    @Bean
    public NotificationDao notificationDao (JdbcTemplate jdbcTemplate){
        return new JdbcNotificationDaoImpl(jdbcTemplate);
    }

    @Bean
    public JdbcTemplate jdbcTemplate (DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer (){
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("db-schema.sql"));
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource());
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    @Bean
    public DataSource dataSource () {
        HikariDataSource dataSource = new HikariDataSource ();
        dataSource.setJdbcUrl("jdbc:h2:mem:test8;DB_CLOSE_DELAY=-1");
        dataSource.setMinimumIdle(2);
        dataSource.setMaximumPoolSize(5);
        return dataSource;
    }

}
