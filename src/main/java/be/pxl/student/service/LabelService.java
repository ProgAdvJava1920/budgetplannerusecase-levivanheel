package be.pxl.student.service;

import be.pxl.student.dao.LabelDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.dao.impl.LabelDaoImpl;
import be.pxl.student.dao.impl.PaymentDaoImpl;
import be.pxl.student.entity.Label;
import be.pxl.student.exceptions.DuplicateLabelException;
import be.pxl.student.exceptions.LabelInUseException;
import be.pxl.student.exceptions.LabelNotFoundException;
import be.pxl.student.util.EntityManagerUtil;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class LabelService {

    private LabelDao labelDao;
    private PaymentDao paymentDao;

    public LabelService() {
        this.labelDao = new LabelDaoImpl(EntityManagerUtil.createEntityManager());
        this.paymentDao = new PaymentDaoImpl(EntityManagerUtil.createEntityManager());;
    }

    public void addLabel(String name) throws DuplicateLabelException {
        Label existingLabel = labelDao.findLabelByName(name);
        if (existingLabel != null) {
            throw new DuplicateLabelException("There already exists a label with the name " + name + ".");
        }
        labelDao.saveLabel(new Label(name));
    }

    public List<Label> getAllLabels() {
        System.out.println(this.labelDao.findAllLabels());
        return this.labelDao.findAllLabels();
    }

    public void removeLabel(long labelId) throws LabelNotFoundException, LabelInUseException {
        Label labelById = labelDao.findLabelById(labelId);
        if (labelById == null) {
            throw new LabelNotFoundException("Label with id " + labelId + " cannot be found.");
        }

        long numberOfPayments = paymentDao.countPaymentsDao(labelId);
        if (numberOfPayments > 0) {
            throw new LabelInUseException("Label " + labelById.getName() + " is in use. Remove the payments first or change their label");
        }

        labelDao.removeLabel(labelById);
    }
}
