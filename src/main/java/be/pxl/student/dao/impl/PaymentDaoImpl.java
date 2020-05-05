package be.pxl.student.dao.impl;

import be.pxl.student.dao.PaymentDao;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class PaymentDaoImpl implements PaymentDao {
    private static final Logger LOGGER = LogManager.getLogger(AccountDaoImpl.class);

    private EntityManager entityManager;

    public PaymentDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public long countPaymentsDao(long labelId) {
        return 0;
    }

    @Override
    public Payment findPaymentById(long id) {
        TypedQuery<Payment> query = entityManager.createNamedQuery("findPaymentById", Payment.class);
        query.setParameter("id",id);
        LOGGER.info("query with payment [" + id + "]");
        try {
            return query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void removePayment(Payment payment) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        payment.getAccount().getPayments().remove(payment);
        entityManager.remove(payment);
        transaction.commit();
    }

    @Override
    public void updatePayment(Payment payment) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(payment);
        transaction.commit();
    }
}
