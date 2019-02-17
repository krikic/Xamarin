package com.example.arek.hateapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText logInLog = (EditText) findViewById(R.id.logtxtLogin);
        final EditText passWordLog = (EditText) findViewById(R.id.logPassword);
        final Button signIn = (Button) findViewById(R.id.logbtnSignIn);
        final Button signUp = (Button) findViewById(R.id.logbtnSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String login = logInLog.getText().toString();
                final String password = passWordLog.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            processResponse(response, login);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(login, password, responseListener);
                RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
                queue.add(loginRequest);
            }
        });
    }

    private void processResponse(String response, String login) throws JSONException {
        JSONObject jsonResponse = new JSONObject(response);
        boolean success = jsonResponse.getBoolean("success");

        if (success) {
            int user_id = jsonResponse.getInt("user_id");
            setParameters(user_id, login);

            Intent intent = new Intent(LoginActivity.this, PostActivity.class);
            LoginActivity.this.startActivity(intent);


        } else {
            showAlert();
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Login Failed")
                .setNegativeButton("Retry", null)
                .create()
                .show();
    }

    private void setParameters(int user_id, String login) {
        GlobalParams.setUser_id(user_id);
        GlobalParams.setLogin(login);
    }
}


