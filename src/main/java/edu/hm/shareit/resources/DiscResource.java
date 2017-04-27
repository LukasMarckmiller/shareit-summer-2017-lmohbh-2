package edu.hm.shareit.resources;

import edu.hm.fachklassen.Disc;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by oliver on 12.04.17.
 */
@Path("/media/discs")
public class DiscResource {
    private final DiscService discService;

    public DiscResource() {
        this(new DiscServiceImpl());
    }

    DiscResource(DiscService service) {
        discService = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDisc(Disc disc) {
        final DiscServiceResult discServiceResult = discService.addDisc(disc);

        return Response.status(discServiceResult.getStatus()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs() {
        return Response.status(Response.Status.OK).entity(discService.getDiscs()).build();
    }

    @GET
    @Path("/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisc(@PathParam("barcode") String barcode) {
        final Disc disc = discService.getDisc(barcode);
        final Response.Status responseStatus = disc == null ? Response.Status.BAD_REQUEST : Response.Status.OK;

        return Response.status(responseStatus).entity(disc).build();
    }

    @PUT
    @Path("/{barcode}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDisc(@PathParam("barcode") String barcode, Disc disc) {
        DiscServiceResult discServiceResult;

        if (disc.getBarcode().isEmpty()) {
            disc = new Disc(disc.getTitle(), barcode, disc.getFsk(), disc.getDirector());
        }

        if (disc.getBarcode() != barcode) {
            discServiceResult = DiscServiceResult.BarcodeJSONAndURIDontMatch;
        } else {
            discServiceResult = discService.updateDisc(disc);
        }

        return Response.status(discServiceResult.getStatus()).build();
    }


}
