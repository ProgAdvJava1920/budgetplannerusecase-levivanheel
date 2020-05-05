package be.pxl.student.rest;

import be.pxl.student.exceptions.LabelInUseException;
import be.pxl.student.exceptions.LabelNotFoundException;
import be.pxl.student.service.LabelService;
import be.pxl.student.service.PaymentService;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/payments")
public class PaymentsRest {

    @Inject
    private PaymentService paymentService;

    @POST
    @Path("{paymentId}/link/{labelId}")
    public Response linkLabelToPayment(@PathParam("paymentId") long paymentId, @PathParam("labelId") long labelId) {
        try {
            paymentService.linkLabelToPayment(paymentId,labelId);
            return Response.accepted().build();
        }
        catch(Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response removePayment(@PathParam("id") long id) {
        paymentService.removePayment(id);
        return Response.accepted().build();
    }
}
