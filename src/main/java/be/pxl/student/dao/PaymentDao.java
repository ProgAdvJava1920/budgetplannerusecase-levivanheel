package be.pxl.student.dao;

import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;

public interface PaymentDao {
    long countPaymentsDao(long labelId);
    Payment findPaymentById(long id);
    void removePayment(Payment payment);
    void updatePayment(Payment payment);
}
