package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.payment.CreditCard;

public interface CreditCardDao {

    boolean save(Long accountId, CreditCard card, Long billingAddressId);

    boolean remove(String cardNumber);

}
