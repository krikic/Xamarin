package com.example.arek.hateapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        final Button addMessage = (Button) findViewById(R.id.sendMessage);
        final Button showMessagges = (Button) findViewById(R.id.allMessages);
        final TextView helloText =(TextView) findViewById(R.id.txtHello);


        String name = GlobalParams.getLogin();

        String message = name + " welcome to ForumPostApp \n tell us something!";
        helloText.setText(message);



        addMessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(PostActivity.this , AddMessage.class);
                PostActivity.this.startActivity(registerIntent);
            }
        });
        showMessagges.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(PostActivity.this , ShowMessages.class);
                PostActivity.this.startActivity(registerIntent);
            }
        });
    }

}
