package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.enums.ShipmentStatus;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberDataNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.ShipmentLogIsAlreadyExistException;
import com.ra.course.aws.online.shopping.service.impl.ShippingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShippingServiceImplTest {
    private ShippingServiceImpl shippingService;
    private ShippingDao shippingDao = mock(ShippingDao.class);
    private Address ADDRESS;
    private final Long MEMBER_ID_IN_DB = 10L;
    private Account accountInDB;
    private Member memberInDB;
    private String shippingNumber = "101010";

    private final ShipmentLog SHIPMENT_LOG = mockShipmentLog("101010", ShipmentStatus.SHIPPED, LocalDateTime.now());
    private final List<ShipmentLog> SHIPMENT_LOG_LIST = mockListOfShipmentLog(SHIPMENT_LOG);
    private final Shipment SHIPMENT = mockShipment();


    @BeforeEach
    public void before() {
        shippingService = new ShippingServiceImpl(shippingDao);
        ADDRESS = new Address("Mira", "Kiyv", "Kyiv", "04114", "Ukraine");
        accountInDB = mockAccount(ADDRESS);
        memberInDB = mockMember(MEMBER_ID_IN_DB, accountInDB);
        when(shippingDao.findByShipmentNumber(SHIPMENT.getShipmentNumber())).thenReturn(SHIPMENT);
        when(shippingDao.findLogListByShipment(SHIPMENT.getShipmentLogs())).thenReturn(SHIPMENT_LOG_LIST);
    }

    @Test
    public void whenAddShipmentLogToShipmentLogListThenReturnTrue() {
        ShipmentLog newShipmentLog = mockShipmentLog("55785", ShipmentStatus.ONHOLD, LocalDateTime.now().minusDays(1));

        boolean actualResponse = shippingService.addShipmentLogToShipment(SHIPMENT, newShipmentLog);

        assertEquals(actualResponse, true);
    }

    @Test
    public void shouldThrowExceptionIfShipmentLogIsAlreadyExist() {

        Throwable exception = Assertions.assertThrows(ShipmentLogIsAlreadyExistException.class, () -> {
            shippingService.addShipmentLogToShipment(SHIPMENT, SHIPMENT_LOG);
        });

        assertEquals(exception.getMessage(), "This ShipmentLog is already exist");
        assertEquals(exception.getClass(), ShipmentLogIsAlreadyExistException.class);
    }


    @Test
    public void shouldGetShippingTrack() {
        when(shippingDao.findByShipmentNumber(shippingNumber)).thenReturn(SHIPMENT);
        List<ShipmentLog> expectedResult = shippingDao.findLogListByShipment(SHIPMENT.getShipmentLogs());

        List<ShipmentLog> actualResponse = shippingService.getShipmentTrack(shippingNumber);

        assertEquals(actualResponse, expectedResult);
    }

    @Test

    public void shouldReturnEmptyListIfShippingNumberNotFound() {
        assertEquals(shippingService.getShipmentTrack(null), Collections.emptyList());
    }

    @Test
    public void whenMemberShouldSpecifyShippingAddress() {
        when(shippingDao.isFoundMemberID(memberInDB.getMemberID())).thenReturn(true);

        var resultOfSpecifyShippingAddress = shippingService.specifyShippingAddress(memberInDB, ADDRESS);
        Assertions.assertSame(true, resultOfSpecifyShippingAddress);

        assertEquals(10L, memberInDB.getMemberID());
        verify(shippingDao).updateShippingAddress(memberInDB, ADDRESS);
    }

    @Test
    public void shouldThrowMemberNotFoundException() {

        when(shippingDao.isFoundMemberID(memberInDB.getMemberID())).thenReturn(false);

        Throwable exception = Assertions.assertThrows(MemberDataNotFoundException.class, () -> {
            shippingService.specifyShippingAddress(memberInDB, ADDRESS);
        });

        assertEquals(exception.getMessage(), "There is not found the Member by this ID");
        assertEquals(exception.getClass(), MemberDataNotFoundException.class);
    }

    private Member mockMember(long id, Account account) {
        Member member = new Member();
        member.setAccount(account);
        member.setMemberID(id);
        return member;
    }

    private Account mockAccount(Address address) {
        List<CreditCard> creditCardList = new ArrayList<>();
        creditCardList.add(new CreditCard("VISA", "77", 44, address));
        List<ElectronicBankTransfer> electronicBankTransfers = new ArrayList<>();
        electronicBankTransfers.add(new ElectronicBankTransfer("P8", "77", "10"));
        Account account = new Account(
                "Ivan",
                "1",
                AccountStatus.ACTIVE,
                "Ann",
                address,
                "sbwbw@sbsb.com",
                "555",
                creditCardList,
                electronicBankTransfers
        );
        return account;
    }


    private ShipmentLog mockShipmentLog(String shipmentNumber, ShipmentStatus status, LocalDateTime creationDate) {
        return new ShipmentLog(shipmentNumber, status, creationDate);
    }


    private List<ShipmentLog> mockListOfShipmentLog(ShipmentLog shipmentLog) {
        List<ShipmentLog> shipmentLogs = new ArrayList<>();
        shipmentLogs.add(shipmentLog);
        return shipmentLogs;
    }

    private Shipment mockShipment() {
        return new Shipment("101010", LocalDateTime.now(), LocalDateTime.now().minusDays(2), "byAir", SHIPMENT_LOG_LIST);
    }
}
