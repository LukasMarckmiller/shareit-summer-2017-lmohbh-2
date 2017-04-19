package edu.hm.shareit.resources;

import edu.hm.fachklassen.Book;

interface BookService
{
    BookServiceResult addBook(Book book);

    Book[] getBooks();

    Book getBook(String isbn);

    BookServiceResult updateBook(Book book);
}