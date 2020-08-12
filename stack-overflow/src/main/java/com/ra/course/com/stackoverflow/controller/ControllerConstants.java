package com.ra.course.com.stackoverflow.controller;

@SuppressWarnings("PMD")
public class ControllerConstants {

    //name of attributes
    public final static String TEXT_ATTR = "text"; //in model with catching exceptions
    public final static String MEMBER_ATTR = "member"; //name of session attribute
    public final static String TAGS_ATTR = "tags"; //name of session attribute
    public final static String QUESTIONS_ATTR = "questions"; //name of model attribute

    //used for Main Controller
    public final static String MAIN_URL = "/";
    public final static String MAIN_VIEW = "main";

    //used for Registration Controller
    public final static String REGISTER_URL = "/registration";
    public final static String REGISTER_VIEW = "authorization/registration";

    //used for LogIn Controller
    public final static String LOGIN_URL = "/login";
    public final static String LOGIN_VIEW = "authorization/login";

    //used in general
    public final static String DELETE_URL = "/delete";
    public final static String UPDATE_URL = "/update";
    public final static String CREATE_URL = "/create";

    //used for Search Controller
    public final static String SEARCH_URL = "/search";
    public final static String MEMBERS_VIEW = "search/list-members";
    public final static String MEMBER_VIEW = "search/member";
    public final static String QUESTIONS_VIEW = "search/list-questions";

    //used for Member Controller
    public final static String MEMBER_URL = "/members";
    public final static String LOGOUT_URL = "/logout";
    public final static String UPDATE_VIEW = "member/update";
    public final static String PROFILE_VIEW = "member/profile";

    //used for Question Controller
    public final static String QUESTION_URL = "/questions";
    public final static String QUESTION_CREATE_VIEW = "question/create";
    public final static String QUESTION_UPDATE_VIEW = "question/update";
    public final static String QUESTION_VIEW = "question/view";

}
