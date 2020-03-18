package be.pxl.student.util;

import be.pxl.student.entity.Account;

public class CounterAccountMapper {
    public Account map(String validLine) throws InvalidPaymentException {
        String[] split = validLine.split(",");
        if (split.length != 7) {
            throw new InvalidPaymentException("Invalid number of fields in line.");
        }
        Account account = new Account();
        account.setIBAN(split[2]);
        return account;
    }
}
