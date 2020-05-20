package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.payment.CreditCard;

public interface CreditCardDao {

    Long save(Long accountId, CreditCard card);

    boolean remove(String cardNumber);

}
