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
    private transient final ElectronicBankTransferDao transferDao;

    public AccountServiceImpl(final AccountDao accountDao, final AddressDao addressDao, final CreditCardDao creditCardDao,
                              final ElectronicBankTransferDao transferDao) {
        this.accountDao = accountDao;
        this.addressDao = addressDao;
        this.creditCardDao = creditCardDao;
        this.transferDao = transferDao;
    }

    @Override
    public Long create(final Account newAccount) {
        final Long addressId = addressDao.save(newAccount.getShippingAddress());
        return accountDao.save(newAccount, addressId);
    }

    @Override
    public boolean update(final Account accountToUpdate) {
        Optional.ofNullable(accountDao.findById(accountToUpdate.getId()))
                .ifPresentOrElse(account -> {
                    final Address newAddress = accountToUpdate.getShippingAddress();
                    newAddress.setId(account.getId());
                    addressDao.update(accountToUpdate.getShippingAddress());
                    accountDao.update(accountToUpdate);
                }, () -> {
                    throw new AccountNotFoundException();
                });
        return true;
    }

    @Override
    public boolean delete(final Long accountId) {
        Optional.ofNullable(accountDao.findById(accountId))
                .ifPresentOrElse(account -> accountDao.remove(accountId), () -> {
                    throw new AccountNotFoundException();
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
    public boolean resetPassword(final Long accountId, final String password) {
        final Account account = accountDao.findById(accountId);
        account.setPassword(password);
        accountDao.update(account);
        return true;
    }

    @Override
    public boolean addCreditCard(final Long accountId, final CreditCard card) {
        final Long billingAddressId = addressDao.save(card.getBillingAddress());
        creditCardDao.save(accountId, card, billingAddressId);
        return true;
    }

    @Override
    public boolean deleteCreditCard(final String cardNumber) {
        creditCardDao.remove(cardNumber);
        return true;
    }

    @Override
    public boolean addElectronicBankTransfer(final Long accountId, final ElectronicBankTransfer transfer) {
        transferDao.save(accountId, transfer);
        return true;
    }

    @Override
    public boolean deleteElectronicBankTransfer(final String routingNumber) {
        transferDao.remove(routingNumber);
        return true;
    }
}
