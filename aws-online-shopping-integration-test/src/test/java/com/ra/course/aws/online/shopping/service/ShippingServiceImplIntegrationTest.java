package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.enums.ShipmentStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberDataNotFoundException;
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

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
//@ActiveProfiles("local")
@ActiveProfiles("test")
public class ShippingServiceImplIntegrationTest {



    @Autowired
    private ShippingService shippingService;

    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
    LocalDateTime time1 = LocalDateTime.of(2020, 3, 20, 22, 22, 11);
    LocalDateTime time3 = LocalDateTime.of(2020, 4, 19, 22, 22, 11);

    private final Shipment shipmentInDb = new Shipment("2", time1, time3, "by air");
    private final ShipmentLog shipmentLogInDB = new ShipmentLog(2, "2", ShipmentStatus.SHIPPED, time1);
    private final ShipmentLog newShipmentLog = new ShipmentLog("2", ShipmentStatus.DELIVERED, LocalDateTime.now());
    private final ShipmentLog shipmentLog1 = new ShipmentLog(1, "1", ShipmentStatus.SHIPPED, time);
    Address addressForUpdate = new Address("Mira, 11", "Kyiv", "Kyiv", "14004", "Ukraine");
    Address addressInDb = new Address("Mira, 10", "Kyiv", "Kyiv", "14004", "Ukraine");
    Member memberInDb = makeMember(3L);
    Member wrongMember = makeMember(55523L);

    @Test
    public void shouldGetShippingTrack() {
        String shippingNumber = "1";

        List<ShipmentLog> expectedResult = makeListOfShipmentLog(shipmentLog1);

        List<ShipmentLog> actualResponse = shippingService.getShipmentTrack(shippingNumber);

        assertEquals(actualResponse, expectedResult);
    }

    @Test
    public void shouldReturnEmptyListIfShippingNumberNotFound() {
        String notExistShipmentNumber = "102012";
        assertEquals(shippingService.getShipmentTrack(notExistShipmentNumber), Collections.emptyList());
    }

    @Test
    public void whenAddShipmentLogToShipmentLogListThenReturnTrue() {
        boolean actualResponse = shippingService.addShipmentLogToShipment(shipmentInDb, newShipmentLog);

        assertEquals(actualResponse, true);
    }

    @Test
    public void shouldThrowExceptionIfShipmentLogIsAlreadyExist() {

        Throwable exception = Assertions.assertThrows(ShipmentLogIsAlreadyExistException.class, () -> {
            shippingService.addShipmentLogToShipment(shipmentInDb, shipmentLogInDB);
        });

        assertEquals(exception.getMessage(), "This ShipmentLog is already exist");
        assertEquals(exception.getClass(), ShipmentLogIsAlreadyExistException.class);
    }

    @Test
    public void whenMemberSuccessfullySpecifiedShippingAddressThenReturnTrue() {

        var resultOfSpecifyShippingAddress = shippingService.specifyShippingAddress(memberInDb, addressForUpdate);

        Assertions.assertSame(true, resultOfSpecifyShippingAddress);

        assertEquals(3L, memberInDb.getMemberID());
    }

    @Test
    public void shouldThrowMemberNotFoundException() {

        Throwable exception = Assertions.assertThrows(MemberDataNotFoundException.class, () -> {
            shippingService.specifyShippingAddress(wrongMember, addressForUpdate);
        });

        assertEquals(exception.getMessage(), "There is not found the Member by this ID");
        assertEquals(exception.getClass(), MemberDataNotFoundException.class);
    }


    private List<ShipmentLog> makeListOfShipmentLog(ShipmentLog shipmentLog1) {
        List<ShipmentLog> shipmentLogs = new ArrayList<>();
        shipmentLogs.add(shipmentLog1);
        return shipmentLogs;
    }

    private Account makeAccount() {
        List<CreditCard> creditCardList = new ArrayList<>();
        creditCardList.add(new CreditCard("VISA", "77", 44, addressInDb));
        List<ElectronicBankTransfer> bankTransfers = new ArrayList<>();
        bankTransfers.add(new ElectronicBankTransfer("P8", "77", "10"));
        Account account = new Account(
                1L,
                "Ivan",
                "1",
                AccountStatus.ACTIVE,
                "Ann",
                addressInDb,
                "111j@gmail.com",
                "38012345111",
                creditCardList,
                bankTransfers
        );
        return account;
    }

    private Member makeMember(Long id) {
        Member member = new Member(makeAccount());
        member.setMemberID(id);
        return member;
    }

}
