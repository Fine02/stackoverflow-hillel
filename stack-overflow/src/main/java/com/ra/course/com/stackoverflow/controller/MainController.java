package com.ra.course.com.stackoverflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.MAIN_TEMPLATE;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainTemplate(){
        return MAIN_TEMPLATE;
    }
}
