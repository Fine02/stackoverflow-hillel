package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.enums.ShipmentStatus;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.exceptions.ShipmentLogIsAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
public class ShippingServiceImplIntegrationTest {

    @Autowired
    private ShippingService shippingService;

    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
    LocalDateTime time1 = LocalDateTime.of(2020, 3, 20, 22, 22, 11);
    LocalDateTime time2 = LocalDateTime.of(2020, 3, 21, 22, 22, 11);
    LocalDateTime time3 = LocalDateTime.of(2020, 4, 19, 22, 22, 11);
    LocalDateTime time4 = LocalDateTime.of(2020, 4, 15, 17, 12, 11);
    private final ShipmentLog shipmentLogInDB1 = new ShipmentLog (2,"2", ShipmentStatus.SHIPPED, time1);
    private final ShipmentLog shipmentLogInDB2 = new ShipmentLog (3,"2", ShipmentStatus.SHIPPED, time2);
    private final Shipment shipmentInDb = new Shipment("3",time, time3,"by air");
    private final ShipmentLog shipmentLogInDB = new ShipmentLog (4,"3", ShipmentStatus.ONHOLD, time4);
    private final ShipmentLog newShipmentLog = new ShipmentLog ("3", ShipmentStatus.DELIVERED, LocalDateTime.now());

    //work correct
    @Test
    public void shouldGetShippingTrack() {
        String shippingNumber ="2";

        List<ShipmentLog> expectedResult =makeListOfShipmentLog(shipmentLogInDB1, shipmentLogInDB2); ;

        List<ShipmentLog> actualResponse = shippingService.getShipmentTrack(shippingNumber);

        assertEquals(actualResponse, expectedResult);
    }

    //work correct
    @Test
    public void shouldReturnEmptyListIfShippingNumberNotFound() {
        String notExistShipmentNumber = "102012";
        assertEquals(shippingService.getShipmentTrack(notExistShipmentNumber), Collections.emptyList());
    }

    //work correct
    @Test
    public void whenAddShipmentLogToShipmentLogListThenReturnTrue() {
        boolean actualResponse = shippingService.addShipmentLogToShipment(shipmentInDb, newShipmentLog);

        assertEquals(actualResponse, true);
    }

    //work correct
    @Test
    public void shouldThrowExceptionIfShipmentLogIsAlreadyExist() {

        Throwable exception = Assertions.assertThrows(ShipmentLogIsAlreadyExistException.class, () -> {
            shippingService.addShipmentLogToShipment(shipmentInDb, shipmentLogInDB);
        });

        assertEquals(exception.getMessage(), "This ShipmentLog is already exist");
        assertEquals(exception.getClass(), ShipmentLogIsAlreadyExistException.class);
    }


    private List<ShipmentLog> makeListOfShipmentLog(ShipmentLog shipmentLog1, ShipmentLog shipmentLog2 ) {
        List<ShipmentLog> shipmentLogs = new ArrayList<>();
        shipmentLogs.add(shipmentLog1);
        shipmentLogs.add(shipmentLog2);
        return shipmentLogs;
    }
}
