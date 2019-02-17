package com.example.arek.hateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMessage extends AppCompatActivity {
    final String POST_REQ_SUCCESS_FLAG = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);

        final EditText msg = (EditText) findViewById(R.id.txtMessage);
        final Button send = (Button) findViewById(R.id.btnSend);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String post = msg.getText().toString();
                String user_id = Integer.toString(GlobalParams.getUser_id());

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean(POST_REQ_SUCCESS_FLAG); //publiczne pola statyczne

                            if (success) {
                                Intent intent = new Intent(AddMessage.this, PostActivity.class);
                                AddMessage.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddMessage.this);
                                builder.setMessage("Send Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                AddMessageRequest addMessageRequest = new AddMessageRequest(user_id, post, responseListener);
                RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
                queue.add(addMessageRequest);
            }
        });
    }
}