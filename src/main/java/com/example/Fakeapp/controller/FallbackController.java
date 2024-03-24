package com.example.Fakeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static com.example.Fakeapp.Statics.Statics.UNPRIVILEGED_USERS_STARTING_ID;

@Controller
public class FallbackController {

    @RequestMapping("/**")
    public String fallback( HttpSession session) {
        if (session.getAttribute("userId") != null){
            if (UNPRIVILEGED_USERS_STARTING_ID > (long) session.getAttribute("userId")){
                return "redirect:/root/users";
            }else {
                return "redirect:/books";
            }
        }
        return "redirect:/login";
    }
}