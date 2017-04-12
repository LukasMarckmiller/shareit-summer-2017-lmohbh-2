package edu.hm.api;

import edu.hm.fachklassen.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Lukas on 12.04.2017.
 */
@Path("/media/books")
public class BookResource {
    @POST
    @Path("{book}")
    public Response createBooks(@PathParam("book")Book book)
    {
        return null;
    }

    @GET
    public Response getBooks()
    {

    }
}
