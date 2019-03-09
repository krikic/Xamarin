package by.zhakov.sudoku.activities;

import android.app.Activity;
import android.os.Bundle;

import by.zhakov.sudoku.R;

/**
 * Created by Aleksei on 14.05.14.
 */
public class SolveActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_field);
    }
}
