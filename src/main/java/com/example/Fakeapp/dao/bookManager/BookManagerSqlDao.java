package com.example.Fakeapp.dao.bookManager;

import com.example.Fakeapp.Conn.DatabaseConnection;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

@Component
public class BookManagerSqlDao implements BookManagerDao {

    @Override
    public boolean add(Book book) {
        String sql = "INSERT INTO books (title, author, ISBN, genre) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                return false;
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setLong(3, book.getISBN());
            pstmt.setString(4, book.getGenre());
            try {
                pstmt.executeUpdate();
            } catch (Exception e){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean edit(Long isbn, Book book) {
        return remove(book.getISBN()) && add(book);
    }

    @Override
    public boolean remove(Long isbn) {
        String sql = "DELETE FROM books WHERE ISBN = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                return false;
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, isbn);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Book get(Long isbn) {
        String sql = "SELECT * FROM books WHERE ISBN = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            Book book = new Book();
            System.out.println("error here");
            if (rs.next()) {

                System.out.println("error here 2");
                int id = rs.getInt("id");
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setISBN(rs.getLong("ISBN"));
                book.setGenre(rs.getString("genre"));
                
                Date dueDate = rs.getDate("dueDate");
                if (dueDate != null) {
                    book.setDueDate(rs.getDate("dueDate"));
                }
                Long owner = rs.getLong("owner");
                if (owner != null) {
                    book.setOwner(rs.getLong("owner"));
                }

                return book;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Book> getAll() {
        ArrayList<Book> books = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM books";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                Long ISBN = rs.getLong("ISBN");
                String genre = rs.getString("genre");


                Book book = new Book(title, author, ISBN, genre);

                Date dueDate = rs.getDate("dueDate");
                if (dueDate != null) {
                    book.setDueDate(rs.getDate("dueDate"));
                }
                Long owner = rs.getLong("owner");
                if (owner != null) {
                    book.setOwner(owner);
                }

                books.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return books;
    }

    @Override
    public ArrayList<Book> findByTitle(String searchTitle) {
        ArrayList<Book> books = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();
            // Use a placeholder (?) for the title in the query
            String sql = "SELECT * FROM books WHERE title = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Set the title in the PreparedStatement
            pstmt.setString(1, searchTitle);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                Long ISBN = rs.getLong("ISBN"); // Adjust this line if your ISBN is stored as a String
                String genre = rs.getString("genre");

                Book book = new Book(title, author, ISBN, genre);

                Date dueDate = rs.getDate("dueDate");
                if (dueDate != null) {
                    book.setDueDate(dueDate);
                }
                book.setOwner(rs.getLong("owner"));

                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return books;
    }

    @Override
    public ArrayList<Book> findByAuthor(String auth) {
        ArrayList<Book> books = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();
            // Use a placeholder (?) for the title the query
            String sql = "SELECT * FROM books WHERE author = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Set the title in the PreparedStatement
            pstmt.setString(1, auth);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                Long ISBN = rs.getLong("ISBN");
                String genre = rs.getString("genre");

                Book book = new Book(title, author, ISBN, genre);

                Date dueDate = rs.getDate("dueDate");
                if (dueDate != null) {
                    book.setDueDate(rs.getDate("dueDate"));
                }
                book.setOwner(rs.getLong("owner"));

                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return books;
    }

    @Override
    public ArrayList<Book> search(String query) {
        ArrayList<Book> books = new ArrayList<>(); // Array to hold Book objects

        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM books WHERE author LIKE ? OR title LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Set the parameters to include the '%' wildcard for partial matches
            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    Long ISBN = rs.getLong("ISBN");
                    String genre = rs.getString("genre");


                    Book book = new Book(title, author, ISBN, genre);

                    Date dueDate = rs.getDate("dueDate");
                    if (dueDate != null) {
                        book.setDueDate(rs.getDate("dueDate"));
                    }
                    book.setOwner(rs.getLong("owner"));

                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
            return books;
        }

    @Override
    public boolean borrowBook(Long isbn, Long ownerId, Date dueDate) {
        String sql = "UPDATE books SET owner = ?, dueDate = ? WHERE ISBN = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                return false;
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, ownerId);
            pstmt.setDate(2, dueDate);
            pstmt.setLong(3, isbn);
            try {
                pstmt.executeUpdate();
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean returnBook(Long isbn) {
        String sql = "UPDATE books SET dueDate = NULL, owner = NULL WHERE ISBN = ?;";

        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                return false;
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, isbn);
            try {
                pstmt.executeUpdate();
            } catch (Exception e){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean available(Long isbn) {
        String sql = "SELECT * FROM books WHERE ISBN = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, isbn);
            ResultSet rs = pstmt.executeQuery();


            if (rs.next()) {
                rs.getString("title");
                rs.getString("author");
                rs.getLong("ISBN");
                rs.getString("genre");

                Date dueDate = rs.getDate("dueDate");
                Long owner = rs.getLong("owner");
                if (dueDate == null){
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Book> getOverdueBooks(Date date) {
        ArrayList<Book> books = new ArrayList<>(); // Array to hold Book objects

        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "SELECT * FROM books WHERE dueDate < ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            // Set the parameters to include the '%' wildcard for partial matches
            pstmt.setDate(1, date);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    Long ISBN = rs.getLong("ISBN");
                    String genre = rs.getString("genre");


                    Book book = new Book(title, author, ISBN, genre);

                    Date dueDate = rs.getDate("dueDate");
                    if (dueDate != null) {
                        book.setDueDate(rs.getDate("dueDate"));
                    }
                    book.setOwner(rs.getLong("owner"));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return books;
    }
}
