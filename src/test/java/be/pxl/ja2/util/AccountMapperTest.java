package be.pxl.ja2.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.AccountMapper;
import be.pxl.student.util.InvalidPaymentException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AccountMapperTest {
    private String validLine = "Jos,BE69771770897312,BE98848156955855,Sun Feb 09 05:30:49 CET 2020,-4732.01,EUR,Voluptatem et deserunt aut.";
    private AccountMapper accountMapper = new AccountMapper();

    @Test
    public void aValidLineIsMappedToAnAccount() throws InvalidPaymentException {
        Account result = accountMapper.map(validLine);
        assertNotNull(result);
        assertEquals("Jos",result.getName());
        assertEquals("BE69771770897312",result.getIBAN());
        assertEquals(1, result.getPayments().size());

        Payment resultPayment = result.getPayments().get(0);
        assertEquals(LocalDateTime.of(2020,2,13,5,47,35), resultPayment.getDate());
        assertEquals("EUR",resultPayment.getCurrency());
        assertEquals(-4732.01,resultPayment.getAmount());
        assertEquals("oluptatem et deserunt aut.",resultPayment.getDetail());
    }
}
