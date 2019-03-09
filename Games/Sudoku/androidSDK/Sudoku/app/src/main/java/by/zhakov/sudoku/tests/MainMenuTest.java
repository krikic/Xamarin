package by.zhakov.sudoku.tests;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import by.zhakov.sudoku.R;
import by.zhakov.sudoku.activities.MainMenuActivity;

public class MainMenuTest extends ActivityInstrumentationTestCase2<MainMenuActivity> {
    private Activity activity;
    private Button btnGame;
    private Button btnOptions;
    private Button btnStatistics;

    public MainMenuTest(){
        super(MainMenuActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        btnGame = (Button) activity.findViewById(R.id.new_game_button);
        btnOptions = (Button) activity.findViewById(R.id.options_button);
        btnStatistics = (Button) activity.findViewById(R.id.statistics_button);
    }

    public void testControlsCreated() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        assertNotNull(activity);
        assertNotNull(btnGame);
        assertNotNull(btnOptions);
        assertNotNull(btnStatistics);
    }

    public void testVisible() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        ViewAsserts.assertOnScreen(btnGame.getRootView(), btnGame);
        ViewAsserts.assertOnScreen(btnOptions.getRootView(), btnOptions);
        ViewAsserts.assertOnScreen(btnStatistics.getRootView(), btnStatistics);
    }
}
