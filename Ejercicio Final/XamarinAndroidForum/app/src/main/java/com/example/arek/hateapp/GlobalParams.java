package com.example.arek.hateapp;

/**
 * Created by Arek on 06.07.2017.
 */

public class GlobalParams {

    static int user_id;
    static String login;


    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        GlobalParams.login = login;
    }

    public static int getUser_id() {
        return user_id;
    }

    public static void setUser_id(int user_id) {
        GlobalParams.user_id = user_id;
    }


}
