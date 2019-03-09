package com.example.mario.pokermaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Difficulty extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        Button media = (Button) findViewById(R.id.button2);
        Button simple = (Button) findViewById(R.id.button);
        Button advanced = (Button) findViewById(R.id.button3);
        Button aars = (Button) findViewById(R.id.button4);


        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next = new Intent(Difficulty.this, Game.class);
                next.putExtra("Dificultad", 5);
                startActivity(next);
            }
        });
        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next = new Intent(Difficulty.this, Game.class);
                next.putExtra("Dificultad", 10);

                startActivity(next);
            }
        });
        advanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next = new Intent(Difficulty.this, Game.class);
                next.putExtra("Dificultad", 0);
                startActivity(next);
            }
        });
        aars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next = new Intent(Difficulty.this, mainView.class);
                startActivity(next);
            }
        });


    }
}
