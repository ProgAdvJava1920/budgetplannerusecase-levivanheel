package be.pxl.student.service;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.impl.AccountDaoImpl;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.exceptions.AccountNotFoundException;
import be.pxl.student.util.EntityManagerUtil;

import javax.ejb.Stateless;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class AccountService {

    private AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDaoImpl(EntityManagerUtil.createEntityManager());
    }

    public List<Payment> findPaymentsByAccountName(String name) throws AccountNotFoundException {
        Account account = accountDao.findAccountByName(name);
        if (account == null) {
            throw new AccountNotFoundException("There already exist an account with name ["+ name + "]");
        }
        return account.getPayments();
    }

    public void createAccount(Account account) throws AccountNotFoundException {
        Account accountExists = accountDao.findAccountByName(account.getName());
        if (accountExists != null) {
            throw new AccountNotFoundException("There already exist an account with name ["+ account.getName() + "]");
        }
        accountDao.createAccount(account);
    }

    public void addPayment(String name, String counterAccountIBAN, float amount, String detail, LocalDate datum) throws AccountNotFoundException {
        Account account = accountDao.findAccountByName(name);
        if (account == null) {
            throw new AccountNotFoundException(name);
        }
        Account counterAccount = accountDao.findAccountByIBAN(counterAccountIBAN);
        if (counterAccount == null) {
            counterAccount = new Account();
            counterAccount.setIBAN(counterAccountIBAN);
            counterAccount = accountDao.createAccount(counterAccount);
        }
        Payment payment = new Payment();
        payment.setCounterAccount(counterAccount);
        payment.setAmount(amount);
        payment.setCurrency("EUR");
        payment.setDate(datum == null ? LocalDate.now() : datum);
        payment.setDetail(detail);
        account.addPayment(payment);
        accountDao.updateAccount(account);
    }
}
