package edu.hm.api;

import edu.hm.fachklassen.Book;
import edu.hm.fachklassen.Medium;

interface BookService
{
    BookServiceResult addBook(Book book);

    Medium[] getBooks();

    BookServiceResult updateBook(Book book);
}