package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.AccountDao;
import com.ra.course.aws.online.shopping.dao.AddressDao;
import com.ra.course.aws.online.shopping.dao.CreditCardDao;
import com.ra.course.aws.online.shopping.dao.ElectronicBankTransferDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.exceptions.AccountNotFoundException;
import com.ra.course.aws.online.shopping.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private transient final AccountDao accountDao;
    private transient final AddressDao addressDao;
    private transient final CreditCardDao creditCardDao;
    private transient final ElectronicBankTransferDao electronicBankTransferDao;

    public AccountServiceImpl(final AccountDao accountDao, final AddressDao addressDao, final CreditCardDao creditCardDao,
                              final ElectronicBankTransferDao electronicBankTransferDao) {
        this.accountDao = accountDao;
        this.addressDao = addressDao;
        this.creditCardDao = creditCardDao;
        this.electronicBankTransferDao = electronicBankTransferDao;
    }

    @Override
    public Long create(final Account newAccount) {
        return accountDao.save(newAccount);
    }

    @Override
    public boolean update(final Account accountToUpdate) {
        Optional.ofNullable(accountDao.findById(accountToUpdate.getId()))
                .ifPresentOrElse(account -> {
                    Address newAddress = accountToUpdate.getShippingAddress();
                    newAddress.setId(account.getId());
                    addressDao.update(accountToUpdate.getShippingAddress());
                    accountDao.update(accountToUpdate);
                }, () -> {
                    throw new AccountNotFoundException("Account with id=" + accountToUpdate.getId() + " not found");
                });
        return true;
    }

    @Override
    public boolean delete(final Long accountId) {
        Optional.ofNullable(accountDao.findById(accountId))
                .ifPresentOrElse(account -> accountDao.remove(accountId), () -> {
                    throw new AccountNotFoundException("Account with id = " + accountId + " not found.");
                });
        return true;
    }

    @Override
    public Account findById(final Long id) {
        return accountDao.findById(id);
    }

    @Override
    public List<Account> findAll() {
        return accountDao.getAll();
    }

    @Override
    public boolean resetPassword(Long accountId, String password) {
        Account account = accountDao.findById(accountId);
        account.setPassword(password);
        accountDao.update(account);
        return true;
    }

    @Override
    public boolean addCreditCard(Long accountId, CreditCard card) {
        Long billingAddressId = addressDao.save(card.getBillingAddress());
        creditCardDao.save(accountId, card, billingAddressId);
        return true;
    }

    @Override
    public boolean deleteCreditCard(String cardNumber) {
        creditCardDao.remove(cardNumber);
        return true;
    }

    @Override
    public boolean addElectronicBankTransfer(Long accountId, ElectronicBankTransfer transfer) {
        electronicBankTransferDao.save(accountId, transfer);
        return true;
    }

    @Override
    public boolean deleteElectronicBankTransfer(String routingNumber) {
        return false;
    }
}
