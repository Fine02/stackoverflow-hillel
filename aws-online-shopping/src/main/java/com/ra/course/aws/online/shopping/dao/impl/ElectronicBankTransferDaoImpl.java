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

    @Value("${INSERT_ELECTRONIC_BANK_TRANSFER}")
    private transient String INSERT_ELECTRONIC_BANK_TRANSFER;
    @Value("${DELETE_TRANSFER}")
    private transient String DELETE_TRANSFER;

    private transient final JdbcTemplate jdbcTemplate;

    public ElectronicBankTransferDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean save(Long accountId, ElectronicBankTransfer transfer) {
        jdbcTemplate.update(INSERT_ELECTRONIC_BANK_TRANSFER, transfer.getBankName(), transfer.getRoutingNumber(),
                transfer.getAccountNumber(), accountId.intValue());
        return true;
    }

    @Override
    public boolean remove(String routingNumber) {
        jdbcTemplate.update(DELETE_TRANSFER, routingNumber);
        return true;
    }
}
