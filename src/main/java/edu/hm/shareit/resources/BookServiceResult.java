package edu.hm.shareit.resources;

import javax.ws.rs.core.Response;

/**
 * Created by Lukas on 12.04.2017.
 */
public enum BookServiceResult {
    NoBookWithIsbnFound("No book with given isbn found (maybe a typo, isbn´s are pretty long).", Response.Status.BAD_REQUEST),
    MissingParamAuthor("Missing author.", Response.Status.BAD_REQUEST),
    MissingParamTitle("Missing title.", Response.Status.BAD_REQUEST),
    MissingParamIsbn("Missing isbn.", Response.Status.BAD_REQUEST),
    BookWithIsbnExistsAlready("Book with given isbn already exists, i don´t know what to do?!.", Response.Status.BAD_REQUEST),
    AllRight("Your request was correct. Like that!", Response.Status.OK);

    private final String message;
    private final Response.Status status;

    BookServiceResult(String message, Response.Status status) {
        this.message = message;
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
