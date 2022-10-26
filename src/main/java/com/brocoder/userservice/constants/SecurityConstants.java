package com.brocoder.userservice.constants;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 60*60*60*24*5; // 5 days expressed in milliseconds
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED="Token cannot be verified";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHODS = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/user/login","/user/signup","/user/resetPassword","/user/image/**"};
}