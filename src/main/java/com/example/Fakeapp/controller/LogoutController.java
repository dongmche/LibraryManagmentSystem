package com.example.Fakeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {
    @GetMapping(value = {"/logout"})
    public String logout(HttpSession session){
        session.setAttribute("userId", null);
        return "login";
    }


}
