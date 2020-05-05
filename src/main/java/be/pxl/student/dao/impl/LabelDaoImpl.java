package be.pxl.student.dao.impl;

import be.pxl.student.dao.LabelDao;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class LabelDaoImpl implements LabelDao {
    private static final Logger LOGGER = LogManager.getLogger(AccountDaoImpl.class);

    private EntityManager entityManager;

    public LabelDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Label findLabelByName(String name) {
        return null;
    }

    @Override
    public List<Label> findAllLabels() {
        TypedQuery<Label> query = entityManager.createNamedQuery("findAllLabels", Label.class);
        LOGGER.info("query with all labels");
        try {
            return query.getResultList();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Label findLabelById(long id) {
        TypedQuery<Label> query = entityManager.createNamedQuery("findLabelById", Label.class);
        query.setParameter("id",id);
        LOGGER.info("query with label [" + id + "]");
        try {
            return query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void saveLabel(Label label) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(label);
        transaction.commit();
    }

    @Override
    public void removeLabel(Label label) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(label);
        transaction.commit();
    }

}
