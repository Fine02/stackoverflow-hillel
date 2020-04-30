package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;

import java.util.List;

public interface AccountService {

    Long create(Account account);

    boolean update(Account account);

    boolean delete(Long id);

    Account findById(Long id);

    List<Account> findAll();

    boolean resetPassword(Long accountId, String password);

    boolean addCreditCard(Long accountId, CreditCard card);

    boolean deleteCreditCard(String cardNumber);

    boolean addElectronicBankTransfer(Long accountId, ElectronicBankTransfer transfer);

    boolean deleteElectronicBankTransfer(String routingNumber);

}
