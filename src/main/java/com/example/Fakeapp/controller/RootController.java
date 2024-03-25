package com.example.Fakeapp.controller;

import com.example.Fakeapp.Service.EmailService;
import com.example.Fakeapp.dao.ReportManager.Report;
import com.example.Fakeapp.dao.ReportManager.ReportManagerDao;
import com.example.Fakeapp.dao.UserManager.User;
import com.example.Fakeapp.dao.UserManager.UserDao;
import com.example.Fakeapp.dao.bookManager.Book;
import com.example.Fakeapp.dao.bookManager.BookManagerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

@Controller
public class RootController {

    private static final String GMAIL_SHOULD_RETURN_BOOK_TEXT = "please return a borrowed book : ";
    private static final String GMAIL_SHOULD_RETURN_BOOK_SUBJECT = "unreturned book";
    @Autowired
    private UserDao userDao;
    @Autowired
    private BookManagerDao bookManagerDao;

    @Autowired
    private ReportManagerDao reportManagerDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ExecutorService taskExecutor;

    @GetMapping("/root/login")
    public ModelAndView longInRoot(){
        ModelAndView ret = new ModelAndView("root/login");
        return ret;
    }

    @PostMapping("/root/login")
    public ModelAndView login(@RequestParam("username") java.lang.String username,
                              @RequestParam("password") java.lang.String password,
                              HttpSession session) {
        System.out.println("catched byy root login");
        User user = userDao.findByUsername(username);

        if (user == null || user.getId() > 1000 || !user.compareUnhashed(password)) {
            ModelAndView res = new ModelAndView("/root/login");
            res.addObject("message", "Either password or username is incorrect");
            return res;
        }


        session.setAttribute("userId", user.getId());
        ModelAndView ret = new ModelAndView("redirect:/root/users");
        return ret;
    }

    @GetMapping("root/users")
    public ModelAndView login(HttpSession session) {
        ArrayList<User> users = userDao.getAll();


        ModelAndView ret = new ModelAndView("root/home");
        ret.addObject("users", users);
        return ret;
    }

    @GetMapping("/user/delete/{username}")
    public String deleteUserByName(@PathVariable("username") String username) {
        userDao.delete(username);

        return "redirect:/root/users"; // Redirect to a list of users after deletion
    }

    @PostMapping("/user/edit/{username}")
    public String editUserByName(@PathVariable("username") String username,
                                 @RequestParam("username") String newUsername,
                                 @RequestParam("gmail") String gmail) {


        User user = userDao.findByUsername(username);
        user.setName(newUsername);
        user.setGmail(gmail);
        userDao.edit(username, user);


        return "redirect:/root/users"; // Redirect to a list of users after editing
    }


    @GetMapping("/root/create/user")
    public ModelAndView createNewUser(){
        return new ModelAndView("root/createUser");
    }


    @PostMapping("/root/create/user")
    public ModelAndView createUser( @RequestParam("gmail") String gmail,
                                    @RequestParam("username") String username,
                                    @RequestParam("password") String password){

        ModelAndView res = new ModelAndView("root/createUser");

        if (userDao.existsByUsernameOrGmail(username, gmail)){
            if (userDao.exists(username)){
                res.addObject("message", "username is already used");
            }else{
                res.addObject("message", "email is already used");
            }
            return res;
        }

        User user = new User(username,password,gmail);
        user.secure();

        if (userDao.create(user)){
            res.addObject("message", "user created successfully");
        }else{
            res.addObject("message", "internal error can not create an username");
        }

        return res;
    }
    @GetMapping("/root/create/book")
    public ModelAndView createBook(){
        return new ModelAndView("root/createBook");
    }

    @PostMapping("/root/create/book")
    public ModelAndView createBook( @RequestParam("author") String author,
                                    @RequestParam("title") String title,
                                    @RequestParam("IBSN") String ibsn,
                                    @RequestParam("genre") String genre
                                    ){

        ModelAndView res = new ModelAndView("root/createBook");
        long IBSNInLong;
        try {
            IBSNInLong = Long.parseLong(ibsn);
        } catch (NumberFormatException e) {
            res.addObject("message", "IBSN should be a correct unique number");
            return res;
        }

        Book book = new Book(title, author, IBSNInLong, genre);
        if(!bookManagerDao.add(book)){
            res.addObject("message", "internal error can not create a book check if your IBSN is unique");
        }else{
            res.addObject("message", "book created succesfully");
        }

        return res;
    }
    @GetMapping("/root/overdue/books")
    public ModelAndView getOverdueBooks(){

        // must change this one
//        ArrayList<Report> reports = reportManagerDao.getAll();
        ArrayList<Book>items = bookManagerDao.getAll();
        ModelAndView ret = new ModelAndView("root/books");
        ret.addObject("items", items);
        return ret;
    }

    @GetMapping("/root/search/user")
    public ModelAndView searchUser( @RequestParam("query") String query){
        ArrayList<User> users = userDao.search(query);

        ModelAndView ret = new ModelAndView("root/home");
        ret.addObject("users", users);
        return ret;
    }

    @GetMapping("/root/reports")
    public ModelAndView getReports(){
        ArrayList<Report> reports = reportManagerDao.getAll();

        ModelAndView ret = new ModelAndView("root/reports");
        ret.addObject("reports", reports);
        return ret;
    }
    @GetMapping("/root/notify/{ownerId}/{isbn}")
    public ModelAndView notifyOwner(@PathVariable("ownerId") Long ownerId,  @PathVariable("isbn") Long isbn) {
        taskExecutor.execute(() -> {
            Book book = bookManagerDao.get(isbn);
            User user = userDao.findById(ownerId);
            emailService.sendMessage(user.getGmail(), GMAIL_SHOULD_RETURN_BOOK_SUBJECT, GMAIL_SHOULD_RETURN_BOOK_TEXT + book.getTitle());
        });

        return new ModelAndView("redirect:/root/overdue/books");
    }
}
