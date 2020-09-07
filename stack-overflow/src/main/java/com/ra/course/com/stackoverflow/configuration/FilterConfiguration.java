package com.ra.course.com.stackoverflow.configuration;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ServletComponentScan(basePackages = "com.ra.course.com.stackoverflow.controller.filter")
public class FilterConfiguration {

}
