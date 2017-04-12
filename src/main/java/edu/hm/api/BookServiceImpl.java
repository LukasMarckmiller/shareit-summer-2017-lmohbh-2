package edu.hm.api;

import edu.hm.fachklassen.Book;
import edu.hm.fachklassen.Medium;

/**
 * Created by Lukas on 12.04.2017.
 */
public class BookServiceImpl implements BookService{
    public BookServiceImpl() {
    }

    @Override
    public BookServiceResult addBook(Book book) {
        return null;
    }

    @Override
    public Medium[] getBooks() {
        return new Medium[0];
    }

    @Override
    public BookServiceResult updateBook(Book book) {
        return null;
    }
}
