package be.pxl.student.service;

import be.pxl.student.dao.LabelDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.dao.impl.LabelDaoImpl;
import be.pxl.student.dao.impl.PaymentDaoImpl;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import be.pxl.student.exceptions.LabelNotFoundException;
import be.pxl.student.util.EntityManagerUtil;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class PaymentService {

    private PaymentDao paymentDao;
    private LabelDao labelDao;

    public PaymentService() {
        this.labelDao = new LabelDaoImpl(EntityManagerUtil.createEntityManager());
        this.paymentDao = new PaymentDaoImpl(EntityManagerUtil.createEntityManager());;
    }

    public void linkLabelToPayment(long paymentId, long labelId) {

        Label labelById = labelDao.findLabelById(labelId);
        Payment paymentById = paymentDao.findPaymentById(paymentId);

        List<Label> labels = paymentById.getLabel();
        labels.add(labelById);

        labelById.setPayment(paymentById);
        paymentById.setLabel(labels);

        paymentDao.updatePayment(paymentById);

    }

    public void removePayment(long paymentId) {
        Payment paymentById = paymentDao.findPaymentById(paymentId);
        paymentDao.removePayment(paymentById);
    }
}
