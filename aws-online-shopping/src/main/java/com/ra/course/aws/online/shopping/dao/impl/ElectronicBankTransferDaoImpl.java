package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.ElectronicBankTransferDao;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("sql-requests.yml")
public class ElectronicBankTransferDaoImpl implements ElectronicBankTransferDao {

    @Value("${insertTransfer}")
    private transient String insertTransfer;
    @Value("${deleteTransfer}")
    private transient String deleteTransfer;

    private transient final JdbcTemplate jdbcTemplate;

    public ElectronicBankTransferDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean save(final Long accountId, final ElectronicBankTransfer transfer) {
        jdbcTemplate.update(insertTransfer, transfer.getBankName(), transfer.getRoutingNumber(),
                transfer.getAccountNumber(), accountId.intValue());
        return true;
    }

    @Override
    public boolean remove(final String routingNumber) {
        jdbcTemplate.update(deleteTransfer, routingNumber);
        return true;
    }
}