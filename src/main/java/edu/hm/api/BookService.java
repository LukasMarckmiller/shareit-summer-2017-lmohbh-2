package edu.hm.api;

import edu.hm.fachklassen.Book;
import edu.hm.fachklassen.Medium;

interface BookService
{
    BookServiceResult addBook(Book book);

    Book[] getBooks();

    Book getBook(String isbn);

    BookServiceResult updateBook(Book book);
}