package be.pxl.student.rest;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.exceptions.AccountNotFoundException;
import be.pxl.student.rest.resources.PaymentCreateResource;
import be.pxl.student.rest.resources.PaymentResource;
import be.pxl.student.service.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Path("/accounts")
public class AccountsRest {

    @Inject
    private AccountService accountService;

    @GET
    @Path("{name}")
    @Produces("application/json")
    public Response getPayments(@PathParam("name") String name) {
        try {
            HashMap<String,Object> payments = new HashMap<>();
            var paymentTotal = mapPayments(accountService.findPaymentsByAccountName(name));
            System.out.println(paymentTotal);
            //List<Payment> payments = accountService.findPaymentsByAccountName(name);
            payments.put("payments",paymentTotal);
            payments.put("receivingAmount",paymentTotal.stream().filter(a -> a.getAmount() > 0).mapToDouble(PaymentResource::getAmount).sum());
            payments.put("resultAmount",paymentTotal.stream().mapToDouble(PaymentResource::getAmount).sum());
            payments.put("spendingAmount",paymentTotal.stream().filter(a -> a.getAmount() < 0).mapToDouble(PaymentResource::getAmount).sum());
            return Response.ok(payments).build();
        } catch(AccountNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{name}")
    public Response addPayment(@PathParam("name") String name, PaymentCreateResource paymentCreateResource) {
        try {
            accountService.addPayment(name,paymentCreateResource.getCounterAccount(), paymentCreateResource.getAmount(),paymentCreateResource.getDetail(), paymentCreateResource.getDatum());
            return Response.created(UriBuilder.fromPath("/accounts/" + name).build()).build();
        } catch(AccountNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response addAccount(Account account) {
        try {
            accountService.createAccount(account);
            return Response.created(UriBuilder.fromPath("/accounts/").build()).build();
        } catch (AccountNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getMessage()).build();
        }
    }

    private List<PaymentResource> mapPayments(List<Payment> payments) {
        return payments.stream().map(this::mapPayment).collect(Collectors.toList());
    }

    private PaymentResource mapPayment(Payment payment) {
        PaymentResource result = new PaymentResource();
        result.setId(payment.getId());
        result.setDate(payment.getDate());
        result.setAmount(payment.getAmount());
        result.setCounterAccount(payment.getCounterAccount().getIBAN());
        result.setCurrency(payment.getCurrency());
        result.setDetail(payment.getDetail());
        return result;
    }
}
