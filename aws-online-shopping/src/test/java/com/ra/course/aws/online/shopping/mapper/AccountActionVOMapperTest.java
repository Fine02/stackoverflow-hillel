package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.vo.AccountActionVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountActionVOMapperTest {
    private AccountActionVOMapper mapper;
    private AccountActionVO accountActionVO;

    @BeforeEach
    void setUp() {
        mapper = new AccountActionVOMapper();
        accountActionVO = new AccountActionVO(1L, "david", "123456", AccountStatus.ACTIVE,
                "David Gilmour", 1L, "Gonchara", "Kyiv", "Kyiv", "01134",
                "Ukraine", "gilmour@gmail.com", "+380523698745", "David Gilmour",
                "555555", 555, 2L, "Zlayoustivska", "Kyiv",
                "Kyiv", "01135", "Ukraine", "Privat", "111111",
                "111111");
    }

    @Test
    public void mapRowTest() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        AccountActionVO expected = accountActionVO;
        when(rs.getLong("id")).thenReturn(1L);
        when(rs.getString("userName")).thenReturn("david");
        when(rs.getString("password")).thenReturn("123456");
        when(rs.getString("account_status")).thenReturn("ACTIVE");
        when(rs.getString("name")).thenReturn("David Gilmour");

        when(rs.getLong("addressId")).thenReturn(1L);
        when(rs.getString("streetAddress")).thenReturn("Gonchara");
        when(rs.getString("state")).thenReturn("Kyiv");
        when(rs.getString("zipcode")).thenReturn("01134");
        when(rs.getString("country")).thenReturn("Ukraine");
        when(rs.getString("city")).thenReturn("Kyiv");

        when(rs.getString("email")).thenReturn("gilmour@gmail.com");
        when(rs.getString("phone")).thenReturn("+380523698745");

        when(rs.getString("nameOnCard")).thenReturn("David Gilmour");
        when(rs.getString("cardNumber")).thenReturn("555555");
        when(rs.getInt("code")).thenReturn(555);

        when(rs.getLong("billingAddressId")).thenReturn(2L);
        when(rs.getString("billingStreetAddress")).thenReturn("Zlayoustivska");
        when(rs.getString("billingState")).thenReturn("Kyiv");
        when(rs.getString("billingZipcode")).thenReturn("01135");
        when(rs.getString("billingCountry")).thenReturn("Ukraine");
        when(rs.getString("billingCity")).thenReturn("Kyiv");

        when(rs.getString("bankName")).thenReturn("Privat");
        when(rs.getString("routingNumber")).thenReturn("111111");
        when(rs.getString("accountNumber")).thenReturn("111111");

        AccountActionVO result = mapper.mapRow(rs, 1);
        assertEquals(expected, result);
    }

    @Test
    public void getMappedAccountsFromVOTest() {
        Address address = new Address("Gonchara", "Kyiv", "Kyiv", "01134", "Ukraine");
        Address billingAddress = new Address("Zlayoustivska", "Kyiv", "Kyiv", "01135", "Ukraine");
        address.setId(1L);
        billingAddress.setId(2L);
        CreditCard card = new CreditCard("David Gilmour", "555555", 555, billingAddress);
        ElectronicBankTransfer transfer = new ElectronicBankTransfer("Privat", "111111", "111111");
        Account expected = new Account(1L, "david", "123456", AccountStatus.ACTIVE,
                "David Gilmour", address, "gilmour@gmail.com", "+380523698745",
                new ArrayList<>() {{
                    add(card);
                }},
                new ArrayList<>() {{
                    add(transfer);
                }});
        Account result = mapper.getMappedAccountsFromVO(new ArrayList<>() {{
            add(accountActionVO);
        }}).get(0);
        assertEquals(expected, result);
    }
}
