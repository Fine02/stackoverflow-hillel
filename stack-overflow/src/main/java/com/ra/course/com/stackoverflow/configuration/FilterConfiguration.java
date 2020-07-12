package com.ra.course.com.stackoverflow.configuration;

import com.ra.course.com.stackoverflow.controller.filter.AuthorizationFilter;
import com.ra.course.com.stackoverflow.controller.filter.MemberFilter;
import com.ra.course.com.stackoverflow.controller.filter.ViewedQuestionsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;

@Configuration
@ServletComponentScan(basePackages = "com.ra.course.com.stackoverflow.controller.filter")
public class FilterConfiguration {
//
//    @Bean
//    public FilterRegistrationBean<AuthorizationFilter> authorizationFilterRegistrationBean() {
//        final var registrationBean = new FilterRegistrationBean<AuthorizationFilter>();
//        registrationBean.setFilter(new AuthorizationFilter());
//        registrationBean.addUrlPatterns(MEMBER_URL + "/*");
//        return registrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean<MemberFilter> memberFilterRegistrationBean() {
//        final var registrationBean = new FilterRegistrationBean<MemberFilter>();
//        registrationBean.setFilter(new MemberFilter());
//        registrationBean.addUrlPatterns(LOGIN_URL, REGISTER_URL);
//        return registrationBean;
//    }

    //    @Bean
//    public FilterRegistrationBean <QuestionFilter> questionFilterRegistrationBean() {
//        final var registrationBean = new FilterRegistrationBean<QuestionFilter>();
//        registrationBean.setFilter(new QuestionFilter());
//        registrationBean.addUrlPatterns(QUESTION_URL + "/*");
//        return registrationBean;
//    }
//    @Bean
//    public FilterRegistrationBean<ViewedQuestionsFilter> searchFilterRegistrationBean() {
//        final var registrationBean = new FilterRegistrationBean<ViewedQuestionsFilter>();
//        registrationBean.setFilter(new ViewedQuestionsFilter());
//        registrationBean.addUrlPatterns("/search/questions/*");
//        return registrationBean;
//    }
}
