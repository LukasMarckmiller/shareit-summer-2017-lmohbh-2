package edu.hm.api;

import edu.hm.fachklassen.Book;
import edu.hm.fachklassen.Medium;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.Optional;

/**
 * Created by Lukas on 12.04.2017.
 */
public class BookServiceImpl implements BookService{
    static final HashSet<Book> booksSet = new HashSet<>();
    public BookServiceImpl() {
    }

    @Override
    public BookServiceResult addBook(Book book) {
        booksSet.add(book);
    }

    @Override
    public Book[] getBooks() {
        return booksSet.toArray(new Book[]{});
    }

    @Override
    public Book getBook(String isbn) {
        Optional<Book> possibleBook = booksSet.stream().filter(b -> b.getIsbn() == isbn).findFirst();
        if (possibleBook.isPresent())
            return possibleBook.get();
        else
            return null;
    }

    @Override
    public BookServiceResult updateBook(Book book)
    {
        booksSet.remove(getBook(book.getIsbn()));
        booksSet.add(book);
        return BookServiceResult.
    }
}
