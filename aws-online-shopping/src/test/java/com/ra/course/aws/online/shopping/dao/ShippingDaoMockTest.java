package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.JdbcShippingDaoImpl;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.ShipmentStatus;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.mapper.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShippingDaoMockTest {
    private ShippingDao shippingDao;
    private JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private ShipmentLogRowMapper sLogRowMapper;
    private BooleanShipmentLogRowMapper booleanShipmentLogRowMapper;
    private GetLastIdRowMapper getIdRowMapper;
    private MemberBooleanRowMapper memberBooleanRowMapper;
    private ShipmentRowMapper shipmentRowMapper;

    @BeforeEach
    public void before() {
        //shippingDao = new JdbcShippingDaoImpl(jdbcTemplate, null, null, null,null);
        shippingDao = new JdbcShippingDaoImpl(jdbcTemplate, booleanShipmentLogRowMapper, getIdRowMapper, memberBooleanRowMapper, sLogRowMapper, shipmentRowMapper);
        sLogRowMapper = mock(ShipmentLogRowMapper.class);
        booleanShipmentLogRowMapper = mock(BooleanShipmentLogRowMapper.class);
        getIdRowMapper = mock(GetLastIdRowMapper.class);
        memberBooleanRowMapper = mock(MemberBooleanRowMapper.class);
        shipmentRowMapper=mock(ShipmentRowMapper.class);
    }

    @Test
    public void testFindShipmentLogById() {
        //given
        var logId = 1L;
        var resultLog = new ShipmentLog();
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.FIND_SHIPMENT_LOG_BY_ID, sLogRowMapper, logId)).thenReturn(resultLog);
        //when
        ShipmentLog result = shippingDao.findShipmentLogById(logId);
        System.out.println(result);
    }

    @Test
    public void testIsThisShipmentLogExist() {
        //given
        var foundId = 1L;
        var existShipmentLog = new ShipmentLog();
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.FIND_SHIPMENT_LOG_BY_ID, booleanShipmentLogRowMapper, foundId)).thenReturn(false);
        //when
        boolean result = shippingDao.isThisShipmentLogExist(existShipmentLog);
        System.out.println(result);
    }

    @Test
    public void testFindLogListByShipment() {
        //given
        LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
        List<ShipmentLog> shipmentLogsList = new ArrayList<>();
        List<ShipmentLog> shipmentLogList = makeListOfShipmentLog(new ShipmentLog(1, "1", ShipmentStatus.SHIPPED, time));
        ShipmentLog shipmentLog = shipmentLogList.get(0);
        String shipmentNumber = shipmentLog.getShipmentNumber();
        when(jdbcTemplate.query(JdbcShippingDaoImpl.GET_SHIPMENT_LOG_BY_SHIPMENT_NUMBER, sLogRowMapper, shipmentNumber)).thenReturn(shipmentLogsList);

        //when
        List<ShipmentLog> result = shippingDao.findLogListByShipment(shipmentLogList);
        System.out.println(result);

    }

    @Test
    public void testFindByShipmentNumber() {
        //given
        var shipmentNumber = "1";
        List<ShipmentLog> shipmentLogsList = new ArrayList<>();
        var shipment = new Shipment();
        when(jdbcTemplate.query(JdbcShippingDaoImpl.GET_SHIPMENT_LOG_BY_SHIPMENT_NUMBER, sLogRowMapper, shipmentNumber)).thenReturn(shipmentLogsList);
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.GET_SHIPMENT_BY_SHIPMENT_NUMBER, shipmentRowMapper, shipmentNumber)).thenReturn(shipment);

        //when
        Shipment result = shippingDao.findByShipmentNumber(shipmentNumber);
        System.out.println(result);
    }

     @Test
     public void testAddShipmentLog(){
         //given
         LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);
         ShipmentLog shipmentLog = new ShipmentLog( "3", ShipmentStatus.SHIPPED, time);
         Integer getIdOfShipment =3;
         Integer getNumberOfStatus =3;
         when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.GET_SHIPMENT_ID, getIdRowMapper, shipmentLog.getShipmentNumber())).thenReturn(getIdOfShipment);
         when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.GET_ID_OF_SHIPMENT_STATUS, getIdRowMapper, shipmentLog.getStatus().toString())).thenReturn(getNumberOfStatus);
         //when
         shippingDao.addShipmentLog(shipmentLog);
     }

    @Test
    public void testIsFoundMemberID() {
        //given
        var memberId = 1L;
        when(jdbcTemplate.queryForObject(JdbcShippingDaoImpl.FIND_MEMBER_BY_ID,  memberBooleanRowMapper, memberId)).thenReturn(false);
        //when
        shippingDao.isFoundMemberID(memberId);
    }

    @Test
    public void testUpdateShippingAddress() {
        //given
        Member member = new Member();
        Address address = new Address();
        //when
        shippingDao.updateShippingAddress(member, address);
    }

    private List<ShipmentLog> makeListOfShipmentLog(ShipmentLog shipmentLog) {
        List<ShipmentLog> shipmentLogs = new ArrayList<>();
        shipmentLogs.add(shipmentLog);
        return shipmentLogs;
    }

}
