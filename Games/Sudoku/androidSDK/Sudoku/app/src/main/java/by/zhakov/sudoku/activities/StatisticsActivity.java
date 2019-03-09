package by.zhakov.sudoku.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import by.zhakov.sudoku.R;
import by.zhakov.sudoku.controllers.DatabaseController;
import by.zhakov.sudoku.model.RecordsInfo;

/**
 * Created by Aleksei on 14.05.14.
 */
public class StatisticsActivity extends Activity {


    @Override
    protected void onResume() {
        super.onResume();
        updateRecords();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        updateRecords();
    }

    public void onClear(View v){
        new DatabaseController().clearDB(getApplicationContext());
        updateRecords();
    }

    private void updateRecords(){
        RecordsInfo easy = new DatabaseController().getRecordsInfo("Easy", getApplicationContext());
        RecordsInfo medium = new DatabaseController().getRecordsInfo("Medium", getApplicationContext());
        RecordsInfo hard = new DatabaseController().getRecordsInfo("Hard", getApplicationContext());

        ((TextView)findViewById(R.id.text_easy)).setText(easy.toString());
        ((TextView)findViewById(R.id.text_medium)).setText(medium.toString());
        ((TextView)findViewById(R.id.text_hard)).setText(hard.toString());
    }
}
