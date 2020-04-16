package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.enums.ShipmentStatus;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
public class ShippingDaoImplIntegrationTest {

    @Primary
    @Bean
    public AccountDao mockedAccountDao() {
        return mock(AccountDao.class);
    }

    @Primary
    @Bean
    public ProductDao mockedProductDao() {
        return mock(ProductDao.class);
    }

    @Primary
    @Bean
    public ShoppingCartDao mockedShoppingCartDao() {
        return mock(ShoppingCartDao.class);
    }

    @Autowired
    private ShippingDao shippingDao;

    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
    LocalDateTime time2 = LocalDateTime.of(2020, 4, 15, 17, 12, 11);

    private final ShipmentLog existShipmentLog = makeShipmentLog(1,"1", ShipmentStatus.SHIPPED, time);
    private final ShipmentLog notExistShipmentLog = makeShipmentLog(5,"1", ShipmentStatus.SHIPPED, time);
    private final ShipmentLog shipmentLog = new ShipmentLog ("2",ShipmentStatus.SHIPPED, time);
    private final List<ShipmentLog> shipmentLogList = makeListOfShipmentLog(existShipmentLog);
    private final List<ShipmentLog> listOfShipmentLog = makeListOfShipmentLog(shipmentLog);
    private final ShipmentLog shipmentLog1 = new ShipmentLog("3", ShipmentStatus.ONHOLD, time2);

    //work correct
    @Test
    public void getInstanceByShipmentLogById() {
        ShipmentLog result = shippingDao.findShipmentLogById(1L);
        System.out.println(result);
    }

    //work correct
    @Test
    public void isThisShipmentLogExistTest() {
        boolean result = shippingDao.isThisShipmentLogExist(existShipmentLog);
        System.out.println(result);
    }

    //work correct
    @Test
    public void findByShipmentNumberTest() {
       Shipment result = shippingDao.findByShipmentNumber("2");
        System.out.println(result);
    }

    //work correct
    @Test
    public void findLogListByShipmentTest() {
        List<ShipmentLog> result = shippingDao.findLogListByShipment(listOfShipmentLog);
        System.out.println(result);
    }

    //work correct
    @Test
    public void addShipmentLogTest() {
        shippingDao.addShipmentLog(shipmentLog1);
    }

    private ShipmentLog makeShipmentLog(long id, String shipmentNumber, ShipmentStatus status, LocalDateTime creationDate) {
        return new ShipmentLog(id, shipmentNumber, status, creationDate);
    }


    private List<ShipmentLog> makeListOfShipmentLog(ShipmentLog shipmentLog) {
        List<ShipmentLog> shipmentLogs = new ArrayList<>();
        shipmentLogs.add(shipmentLog);
        return shipmentLogs;
    }

}
