package com.example.arek.hateapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arek on 11.06.2017.
 */

public class RegisterRequest extends StringRequest {
    private final static String REGISTER_REQUEST_URL = "https://lamp.ii.us.edu.pl/~ii277258/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String login, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("login", login);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
