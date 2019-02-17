package com.example.arek.hateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowMessages extends AppCompatActivity {

    ArrayList<MPost> dataModels;
    ListView listView;
    private ListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_messages);
        listView = (ListView) findViewById(R.id.myListView);
        dataModels = new ArrayList<>();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showListView(response);
            }
        };

        ShowMessagesRequest showMessagesRequest = new ShowMessagesRequest(responseListener);
        RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
        queue.add(showMessagesRequest);


    }

    private void showListView(String response) {
        try {
            JSONArray jsonResponse = new JSONArray(response);
            JSONArray data = jsonResponse.getJSONArray(0);

            for (int i = 0; i < 20; i++) {
                JSONObject obj = data.getJSONObject(i);
                String login = obj.getString("login");
                String post = obj.getString("post");
                dataModels.add(new MPost(login, post));
            }
            adapter = new ListViewAdapter(dataModels, getApplicationContext());
            listView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
