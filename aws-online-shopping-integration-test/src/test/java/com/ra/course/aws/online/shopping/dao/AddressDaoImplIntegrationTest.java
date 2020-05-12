package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:test-data-account.sql"})
public class AddressDaoImplIntegrationTest {

    @Autowired
    private AddressDao addressDao;
    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address("Garmatna", "Kyiv", "Kyiv", "01135", "Ukraine");
    }

    @Test
    @DisplayName("Should return true when account address inserted")
    @Rollback
    public void saveAccAddTest() {
        assertTrue(addressDao.saveAccAdd(address, 3L));
    }

    @Test
    @DisplayName("Should return true when billing address inserted")
    @Rollback
    public void saveBilAddTest() {
        assertTrue(addressDao.saveBillAdd(address, 3L));
    }

    @Test
    @DisplayName("Returned account should be equal to expected")
    @Rollback
    public void findAccAddByIDTest() {
        Address address = addressDao.findAccAddById(1L);
        assertAll(() -> {
            assertEquals(address.getStreetAddress(), "Gonchara");
            assertEquals(address.getCity(), "Kyiv");
            assertEquals(address.getState(), "Kyiv");
            assertEquals(address.getZipCode(), "01134");
            assertEquals(address.getCountry(), "Ukraine");
            assertEquals(address.getId(), 1L);
        });
    }

    @Test
    @DisplayName("Should return null when there is no address with following id in DB")
    @Rollback
    public void findAccAddByIDNullTest() {
        assertNull(addressDao.findAccAddById(999L));
    }

    @Test
    @DisplayName("Should return true when account address updated")
    @Rollback
    public void updateAccAddTest() {
        address.setId(1L);
        boolean result = addressDao.updateAccAdd(address);
        Address updAddress = addressDao.findAccAddById(1L);
        assertAll(() -> {
            assertTrue(result);
            assertEquals(updAddress.getStreetAddress(), "Garmatna");
            assertEquals(updAddress.getZipCode(), "01135");
        });
    }
}