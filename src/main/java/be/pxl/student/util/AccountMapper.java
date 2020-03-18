package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class AccountMapper {

    public Account map(String validLine) throws InvalidPaymentException {
        String[] lines = validLine.split(",");
        if (lines.length != 7) {
            throw new InvalidPaymentException("Invalid number of fields in line.");
        }
        Account account = new Account();
        account.setName(lines[0]);
        account.setIBAN(lines[1]);
        return account;
    }
}
