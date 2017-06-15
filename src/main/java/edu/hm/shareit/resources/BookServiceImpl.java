package edu.hm.shareit.resources;

import edu.hm.fachklassen.Book;
import edu.hm.persitence.HibernateUtils;

import java.util.HashSet;
import java.util.List;

/**
 * Defines Service routine for Book Objects and stores them
 * Created by Lukas Marckmiller on 12.04.2017.
 *
 * @author Lukas Marckmiller, l.marckmil@hm.edu
 * @version 1.1
 */
public class BookServiceImpl implements BookService {
    public static final String BOOK = "Book";
    //has to be static because Jetty creates a new BookResource Object for each request
    //so we need a static and persistent container
    static final HashSet<Book> BOOKS_SET = new HashSet<>();
    public static final String ISBN = "isbn";

    /**
     * Add book do data structure.
     *
     * @param book - A Book Object with Title,ISBN and Author
     * @return BookServiceResult
     */
    @Override
    public BookServiceResult addBook(Book book) {
        //if book already exists no duplicate handling implemented
        BookServiceResult bookServiceResult = book.getAuthor().equals("")
                ? BookServiceResult.MissingParamAuthor : book.getTitle().equals("")
                ? BookServiceResult.MissingParamTitle : book.getIsbn().equals("")
                ? BookServiceResult.MissingParamIsbn : book.getIsbn().length() != 13
                ? BookServiceResult.InvalidIsbn : (!HibernateUtils.doSelectWhere(BOOK, ISBN,book.getIsbn()).isEmpty())
                ? BookServiceResult.BookWithIsbnExistsAlready : BookServiceResult.AllRight;

        if (bookServiceResult == BookServiceResult.AllRight) {
            HibernateUtils.doInsert(book);
            BOOKS_SET.add(book);
        }
        return bookServiceResult;
    }

    @Override
    public Book[] getBooks() {
        return ((List<Book>)HibernateUtils.doSelectWhere(BOOK,null,null)).toArray(new Book[]{});
    }

    @Override
    public Book getBook(String isbn) {
        //Set doesnt contain dublicates so findFirst always returns null or the proper object
        List<Book> result = HibernateUtils.doSelectWhere(BOOK, ISBN,isbn);
        return result.stream().findFirst().orElse(null);
    }

    @Override
    public BookServiceResult updateBook(Book book) {
        if (!HibernateUtils.doSelectWhere(BOOK, ISBN,book.getIsbn()).isEmpty()) {
            HibernateUtils.doUpdate(BOOK, ISBN,book.getIsbn(),Book.class,(foundBook) ->
                    ((Book)foundBook).setAuthor(book.getAuthor()).setIsbn(book.getIsbn()).setTitle(book.getTitle()));
        } else {
            return BookServiceResult.NoBookWithIsbnFound;
        }
        return BookServiceResult.AllRight;
    }
}
