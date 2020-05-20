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
        final Long accountId = accountDao.save(newAccount);
        addressDao.saveAccAdd(newAccount.getShippingAddress(), accountId);
        return accountId;
    }

    @Override
    public boolean update(final Account accountToUpdate) {
        final Optional<Account> accountOpt = Optional.ofNullable(accountDao.findById(accountToUpdate.getId()));
        if (accountOpt.isPresent()) {
            final Account account = accountOpt.get();
            final Address newAddress = accountToUpdate.getShippingAddress();
            newAddress.setId(account.getShippingAddress().getId());
            addressDao.updateAccAdd(newAddress);
            accountDao.update(accountToUpdate);
        } else {
            throw new AccountNotFoundException();
        }
        return true;
    }

    @Override
    public boolean delete(final Long accountId) {
        accountDao.remove(accountId);
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
    public boolean addCreditCard(final Long accountId, final CreditCard card) {
        final Long cardId = creditCardDao.save(accountId, card);
        addressDao.saveBillAdd(card.getBillingAddress(), cardId);
        return true;
    }

    @Override
    public boolean deleteCreditCard(final String cardNumber) {
        return creditCardDao.remove(cardNumber);
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