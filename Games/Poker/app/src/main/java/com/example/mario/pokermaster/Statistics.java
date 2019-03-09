package com.example.mario.pokermaster;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Statistics extends AppCompatActivity {

    private TextView text_partidasGanadas,text_partidasPerdidas,text_rondasGanadas,text_rondasPerdidas;
    private TextView text_handValue1,text_handValue2,text_handValue3,text_handValue4,text_handValue5,text_handValue6,text_handValue7,text_handValue8,text_handValue9,text_handValue10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Button atras = (Button)findViewById(R.id.button100);

        text_partidasGanadas= findViewById(R.id.textView18);
        text_partidasPerdidas= findViewById(R.id.textView19);
        text_rondasGanadas= findViewById(R.id.textView20);
        text_rondasPerdidas= findViewById(R.id.textView21);

        text_handValue1= findViewById(R.id.textView23);
        text_handValue2= findViewById(R.id.textView24);
        text_handValue3= findViewById(R.id.textView25);
        text_handValue4= findViewById(R.id.textView26);
        text_handValue5= findViewById(R.id.textView27);
        text_handValue6= findViewById(R.id.textView28);
        text_handValue7= findViewById(R.id.textView29);
        text_handValue8= findViewById(R.id.textView30);
        text_handValue9= findViewById(R.id.textView31);
        text_handValue10= findViewById(R.id.textView32);

        SharedPreferences prefs =
                getSharedPreferences("Estadisticas",Game.MODE_PRIVATE);


        text_partidasGanadas.setText("Ganadas: "+prefs.getInt("partidasGanadas",0));
        text_partidasPerdidas.setText("Perdidas: "+prefs.getInt("partidasPerdidas",0));
        text_rondasGanadas.setText("Ganadas: "+prefs.getInt("rondasGanadas",0));
        text_rondasPerdidas.setText("Perdidas: "+prefs.getInt("rondasPerdidas",0));

        text_handValue1.setText("A High Card: "+prefs.getInt("a High Card",0));
        text_handValue2.setText("One Pair: "+prefs.getInt("One Pair",0));
        text_handValue3.setText("Two Pairs: "+prefs.getInt("Two Pairs",0));
        text_handValue4.setText("Three of a Kind: "+prefs.getInt("Three of a Kind",0));
        text_handValue5.setText("A Straight: "+prefs.getInt("a Straight",0));
        text_handValue6.setText("A Flush: "+prefs.getInt("a Flush",0));
        text_handValue7.setText("A Full House: "+prefs.getInt("a Full House",0));
        text_handValue8.setText("Four of a Kind: "+prefs.getInt("Four of a Kind",0));
        text_handValue9.setText("A Straight Flush: "+prefs.getInt("a Straight Flush",0));
        text_handValue10.setText("A Royal Flush: "+prefs.getInt("a Royal Flush",0));

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next = new Intent(Statistics.this, mainView.class);
                startActivity(next);
            }
        });

    }
}
