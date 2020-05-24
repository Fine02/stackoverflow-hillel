package com.ra.course.com.stackoverflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.MAIN_URL;
import static com.ra.course.com.stackoverflow.controller.ControllerConstants.MAIN_VIEW;

@Controller
@RequestMapping(MAIN_URL)
public class MainController {

    @GetMapping
    public String mainTemplate(){
        return MAIN_VIEW;
    }
}
