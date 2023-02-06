package com.example.ratatouille23.DAO;

import android.util.Log;

public class DAOBaseUrl
{
    private static String baseUrl;

    public static String getBaseUrl(){
            return baseUrl;
    }
    public static void setBaseUrl(String indirizzoIP) {
        Log.i("IP BASEURL", indirizzoIP);
        baseUrl = "http://" + indirizzoIP + ":8080/";
    }

}
