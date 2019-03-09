package com.example.mario.pokermaster;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mario.pokermaster.actions.Action;
import com.example.mario.pokermaster.actions.BetAction;
import com.example.mario.pokermaster.actions.RaiseAction;
import com.example.mario.pokermaster.ai.AiPlayer;
import com.example.mario.pokermaster.ai.StateAction;
import com.example.mario.pokermaster.util.Bot;
import com.example.mario.pokermaster.util.Card;
import com.example.mario.pokermaster.util.Deck;
import com.example.mario.pokermaster.util.GameState;
import com.example.mario.pokermaster.util.Hand;
import com.example.mario.pokermaster.util.HandValue;
import com.example.mario.pokermaster.util.Human;
import com.example.mario.pokermaster.util.Player;
import com.example.mario.pokermaster.util.Pot;
import com.example.mario.pokermaster.util.TableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Game extends AppCompatActivity implements View.OnClickListener {

    // Layout Views
    private TextView text_player_cash, text_player_bet;
    private TextView text_ai1_cash, text_ai1_bet, text_ai2_cash, text_ai2_bet, text_ai3_cash, text_ai3_bet;
    private TextView text_pot;
    private TextView text_player_action, text_ai1_action, text_ai2_action, text_ai3_action;


    private ImageView img_player_card1, img_player_card2;
    private ImageView img_ai1_card1, img_ai1_card2, img_ai2_card1, img_ai2_card2, img_ai3_card1, img_ai3_card2;
    private ImageView img_table_card1, img_table_card2, img_table_card3, img_table_card4, img_table_card5;
    private ImageView img_player_dealer, img_ai1_dealer, img_ai2_dealer, img_ai3_dealer;

    private Button but_next, but_check_call, but_bet_raise, but_fold;

    // Main Attributes
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
    private static final int STARTING_CASH = 1000;

    /**
     * The table.
     */
    private Table table;

    /**
     * The players at the table.
     */
    private Map<String, Player> players;

    /**
     * The human player.
     */
    private Human player;

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

    private String actualPhaseName;

    private Observable<String> myObservable;
    private Observer<String> myObserver;

    private Dialog dialog_loading;
    private TextView text_loading;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        this.text_player_cash = (TextView) findViewById(R.id.textView);
        this.text_player_bet = (TextView) findViewById(R.id.textView8);
        this.text_ai1_cash = (TextView) findViewById(R.id.textView2);
        this.text_ai1_bet = (TextView) findViewById(R.id.textView5);
        this.text_ai2_cash = (TextView) findViewById(R.id.textView3);
        this.text_ai2_bet = (TextView) findViewById(R.id.textView6);
        this.text_ai3_cash = (TextView) findViewById(R.id.textView4);
        this.text_ai3_bet = (TextView) findViewById(R.id.textView7);

        this.text_pot = findViewById(R.id.textView9);

        this.text_player_action = findViewById(R.id.textView13);
        this.text_ai1_action = findViewById(R.id.textView10);
        this.text_ai2_action = findViewById(R.id.textView11);
        this.text_ai3_action = findViewById(R.id.textView12);

        this.text_player_action.setVisibility(View.VISIBLE);
        this.text_ai1_action.setVisibility(View.VISIBLE);
        this.text_ai2_action.setVisibility(View.VISIBLE);
        this.text_ai3_action.setVisibility(View.VISIBLE);

        this.img_player_card1 = (ImageView) findViewById(R.id.imageView7);
        this.img_player_card2 = (ImageView) findViewById(R.id.imageView8);
        this.img_ai1_card1 = (ImageView) findViewById(R.id.imageView10);
        this.img_ai1_card2 = (ImageView) findViewById(R.id.imageView11);
        this.img_ai2_card1 = (ImageView) findViewById(R.id.imageView12);
        this.img_ai2_card2 = (ImageView) findViewById(R.id.imageView13);
        this.img_ai3_card1 = (ImageView) findViewById(R.id.imageView14);
        this.img_ai3_card2 = (ImageView) findViewById(R.id.imageView15);

        this.img_table_card1 = (ImageView) findViewById(R.id.imageView);
        this.img_table_card2 = (ImageView) findViewById(R.id.imageView2);
        this.img_table_card3 = (ImageView) findViewById(R.id.imageView3);
        this.img_table_card4 = (ImageView) findViewById(R.id.imageView4);
        this.img_table_card5 = (ImageView) findViewById(R.id.imageView5);

        this.img_player_dealer = findViewById(R.id.imageView21);
        this.img_ai1_dealer = findViewById(R.id.imageView18);
        this.img_ai2_dealer = findViewById(R.id.imageView19);
        this.img_ai3_dealer = findViewById(R.id.imageView20);

        this.img_player_dealer.setVisibility(View.INVISIBLE);
        this.img_ai1_dealer.setVisibility(View.INVISIBLE);
        this.img_ai2_dealer.setVisibility(View.INVISIBLE);
        this.img_ai3_dealer.setVisibility(View.INVISIBLE);

        /*img_ai1_card1.setRotation(img_ai1_card1.getRotation() + 270);
        img_ai1_card2.setRotation(img_ai1_card2.getRotation() + 270);
        img_ai2_card1.setRotation(img_ai2_card1.getRotation() + 270);
        img_ai2_card2.setRotation(img_ai2_card2.getRotation() + 270);
        img_ai3_card1.setRotation(img_ai3_card1.getRotation() + 270);
        img_ai3_card2.setRotation(img_ai3_card2.getRotation() + 270);*/


        this.but_next = (Button) findViewById(R.id.but_next);
        this.but_check_call = (Button) findViewById(R.id.button22);
        this.but_bet_raise = (Button) findViewById(R.id.button23);
        this.but_fold = (Button) findViewById(R.id.button24);

        but_check_call.setVisibility(View.INVISIBLE);
        but_bet_raise.setVisibility(View.INVISIBLE);
        but_fold.setVisibility(View.INVISIBLE);

        players = new LinkedHashMap<String, Player>();
        player = new Human("Human", STARTING_CASH);

        player.setBtn_check_call(but_check_call);
        player.setBtn_bet_raise(but_bet_raise);
        player.setBtn_fold(but_fold);

        ai1 = new AiPlayer("aiPlayer1", STARTING_CASH, 5, 60);
        ai2 = new AiPlayer("aiPlayer2", STARTING_CASH, 10, 40);
        ai3 = new AiPlayer("aiPlayer3", STARTING_CASH, 15, 20);
        ai1.setRandom_prob(getIntent().getExtras().getInt("Dificultad"));
        ai2.setRandom_prob(getIntent().getExtras().getInt("Dificultad"));
        ai3.setRandom_prob(getIntent().getExtras().getInt("Dificultad"));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        dialog_loading = new Dialog(Game.this);
        dialog_loading.setTitle("Loading");
        dialog_loading.setContentView(R.layout.dialog_loading);
        text_loading = dialog_loading.findViewById(R.id.text_simul);
        progressBar = dialog_loading.findViewById(R.id.progressBar);
        dialog_loading.show();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                loadQVectors();
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dialog_loading.dismiss();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        players.put("Human", player);
        players.put("aiPlayer1", ai1);
        players.put("aiPlayer2", ai2);
        players.put("aiPlayer3", ai3);

    }

    @Override
    protected void onStart() {
        super.onStart();

        table = new Table(TABLE_TYPE, BIG_BLIND);
        for (Player player : players.values()) {
            table.addPlayer(player);
        }

        createObservableAndObserver();

        if (table.joinTable()) {
            but_next.setEnabled(false);
            this.img_table_card1.setVisibility(View.INVISIBLE);
            this.img_table_card2.setVisibility(View.INVISIBLE);
            this.img_table_card3.setVisibility(View.INVISIBLE);
            this.img_table_card4.setVisibility(View.INVISIBLE);
            this.img_table_card5.setVisibility(View.INVISIBLE);
            actualPhaseName = table.doPreFlop();
            refreshLayout();
            but_next.setEnabled(false);
            myObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(myObserver);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.but_next:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text_ai1_action.setText("");
                        text_ai2_action.setText("");
                        text_ai3_action.setText("");
                        text_player_action.setText("");
                    }
                });
                switch (actualPhaseName) {
                    case "preFlop":
                        System.out.println(actualPhaseName);
                        actualPhaseName = table.doFlop();
                        refreshLayout();
                        but_next.setEnabled(false);
                        myObservable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(myObserver);
                        but_check_call.setVisibility(View.INVISIBLE);
                        but_bet_raise.setVisibility(View.INVISIBLE);
                        but_fold.setVisibility(View.INVISIBLE);

                        break;
                    case "Flop":
                        System.out.println(actualPhaseName);
                        actualPhaseName = table.doTurn();
                        refreshLayout();
                        but_next.setEnabled(false);
                        myObservable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(myObserver);
                        but_check_call.setVisibility(View.INVISIBLE);
                        but_bet_raise.setVisibility(View.INVISIBLE);
                        but_fold.setVisibility(View.INVISIBLE);

                        break;
                    case "Turn":
                        System.out.println(actualPhaseName);
                        actualPhaseName = table.doRiver();
                        refreshLayout();
                        but_next.setEnabled(false);
                        myObservable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(myObserver);
                        but_check_call.setVisibility(View.INVISIBLE);
                        but_bet_raise.setVisibility(View.INVISIBLE);
                        but_fold.setVisibility(View.INVISIBLE);

                        break;
                    case "River":
                        System.out.println(actualPhaseName);
                        // Showdown.
                        if (table.activePlayers.size() > 1) {
                            table.bet = 0;
                            table.minBet = table.bigBlind;
                            table.doShowdown();
                        }

                        showLog();
                        text_player_action.setText("");
                        text_ai1_action.setText("");
                        text_ai2_action.setText("");
                        text_ai3_action.setText("");
                  /*      text_pot.setText("0");
                        actualPhaseName = table.doPreFlop();
                        refreshLayout();

                        this.img_table_card1.setVisibility(View.INVISIBLE);
                        this.img_table_card2.setVisibility(View.INVISIBLE);
                        this.img_table_card3.setVisibility(View.INVISIBLE);
                        this.img_table_card4.setVisibility(View.INVISIBLE);
                        this.img_table_card5.setVisibility(View.INVISIBLE);
                        but_next.setEnabled(false);
                        myObservable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(myObserver);*/
                        break;
                }
                break;
            case R.id.button22:
                Action action = null;
                Iterator<Action> it = table.getAllowedActions(table.actor).iterator();
                while (it.hasNext()) {
                    action = it.next();
                    if (action.getName().equals(but_check_call.getText().toString())) {
                        table.selectedAction = action;
                    }
                }
                but_check_call.setVisibility(View.INVISIBLE);
                but_bet_raise.setVisibility(View.INVISIBLE);
                but_fold.setVisibility(View.INVISIBLE);
                break;
            case R.id.button23:
                Action action2 = null;
                Iterator<Action> it2 = table.getAllowedActions(table.actor).iterator();
                while (it2.hasNext()) {
                    action2 = it2.next();
                    if (action2.getName().equals(but_bet_raise.getText().toString())) {
                        table.selectedAction = action2;
                    }
                }
                show();
                but_check_call.setVisibility(View.INVISIBLE);
                but_fold.setVisibility(View.INVISIBLE);
                break;
            case R.id.button24:
                Action action3 = null;
                Iterator<Action> it3 = table.getAllowedActions(table.actor).iterator();
                while (it3.hasNext()) {
                    action3 = it3.next();
                    if (action3.getName().equals(but_fold.getText().toString())) {
                        table.selectedAction = action3;
                    }
                }
                but_check_call.setVisibility(View.INVISIBLE);
                but_bet_raise.setVisibility(View.INVISIBLE);
                but_fold.setVisibility(View.INVISIBLE);
                break;
            case R.id.button25:
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                dialog_loading = new Dialog(Game.this);
                dialog_loading.setTitle("Loading");
                dialog_loading.setContentView(R.layout.dialog_loading);
                text_loading = dialog_loading.findViewById(R.id.text_simul);
                progressBar = dialog_loading.findViewById(R.id.progressBar);
                dialog_loading.show();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveQVectors();

                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dialog_loading.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Intent next = new Intent(Game.this, mainView.class);
                next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(next);
                finish();
                System.exit(0);
                System.out.println("PRUEBA SUPER UTIL");
                break;
        }
    }

    public void show() {

        final TextView txtv = (TextView) findViewById(R.id.tv);

        final Dialog d = new Dialog(Game.this);
        d.setTitle("Apuesta:");
        d.setContentView(R.layout.activity_dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        int max_value = table.actor.getCash();
        if (but_bet_raise.getText().equals("Raise")) {
            max_value -= table.minBet;
        }
        np.setMaxValue(max_value);
        np.setMinValue(table.minBet);
        np.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtv.setText(String.valueOf(np.getValue()));
                table.selectedBetRaise = Integer.parseInt(txtv.getText().toString());
                but_bet_raise.setVisibility(View.INVISIBLE);
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }

    public void showLog() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(Game.this);
        builder1.setMessage(table.winnerText.toString() + table.hands.toString());
        builder1.setCancelable(false);

        if (ai1.getCards().length > 0) {
            img_ai1_card1.setImageResource(getResources().getIdentifier(ai1.getCards()[0].toString2(), "drawable", getPackageName()));
            img_ai1_card2.setImageResource(getResources().getIdentifier(ai1.getCards()[1].toString2(), "drawable", getPackageName()));
            img_ai1_card1.setVisibility(View.VISIBLE);
            img_ai1_card2.setVisibility(View.VISIBLE);
        } else  {
            img_ai1_card1.setVisibility(View.INVISIBLE);
            img_ai1_card2.setVisibility(View.INVISIBLE);
        }

        if (ai2.getCards().length > 0) {
            img_ai2_card1.setImageResource(getResources().getIdentifier(ai2.getCards()[0].toString2(), "drawable", getPackageName()));
            img_ai2_card2.setImageResource(getResources().getIdentifier(ai2.getCards()[1].toString2(), "drawable", getPackageName()));
            img_ai2_card1.setVisibility(View.VISIBLE);
            img_ai2_card2.setVisibility(View.VISIBLE);
        } else  {
            img_ai2_card1.setVisibility(View.INVISIBLE);
            img_ai2_card2.setVisibility(View.INVISIBLE);
        }

        if (ai3.getCards().length > 0) {
            img_ai3_card1.setImageResource(getResources().getIdentifier(ai3.getCards()[0].toString2(), "drawable", getPackageName()));
            img_ai3_card2.setImageResource(getResources().getIdentifier(ai3.getCards()[1].toString2(), "drawable", getPackageName()));
            img_ai3_card1.setVisibility(View.VISIBLE);
            img_ai3_card2.setVisibility(View.VISIBLE);
        } else {
            img_ai3_card1.setVisibility(View.INVISIBLE);
            img_ai3_card2.setVisibility(View.INVISIBLE);
        }

        img_ai1_card1.setRotation(0);
        img_ai1_card2.setRotation(0);
        img_ai2_card1.setRotation(0);
        img_ai2_card2.setRotation(0);
        img_ai3_card1.setRotation(0);
        img_ai3_card2.setRotation(0);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        text_pot.setText("0");
                        actualPhaseName = table.doPreFlop();
                        refreshLayout();

                        img_table_card1.setVisibility(View.INVISIBLE);
                        img_table_card2.setVisibility(View.INVISIBLE);
                        img_table_card3.setVisibility(View.INVISIBLE);
                        img_table_card4.setVisibility(View.INVISIBLE);
                        img_table_card5.setVisibility(View.INVISIBLE);
                        but_next.setEnabled(false);
                        myObservable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(myObserver);


                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();


    }

    private void createObservableAndObserver() {

        myObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                // do bet
                try {
                    table.doBettingRound();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.onComplete();
            }
        });

        myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                if (table.activePlayers.size() == 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text_pot.setText("0");

                            refreshLayout();

                            img_table_card1.setVisibility(View.INVISIBLE);
                            img_table_card2.setVisibility(View.INVISIBLE);
                            img_table_card3.setVisibility(View.INVISIBLE);
                            img_table_card4.setVisibility(View.INVISIBLE);
                            img_table_card5.setVisibility(View.INVISIBLE);
                            but_next.setEnabled(false);
                        }
                    });
                    /*if(actualPhaseName.equals("preFlop")) {
                        actualPhaseName = table.doFlop();
                        actualPhaseName = table.doTurn();
                        actualPhaseName = table.doRiver();
                    } else if(actualPhaseName.equals("Flop")) {
                        actualPhaseName = table.doTurn();
                        actualPhaseName = table.doRiver();
                    } else if(actualPhaseName.equals("Turn")) {
                        actualPhaseName = table.doRiver();
                    }
                    table.doShowdown();
                    //actualPhaseName = table.doPreFlop();

                    myObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(myObserver);
                   // but_next.setEnabled(true);*/
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(table.getTotalPot());
                            text_pot.setText(String.valueOf(table.getTotalPot()));
                            refreshLayout();
                        }
                    });
                    but_next.setEnabled(true);
                }
            }
        };
    }

    private void refreshLayout() {

        this.text_player_action.setVisibility(View.VISIBLE);
        this.text_ai1_action.setVisibility(View.VISIBLE);
        this.text_ai2_action.setVisibility(View.VISIBLE);
        this.text_ai3_action.setVisibility(View.VISIBLE);

        text_player_cash.setText(String.valueOf(player.getCash()));
        text_player_bet.setText(String.valueOf(player.getBet()));
        text_ai1_cash.setText(String.valueOf(ai1.getCash()));
        text_ai1_bet.setText(String.valueOf(ai1.getBet()));
        text_ai2_cash.setText(String.valueOf(ai2.getCash()));
        text_ai2_bet.setText(String.valueOf(ai2.getBet()));
        text_ai3_cash.setText(String.valueOf(ai3.getCash()));
        text_ai3_bet.setText(String.valueOf(ai3.getBet()));

        if (table.dealer.getName().equals("Human")) {
            this.img_player_dealer.setVisibility(View.VISIBLE);
            this.img_ai1_dealer.setVisibility(View.INVISIBLE);
            this.img_ai2_dealer.setVisibility(View.INVISIBLE);
            this.img_ai3_dealer.setVisibility(View.INVISIBLE);
        } else if (table.dealer.getName().equals("aiPlayer1")) {
            this.img_player_dealer.setVisibility(View.INVISIBLE);
            this.img_ai1_dealer.setVisibility(View.VISIBLE);
            this.img_ai2_dealer.setVisibility(View.INVISIBLE);
            this.img_ai3_dealer.setVisibility(View.INVISIBLE);
        } else if (table.dealer.getName().equals("aiPlayer2")) {
            this.img_player_dealer.setVisibility(View.INVISIBLE);
            this.img_ai1_dealer.setVisibility(View.INVISIBLE);
            this.img_ai2_dealer.setVisibility(View.VISIBLE);
            this.img_ai3_dealer.setVisibility(View.INVISIBLE);
        } else if (table.dealer.getName().equals("aiPlayer3")) {
            this.img_player_dealer.setVisibility(View.INVISIBLE);
            this.img_ai1_dealer.setVisibility(View.INVISIBLE);
            this.img_ai2_dealer.setVisibility(View.INVISIBLE);
            this.img_ai3_dealer.setVisibility(View.VISIBLE);
        }

        if (player.getCards().length > 0) {
            img_player_card1.setImageResource(getResources().getIdentifier(player.getCards()[0].toString2(), "drawable", getPackageName()));
            img_player_card2.setImageResource(getResources().getIdentifier(player.getCards()[1].toString2(), "drawable", getPackageName()));
            img_player_card1.setVisibility(View.VISIBLE);
            img_player_card2.setVisibility(View.VISIBLE);
        } else  {
            img_player_card1.setVisibility(View.INVISIBLE);
            img_player_card2.setVisibility(View.INVISIBLE);
        }

        if (ai1.getCards().length > 0) {
            img_ai1_card1.setImageResource(getResources().getIdentifier("cartabocaabajo", "drawable", getPackageName()));
            img_ai1_card2.setImageResource(getResources().getIdentifier("cartabocaabajo", "drawable", getPackageName()));
            img_ai1_card1.setVisibility(View.VISIBLE);
            img_ai1_card2.setVisibility(View.VISIBLE);
        } else  {
            img_ai1_card1.setVisibility(View.INVISIBLE);
            img_ai1_card2.setVisibility(View.INVISIBLE);
        }

        if (ai2.getCards().length > 0) {
            img_ai2_card1.setImageResource(getResources().getIdentifier("cartabocaabajo", "drawable", getPackageName()));
            img_ai2_card2.setImageResource(getResources().getIdentifier("cartabocaabajo", "drawable", getPackageName()));
            img_ai2_card1.setVisibility(View.VISIBLE);
            img_ai2_card2.setVisibility(View.VISIBLE);
        } else  {
            img_ai2_card1.setVisibility(View.INVISIBLE);
            img_ai2_card2.setVisibility(View.INVISIBLE);
        }

        if (ai3.getCards().length > 0) {
            img_ai3_card1.setImageResource(getResources().getIdentifier("cartabocaabajo", "drawable", getPackageName()));
            img_ai3_card2.setImageResource(getResources().getIdentifier("cartabocaabajo", "drawable", getPackageName()));
            img_ai3_card1.setVisibility(View.VISIBLE);
            img_ai3_card2.setVisibility(View.VISIBLE);
        } else  {
            img_ai3_card1.setVisibility(View.INVISIBLE);
            img_ai3_card2.setVisibility(View.INVISIBLE);
        }

        if(img_ai1_card1.getRotation() == 0) {
            img_ai1_card1.setRotation(90);
            img_ai1_card2.setRotation(90);
            img_ai2_card1.setRotation(90);
            img_ai2_card2.setRotation(90);
            img_ai3_card1.setRotation(90);
            img_ai3_card2.setRotation(90);
        }

        if (!table.board.isEmpty()) {
            if (table.board.size() > 0) {
                img_table_card1.setImageResource(getResources().getIdentifier(table.board.get(0).toString2(), "drawable", getPackageName()));
                img_table_card1.setVisibility(View.VISIBLE);
            }
            if (table.board.size() > 1) {
                img_table_card2.setImageResource(getResources().getIdentifier(table.board.get(1).toString2(), "drawable", getPackageName()));
                img_table_card2.setVisibility(View.VISIBLE);
            }
            if (table.board.size() > 2) {
                img_table_card3.setImageResource(getResources().getIdentifier(table.board.get(2).toString2(), "drawable", getPackageName()));
                img_table_card3.setVisibility(View.VISIBLE);
            }
            if (table.board.size() > 3) {
                img_table_card4.setImageResource(getResources().getIdentifier(table.board.get(3).toString2(), "drawable", getPackageName()));
                img_table_card4.setVisibility(View.VISIBLE);
            }
            if (table.board.size() > 4) {
                img_table_card5.setImageResource(getResources().getIdentifier(table.board.get(4).toString2(), "drawable", getPackageName()));
                img_table_card5.setVisibility(View.VISIBLE);
            }
        }

        /*System.out.println("\nINFO LAYOUT\n");
        System.out.println(player.getName() + " " + player.getCash() + " " + player.getBet() + " " + player.getAction() + " " + player.getCards());
        System.out.println(ai1.getName() + " " + ai1.getCash() + " " + ai1.getBet() + " " + ai1.getAction() + " " + ai1.getCards());
        System.out.println(ai2.getName() + " " + ai2.getCash() + " " + ai2.getBet() + " " + ai2.getAction() + " " + ai2.getCards());
        System.out.println(ai3.getName() + " " + ai3.getCash() + " " + ai3.getBet() + " " + ai3.getAction() + " " + ai3.getCards());
        System.out.println();*/

    }

    private void refreshTextAction(Player actor, Action action) {
        if (actor.getName().equals("Human")) {
            if (action != null) {
                this.text_player_action.setText(action.getName());
                this.text_player_action.setVisibility(View.VISIBLE);
            }
        }
        if (actor.getName().equals("aiPlayer1")) {
            if (action != null) {
                this.text_ai1_action.setText(action.getName());
                this.text_ai1_action.setVisibility(View.VISIBLE);
            }
        }
        if (actor.getName().equals("aiPlayer2")) {
            if (action != null) {
                this.text_ai2_action.setText(action.getName());
                this.text_ai2_action.setVisibility(View.VISIBLE);
            }
        }
        if (actor.getName().equals("aiPlayer3")) {
            if (action != null) {
                this.text_ai3_action.setText(action.getName());
                this.text_ai3_action.setVisibility(View.VISIBLE);
            }
        }
    }

    private void saveQVectors() {

        final int totalEstados = ai1.getvEstadosAcciones().size() + ai1.getvEstadosAcciones_turn().size() + ai1.getvEstadosAcciones_river().size() +
                ai2.getvEstadosAcciones().size() + ai2.getvEstadosAcciones_turn().size() + ai2.getvEstadosAcciones_river().size() +
                ai2.getvEstadosAcciones().size() + ai2.getvEstadosAcciones_turn().size() + ai2.getvEstadosAcciones_river().size();
        int contEstados = 0;

        System.out.println(totalEstados);

        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setMax(totalEstados);
            }
        });

        //ai1
        SharedPreferences prefs_ai1 =
                getSharedPreferences("QVector_ai1", Game.MODE_PRIVATE);

        SharedPreferences.Editor editor_ai1 = prefs_ai1.edit();
        for (StateAction sa : ai1.getvEstadosAcciones()) {
            editor_ai1.putString("Flop#" + sa.getEstado(), sa.toSave());
            contEstados++;
            final int finalContEstados = contEstados;
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(finalContEstados);
                }
            });
            text:text_loading.post(new Runnable() {
                @Override
                public void run() {
                    text_loading.setText(finalContEstados + "/" + totalEstados + " Estados Cargados");
                }
            });
        }
        for (StateAction sa : ai1.getvEstadosAcciones_turn()) {
            editor_ai1.putString("Turn#" + sa.getEstado(), sa.toSave());
            contEstados++;
            final int finalContEstados = contEstados;
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(finalContEstados);
                }
            });
            text:text_loading.post(new Runnable() {
                @Override
                public void run() {
                    text_loading.setText(finalContEstados + "/" + totalEstados + " Estados Cargados");
                }
            });
        }
        for (StateAction sa : ai1.getvEstadosAcciones_river()) {
            editor_ai1.putString("River#" + sa.getEstado(), sa.toSave());
            contEstados++;
            final int finalContEstados = contEstados;
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(finalContEstados);
                }
            });
            text:text_loading.post(new Runnable() {
                @Override
                public void run() {
                    text_loading.setText(finalContEstados + "/" + totalEstados + " Estados Cargados");
                }
            });
        }
        editor_ai1.commit();

        //ai2
        SharedPreferences prefs_ai2 =
                getSharedPreferences("QVector_ai2", Game.MODE_PRIVATE);

        SharedPreferences.Editor editor_ai2 = prefs_ai2.edit();
        for (StateAction sa : ai2.getvEstadosAcciones()) {
            editor_ai2.putString("Flop#" + sa.getEstado(), sa.toSave());
            contEstados++;
            final int finalContEstados = contEstados;
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(finalContEstados);
                }
            });
            text:text_loading.post(new Runnable() {
                @Override
                public void run() {
                    text_loading.setText(finalContEstados + "/" + totalEstados + " Estados Cargados");
                }
            });
        }
        for (StateAction sa : ai2.getvEstadosAcciones_turn()) {
            editor_ai2.putString("Turn#" + sa.getEstado(), sa.toSave());
            contEstados++;
            final int finalContEstados = contEstados;
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(finalContEstados);
                }
            });
            text:text_loading.post(new Runnable() {
                @Override
                public void run() {
                    text_loading.setText(finalContEstados + "/" + totalEstados + " Estados Cargados");
                }
            });
        }
        for (StateAction sa : ai2.getvEstadosAcciones_river()) {
            editor_ai2.putString("River#" + sa.getEstado(), sa.toSave());
            contEstados++;
            final int finalContEstados = contEstados;
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(finalContEstados);
                }
            });
            text:text_loading.post(new Runnable() {
                @Override
                public void run() {
                    text_loading.setText(finalContEstados + "/" + totalEstados + " Estados Cargados");
                }
            });
        }
        editor_ai2.commit();

        //ai3
        SharedPreferences prefs_ai3 =
                getSharedPreferences("QVector_ai3", Game.MODE_PRIVATE);

        SharedPreferences.Editor editor_ai3 = prefs_ai3.edit();
        for (StateAction sa : ai3.getvEstadosAcciones()) {
            editor_ai3.putString("Flop#" + sa.getEstado(), sa.toSave());
            contEstados++;
            final int finalContEstados = contEstados;
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(finalContEstados);
                }
            });
            text:text_loading.post(new Runnable() {
                @Override
                public void run() {
                    text_loading.setText(finalContEstados + "/" + totalEstados + " Estados Cargados");
                }
            });
        }
        for (StateAction sa : ai3.getvEstadosAcciones_turn()) {
            editor_ai3.putString("Turn#" + sa.getEstado(), sa.toSave());
            contEstados++;
            final int finalContEstados = contEstados;
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(finalContEstados);
                }
            });
            text:text_loading.post(new Runnable() {
                @Override
                public void run() {
                    text_loading.setText(finalContEstados + "/" + totalEstados + " Estados Cargados");
                }
            });
        }
        for (StateAction sa : ai3.getvEstadosAcciones_river()) {
            editor_ai3.putString("River#" + sa.getEstado(), sa.toSave());
            contEstados++;
            final int finalContEstados = contEstados;
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(finalContEstados);
                }
            });
            text:text_loading.post(new Runnable() {
                @Override
                public void run() {
                    text_loading.setText(finalContEstados + "/" + totalEstados + " Estados Cargados");
                }
            });
        }
        editor_ai3.commit();
    }

    private void loadQVectors() {
        //ai1
        SharedPreferences prefs_ai1 =
                getSharedPreferences("QVector_ai1", Game.MODE_PRIVATE);
        Map<String, ?> keys_ai1 = prefs_ai1.getAll();
        SharedPreferences prefs_ai2 =
                getSharedPreferences("QVector_ai2", Game.MODE_PRIVATE);
        Map<String, ?> keys_ai2 = prefs_ai2.getAll();
        SharedPreferences prefs_ai3 =
                getSharedPreferences("QVector_ai3", Game.MODE_PRIVATE);
        Map<String, ?> keys_ai3 = prefs_ai3.getAll();

        final int totalEstados = keys_ai1.size() + keys_ai2.size() + keys_ai3.size();
        int contEstados = 0;

        System.out.println(totalEstados);

        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setMax(totalEstados);
            }
        });

        for (Map.Entry<String, ?> entry : keys_ai1.entrySet()) {
            if (entry.getKey().split("#")[0].equals("Flop")) {
                ai1.getvEstadosAcciones().add(new StateAction(entry.getValue().toString()));
            } else if (entry.getKey().split("#")[0].equals("Turn")) {
                ai1.getvEstadosAcciones_turn().add(new StateAction(entry.getValue().toString()));
            } else if (entry.getKey().split("#")[0].equals("River")) {
                ai1.getvEstadosAcciones_river().add(new StateAction(entry.getValue().toString()));
            }
            contEstados++;
            final int finalContEstados = contEstados;
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(finalContEstados);
                }
            });
            text:text_loading.post(new Runnable() {
                @Override
                public void run() {
                    text_loading.setText(finalContEstados + "/" + totalEstados + " Estados Cargados");
                }
            });
        }

        //ai2
        for (Map.Entry<String, ?> entry : keys_ai2.entrySet()) {
            if (entry.getKey().split("#")[0].equals("Flop")) {
                ai2.getvEstadosAcciones().add(new StateAction(entry.getValue().toString()));
            } else if (entry.getKey().split("#")[0].equals("Turn")) {
                ai2.getvEstadosAcciones_turn().add(new StateAction(entry.getValue().toString()));
            } else if (entry.getKey().split("#")[0].equals("River")) {
                ai2.getvEstadosAcciones_river().add(new StateAction(entry.getValue().toString()));
            }
            contEstados++;
            final int finalContEstados = contEstados;
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(finalContEstados);
                }
            });
            text:text_loading.post(new Runnable() {
                @Override
                public void run() {
                    text_loading.setText(finalContEstados + "/" + totalEstados + " Estados Cargados");
                }
            });
        }

        //ai3
        for (Map.Entry<String, ?> entry : keys_ai3.entrySet()) {
            if (entry.getKey().split("#")[0].equals("Flop")) {
                ai3.getvEstadosAcciones().add(new StateAction(entry.getValue().toString()));
            } else if (entry.getKey().split("#")[0].equals("Turn")) {
                ai3.getvEstadosAcciones_turn().add(new StateAction(entry.getValue().toString()));
            } else if (entry.getKey().split("#")[0].equals("River")) {
                ai3.getvEstadosAcciones_river().add(new StateAction(entry.getValue().toString()));
            }
            contEstados++;
            final int finalContEstados = contEstados;
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(finalContEstados);
                }
            });
            text:text_loading.post(new Runnable() {
                @Override
                public void run() {
                    text_loading.setText(finalContEstados + "/" + totalEstados + " Estados Cargados");
                }
            });
        }
    }

    private class Table {

        private StringBuilder winnerText;
        private StringBuilder hands;

        private Action selectedAction;

        /**
         * The maximum number of rounds to play in one game
         */
        private static final int MAX_ROUNDS = 20;

        /**
         * In fixed-limit games, the maximum number of raises per betting round.
         */
        private static final int MAX_RAISES = 4;

        /**
         * Whether players will always call the showdown, or fold when no chance.
         */
        private static final boolean ALWAYS_CALL_SHOWDOWN = true;

        /**
         * Table type (poker variant).
         */
        private final TableType tableType;

        /**
         * The size of the big blind.
         */
        private final int bigBlind;

        /**
         * The players at the table.
         */
        private final List<Player> players;

        /**
         * The active players in the current hand.
         */
        private final List<Player> activePlayers;

        /**
         * The deck of cards.
         */
        private final Deck deck;

        /**
         * The community cards on the board.
         */
        private final List<Card> board;

        /**
         * The current dealer position.
         */
        private int dealerPosition;

        /**
         * The current dealer.
         */
        private Player dealer;

        /**
         * The position of the acting player.
         */
        private int actorPosition;

        /**
         * The acting player.
         */
        private Player actor;

        /**
         * The minimum bet in the current hand.
         */
        private int minBet;

        /**
         * The current bet in the current hand.
         */
        private int bet;

        /**
         * All pots in the current hand (main pot and any side pots).
         */
        private final List<Pot> pots;

        /**
         * The player who bet or raised last (aggressor).
         */
        private Player lastBettor;

        /**
         * Number of raises in the current betting round.
         */
        private int raises;

        private Action action;

        private Set<Action> allowedActions;

        private int selectedBetRaise;

        private GameState gameState;

        /**
         * Constructor.
         *
         * @param bigBlind The size of the big blind.
         */
        public Table(TableType type, int bigBlind) {
            this.tableType = type;
            this.bigBlind = bigBlind;
            players = new ArrayList<Player>();
            activePlayers = new ArrayList<Player>();
            deck = new Deck();
            board = new ArrayList<Card>();
            pots = new ArrayList<Pot>();
        }

        /**
         * Adds a player.
         *
         * @param player The player.
         */
        public void addPlayer(Player player) {
            players.add(player);
        }

        public boolean joinTable() {
            for (Player player : players) {
                System.out.println(player.getName() + " joined the table.");
            }

            dealerPosition = -1;
            actorPosition = -1;

            int noOfActivePlayers = 0;
            for (Player player : players) {
                if (player.getCash() >= bigBlind) {
                    noOfActivePlayers++;
                }
            }
            if (noOfActivePlayers > 1) {
                return true;
            } else {
                return false;
            }
        }

        public String doPreFlop() {

            resetHand();
            for (int i = 0; i < players.size(); i++) {
                players.get(i).resetBet();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text_pot.setText("0");
                    text_ai1_action.setText("");
                    text_ai2_action.setText("");
                    text_ai3_action.setText("");
                    text_player_action.setText("");
                }
            });
            // Small blind.
            if (activePlayers.size() >= 2 && player.getCash() > 0) {
                if (activePlayers.size() > 2) rotateActor();
                postSmallBlind();

                // Big blind.
                rotateActor();
                postBigBlind();

                // Pre-Flop.
                dealHoleCards();

                gameState = GameState.preFlop;
                return "preFlop";
            } else {
                if (player.getCash() == 0) {

                    SharedPreferences prefs =
                            getSharedPreferences("Estadisticas", Game.MODE_PRIVATE);

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("partidasPerdidas", prefs.getInt("partidasPerdidas", 0) + 1);
                    editor.commit();

                } else {
                    SharedPreferences prefs =
                            getSharedPreferences("Estadisticas", Game.MODE_PRIVATE);

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("partidasGanadas", prefs.getInt("partidasGanadas", 0) + 1);
                    editor.commit();
                }
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                dialog_loading = new Dialog(Game.this);
                dialog_loading.setTitle("Loading");
                dialog_loading.setContentView(R.layout.dialog_loading);
                text_loading = dialog_loading.findViewById(R.id.text_simul);
                progressBar = dialog_loading.findViewById(R.id.progressBar);
                dialog_loading.show();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveQVectors();

                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dialog_loading.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Intent next = new Intent(Game.this, mainView.class);
                next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(next);
                finish();
                System.exit(0);
                System.out.println("PRUEBA SUPER UTIL PREFLOP");
            }
            return "";
        }

        public String doFlop() {
            if (activePlayers.size() > 1) {
                bet = 0;
                dealCommunityCards("Flop", 3);
                minBet = bigBlind;
            }
            gameState = GameState.Flop;
            return "Flop";
        }

        public String doTurn() {
            if (activePlayers.size() > 1) {
                bet = 0;
                dealCommunityCards("Turn", 1);
                if (tableType == TableType.FIXED_LIMIT) {
                    minBet = 2 * bigBlind;
                } else {
                    minBet = bigBlind;
                }
            }
            gameState = GameState.Turn;
            return "Turn";
        }

        public String doRiver() {
            if (activePlayers.size() > 1) {
                bet = 0;
                dealCommunityCards("River", 1);
                if (tableType == TableType.FIXED_LIMIT) {
                    minBet = 2 * bigBlind;
                } else {
                    minBet = bigBlind;
                }
            }
            gameState = GameState.River;
            return "River";
        }

        /**
         * Performs a betting round.
         */
        private void doBettingRound() throws InterruptedException {
            // Determine the number of active players.
            int playersToAct = activePlayers.size();
            // Determine the initial player and bet size.
            if (board.size() == 0) {
                // Pre-Flop; player left of big blind starts, bet is the big blind.
                bet = bigBlind;
            } else {
                // Otherwise, player left of dealer starts, no initial bet.
                actorPosition = dealerPosition;
                bet = 0;
            }

		/*
         * if (playersToAct == 2) { //removed, fix by IW // Heads Up mode; player who is
		 * not the dealer starts. actorPosition = dealerPosition; }
		 */

            lastBettor = null;
            raises = 0;

            while (playersToAct > 0) {
                rotateActor();
                action = null;
                selectedAction = null;
                selectedBetRaise = 0;
                if (actor.isAllIn()) {
                    // Player is all-in, so must check.
                    action = Action.CHECK;
                    playersToAct--;
                } else {
                    // Otherwise allow client to act.
                    allowedActions = getAllowedActions(actor);
                    if (actor instanceof AiPlayer) {
                        ((AiPlayer) actor).setGameState(gameState);
                        action = actor.act(minBet, bet, allowedActions, new Hand(board));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (actor.getName()) {
                                    case "aiPlayer1":
                                        text_ai1_action.setText(action.getName());
                                        break;
                                    case "aiPlayer2":
                                        text_ai2_action.setText(action.getName());
                                        break;
                                    case "aiPlayer3":
                                        text_ai3_action.setText(action.getName());
                                        break;
                                }
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Iterator<Action> it = allowedActions.iterator();
                                Action action_temp = null;
                                System.out.println("ALLOWED ACTIONS = " + allowedActions.size());
                                while (it.hasNext()) {
                                    action_temp = it.next();
                                    if (action_temp.getName().equals(Action.CHECK.getName()) || action_temp.getName().equals(Action.CALL.getName())) {
                                        but_check_call.setText(action_temp.getName());
                                        but_check_call.setVisibility(View.VISIBLE);
                                    }
                                    if (action_temp.getName().equals(Action.BET.getName()) || action_temp.getName().equals(Action.RAISE.getName())) {
                                        but_bet_raise.setText(action_temp.getName());
                                        but_bet_raise.setVisibility(View.VISIBLE);
                                    }
                                    if (action_temp.getName().equals(Action.FOLD.getName())) {
                                        but_fold.setText(action_temp.getName());
                                        but_fold.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        });
                        while (action == null) {
                            action = selectedAction;
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text_player_action.setText(action.getName());
                            }
                        });

                    }
                    System.out.println("Selected action = " + action.getName());
                    // Verify chosen action to guard against broken clients (accidental or on
                    // purpose).
                    if (!allowedActions.contains(action)) {
                        if (!(action instanceof BetAction && allowedActions.contains(Action.BET))
                                && !(action instanceof RaiseAction && allowedActions.contains(Action.RAISE))) {
                            throw new IllegalStateException(
                                    String.format("Player '%s' acted with illegal %s action", actor, action));
                        }
                    }
                    playersToAct--;
                    if (action == Action.CHECK) {
                        // Do nothing.
                    } else if (action == Action.CALL) {
                        int betIncrement = bet - actor.getBet();
                        if (betIncrement > actor.getCash()) {
                            betIncrement = actor.getCash();
                        }
                        actor.payCash(betIncrement);
                        actor.setBet(actor.getBet() + betIncrement);
                        contributePot(betIncrement);
                    } else if (action instanceof BetAction) {
                        if (actor instanceof AiPlayer) {
                        } else {
                            while (selectedBetRaise == 0) {
                                Thread.sleep(200);
                            }
                        }
                        if (actor instanceof Human) {
                            action = new RaiseAction(selectedBetRaise);
                        }
                        int amount = (tableType == TableType.FIXED_LIMIT) ? minBet : action.getAmount();
                        if (amount < minBet && amount < actor.getCash()) {
                            throw new IllegalStateException("Illegal client action: bet less than minimum bet!");
                        }
                        if (amount > actor.getCash() && actor.getCash() >= minBet) {
                            throw new IllegalStateException("Illegal client action: bet more cash than you own!");
                        }
                        bet = amount;
                        minBet = amount;
                        int betIncrement = bet - actor.getBet();
                        if (betIncrement > actor.getCash()) {
                            betIncrement = actor.getCash();
                        }
                        actor.setBet(bet);
                        actor.payCash(betIncrement);
                        contributePot(betIncrement);
                        lastBettor = actor;
                        playersToAct = (tableType == TableType.FIXED_LIMIT) ? activePlayers.size()
                                : (activePlayers.size() - 1);
                    } else if (action instanceof RaiseAction) {
                        if (actor instanceof AiPlayer) {
                        } else {
                            while (selectedBetRaise == 0) {
                                Thread.sleep(200);
                            }
                        }
                        if (actor instanceof Human) {
                            action = new RaiseAction(selectedBetRaise);
                        }
                        int amount = (tableType == TableType.FIXED_LIMIT) ? minBet : action.getAmount();
                        if (amount < minBet && amount < actor.getCash()) {
                            throw new IllegalStateException("Illegal client action: raise less than minimum bet!");
                        }
                        if (amount > actor.getCash() && actor.getCash() >= minBet) {
                            throw new IllegalStateException("Illegal client action: raise more cash than you own!");
                        }
                        bet += amount;
                        minBet = amount;
                        int betIncrement = bet - actor.getBet();
                        if (betIncrement > actor.getCash()) {
                            betIncrement = actor.getCash();
                        }
                        actor.setBet(bet);
                        actor.payCash(betIncrement);
                        contributePot(betIncrement);
                        lastBettor = actor;
                        raises++;
                        if (tableType == TableType.FIXED_LIMIT && (raises < MAX_RAISES || activePlayers.size() == 2)) {
                            // All players get another turn.
                            playersToAct = activePlayers.size();
                        } else {
                            // Max. number of raises reached; other players get one more turn.
                            playersToAct = activePlayers.size() - 1;
                        }
                    } else if (action == Action.FOLD) {
                        System.out.println("DENTRO DEL FOLD");
                        if (actor instanceof Human) {
                            SharedPreferences prefs =
                                    getSharedPreferences("Estadisticas", Game.MODE_PRIVATE);

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("rondasPerdidas", prefs.getInt("rondasPerdidas", 0) + 1);
                            editor.commit();
                        } else {
                            ((AiPlayer) actor).Refuerzo(-10);

                            ((AiPlayer) actor).EstadoAccionAnterior = null;
                            ((AiPlayer) actor).Refuerzo_turn(-10);

                            ((AiPlayer) actor).EstadoAccionAnterior_turn = null;
                            ((AiPlayer) actor).Refuerzo_river(-10);

                            ((AiPlayer) actor).EstadoAccionAnterior_river = null;
                        }
                        actor.setCards(null);
                        activePlayers.remove(actor);
                        actorPosition--;
                        if (activePlayers.size() == 1) {
                            // Only one player left, so he wins the entire pot.
                            Player winner = activePlayers.get(0);
                            int amount = getTotalPot();
                            winner.win(amount);
                            System.out.printf("\n%s wins $ %d.", winner, amount);
                            playersToAct = 0;
                            //TO-DO --fixed
                            winnerText = new StringBuilder();
                            Hand hand = new Hand(board);
                            hand.addCards(winner.getCards());
                            HandValue handValue = new HandValue(hand);
                            winnerText.append(String.format("%s wins %d with %s %s \n", winner, amount, handValue.getDescription(), winner.getHand()));
                            hands = new StringBuilder();
                            hands.append("");
                            if (winner instanceof Human) {
                                SharedPreferences prefs =
                                        getSharedPreferences("Estadisticas", Game.MODE_PRIVATE);

                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putInt("rondasGanadas", prefs.getInt("rondasGanadas", 0) + 1);
                                editor.putInt(handValue.getDescription(), prefs.getInt(handValue.getDescription(), 0) + 1);
                                editor.commit();
                            } else {
                                ((AiPlayer) winner).Refuerzo(10);

                                ((AiPlayer) winner).EstadoAccionAnterior = null;
                                ((AiPlayer) winner).Refuerzo_turn(10);

                                ((AiPlayer) winner).EstadoAccionAnterior_turn = null;
                                ((AiPlayer) winner).Refuerzo_river(10);

                                ((AiPlayer) winner).EstadoAccionAnterior_river = null;
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showLog();
                                }
                            });
                        }
                    } else {
                        // Programming error, should never happen.
                        throw new IllegalStateException("Invalid action: " + action);
                    }
                }
                actor.setAction(action);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout();
                    }
                });
                Thread.sleep(1000);
            }

            for (int i = 0; i < players.size(); i++) {
                players.get(i).resetBet();
            }

            /*// Reset player's bets.
            for (Player player : activePlayers) {
                player.resetBet();
            }*/
        }

        /**
         * Performs the showdown.
         */
        private void doShowdown() {
            // System.out.println("\n[DEBUG] Pots:");
            // for (Pot pot : pots) {
            // System.out.format(" %s\n", pot);
            // }
            // System.out.format("[DEBUG] Total: %d\n", getTotalPot());

            // Determine show order; start with all-in players...
            List<Player> showingPlayers = new ArrayList<Player>();
            for (Pot pot : pots) {
                for (Player contributor : pot.getContributors()) {
                    if (!showingPlayers.contains(contributor) && contributor.isAllIn()) {
                        showingPlayers.add(contributor);
                    }
                }
            }
            // ...then last player to bet or raise (aggressor)...
            if (lastBettor != null) {
                if (!showingPlayers.contains(lastBettor)) {
                    showingPlayers.add(lastBettor);
                }
            }
            // ...and finally the remaining players, starting left of the button.
            int pos = (dealerPosition + 1) % activePlayers.size();
            while (showingPlayers.size() < activePlayers.size()) {
                Player player = activePlayers.get(pos);
                if (!showingPlayers.contains(player)) {
                    showingPlayers.add(player);
                }
                pos = (pos + 1) % activePlayers.size();
            }

            // Players automatically show or fold in order.
            boolean firstToShow = true;
            int bestHandValue = -1;
            for (Player playerToShow : showingPlayers) {
                Hand hand = new Hand(board);
                hand.addCards(playerToShow.getCards());
                HandValue handValue = new HandValue(hand);
                boolean doShow = ALWAYS_CALL_SHOWDOWN;
                if (!doShow) {
                    if (playerToShow.isAllIn()) {
                        // All-in players must always show.
                        doShow = true;
                        firstToShow = false;
                    } else if (firstToShow) {
                        // First player must always show.
                        doShow = true;
                        bestHandValue = handValue.getValue();
                        firstToShow = false;
                    } else {
                        // Remaining players only show when having a chance to win.
                        if (handValue.getValue() >= bestHandValue) {
                            doShow = true;
                            bestHandValue = handValue.getValue();
                        }
                    }
                }
                if (doShow) {
                    // Show hand.
                    System.out.printf("\n%s has %s.", playerToShow, handValue.getDescription());
                } else {
                    // Fold.
                    playerToShow.setCards(null);
                    activePlayers.remove(playerToShow);
                    System.out.printf("\n%s folds.", playerToShow);
                }
            }

            // Sort players by hand value (highest to lowest).
            Map<HandValue, List<Player>> rankedPlayers = new TreeMap<HandValue, List<Player>>();
            for (Player player : activePlayers) {
                // Create a hand with the community cards and the player's hole cards.
                Hand hand = new Hand(board);
                hand.addCards(player.getCards());
                // Store the player together with other players with the same hand value.
                HandValue handValue = new HandValue(hand);
                // System.out.format("[DEBUG] %s: %s\n", player, handValue);
                List<Player> playerList = rankedPlayers.get(handValue);
                if (playerList == null) {
                    playerList = new ArrayList<Player>();
                }
                playerList.add(player);
                rankedPlayers.put(handValue, playerList);
            }

            // Per rank (single or multiple winners), calculate pot distribution.
            int totalPot = getTotalPot();
            Map<Player, Integer> potDivision = new HashMap<Player, Integer>();
            for (HandValue handValue : rankedPlayers.keySet()) {
                List<Player> winners = rankedPlayers.get(handValue);
                for (Pot pot : pots) {
                    // Determine how many winners share this pot.
                    int noOfWinnersInPot = 0;
                    for (Player winner : winners) {
                        if (pot.hasContributer(winner)) {
                            noOfWinnersInPot++;
                        }
                    }
                    if (noOfWinnersInPot > 0) {
                        // Divide pot over winners.
                        int potShare = pot.getValue() / noOfWinnersInPot;
                        for (Player winner : winners) {
                            if (pot.hasContributer(winner)) {
                                Integer oldShare = potDivision.get(winner);
                                if (oldShare != null) {
                                    potDivision.put(winner, oldShare + potShare);
                                } else {
                                    potDivision.put(winner, potShare);
                                }

                            }
                        }
                        // Determine if we have any odd chips left in the pot.
                        int oddChips = pot.getValue() % noOfWinnersInPot;
                        if (oddChips > 0) {
                            // Divide odd chips over winners, starting left of the dealer.
                            pos = dealerPosition;
                            while (oddChips > 0) {
                                pos = (pos + 1) % activePlayers.size();
                                Player winner = activePlayers.get(pos);
                                Integer oldShare = potDivision.get(winner);
                                if (oldShare != null) {
                                    potDivision.put(winner, oldShare + 1);
                                    // System.out.format("[DEBUG] %s receives an odd chip from the pot.\n", winner);
                                    oddChips--;
                                }
                            }

                        }
                        pot.clear();
                    }
                }
            }

            // Divide winnings.
            winnerText = new StringBuilder();
            int totalWon = 0;
            for (Player player : players) {
                if (!potDivision.containsKey(player)) {
                    if (player instanceof AiPlayer) {
                        ((AiPlayer) player).Refuerzo(-10);

                        ((AiPlayer) player).EstadoAccionAnterior = null;
                        ((AiPlayer) player).Refuerzo_turn(-10);

                        ((AiPlayer) player).EstadoAccionAnterior_turn = null;
                        ((AiPlayer) player).Refuerzo_river(-10);

                        ((AiPlayer) player).EstadoAccionAnterior_river = null;
                    }
                }
            }
            winnerText.append("Cartas Mesa: " + board + "\n");
            for (Player winner : potDivision.keySet()) {
                int potShare = potDivision.get(winner);
                winner.win(potShare);
                totalWon += potShare;
                if (winner instanceof AiPlayer) {
                    ((AiPlayer) winner).Refuerzo(10);

                    ((AiPlayer) winner).EstadoAccionAnterior = null;
                    ((AiPlayer) winner).Refuerzo_turn(10);

                    ((AiPlayer) winner).EstadoAccionAnterior_turn = null;
                    ((AiPlayer) winner).Refuerzo_river(10);

                    ((AiPlayer) winner).EstadoAccionAnterior_river = null;
                }
                Hand hand = new Hand(board);
                hand.addCards(winner.getCards());
                HandValue handValue = new HandValue(hand);
                winnerText.append(String.format("%s wins %d with %s %s \n", winner, potShare, handValue.getDescription(), winner.getHand()));

                if (winner instanceof Human) {
                    SharedPreferences prefs =
                            getSharedPreferences("Estadisticas", Game.MODE_PRIVATE);

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("rondasGanadas", prefs.getInt("rondasGanadas", 0) + 1);
                    editor.putInt(handValue.getDescription(), prefs.getInt(handValue.getDescription(), 0) + 1);
                    editor.commit();
                }
            }
            System.out.println(winnerText.toString());
            hands = new StringBuilder();
            for (Player player : activePlayers) {
                if (!potDivision.containsKey(player)) {
                    Hand hand = new Hand(board);
                    hand.addCards(player.getCards());
                    HandValue handValue = new HandValue(hand);
                    hands.append(String.format("%s losses with %s %s \n", player, handValue.getDescription(), player.getHand()));
                    if (player instanceof Human) {
                        SharedPreferences prefs =
                                getSharedPreferences("Estadisticas", Game.MODE_PRIVATE);

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("rondasPerdidas", prefs.getInt("rondasPerdidas", 0) + 1);
                        editor.commit();
                    }
                }
            }

          /*  runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text_log.setText(winnerText.toString()+hands.toString());
                }
            });*/

            // Sanity check.
            if (totalWon != totalPot) {
                throw new IllegalStateException("Incorrect pot division!");
            }
        }

        /**
         * Returns the total pot size.
         *
         * @return The total pot size.
         */
        private int getTotalPot() {
            int totalPot = 0;
            for (Pot pot : pots) {
                totalPot += pot.getValue();
            }
            return totalPot;
        }

        /**
         * Returns the allowed actions of a specific player.
         *
         * @param player The player.
         * @return The allowed actions.
         */
        //TO-DO
        private Set<Action> getAllowedActions(Player player) {
            Set<Action> actions = new HashSet<Action>();
            if (player.isAllIn()) {
                actions.add(Action.CHECK);
            } else {
                int actorBet = actor.getBet();
                if (bet == 0) {
                    actions.add(Action.CHECK);
                    if (raises < MAX_RAISES) {
                        actions.add(Action.BET);
                    }
                } else {
                    if (actorBet < bet) {
                        actions.add(Action.CALL);
                        if (raises < MAX_RAISES) {
                            actions.add(Action.RAISE);
                        }
                    } else {
                        actions.add(Action.CHECK);
                        if (raises < MAX_RAISES) {
                            actions.add(Action.RAISE);
                        }
                    }
                }
                actions.add(Action.FOLD);
            }
            return actions;
        }


        /**
         * Resets the game for a new hand.
         */
        private void resetHand() {
            // Clear the board.
            board.clear();
            pots.clear();

            // Determine the active players.
            activePlayers.clear();
            for (Player player : players) {
                player.resetHand();
                // Player must be able to afford at least the big blind.
                if (player.getCash() >= bigBlind) {
                    activePlayers.add(player);
                }
            }

            // Rotate the dealer button.
            dealerPosition = (dealerPosition + 1) % activePlayers.size();
            dealer = activePlayers.get(dealerPosition);

            // Shuffle the deck.
            deck.shuffle();

            // Determine the first player to act.
            actorPosition = dealerPosition;
            actor = activePlayers.get(actorPosition);

            // Set the initial bet to the big blind.
            minBet = bigBlind;
            bet = minBet;

            System.out.println("\nNew hand, " + dealer + " is the dealer.\n\n");
        }

        /**
         * Rotates the position of the player in turn (the actor).
         */
        private void rotateActor() {
            actorPosition = (actorPosition + 1) % activePlayers.size();
            actor = activePlayers.get(actorPosition);
        }

        /**
         * Posts the small blind.
         */
        private void postSmallBlind() {
            final int smallBlind = bigBlind / 2;
            actor.postSmallBlind(smallBlind);
            contributePot(smallBlind);
            System.out.println(actor.getName() + " posts the small blind.");
        }

        /**
         * Posts the big blind.
         */
        private void postBigBlind() {
            actor.postBigBlind(bigBlind);
            contributePot(bigBlind);
            System.out.println(actor.getName() + " posts the big blind.");

        }

        /**
         * Contributes to the pot.
         *
         * @param amount The amount to contribute.
         */
        private void contributePot(int amount) {
            for (Pot pot : pots) {
                if (!pot.hasContributer(actor)) {
                    int potBet = pot.getBet();
                    if (amount >= potBet) {
                        // Regular call, bet or raise.
                        pot.addContributer(actor);
                        amount -= pot.getBet();
                    } else {
                        // Partial call (all-in); redistribute pots.
                        pots.add(pot.split(actor, amount));
                        amount = 0;
                    }
                }
                if (amount <= 0) {
                    break;
                }
            }
            if (amount > 0) {
                Pot pot = new Pot(amount);
                pot.addContributer(actor);
                pots.add(pot);
            }
        }

        /**
         * Deals a number of community cards.
         *
         * @param phaseName The name of the phase.
         * @param noOfCards The number of cards to deal.
         */
        private void dealCommunityCards(String phaseName, int noOfCards) {
            for (int i = 0; i < noOfCards; i++) {
                board.add(deck.deal());
            }
            System.out.println("\n" + dealer + " deals the " + phaseName + ".\n");
            System.out.println(board);
        }

        /**
         * Deals the Hole Cards.
         */
        private void dealHoleCards() {
            for (Player player : activePlayers) {
                player.setCards(deck.deal(2));
            }
            System.out.println("\n" + dealer + " deals the hole cards.");
        }
    }

}
