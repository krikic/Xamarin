package com.example.arek.hateapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText logIn, passWord;
    private String login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        logIn = (EditText) findViewById(R.id.txtLogin);
        passWord = (EditText) findViewById(R.id.txtPassword);
        final Button register = (Button) findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initialize();
                if (!validationProcess()) {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            processResponse(response);
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(login, password, responseListener);
                    RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
                    queue.add(registerRequest);
                }

            }
        });
    }

    private boolean validationProcess() {
        boolean validate = true;
        if (login.isEmpty() || login.length() > 32) {
            logIn.setError("Please enter correct login");
            validate = false;
        }
        if (password.isEmpty() || password.length() > 32) {
            passWord.setError("Please enter correct password");
            validate = false;
        }
        return validate;
    }

    private void processResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("success");

            if (success) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("Register Failed")
                        .setNegativeButton("Retry", null)
                        .create()
                        .show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        login = logIn.getText().toString();
        password = passWord.getText().toString();
    }

}
