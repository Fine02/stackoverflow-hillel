package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.JdbcShippingDaoImpl;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.ShipmentStatus;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.mapper.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ShippingDaoMockTest {
    private ShippingDao shippingDao;
    private JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private ShipmentLogRowMapper sLogRowMapper = mock(ShipmentLogRowMapper.class);
    private BooleanShipmentLogRowMapper booleanLogMapper = mock(BooleanShipmentLogRowMapper.class);
    private GetLastIdRowMapper getIdRowMapper = mock(GetLastIdRowMapper.class);
    private MemberBooleanRowMapper memberMapper = mock(MemberBooleanRowMapper.class);
    private ShipmentRowMapper shipmentRowMapper = mock(ShipmentRowMapper.class);
    private LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);

    @BeforeEach
    public void before() {
        shippingDao = new JdbcShippingDaoImpl(jdbcTemplate, getIdRowMapper, sLogRowMapper, booleanLogMapper, memberMapper, shipmentRowMapper);
    }

    @Test
    public void testFindShipmentLogById() {
        //given
        var logId = 1L;
        var resultLog = new ShipmentLog();
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.FIND_SLOG, sLogRowMapper, logId)).thenReturn(resultLog);
        //when
        ShipmentLog result = shippingDao.findShipmentLogById(logId);
        Assert.assertEquals(resultLog, result);
    }

    @Test
    public void testIsThisShipmentLogExistReturnFalse() {
        //given
        var foundId = 1L;
        var shipmentLog = new ShipmentLog();
        shipmentLog.setId(foundId);
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.FIND_SLOG, booleanLogMapper, foundId)).thenReturn(false);
        //when
        boolean result = shippingDao.isThisShipmentLogExist(shipmentLog);
        Assert.assertEquals(false, result);
    }

    @Test
    public void testIsThisShipmentLogExistReturnTrue() {
        //given
        var foundId = 1L;
        var existShipmentLog = new ShipmentLog();
        existShipmentLog.setId(foundId);
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.FIND_SLOG, booleanLogMapper, foundId)).thenReturn(true);
        //when
        boolean result = shippingDao.isThisShipmentLogExist(existShipmentLog);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testFindLogListByShipment() {
        //given
        List<ShipmentLog> shipmentLogList = new ArrayList<>();
        shipmentLogList.add(new ShipmentLog(1, "1", ShipmentStatus.SHIPPED, time));
        ShipmentLog shipmentLog = shipmentLogList.get(0);
        String shipmentNumber = shipmentLog.getShipmentNumber();
        when(jdbcTemplate.query(JdbcShippingDaoImpl.GET_SHIPMENT_LOG, sLogRowMapper, shipmentNumber)).thenReturn(shipmentLogList);
        //when
        List<ShipmentLog> result = shippingDao.findLogListByShipment(shipmentLogList);
        System.out.println(result);
        assertThat(result).isNotNull().isEqualTo(shipmentLogList);
    }

    @Test
    public void testFindByShipmentNumberReturnEmptyObject() {
        //given
        var shipmentNumber = "1";
        List<ShipmentLog> shipmentLogsList = new ArrayList<>();
        Shipment shipment = new Shipment();

        Shipment expectedResult = new Shipment();
        expectedResult.setShipmentLogs(shipmentLogsList);

        when(jdbcTemplate.query(JdbcShippingDaoImpl.GET_SHIPMENT_LOG, sLogRowMapper, shipmentNumber)).thenReturn(shipmentLogsList);
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.GET_SHIPMENT, shipmentRowMapper, shipmentNumber)).thenReturn(shipment);

        //when
        Shipment result = shippingDao.findByShipmentNumber(shipmentNumber);
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testFindByShipmentNumberReturnShipment() {
        //given
        var shipmentNumber = "2";
        List<ShipmentLog> shipmentLogsList = new ArrayList<>();
        shipmentLogsList.add(new ShipmentLog(2, "2", ShipmentStatus.DELIVERED, time));
        Shipment shipment = new Shipment("2", time, time, "by air");

        Shipment expectedResult = new Shipment("2", time, time, "by air");
        expectedResult.setShipmentLogs(shipmentLogsList);

        when(jdbcTemplate.query(JdbcShippingDaoImpl.GET_SHIPMENT_LOG, sLogRowMapper, shipmentNumber)).thenReturn(shipmentLogsList);
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.GET_SHIPMENT, shipmentRowMapper, shipmentNumber)).thenReturn(shipment);

        //when
        Shipment result = shippingDao.findByShipmentNumber(shipmentNumber);
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testAddShipmentLog() {
        //given
        LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
        ShipmentLog shipmentLog = new ShipmentLog("3", ShipmentStatus.SHIPPED, time);
        Integer getIdOfShipment = 52;
        Integer getNumberOfStatus = 5;
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.GET_SHIPMENT_ID, getIdRowMapper, shipmentLog.getShipmentNumber())).thenReturn(getIdOfShipment);
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.GET_STATUS_ID, getIdRowMapper, shipmentLog.getStatus().toString())).thenReturn(getNumberOfStatus);
        //when
        shippingDao.addShipmentLog(shipmentLog);
        verify(jdbcTemplate).update(JdbcShippingDaoImpl.INSERT_SLOG, shipmentLog.getShipmentNumber(), getNumberOfStatus, shipmentLog.getCreationDate(), getIdOfShipment);
    }

    @Test
    public void testIsFoundMemberIDReturnFalse() {
        //given
        var memberId = 1L;
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.FIND_MEMBER_BY_ID, memberMapper, memberId)).thenReturn(false);
        //when
        var result = shippingDao.isFoundMemberID(memberId);
        Assert.assertEquals(false, result);
    }

    @Test
    public void testIsFoundMemberIDReturnTrue() {
        //given
        var memberId = 1L;
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.FIND_MEMBER_BY_ID, memberMapper, memberId)).thenReturn(true);
        //when
        var result = shippingDao.isFoundMemberID(memberId);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testUpdateShippingAddress() {
        //given
        Member member = new Member();
        Address address = new Address();
        Integer addressId = null;
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.GET_ADDRESS_ID, getIdRowMapper, member.getMemberID())).thenReturn(addressId);
        //when
        shippingDao.updateShippingAddress(member, address);
        verify(jdbcTemplate).update(JdbcShippingDaoImpl.UPDATE_ADDRESS, address.getStreetAddress(), address.getCity(), address.getState(), address.getZipCode(), address.getCountry(), addressId);
    }

}
