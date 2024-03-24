package com.example.Fakeapp.controller;

import com.example.Fakeapp.dao.UserManager.User;
import com.example.Fakeapp.dao.UserManager.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/register")
    public String register(){

        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerPost(@RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     @RequestParam("email") String gmail){

        if (userDao.existsByUsernameOrGmail(username, gmail)){
            ModelAndView res = new ModelAndView("register");
            if (userDao.exists(username)){
                res.addObject("message", "username is already used");
            }else{
                res.addObject("message", "email is already used");
            }
            return res;
        }

        User user = new User(username,password,gmail);
        user.secure();
        userDao.create(user);


        return new ModelAndView("login");
    }


}
