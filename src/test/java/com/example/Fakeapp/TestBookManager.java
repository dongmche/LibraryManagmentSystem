package com.example.Fakeapp;

import com.example.Fakeapp.Conn.DatabaseConnection;
import com.example.Fakeapp.dao.UserManager.User;
import com.example.Fakeapp.dao.bookManager.Book;
import com.example.Fakeapp.dao.bookManager.BookManagerDao;
import com.example.Fakeapp.dao.bookManager.BookManagerSqlDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestBookManager {
    @BeforeEach
    public void createAndCleanTable() {
        // SQL command to drop the table if it exists
        String dropTableSql = "DROP TABLE IF EXISTS books";
        // SQL command to create a table
        String sql = "CREATE TABLE books (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "title VARCHAR(255) NOT NULL, " +
                "author VARCHAR(255) NOT NULL, " +
                "ISBN VARCHAR(20) NOT NULL UNIQUE, " +
                "genre VARCHAR(100) NOT NULL, " +
                "dueDate DATE, " + // Assuming due_date is a date. No NOT NULL constraint means it can be NULL.
                "owner BIGINT" + // No NOT NULL constraint means it can be NULL.
                ")";

        try {
            // Establishing a connection to the database
            Connection conn = DatabaseConnection.getConnection();
            // Creating a statement
            Statement stmt = conn.createStatement();

            // Executing the command to drop the table if it exists
            stmt.execute(dropTableSql);

            // Executing the SQL command
            stmt.execute(sql);

            // Closing the statement and connection
            stmt.close();
            conn.close();

        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
    private BookManagerDao bookManager = new BookManagerSqlDao();
    @Test
    public void testCreateBook(){
        Book book = new Book("foo","gela", 32345442L, "mkacri");
        assert true == bookManager.add(book);
        assert false == bookManager.add(book);
    }
    @Test
    public void testCanNotCreateBookWithSameIbsn(){
        BookManagerDao bookManager = new BookManagerSqlDao();
        Book book = new Book("foo","gela", 32345443L, "mkacri");
        assert true == bookManager.add(book);
        book = new Book("sxvaavtori","sxva", 32345443L, "sxvakanri");
        assert false == bookManager.add(book);
    }
    @Test
    public void testRemoveBook(){
        BookManagerDao bookManager = new BookManagerSqlDao();
        Book book = new Book("foo","gela", 12345432L, "mkacri");
        bookManager.add(book);
        assert true == bookManager.remove(book.getISBN());
        assert false == bookManager.remove(book.getISBN());
        assert null == bookManager.get(book.getISBN());
    }
    @Test
    public void testGetBook(){
        BookManagerDao bookManager = new BookManagerSqlDao();
        Book book = new Book("giorgi","gela", 22345442L, "lamazi");
        bookManager.add(book);
        Book res =  bookManager.get(book.getISBN());
        assert res != null;
        assert res.equals(book);

    }
    @Test
    public void testEdit(){
        BookManagerDao bookManager = new BookManagerSqlDao();
        Book book = new Book("giorgi","gela", 12345442L, "lamazi");
        assert true == bookManager.add(book);
        book.setGenre("horror");
        book.setAuthor("giorgi kachkashvili");
        assert true == bookManager.edit(book.getISBN(), book);
        Book res = bookManager.get(book.getISBN());

        assert res.equals(book);


    }
    @Test
    public void testGetAll(){
        BookManagerDao bookManager = new BookManagerSqlDao();
        bookManager.add(new Book("giorgi","mcxeta", 123443442L, "dsa"));
        bookManager.add(new Book("foo","mcxa", 12344343242L, "dsa"));
        bookManager.add(new Book("vaja","mcsata", 12344344223L, "dsa"));

        ArrayList<Book> books = bookManager.getAll();
        assertNotNull(books);
        assert 3 == books.size();
    }
    @Test
    public void testFindByTitle() {
        BookManagerDao bookManager = new BookManagerSqlDao();
        String title = "Hamlet";
        Book bookOne = new Book(title, "Stephen King", 123443442L, "dsa");
        bookManager.add(bookOne);
        Book bookTwo = new Book(title, "William Shakespeare", 12344343242L, "dsa");
        bookManager.add(bookTwo);

        Book bookThird = new Book(title, "Nicholas Sparks", 12344344223L, "dsa");
        bookManager.add(bookThird);


        ArrayList<Book> books = bookManager.findByTitle(title);
        assert books.size() == 3;
        for (int i = 0; i < books.size(); i++) {
            assert books.get(i).getTitle().equals(title);
        }
        books = bookManager.findByTitle("nonsense");
        assert books.size() == 0;
    }
        @Test
        public void testFindByAuthor(){
            String author = "Nicholas Sparks";

            Book bookOne = new Book("foo", author, 123443442L, "dsa");
            bookManager.add(bookOne);
            Book bookTwo = new Book("foo", author, 12344343242L, "dsa");
            bookManager.add(bookTwo);

            Book bookThird = new Book("foo", author, 12344344223L, "dsa");
            bookManager.add(bookThird);


            ArrayList<Book> books = bookManager.findByAuthor(author);
            assert books.size() == 3;
            for (int i =0; i< books.size(); i++){
                assert books.get(i).getAuthor().equals(author);
            }
            books = bookManager.findByAuthor("nonsense");
            assert books.size() == 0;

            books = bookManager.search("f");
            assert books.size() == 3;
            for (int i =0; i< books.size(); i++){
                assert books.get(i).getAuthor().equals(author);
            }
        }
    @Test
    public void testAvailableAndBorrowBook(){
        String author = "Nicholas Sparks";
        BookManagerDao bookManager = new BookManagerSqlDao();

        Book bookOne = new Book("foo", author, 123443442L, "dsa");
        assert  true == bookManager.add(bookOne);
        Book bookTwo = new Book("foo", author, 12344343242L, "dsa");
        assert  true ==bookManager.add(bookTwo);
        Book bookThird = new Book("foo", author, 12344344223L, "dsa");
        assert  true ==bookManager.add(bookThird);
        Book bookFourth = new Book("foo", author, 12344344224L, "dsas");
        assert  true == bookManager.add(bookFourth);



        User user = new User("gio","gio", "gio");
        Long fakeId = 1L;
        java.util.Date utilDate = new java.util.Date(); // This captures the current date and time
        java.sql.Date dueDate = new java.sql.Date(utilDate.getTime());



        assert false == bookManager.available(bookOne.getISBN());
        assert false == bookManager.available(bookTwo.getISBN());
        assert false == bookManager.available(bookThird.getISBN());
        assert false == bookManager.available(bookFourth.getISBN());


        assert true == bookManager.borrowBook(bookOne.getISBN(),fakeId,dueDate);
        assert true == bookManager.borrowBook(bookTwo.getISBN(),fakeId,dueDate);
        assert true == bookManager.borrowBook(bookThird.getISBN(),fakeId,dueDate);
        assert true == bookManager.borrowBook(bookFourth.getISBN(),fakeId,dueDate);


        assert true == bookManager.available(bookOne.getISBN());
        assert true == bookManager.available(bookTwo.getISBN());
        assert true == bookManager.available(bookThird.getISBN());
        assert true == bookManager.available(bookFourth.getISBN());

        assert true == bookManager.returnBook(bookOne.getISBN());
        assert true == bookManager.returnBook(bookTwo.getISBN());
        assert true == bookManager.returnBook(bookThird.getISBN());
        assert true == bookManager.returnBook(bookFourth.getISBN());

        assert false == bookManager.available(bookOne.getISBN());
        assert false == bookManager.available(bookTwo.getISBN());
        assert false == bookManager.available(bookThird.getISBN());
        assert false == bookManager.available(bookFourth.getISBN());

    }

}
