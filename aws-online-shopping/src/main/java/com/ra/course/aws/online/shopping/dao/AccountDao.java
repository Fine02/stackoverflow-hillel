package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDao extends Dao<Account> {

    Long save(Account account);

    Account findById(Long id);

    boolean update(Account account);

    boolean remove(Long id);

    List<Account> getAll();

    boolean saveCreditCard(CreditCard creditCard, Long accountId);

    boolean saveElectornicBankTransfer(ElectronicBankTransfer transfer, Long accountId);

}
