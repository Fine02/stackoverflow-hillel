package service;

import com.ra.course.aws.online.shopping.entity.payment.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.user.Account;

public interface PaymentService {
    PaymentStatus makeCreditCardPayment(Account account);
    PaymentStatus makeBankTransferPayment(Account account);
}
