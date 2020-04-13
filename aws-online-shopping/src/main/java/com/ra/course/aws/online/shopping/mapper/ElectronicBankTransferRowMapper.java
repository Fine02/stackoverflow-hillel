package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ElectronicBankTransferRowMapper implements RowMapper<ElectronicBankTransfer> {

    @Override
    public ElectronicBankTransfer mapRow(ResultSet rs, int rowNum) throws SQLException {
        ElectronicBankTransfer transfer = new ElectronicBankTransfer();
        transfer.setBankName(rs.getString("bankName"));
        transfer.setRoutingNumber(rs.getString("routingNumber"));
        transfer.setAccountNumber(rs.getString("accountNumber"));
        return transfer;
    }
}
