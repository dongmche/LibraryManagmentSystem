package com.example.Fakeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
    @GetMapping("/test")
    public ModelAndView test(){
        ModelAndView res = new ModelAndView("test");
        return res;
    }
    @GetMapping("/count")
    @ResponseBody
    public ModelAndView getCount(Model model) {
        model.addAttribute("name", "gio");
        return new ModelAndView("test");
    }
}
