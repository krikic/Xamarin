package com.example.arek.hateapp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


/**
 * Created by Arek on 14.07.2017.
 */

public class ShowMessagesRequest extends StringRequest {
    private final static String LOGIN_REQUEST_URL = "https://lamp.ii.us.edu.pl/~ii277258/showPosts.php";


    public ShowMessagesRequest(Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);

    }


}
