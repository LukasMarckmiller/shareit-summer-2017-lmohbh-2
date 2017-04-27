package edu.hm.shareit.resources;

import edu.hm.fachklassen.Book;

import java.util.HashSet;
import java.util.Optional;

/**
 * Defines Service routine for Book Objects and stores them
 * Created by Lukas Marckmiller on 12.04.2017.
 *
 * @author Lukas Marckmiller, l.marckmil@hm.edu
 * @version 1.1
 */
public class BookServiceImpl implements BookService {
    //has to be static because Jetty creates a new BookResource Object for each request
    //so we need a static and persistent container
    static final HashSet<Book> booksSet = new HashSet<>();

    public BookServiceImpl() {
    }

    /**
     * Add book do data structure.
     *
     * @param book - A Book Object with Title,ISBN and Author
     * @return BookServiceResult
     */
    @Override
    public BookServiceResult addBook(Book book) {
        //if book already exists no duplicate handling implemented
        BookServiceResult bookServiceResult = book.getAuthor().equals("") ?
                BookServiceResult.MissingParamAuthor : book.getTitle().equals("") ?
                BookServiceResult.MissingParamTitle : book.getIsbn().equals("") ?
                BookServiceResult.MissingParamIsbn : booksSet.contains(getBook(book.getIsbn())) ?
                BookServiceResult.BookWithIsbnExistsAlready : BookServiceResult.AllRight;

        if (bookServiceResult == BookServiceResult.AllRight)
            booksSet.add(book);
        return bookServiceResult;
    }

    @Override
    public Book[] getBooks() {
        return booksSet.toArray(new Book[]{});
    }

    @Override
    public Book getBook(String isbn) {
        //Set doesnt contain dublicates so findFirst always returns null or the proper object
        Optional<Book> possibleBook = booksSet.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst();
        return possibleBook.orElse(null);
    }

    @Override
    public BookServiceResult updateBook(Book book) {
        if (booksSet.remove(getBook(book.getIsbn())))
            booksSet.add(book);
        else
            return BookServiceResult.NoBookWithIsbnFound;
        return BookServiceResult.AllRight;
    }
}
