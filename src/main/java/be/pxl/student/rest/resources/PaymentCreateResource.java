package be.pxl.student.rest.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentCreateResource {
    private String counterAccount;
    private float amount;
    private String detail;
    private LocalDate datum;

    public String getCounterAccount() {
        return counterAccount;
    }

    public void setCounterAccount(String counterAccount) {
        this.counterAccount = counterAccount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
}
