package edu.hm.api;

import edu.hm.fachklassen.Disc;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by oliver on 12.04.17.
 */
@Path("/media/discs")
public class MediaResourceDiscs {

    private final MediaServiceDiscs msd = new MediaServiceImplDiscs();

    public MediaResourceDiscs(){}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDisc(Disc disc){
        final MediaServiceResultDiscs result = msd.addDisc(disc);
        return Response.status(result.getCode()).entity(result.getStatus()).build();
    }

    @GET
    public Response getDiscs(){
        return Response.status(200).entity(msd.getDiscs()).build();
    }

    //@GET
    //@Path("/{barcode}")
    //public Response getDiscs(@PathParam("barcode") Disc disc){
    //    return null;
    //}

    //@PUT
    //@Path("/{barcode}")
    //public Response updateDisc(@PathParam("barcode") Disc disc){
    //    return Response.status(200).build();
    //}


}
