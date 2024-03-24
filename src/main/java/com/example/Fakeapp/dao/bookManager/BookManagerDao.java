package com.example.Fakeapp.dao.bookManager;

import java.util.ArrayList;
import java.sql.Date;

public interface BookManagerDao {
    public boolean add(Book book);
    public boolean edit(Long isbn, Book book);
    public boolean remove(Long isbn);

    public Book get(Long isbn);
    public ArrayList<Book> getAll();

    public ArrayList<Book> findByTitle(String title);

    public ArrayList<Book> findByAuthor(String author);

    public ArrayList<Book> search(String query);

    public boolean borrowBook(Long isbn, Long ownerId, Date dueDate);

    public boolean returnBook(Long isbn);


    // primarily used for tests
    public boolean available(Long isbn);


}
