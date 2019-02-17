package com.example.arek.hateapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arek on 04.07.2017.
 */

public class AddMessageRequest extends StringRequest {
    private final static String POST_REQUEST_URL = "https://lamp.ii.us.edu.pl/~ii277258/SendPost.php";
    private Map<String, String> params;


    public AddMessageRequest(String user_id, String post, Response.Listener<String> listener) {
        super(Method.POST, POST_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("post", post);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
