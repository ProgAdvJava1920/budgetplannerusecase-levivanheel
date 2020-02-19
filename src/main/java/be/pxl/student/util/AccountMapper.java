package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class AccountMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

    public Account map(String validLine) {
        String[] lines = validLine.split(",");

        Account account = new Account();
        account.setIBAN(lines[1]);
        account.setName(lines[0]);
        account.setPayments(new ArrayList<>());

        Payment payment = new Payment(LocalDateTime.parse(lines[3],FORMATTER), Float.parseFloat(lines[4]), lines[5], lines[6]);
        account.getPayments().add(payment);

        return account;
    }
}
