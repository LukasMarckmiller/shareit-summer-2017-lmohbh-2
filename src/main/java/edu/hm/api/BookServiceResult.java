package edu.hm.api;

import javax.ws.rs.core.Response;

/**
 * Created by Lukas on 12.04.2017.
 */
public enum BookServiceResult {
    MissingParamAuthor(Response.Status.BAD_REQUEST,"MissingAuthor");
    AllRight(Response.Status.);
    public String getMessage() {
        return message;
    }

    public Response.Status getStatus() {
        return status;
    }

    private final String message;
    private final Response.Status status;

    BookServiceResult(String message, Response.Status status) {
        this.message = message;
        this.status = status;
    }
}
