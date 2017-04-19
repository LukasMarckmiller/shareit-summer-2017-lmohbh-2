package edu.hm.shareit.resources;

import edu.hm.fachklassen.Book;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*
 * (C) 2017, Lukas, l.marckmiller@hm.edu on 12.04.2017.
 * Java 1.8.0_111, Windows 10 Pro 64bit
 * Intel Core i5-6600K CPU/3.50GHz overclocked 4.1GHz, 4 cores, 16000 MByte RAM)
 * with IntelliJ IDEA 2017.1.1
 *
 */
@Path("/media/books")
public class BookResource {

    private final BookService bookService;

    public BookResource() {
        this.bookService = new BookServiceImpl();
    }

    private BookService getBookService() {
        return bookService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book)
    {

        BookServiceResult result = bookService.addBook(book);

        return Response
                .status(result.getStatus())
                .build();
    }

    @PUT
    @Path("{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("isbn")String isbn, Book book)
    {
        //isbn in uri differs from isbn in book object
        if (!book.getIsbn().equals("") && !isbn.equals(book.getIsbn()))
        {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new JSONObject().put("Message","ISBN"))
                    .build();
        }
        //no isbn in request json body
        else if (book.getIsbn().equals(""))
              book = new Book(book.getTitel(),book.getAuthor(),isbn);


        //find and replace book object
        BookServiceResult result = bookService.updateBook(book);
        return Response.status(result.getStatus())
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        return Response
                .status(Response.Status.ACCEPTED)
                .entity(getBookService().getBooks())
                .build();
    }

    @GET
    @Path("{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("isbn") String isbn)
    {
        Book book = getBookService().getBook(isbn);
        return Response
                .status(book == null ? Response.Status.BAD_REQUEST : Response.Status.ACCEPTED)
                .entity(book)
                .build();
    }
}
