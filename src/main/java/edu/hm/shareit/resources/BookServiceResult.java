package edu.hm.shareit.resources;

import javax.ws.rs.core.Response;

/**
 * Created by Lukas on 12.04.2017.
 */
public enum BookServiceResult {
    NoBookWithIsbnFound("No Book with given ISBN found", Response.Status.BAD_REQUEST),
    MissingParamAuthor("Missing Author",Response.Status.BAD_REQUEST),
    MissingParamTitle("Missing Title",Response.Status.BAD_REQUEST),
    MissingParamIsbn("Missing ISBN",Response.Status.BAD_REQUEST),
    AllRight("Accepted",Response.Status.OK);

    private final String message;
    private final Response.Status status;

    BookServiceResult(String message, Response.Status status) {
        this.message = message;
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
