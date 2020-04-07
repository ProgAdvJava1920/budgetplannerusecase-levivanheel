package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    private static final Logger LOGGER = LogManager.getLogger(BudgetPlannerImporter.class);
    private PathMatcher csvMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.csv");
    private AccountMapper accountMapper = new AccountMapper();
    private CounterAccountMapper counterAccountMapper = new CounterAccountMapper();
    private PaymentMapper paymentMapper = new PaymentMapper();
    private Map<String, Account> createdAccounts = new HashMap<>();
    private EntityManager entityManager;

    public BudgetPlannerImporter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void importCV(Path pad) {
        if (!csvMatcher.matches(pad)) {
            LOGGER.error("Invalid file: .csv expected. Provided {}",pad);
            return;
        }

        if (!Files.exists(pad)) {
            LOGGER.error("File {} does not exist",pad);
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(pad)) {
            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();
            String line = null;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                try{
                    Payment payment = paymentMapper.map(line);
                    payment.setAccount(getOrCreateAccount(accountMapper.map(line)));
                    payment.setCounterAccount(getOrCreateAccount(counterAccountMapper.map(line)));
                    entityManager.persist(payment);
                } catch(InvalidPaymentException ex) {
                    LOGGER.error("Error while mapping line : {}", ex.getMessage());
                }
            }
            tx.commit();
        } catch (IOException ex) {
            LOGGER.fatal("An error occured while reading : {}", pad);
        }
    }

    private Account getOrCreateAccount(Account account) {
        if (createdAccounts.containsKey(account.getIBAN())) {
            return createdAccounts.get(account.getIBAN());
        }
        entityManager.persist(account);
        createdAccounts.put(account.getIBAN(), account);
        return account;
    }
}
