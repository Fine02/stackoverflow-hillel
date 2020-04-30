package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;

public interface ElectronicBankTransferDao {

    boolean save(Long accountId, ElectronicBankTransfer transfer);

    boolean remove(String routingNumber);
}
