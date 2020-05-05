package com.ra.course.aws.online.shopping.dao;

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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("test")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
public class ShippingDaoImplIntegrationTest {

    @Autowired
    private ShippingDao shippingDao;

    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
    LocalDateTime time2 = LocalDateTime.of(2020, 4, 15, 17, 12, 11);
    LocalDateTime time3 = LocalDateTime.of(2020, 4, 19, 22, 22, 11);

    private final ShipmentLog existShipmentLog = makeShipmentLog(1, "1", ShipmentStatus.SHIPPED, time);
    private final ShipmentLog notExistShipmentLog = makeShipmentLog(85555, "85555", ShipmentStatus.SHIPPED, time);
    private final List<ShipmentLog> shipmentLogList = makeListOfShipmentLog(existShipmentLog);
    private final ShipmentLog shipmentLog1 = new ShipmentLog("3", ShipmentStatus.ONHOLD, time2);
    Address addressForUpdate = new Address("Mira, 11", "Kyiv", "Kyiv", "14004", "Ukraine");
    Address addressInDb = new Address("Mira, 10", "Kyiv", "Kyiv", "14004", "Ukraine");
    Member member = makeMember(3L);

    @Test
    public void getInstanceOfShipmentLogTest() {
        ShipmentLog result = shippingDao.findShipmentLogById(1L);

        assertEquals(existShipmentLog, result);
    }

    @Test
    public void getNullIfShipmentLogByIdWasNotFoundTest() {
        ShipmentLog result = shippingDao.findShipmentLogById(15552L);

        assertEquals(null, result);
    }

    @Test
    public void isThisShipmentLogExistTest() {
        boolean result = shippingDao.isThisShipmentLogExist(existShipmentLog);

        assertEquals(true, result);
    }

    @Test
    public void getFalseIfShipmentLogDoesNotExistTest() {
        boolean result = shippingDao.isThisShipmentLogExist(notExistShipmentLog);

        assertEquals(false, result);
    }

    @Test
    public void getInstanceOfShipmentTest() {
        Shipment shipmentInDb = new Shipment("1", time, time3, "by air");
        shipmentInDb.setShipmentLogs(shipmentLogList);

        Shipment result = shippingDao.findByShipmentNumber("1");

        assertEquals(shipmentInDb, result);
    }

    @Test
    public void getNullIfShipmentWasNotFoundTest() {
        Shipment result = shippingDao.findByShipmentNumber("99991");

        assertEquals(null, result);
    }

    @Test
    public void getInstanceOfLogListTest() {
        List<ShipmentLog> result = shippingDao.findLogListByShipment(shipmentLogList);

        assertEquals(shipmentLogList, result);
    }

    @Test
    public void getTrueIfMemberExistInDBTest() {
        boolean result = shippingDao.isFoundMemberID(3L);

        assertEquals(true, result);
    }

    @Test
    public void getFalseIfMemberDoesNotExistInDBTest() {
        boolean result = shippingDao.isFoundMemberID(913L);

        assertEquals(false, result);
    }

    @Test
    public void addShipmentLogTest() {
        shippingDao.addShipmentLog(shipmentLog1);
    }

    @Test
    public void updateShippingAddressTest() {

        shippingDao.updateShippingAddress(member, addressForUpdate);
    }

    private ShipmentLog makeShipmentLog(long id, String shipmentNumber, ShipmentStatus status, LocalDateTime creationDate) {
        return new ShipmentLog(id, shipmentNumber, status, creationDate);
    }

    private List<ShipmentLog> makeListOfShipmentLog(ShipmentLog shipmentLog) {
        List<ShipmentLog> shipmentLogs = new ArrayList<>();
        shipmentLogs.add(shipmentLog);
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
