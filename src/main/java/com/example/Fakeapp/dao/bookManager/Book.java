package com.example.Fakeapp.dao.bookManager;

import java.sql.Date;
import java.util.Objects;


public class Book {
    private  String title;
    private String author;
    private Long ISBN;
    private String genre;

    private Date dueDate;

    private Long owner;

    public Book(String title, String author, Long isbn, String genre) {
        this.title = title;
        this.author = author;
        this.ISBN = isbn;
        this.genre = genre;
    }
    public Book(){

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getISBN() {
        return ISBN;
    }

    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {

        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString(){
        return "\ntitle " + title + "\nauthor " + author + "\ngenre " +  genre + "\nisbn " + ISBN;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(ISBN, book.ISBN) &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(genre, book.genre);
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date due_date) {
        this.dueDate = due_date;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }
}
