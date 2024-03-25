package com.example.Fakeapp.controller;

import com.example.Fakeapp.Service.EmailService;
import com.example.Fakeapp.dao.ReportManager.Report;
import com.example.Fakeapp.dao.ReportManager.ReportManagerDao;
import com.example.Fakeapp.dao.ReportManager.Status;
import com.example.Fakeapp.dao.UserManager.User;
import com.example.Fakeapp.dao.UserManager.UserDao;
import com.example.Fakeapp.dao.bookManager.Book;
import com.example.Fakeapp.dao.bookManager.BookManagerDao;
import com.example.Fakeapp.func.CalcDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.example.Fakeapp.func.CalcDate.calcReturnDate;
import static com.example.Fakeapp.func.CalcDate.today;


@Controller
public class BookController {
    private static final String GMAIL_BORROW_SUBJECT = "Borrowing book email";
    private static final String GMAIL_BORROW_TEXT = "successfully borrowed a book";


    @Autowired
    private BookManagerDao bookManagerDao;
    @Autowired
    private ReportManagerDao reportManagerDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private EmailService emailService;


    // used this because sending emails takes a lot of time
    @Autowired
    private ExecutorService taskExecutor;



    @GetMapping("/books")
    public ModelAndView books(HttpSession session) {
        List<Book> books = bookManagerDao.getAll();
        ModelAndView ret = new ModelAndView("books");
        ret.addObject("books", books);
        ret.addObject("userId", session.getAttribute("userId"));
        return ret;
    }


    @PostMapping("/book/borrow/{isbn}")
    public String borrowBook(HttpSession session, @PathVariable("isbn") String isbn) {
        Long ISBN = Long.parseLong(isbn);


        //sent a book by gmail
        // might handle it differently later should check if gmail is sent successfully now just send it
        taskExecutor.execute(() -> {
            Book book = bookManagerDao.get(ISBN);
            User user = userDao.findById((Long) session.getAttribute("userId"));
            emailService.sendMessage(user.getGmail(), GMAIL_BORROW_SUBJECT, GMAIL_BORROW_TEXT + book.getTitle() );
        });


        //borrow book
        bookManagerDao.borrowBook(ISBN, (Long) session.getAttribute("userId"), calcReturnDate());


        // report that book is borrowed
        Date today = CalcDate.calcReturnDate();
        Report report = new Report(isbn, today, Status.BORROWED);
        reportManagerDao.report(report);


        return "redirect:/books";
    }


    @PostMapping("/book/return/{isbn}")
    public String returnBook(HttpSession session, @PathVariable("isbn") String isbn) {

        Long ISBN = Long.parseLong(isbn);

        long userId = (long) session.getAttribute("userId");



        // return book
        bookManagerDao.returnBook(ISBN);
        // report that book is returned
        Report report = new Report(isbn, today(), Status.RETURNED);
        reportManagerDao.report(report);

        return "redirect:/books";
    }

    @GetMapping("/book/{isbn}")
    public ModelAndView getBook(HttpSession session, @PathVariable("isbn") String isbn) {
        Long ISBN = Long.parseLong(isbn);
        Book book = bookManagerDao.get(ISBN);

        ModelAndView res = new ModelAndView("book");
        res.addObject("book", book);

        return res;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam String query) {
        List<Book> books = bookManagerDao.search(query);
        ModelAndView ret = new ModelAndView("books");
        ret.addObject("books", books);
        return ret;
    }
}
