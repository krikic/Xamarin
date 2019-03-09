package com.examples.evill.sietemedia;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examples.evill.sietemedia.game.Carta;
import com.examples.evill.sietemedia.game.IViewJuego;
import com.examples.evill.sietemedia.game.Juego;
import com.examples.evill.sietemedia.game.JuegoPresenter;

public class GameActivity extends AppCompatActivity implements IViewJuego{


    private Juego juego;
    private JuegoPresenter jpresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Creacion del juego nuevo
        juego = new Juego();
        //Creacion presenter
        jpresenter = new JuegoPresenter(this, juego);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Drawable getDrawable(String name) {
        return getResources().getDrawable(this.getResources().getIdentifier(name, "drawable", this.getPackageName()));
    }

    @Override
    public void actualizaPuntosJugador(double p) {
        TextView tv = (TextView) findViewById(R.id.tvPuntJug);
        tv.setText(String.valueOf(p));
    }

    @Override
    public void actualizaPuntosBanca(double p) {
        TextView tv = (TextView) findViewById(R.id.tvPuntBan);
        tv.setText(String.valueOf(p));
    }

    @Override
    public void mostrarCartaJugador(Carta c) {
        LinearLayout la = (LinearLayout)findViewById(R.id.tableroJugador);
        ImageView iv = new ImageView(this);
        iv.setImageDrawable(getDrawable(c.getRepresentacion()));
        iv.setAdjustViewBounds(true);
        la.addView(iv);
    }

    @Override
    public void mostrarCartaBanca(Carta c) {
        LinearLayout la = (LinearLayout)findViewById(R.id.tableroBanca);
        ImageView iv = new ImageView(this);
        iv.setImageDrawable(getDrawable(c.getRepresentacion()));
        iv.setAdjustViewBounds(true);
        la.addView(iv);
    }

    @Override
    public void jugadorPideCarta(View v) {
        jpresenter.jugadorObtieneCarta();
    }

    @Override
    public void jugadorSePlanta(View v) {
        jpresenter.jugadorSePlanta();
    }

    @Override
    public void bloqueaCarta() {
         findViewById(R.id.btnCarta).setEnabled(false);
    }

    @Override
    public void bloqueaPlantarse() {
        findViewById(R.id.btnPlanta).setEnabled(false);
    }

    @Override
    public void ganaJugador() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) findViewById(R.id.tvVictoria);
                tv.setText(getResources().getString(R.string.txtGanaJugador));
                tv.setVisibility(View.VISIBLE);

                Button btn = (Button) findViewById(R.id.btnReset);
                btn.setEnabled(true);
                btn.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void ganaBanca() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) findViewById(R.id.tvVictoria);
                tv.setText(getResources().getString(R.string.txtPierdeJugador));
                tv.setVisibility(View.VISIBLE);

                Button btn = (Button) findViewById(R.id.btnReset);
                btn.setEnabled(true);
                btn.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void empate() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) findViewById(R.id.tvVictoria);
                tv.setText(getResources().getString(R.string.txtNadieGana));
                tv.setVisibility(View.VISIBLE);

                Button btn = (Button) findViewById(R.id.btnReset);
                btn.setEnabled(true);
                btn.setVisibility(View.VISIBLE);
            }
        });
    }


    public void reiniciarUI(View v) {
        juego.inicializar();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // activo los botones
                findViewById(R.id.btnPlanta).setEnabled(true);
                findViewById(R.id.btnCarta).setEnabled(true);
                // Elimino los paneles
                LinearLayout la = (LinearLayout)findViewById(R.id.tableroBanca);
                la.removeAllViews();
                la = (LinearLayout)findViewById(R.id.tableroJugador);
                la.removeAllViews();
                // Elimino el mensaje de victoria
                TextView tv = (TextView)findViewById(R.id.tvVictoria);
                tv.setText("");
                tv.setVisibility(View.INVISIBLE);
                // Reinicio puntuaciones
                tv = (TextView) findViewById(R.id.tvPuntBan);
                tv.setText("0");
                tv = (TextView) findViewById(R.id.tvPuntJug);
                tv.setText("0");
                // Desactivo boton reset
                Button btn = (Button)findViewById(R.id.btnReset);
                btn.setEnabled(false);
                btn.setVisibility(View.INVISIBLE);
            }
        });

    }


}
