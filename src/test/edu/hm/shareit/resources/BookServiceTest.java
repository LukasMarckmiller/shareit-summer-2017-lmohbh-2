package edu.hm.shareit.resources;

import edu.hm.fachklassen.Book;
import edu.hm.shareit.resources.BookResource;
import edu.hm.shareit.resources.BookServiceImpl;
import edu.hm.shareit.resources.BookServiceResult;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

/*
*ShareIt
* Date: 23.04.2017
* java version 1.8.0_73
* Windows 10 (1607)
* Intel(R) Core(TM) i5-4200U CPU @ 1.60GHz 2.30 GHz
* @Author Sebastian Heunke, heunke@hm.edu
*/

public class BookServiceTest {

    //Tests for the BookServiceImplementation
    @Test
    public void addingValidBook(){
        BookServiceImpl sut = new BookServiceImpl();
        Book book = new Book("Mobby Dick","Author","123456");
        BookServiceResult result = sut.addBook(book);
        Assert.assertEquals(BookServiceResult.AllRight,result);
    }

    @Test
    public void noEmptyFieldsAccepted(){
        BookServiceImpl sut = new BookServiceImpl();

        //No Titel
        Book book = new Book("","Author","123456");
        BookServiceResult result = sut.addBook(book);
        Assert.assertEquals(BookServiceResult.MissingParamTitle,result);
        //No Author
        book = new Book("Java for Dummies","","42");
        result = sut.addBook(book);
        Assert.assertEquals(BookServiceResult.MissingParamAuthor,result);
        //No Isbn
        book = new Book("Isbn Book","Santa Claus","");
        result = sut.addBook(book);
        Assert.assertEquals(BookServiceResult.MissingParamIsbn,result);
    }

    @Test
    public void getBooksWorks(){ //todo: rewrite
        Book[] expected = new Book[2];
        BookServiceImpl sut = new BookServiceImpl();
        Book book = new Book("My First Book","Someone","#1");
        expected[0] = book;
        sut.addBook(book);

        book = new Book("My Second Book","Someone","#2");
        sut.addBook(book);
        expected[1] = book;
        Book[] result = sut.getBooks();
        Assert.assertTrue(result.length >= 2); //temporary workaround, need to reset data between tests
    }

    @Test
    public void getBookWorks(){
        BookServiceImpl sut = new BookServiceImpl();
        Book book = new Book("booooook","authoooor","isbn");
        sut.addBook(book);
        Book result = sut.getBook("isbn");
        Assert.assertEquals(book,result);

        result = sut.getBook("Hello Book Service!");
        Assert.assertEquals(null,result);

    }

    @Test
    public void updateBookWorks() {
        BookServiceImpl sut = new BookServiceImpl();
        Book book = new Book("HeloWorld","Me","0");
        sut.addBook(book);
        book = new Book("Hello World!","Not Me","0");
        BookServiceResult result = sut.updateBook(book);
        Assert.assertEquals(BookServiceResult.AllRight,result);

        Book updatedBook = sut.getBook("0");
        Assert.assertEquals(book,updatedBook);

        result = sut.updateBook(new Book("Goodbye.","?","42"));
        Assert.assertEquals(BookServiceResult.NoBookWithIsbnFound,result);
    }

    /*
    Test for the BookResource Class. At this point the BookServiceImpl should be save to asume working.
     */


    @Test
    public void postNewBook(){
        BookResource sut = new BookResource();
        Book book = new Book("TITEL","AUTHOR","ISBN");
        Response result = sut.createBook(book);
        Assert.assertEquals(Response.Status.OK.getStatusCode(),result.getStatus());

        book = new Book("","AUTHOR","4");
        result = sut.createBook(book);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),result.getStatus());

        book = new Book("A good Titel","A medicore Author","");
        result = sut.createBook(book);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),result.getStatus());

        book = new Book("No Book","","404");
        result = sut.createBook(book);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),result.getStatus());

        //todo: dupplicate handling
    }

    @Test
    public void getPostedBooks(){
        BookResource sut = new BookResource();
        Book book = new Book(".",",",";");
        sut.createBook(book);
        Response result = sut.getBook(";");
        Assert.assertEquals(book,result.getEntity());

        Response nullBook = sut.getBook("NOT A ISBN");
        Assert.assertEquals(null, nullBook.getEntity());

        result = sut.getBooks();
        Assert.assertEquals(Book[].class,result.getEntity().getClass());//temporary test
    }

    @Test
    public void updateBooks(){
        BookResource sut = new BookResource();
        Book book = new Book("abc","def","ghi");
        sut.createBook(book);
        Response result = sut.updateBook("ghi",new Book("ABC","DEF","XYZ"));

        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),result.getStatus());
        Assert.assertEquals(book,sut.getBook("ghi").getEntity());

        book = new Book("ABC","DEF","ghi");
        result = sut.updateBook("ghi",book);
        Assert.assertEquals(Response.Status.OK.getStatusCode(),result.getStatus());
        Assert.assertEquals(book,sut.getBook("ghi").getEntity());
    }





}