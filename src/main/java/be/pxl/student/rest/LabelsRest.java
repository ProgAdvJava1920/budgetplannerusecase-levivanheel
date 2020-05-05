package be.pxl.student.rest;

import be.pxl.student.entity.Label;
import be.pxl.student.exceptions.DuplicateLabelException;
import be.pxl.student.exceptions.LabelInUseException;
import be.pxl.student.exceptions.LabelNotFoundException;
import be.pxl.student.service.LabelService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;


@Path("/labels")
public class LabelsRest {

    @Inject
    private LabelService labelService;

    @GET
    @Produces("application/json")
    public Response getLabels() {
        List<Label> labels = labelService.getAllLabels();
        return Response.ok(labels).build();
    }

    @POST
    public Response addLabel(Label label) {
        try {
            labelService.addLabel(label.getName());
            return Response.created(UriBuilder.fromPath("/labels/").build()).build();
        } catch (DuplicateLabelException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response removeLabel(@PathParam("id") long id) {
        try {
            labelService.removeLabel(id);
            return Response.accepted(UriBuilder.fromPath("/labels/{id}")).build();
        } catch (LabelNotFoundException | LabelInUseException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getMessage()).build();
        }
    }


}
