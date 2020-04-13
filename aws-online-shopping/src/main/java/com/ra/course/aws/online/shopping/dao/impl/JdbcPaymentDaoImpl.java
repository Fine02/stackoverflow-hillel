package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.PaymentDao;
import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.CreditCardTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.mapper.CreditCardRowMapper;
import com.ra.course.aws.online.shopping.mapper.GetLastIdRowMapper;
import com.ra.course.aws.online.shopping.mapper.GetNumberRowMapper;
import com.ra.course.aws.online.shopping.mapper.ElectronicBankTransferRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcPaymentDaoImpl implements PaymentDao {
    private static final String GET_ELECTRONIC_BANK_TRANSFER_LIST_BY_ACCOUNT_ID = "SELECT ebt.bankName, ebt.routingNumber, ebt.accountNumber\n" +
            "FROM member m JOIN account a ON m.account_id= a.id\n" +
            "JOIN electronic_bank_transfer ebt ON ebt.account_id = a.id\n" +
            "WHERE ebt.account_id=?";

    private static final String GET_ACCOUNT_ID_OF_BANK_TRANSFER_IN_DB = "SELECT ebt.account_id FROM electronic_bank_transfer ebt WHERE accountNumber=?";

    private static final String GET_CREDIT_CARD_LIST_BY_ACCOUNT_ID = " SELECT \n" +
            "crc.nameOnCard, crc.cardNumber,crc.code, adr.streetAddress, adr.city, adr.state, adr.zipcode, adr.country\n" +
            "FROM (\"member\" m JOIN account a ON m.account_id= a.id\n" +
            "JOIN credit_card crc ON crc.account_id = a.id), address adr\n" +
            "WHERE (crc.address_id = adr.id AND crc.account_id=?)";

    private static final String GET_ACCOUNT_ID_OF_CREDIT_CARD_IN_DB = "SELECT cc.account_id FROM credit_card cc WHERE cardNumber=?";
    //INSERT INTO notification (createdOn, content) VALUES (?, ?) RETURNING notification.id";
    private static final String INSERT_DATA_INTO_PAYMENT_TABLE = "INSERT INTO payment (payment_status_id, amount) VALUES (?, ?) RETURNING payment.id";
    private static final String GET_ID_OF_PAYMENT_STATUS = "SELECT ps.id FROM payment_status ps WHERE ps.status=?";
    private static final String INSERT_DATA_INTO_ELECTRONIC_BANK_TRANSACTION = "INSERT INTO electronic_bank_transaction (payment_id) VALUES (?)";
    private static final String INSERT_DATA_INTO_CREDIT_CARD_TRANSACTION = "INSERT INTO credit_card_transaction (payment_id) VALUES (?)";


    private final JdbcTemplate jdbcTemplate;
    private final ElectronicBankTransferRowMapper listTransferRowMapper;
    private final GetNumberRowMapper getNumberRowMapper;
    private final CreditCardRowMapper cardRowMapper;
    private final GetLastIdRowMapper getLastIdRowMapper;

    @Autowired
    public JdbcPaymentDaoImpl(JdbcTemplate jdbcTemplate, ElectronicBankTransferRowMapper listTransferRowMapper, GetNumberRowMapper getNumberRowMapper, CreditCardRowMapper cardRowMapper, GetLastIdRowMapper getLastIdRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.listTransferRowMapper = listTransferRowMapper;
        this.getNumberRowMapper = getNumberRowMapper;
        this.cardRowMapper = cardRowMapper;
        this.getLastIdRowMapper = getLastIdRowMapper;
    }

    @Override
    public Member foundMemberById(Long memberId) {
        return null;
    }

    @Override
    public List<ElectronicBankTransfer> foundListOfBankTransfer(List<ElectronicBankTransfer> bankTransfers) {
        ElectronicBankTransfer transfer = bankTransfers.get(0);
        String numberAccount = transfer.getAccountNumber();
        Integer getMemberAccountId = jdbcTemplate.queryForObject(GET_ACCOUNT_ID_OF_BANK_TRANSFER_IN_DB, getNumberRowMapper, numberAccount);
        List<ElectronicBankTransfer> bankTransferList = jdbcTemplate.query(GET_ELECTRONIC_BANK_TRANSFER_LIST_BY_ACCOUNT_ID, listTransferRowMapper, getMemberAccountId);
        return bankTransferList;
    }

    @Override
    public List<CreditCard> foundListOfCreditCard(List<CreditCard> creditCards) {
        CreditCard creditCard = creditCards.get(0);
        String cardNumber = creditCard.getCardNumber();
        Integer getMemberAccountId = jdbcTemplate.queryForObject(GET_ACCOUNT_ID_OF_CREDIT_CARD_IN_DB, getNumberRowMapper, cardNumber);
        List<CreditCard> cardList = jdbcTemplate.query(GET_CREDIT_CARD_LIST_BY_ACCOUNT_ID, cardRowMapper, getMemberAccountId);
        return cardList;
    }


    @Override
    public void createTransaction(ElectronicBankTransaction bankTransaction) {
        Integer paymentStatusID = jdbcTemplate.queryForObject(GET_ID_OF_PAYMENT_STATUS, getLastIdRowMapper, bankTransaction.getStatus().toString());
        Integer lastInsertPaymentId = jdbcTemplate.queryForObject(INSERT_DATA_INTO_PAYMENT_TABLE, getLastIdRowMapper, paymentStatusID, bankTransaction.getAmount());
        jdbcTemplate.update(INSERT_DATA_INTO_ELECTRONIC_BANK_TRANSACTION, lastInsertPaymentId);
    }

    @Override
    public void createTransaction(CreditCardTransaction cardTransaction) {
        Integer paymentStatusID = jdbcTemplate.queryForObject(GET_ID_OF_PAYMENT_STATUS, getLastIdRowMapper, cardTransaction.getStatus().toString());
        Integer lastInsertPaymentId = jdbcTemplate.queryForObject(INSERT_DATA_INTO_PAYMENT_TABLE, getLastIdRowMapper, paymentStatusID, cardTransaction.getAmount());
        jdbcTemplate.update(INSERT_DATA_INTO_CREDIT_CARD_TRANSACTION, lastInsertPaymentId);
    }
}
