package com.example.ratatouille23.DAO;

public class DAOBaseUrl
{
    private static String baseUrl;

    public static String getBaseUrl(){
            return baseUrl;
    }
    public static void setBaseUrl(String indirizzoIP) {
        baseUrl = "http://" + indirizzoIP + ":8080/";
    }

}
