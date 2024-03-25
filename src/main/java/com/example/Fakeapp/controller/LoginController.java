package com.example.Fakeapp.controller;


import com.example.Fakeapp.dao.UserManager.User;
import com.example.Fakeapp.dao.UserManager.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserDao userDao;

    @GetMapping(value = {"/", "/login"})
    public ModelAndView sayHello(){
        return new ModelAndView("login");
    }


    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpSession session) {

        System.out.println("catche here login");
        User user = userDao.findByUsername(username);

        if (user == null || !user.compareUnhashed(password)) {
            ModelAndView res = new ModelAndView("login");
            res.addObject("message", "Either password or username is incorrect");
            return res;
        }
        session.setAttribute("userId", user.getId());
        return new ModelAndView("redirect:/books");
    }

}
