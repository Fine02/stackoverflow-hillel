package com.ra.course.com.stackoverflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public ModelAndView mainTemplate(){
        return new ModelAndView("main");
    }
}
