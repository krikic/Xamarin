package by.zhakov.sudoku.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;

import by.zhakov.sudoku.R;
import by.zhakov.sudoku.controllers.DatabaseController;
import by.zhakov.sudoku.controllers.GameController;
import by.zhakov.sudoku.util.SudokuFileReader;
import by.zhakov.sudoku.views.SudokuFieldView;

public class GameActivity extends Activity{
    private long startTime;
    private long time;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            time = System.currentTimeMillis() - startTime;
            int seconds = (int) (time / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            ((TextView)findViewById(R.id.time_text_view)).setText(String.format("%d:%02d",
                    minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
        SharedPreferences sRef = PreferenceManager.getDefaultSharedPreferences(this);
        sRef.registerOnSharedPreferenceChangeListener(new OptionsListener());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_field);
        startGame();
    }

    public void startGame(){
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(this);
        String diff = sPref.getString("difficultyPref", "");
        InputStream is = null;
        if (diff.equals("Medium")){
            is = getResources().openRawResource(R.raw.medium);
        } else if (diff.equals("Hard")){
            is = getResources().openRawResource(R.raw.hard);
        } else {
            is = getResources().openRawResource(R.raw.easy);
        }
        GameController.getInstance().setInitial(SudokuFileReader.getRandSudoku(is));
        GameController.getInstance().clean();
        findViewById(R.id.game_field).invalidate();
    }

    public void onDigitsPress(View v){
        int num = 0;
        switch (v.getId()){
            case R.id.button_1 : num = 1; break;
            case R.id.button_2 : num = 2; break;
            case R.id.button_3 : num = 3; break;
            case R.id.button_4 : num = 4; break;
            case R.id.button_5 : num = 5; break;
            case R.id.button_6 : num = 6; break;
            case R.id.button_7 : num = 7; break;
            case R.id.button_8 : num = 8; break;
            case R.id.button_9 : num = 9; break;
        }
        ((SudokuFieldView)findViewById(R.id.game_field)).onDigitsPress(num);
        if (GameController.getInstance().isSolved()){
            timerHandler.removeCallbacks(timerRunnable);
            DatabaseController dbc = new DatabaseController();
            SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(this);
            dbc.putRecord(time, sPref.getString("difficultyPref", ""), getApplicationContext());
        }
    }

    public class OptionsListener implements SharedPreferences.OnSharedPreferenceChangeListener{
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            startGame();
        }
    }

}
