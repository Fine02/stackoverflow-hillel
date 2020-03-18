package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.payment.Payment;

public interface PaymentDao<T extends Payment> {

    Long save (T t);

    Payment findByID (Long id);

    boolean update (T t);

    boolean delete (Long id);
}
