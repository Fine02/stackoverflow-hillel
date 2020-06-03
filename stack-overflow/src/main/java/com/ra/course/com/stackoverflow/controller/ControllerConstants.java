package com.ra.course.com.stackoverflow.controller;

@SuppressWarnings("PMD")
public class ControllerConstants {

    //name of attribute in model with catching exceptions
    public final static String TEXT_ATTR = "text";

    //used for Main Controller
    public final static String MAIN_URL = "/";
    public final static String MAIN_VIEW = "main";

    //used for Registration Controller
    public final static String REGISTER_URL = "/registration";
    public final static String REGISTER_VIEW = "authorization/registration";

    //used for LogIn Controller
    public final static String LOGIN_URL = "/login";
    public final static String LOGIN_VIEW = "authorization/login";

    //used for Search Controller
    public final static String SEARCH_URL = "/search";
    public final static String MEMBERS_VIEW = "search/view-members";

    //used for Member Controller
    public final static String MEMBER_ATTR = "memberDto"; //name of session attribute
    public final static String MEMBER_URL = "/member";
    public final static String UPDATE_URL = "/update";
    public final static String DELETE_URL = "/delete";
    public final static String LOGOUT_URL = "/logout";
    public final static String UPDATE_VIEW = "member/update";
    public final static String PROFILE_VIEW = "member/profile";

}
