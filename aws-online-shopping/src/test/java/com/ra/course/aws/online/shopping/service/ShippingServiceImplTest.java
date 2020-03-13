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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShippingServiceImplTest {
    private ShippingServiceImpl shippingService;
    private ShippingDao shippingAddressDao = mock(ShippingDao.class);
    private Address ADDRESS_IN_DB ;
    private final Long MEMBER_ID_IN_DB = 10L;

    @BeforeEach
    public void before() {
        shippingService = new ShippingServiceImpl(shippingAddressDao);
        ADDRESS_IN_DB=new Address("Mira", "Kiyv", "Kyiv", "04114", "Ukraine");
    }

    @Test
    public void whenMemberShouldSpecifyShippingAddress()  {
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
    public void shouldThrowMemberNotFoundException()  {
        var accountInDB = mockAccount(ADDRESS_IN_DB);
        var memberInDB = mockMember(MEMBER_ID_IN_DB, accountInDB);
        var shipmentAddress = memberInDB.getAccount().getShippingAddress();
        when(shippingAddressDao.isFoundMemberID(memberInDB.getMemberID())).thenReturn(false);
        when(shippingAddressDao.findShippingAddress(shipmentAddress)).thenReturn(true);
        Throwable exception = Assertions.assertThrows(MemberNotFoundException.class, () -> {
            shippingService.specifyShippingAddress(memberInDB, ADDRESS_IN_DB);;;
        });

        assertEquals(exception.getMessage(), "There is not found the Member by this ID");
        assertEquals(exception.getClass(), MemberNotFoundException.class);
    }

    @Test
    public void shouldThrowShippingAddressNotFoundException() {
        var accountInDB = mockAccount(ADDRESS_IN_DB);
        var memberInDB = mockMember(MEMBER_ID_IN_DB, accountInDB);
        var shipmentAddress = memberInDB.getAccount().getShippingAddress();
        when(shippingAddressDao.isFoundMemberID(memberInDB.getMemberID())).thenReturn(true);
        when(shippingAddressDao.findShippingAddress(shipmentAddress)).thenReturn(false);

        Throwable exception = Assertions.assertThrows(ShippingAddressNotFoundException.class, () -> {
            shippingService.specifyShippingAddress(memberInDB, ADDRESS_IN_DB);
        });

        assertEquals(exception.getMessage(), "There is not found shipping address");
        assertEquals(exception.getClass(), ShippingAddressNotFoundException.class);

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
