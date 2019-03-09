package by.zhakov.sudoku.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import by.zhakov.sudoku.R;
import by.zhakov.sudoku.controllers.GameController;

public class MainMenuActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    }

    public void onButtonClick(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.new_game_button:
                intent = new Intent(this, GameActivity.class);
                break;
            case R.id.options_button:
                intent = new Intent(this, OptionsActivity.class);
                break;
            case R.id.statistics_button:
                intent = new Intent(this, StatisticsActivity.class);
                break;
        }
        if (intent != null){
            startActivity(intent);
        }
    }
}
