package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.AccountStatus;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.ShippingAddressNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShippingServiceImplTest {
    private ShippingServiceImpl shippingService;
    private ShippingDao shippingAddressDao = mock(ShippingDao.class);
    private final Address ADDRESS_IN_DB = new Address("Mira", "Kiyv", "Kyiv", "04114", "Ukraine");
    private final Long MEMBER_ID_IN_DB = 10L;
    private Exception exception;

    @BeforeEach
    public void before() {
        shippingService = new ShippingServiceImpl(shippingAddressDao);
    }

    @Test
    public void whenMemberShouldSpecifyShippingAddress() throws MemberNotFoundException, ShippingAddressNotFoundException {
        var accountInDB = mockAccount(ADDRESS_IN_DB);
        var memberInDB = mockMember(MEMBER_ID_IN_DB, accountInDB);
        var shipmentAddress = memberInDB.getAccount().getShippingAddress();
        when(shippingAddressDao.isFoundMemberID(memberInDB.getMemberID())).thenReturn(true);
        when(shippingAddressDao.findShippingAddress(shipmentAddress)).thenReturn(true);
        var resultOfSpecifyShippingAddress = shippingService.specifyShippingAddress(memberInDB, ADDRESS_IN_DB);
        Assertions.assertSame(resultOfSpecifyShippingAddress, shipmentAddress);
        assertEquals(10L, memberInDB.getMemberID());
        assertEquals(ADDRESS_IN_DB, shipmentAddress);
    }

    @Test
    public void shouldThrowMemberNotFoundException() throws ShippingAddressNotFoundException {
        var accountInDB = mockAccount(ADDRESS_IN_DB);
        var memberInDB = mockMember(MEMBER_ID_IN_DB, accountInDB);
        var shipmentAddress = memberInDB.getAccount().getShippingAddress();
        when(shippingAddressDao.isFoundMemberID(memberInDB.getMemberID())).thenReturn(false);
        when(shippingAddressDao.findShippingAddress(shipmentAddress)).thenReturn(true);
        try {
            shippingService.specifyShippingAddress(memberInDB, ADDRESS_IN_DB);
        } catch (MemberNotFoundException e) {
            exception = e;
        }
        assertTrue(exception instanceof MemberNotFoundException);
        assertEquals("There is not found the Member by this ID", exception.getMessage());
    }

    @Test
    public void shouldThrowShippingAddressNotFoundException() throws MemberNotFoundException {
        var accountInDB = mockAccount(ADDRESS_IN_DB);
        var memberInDB = mockMember(MEMBER_ID_IN_DB, accountInDB);
        var shipmentAddress = memberInDB.getAccount().getShippingAddress();
        when(shippingAddressDao.isFoundMemberID(memberInDB.getMemberID())).thenReturn(true);
        when(shippingAddressDao.findShippingAddress(shipmentAddress)).thenReturn(false);
        try {
            shippingService.specifyShippingAddress(memberInDB, ADDRESS_IN_DB);
        } catch (ShippingAddressNotFoundException e) {
            exception = e;
        }
        assertTrue(exception instanceof ShippingAddressNotFoundException);
        assertEquals("There is not found shipping address", exception.getMessage());
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
}
