package com.example.mario.pokermaster;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mario.pokermaster.ai.AiPlayer;
import com.example.mario.pokermaster.ai.StateAction;
import com.example.mario.pokermaster.simulation.SimulationTable;
import com.example.mario.pokermaster.util.Player;
import com.example.mario.pokermaster.util.TableType;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class mainView extends AppCompatActivity {

    /**
     * Table type (betting structure).
     */
    private static final TableType TABLE_TYPE = TableType.NO_LIMIT;

    /**
     * The size of the big blind.
     */
    private static final int BIG_BLIND = 10;

    /**
     * The starting cash per player.
     */
    private static final int STARTING_CASH = 3000;

    /**
     * The table.
     */
    private SimulationTable table;

    /**
     * The players at the table.
     */
    private Map<String, Player> players;

    /**
     * The AI players.
     */
    private AiPlayer ai1, ai2, ai3;

    /**
     * The current dealer's name.
     */
    private String dealerName;

    /**
     * The current actor's name.
     */
    private String actorName;

    private Observable<String> myObservable;
    private Observer<String> myObserver;

    private int NUM_SIMULATION_GAMES = 50;

    private Dialog d;
    private TextView tv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        Button siguiente = (Button) findViewById(R.id.btn1);
        Button salir = (Button) findViewById(R.id.btn2);
        Button estadistics = (Button) findViewById(R.id.btn6);
        Button simulation = (Button) findViewById(R.id.but_simulation);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next = new Intent(mainView.this, Difficulty.class);
                startActivity(next);
            }
        });

        estadistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next2 = new Intent(mainView.this, Statistics.class);
                startActivity(next2);
            }
        });


        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        simulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mainView.this);
                                alertDialog.setTitle("Numbe Games");
                                alertDialog.setMessage("Introduce el numero de juegos para simular (50 por defecto):");

                                final EditText input = new EditText(mainView.this);
                                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                                input.setHint("50");
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                alertDialog.setView(input);

                                alertDialog.setPositiveButton("YES",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(!input.getText().toString().equals("")) {
                                                    NUM_SIMULATION_GAMES = Integer.parseInt(input.getText().toString());
                                                }
                                                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                d = new Dialog(mainView.this);
                                                d.setTitle("Loading");
                                                d.setContentView(R.layout.dialog_loading);
                                                tv = d.findViewById(R.id.text_simul);
                                                progressBar = d.findViewById(R.id.progressBar);
                                                progressBar.setMax(NUM_SIMULATION_GAMES);
                                                tv.setText("0/" + NUM_SIMULATION_GAMES + " Partidas Simuladas");
                                                d.show();
                                                new Thread(new Runnable() {
                                                    public void run() {
                                                        doSimul_backup();
                                                    }
                                                }).start();
                                            }
                                        });

                                alertDialog.setNegativeButton("NO",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                    }
                                                });
                                            }
                                        });

                                alertDialog.show();
                            }
                        });
                    }

                });
                t.start();

                /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                d = new Dialog(mainView.this);
                d.setTitle("Loading");
                d.setContentView(R.layout.dialog_loading);
                tv = d.findViewById(R.id.text_simul);
                progressBar = d.findViewById(R.id.progressBar);
                progressBar.setMax(NUM_SIMULATION_GAMES);
                tv.setText("0/" + NUM_SIMULATION_GAMES + " Partidas Simuladas");
                d.show();
                new Thread(new Runnable() {
                    public void run() {
                        doSimul_backup();
                    }
                }).start();*/
            }
        });

    }

    private void doSimul_backup() {
        int num_partidas = 0;
        while (num_partidas < NUM_SIMULATION_GAMES) {
            players = new LinkedHashMap<String, Player>();
            ai1 = new AiPlayer("aiPlayer1", STARTING_CASH, 5, 60);
            ai2 = new AiPlayer("aiPlayer2", STARTING_CASH, 10, 40);
            ai3 = new AiPlayer("aiPlayer3", STARTING_CASH, 15, 20);
            ai1.setRandom_prob(0);
            ai2.setRandom_prob(0);
            ai3.setRandom_prob(0);
            loadQVectors();
            players.put("aiPlayer1", ai1);
            players.put("aiPlayer2", ai2);
            players.put("aiPlayer3", ai3);

            table = new SimulationTable(TABLE_TYPE, BIG_BLIND);
            for (Player player : players.values()) {
                table.addPlayer(player);
            }

            // Start the game.
            table.run();

            saveQVectors();
            num_partidas++;
            final int finalNum_partidas = num_partidas;
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(finalNum_partidas);
                }
            });
            tv.post(new Runnable() {
                @Override
                public void run() {
                    tv.setText(finalNum_partidas + "/" + NUM_SIMULATION_GAMES + " Partidas Simuladas");
                }
            });
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                d.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
    }

    private void saveQVectors() {
        //ai1
        SharedPreferences prefs_ai1 =
                getSharedPreferences("QVector_ai1", Game.MODE_PRIVATE);

        SharedPreferences.Editor editor_ai1 = prefs_ai1.edit();
        for (StateAction sa : ai1.getvEstadosAcciones()) {
            editor_ai1.putString("Flop#" + sa.getEstado(), sa.toSave());
        }
        for (StateAction sa : ai1.getvEstadosAcciones_turn()) {
            editor_ai1.putString("Turn#" + sa.getEstado(), sa.toSave());
        }
        for (StateAction sa : ai1.getvEstadosAcciones_river()) {
            editor_ai1.putString("River#" + sa.getEstado(), sa.toSave());
        }
        editor_ai1.commit();

        //ai2
        SharedPreferences prefs_ai2 =
                getSharedPreferences("QVector_ai2", Game.MODE_PRIVATE);

        SharedPreferences.Editor editor_ai2 = prefs_ai2.edit();
        for (StateAction sa : ai2.getvEstadosAcciones()) {
            editor_ai2.putString("Flop#" + sa.getEstado(), sa.toSave());
        }
        for (StateAction sa : ai2.getvEstadosAcciones_turn()) {
            editor_ai2.putString("Turn#" + sa.getEstado(), sa.toSave());
        }
        for (StateAction sa : ai2.getvEstadosAcciones_river()) {
            editor_ai2.putString("River#" + sa.getEstado(), sa.toSave());
        }
        editor_ai2.commit();

        //ai3
        SharedPreferences prefs_ai3 =
                getSharedPreferences("QVector_ai3", Game.MODE_PRIVATE);

        SharedPreferences.Editor editor_ai3 = prefs_ai3.edit();
        for (StateAction sa : ai3.getvEstadosAcciones()) {
            editor_ai3.putString("Flop#" + sa.getEstado(), sa.toSave());
        }
        for (StateAction sa : ai3.getvEstadosAcciones_turn()) {
            editor_ai3.putString("Turn#" + sa.getEstado(), sa.toSave());
        }
        for (StateAction sa : ai3.getvEstadosAcciones_river()) {
            editor_ai3.putString("River#" + sa.getEstado(), sa.toSave());
        }
        editor_ai3.commit();
    }

    private void loadQVectors() {
        //ai1
        SharedPreferences prefs_ai1 =
                getSharedPreferences("QVector_ai1", Game.MODE_PRIVATE);
        Map<String, ?> keys_ai1 = prefs_ai1.getAll();
        for (Map.Entry<String, ?> entry : keys_ai1.entrySet()) {
            if (entry.getKey().split("#")[0].equals("Flop")) {
                ai1.getvEstadosAcciones().add(new StateAction(entry.getValue().toString()));
            } else if (entry.getKey().split("#")[0].equals("Turn")) {
                ai1.getvEstadosAcciones_turn().add(new StateAction(entry.getValue().toString()));
            } else if (entry.getKey().split("#")[0].equals("River")) {
                ai1.getvEstadosAcciones_river().add(new StateAction(entry.getValue().toString()));
            }
            //Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
        }

        //ai2
        SharedPreferences prefs_ai2 =
                getSharedPreferences("QVector_ai2", Game.MODE_PRIVATE);
        Map<String, ?> keys_ai2 = prefs_ai2.getAll();
        for (Map.Entry<String, ?> entry : keys_ai2.entrySet()) {
            System.out.println(entry.getValue().toString());
            if (entry.getKey().split("#")[0].equals("Flop")) {
                ai2.getvEstadosAcciones().add(new StateAction(entry.getValue().toString()));
            } else if (entry.getKey().split("#")[0].equals("Turn")) {
                ai2.getvEstadosAcciones_turn().add(new StateAction(entry.getValue().toString()));
            } else if (entry.getKey().split("#")[0].equals("River")) {
                ai2.getvEstadosAcciones_river().add(new StateAction(entry.getValue().toString()));
            }
            //Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
        }

        //ai3
        SharedPreferences prefs_ai3 =
                getSharedPreferences("QVector_ai3", Game.MODE_PRIVATE);
        Map<String, ?> keys_ai3 = prefs_ai3.getAll();
        for (Map.Entry<String, ?> entry : keys_ai3.entrySet()) {
            System.out.println(entry.getValue().toString());
            if (entry.getKey().split("#")[0].equals("Flop")) {
                ai3.getvEstadosAcciones().add(new StateAction(entry.getValue().toString()));
            } else if (entry.getKey().split("#")[0].equals("Turn")) {
                ai3.getvEstadosAcciones_turn().add(new StateAction(entry.getValue().toString()));
            } else if (entry.getKey().split("#")[0].equals("River")) {
                ai3.getvEstadosAcciones_river().add(new StateAction(entry.getValue().toString()));
            }
            //Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
        }
    }

}
