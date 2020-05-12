package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.ElectronicBankTransferDaoImpl;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ElBanTrDaoImplTest {

    private ElectronicBankTransferDao electronicBankTransferDao;
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

    @BeforeEach
    void setUp() {
        electronicBankTransferDao = new ElectronicBankTransferDaoImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("Should return true when EBT inserted")
    public void saveTest() {
        //given
        ElectronicBankTransfer transfer = new ElectronicBankTransfer("PrivatBank123", "1225745123698541",
                "125647893");
        Long accountId = 1L;
        when(jdbcTemplate.update(anyString(), eq(transfer.getBankName()), eq(transfer.getRoutingNumber()),
                eq(transfer.getAccountNumber()), eq(accountId.intValue()))).thenReturn(1);
        //then
        assertTrue(electronicBankTransferDao.save(accountId, transfer));
    }

    @Test
    @DisplayName("Should return true when EBT deleted")
    public void removeTest() {
        //when
        assertTrue(electronicBankTransferDao.remove("444444"));
        //then
        verify(jdbcTemplate).update(any(), eq("444444"));
    }
}

