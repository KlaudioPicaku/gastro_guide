package com.uniroma3.it.gastroguide.constants;

public class StaticURLs {
    public static final String HOST_PROTOCOL = "";
    public static final String HOST_DOMAIN = "localhost";

    public static final String SPECIAL_ADDON="://";
    public static final int HOST_PORT = 8080;

    public static final String HOST_URL = HOST_PROTOCOL + (HOST_PROTOCOL.isEmpty() ? "" : SPECIAL_ADDON) + HOST_DOMAIN + (HOST_PORT == 80 ? "" : ":" + HOST_PORT);

    public static String LOCAL_HOST="http://localhost:8080";
    public static final String RECIPE_DETAIL_URL="/recipe/detail?id=";
    public static final String CHEF_DETAIL_URL="/chef/detail?name=";

}