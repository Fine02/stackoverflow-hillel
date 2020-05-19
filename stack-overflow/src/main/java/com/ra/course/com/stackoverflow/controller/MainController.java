package com.ra.course.com.stackoverflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainTemplate(){
        return "main";
    }
}
