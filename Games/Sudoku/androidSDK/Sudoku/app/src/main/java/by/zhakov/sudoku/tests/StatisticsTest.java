package by.zhakov.sudoku.tests;

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import by.zhakov.sudoku.R;
import by.zhakov.sudoku.activities.StatisticsActivity;
import by.zhakov.sudoku.controllers.DatabaseController;
import by.zhakov.sudoku.model.RecordsInfo;

public class StatisticsTest extends ActivityInstrumentationTestCase2<StatisticsActivity> {
    private Activity activity;
    private TextView textEasy;
    private TextView textMedium;
    private TextView textHard;
    private Button btnClear;
    private DatabaseController dbc = new DatabaseController();
    private Context appContext;
    RecordsInfo infoEasy;
    RecordsInfo infoMedium;
    RecordsInfo infoHard;

    public StatisticsTest(){
        super(StatisticsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        textEasy = (TextView) activity.findViewById(R.id.text_easy);
        textMedium = (TextView) activity.findViewById(R.id.text_medium);
        textHard = (TextView) activity.findViewById(R.id.text_hard);
        btnClear = (Button) activity.findViewById(R.id.clear_statistics);
        appContext = activity.getApplicationContext();
    }

    public void testControlsCreated() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        assertNotNull(activity);
        assertNotNull(textEasy);
        assertNotNull(textMedium);
        assertNotNull(textHard);
        assertNotNull(btnClear);
    }

    public void testVisible() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        ViewAsserts.assertOnScreen(btnClear.getRootView(), btnClear);
        ViewAsserts.assertOnScreen(textEasy.getRootView(), textEasy);
        ViewAsserts.assertOnScreen(textMedium.getRootView(), textMedium);
        ViewAsserts.assertOnScreen(textHard.getRootView(), textHard);
    }

    public void testTextSet() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        assertTrue(!textEasy.getText().equals("New Text"));
        assertTrue(!textMedium.getText().equals("New Text"));
        assertTrue(!textHard.getText().equals("New Text"));
    }

    public void testResultsGain(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        updateInfo();
        assertNotNull(infoEasy);
        assertNotNull(infoMedium);
        assertNotNull(infoHard);
    }

    public void testCleaning() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        dbc.putRecord(1000, "Easy", appContext);
        dbc.putRecord(1000, "Medium", appContext);
        dbc.putRecord(1000, "Hard", appContext);
        dbc.clearDB(appContext);
        updateInfo();
        assertTrue(infoEasy.getSize() == 0);
        assertTrue(infoMedium.getSize() == 0);
        assertTrue(infoHard.getSize() == 0);
    }

    public void testAverageTime(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        dbc.clearDB(appContext);
        dbc.putRecord(1000, "Easy", appContext);
        updateInfo();
        dbc.putRecord(2000, "Easy", appContext);
        updateInfo();
        int after = infoEasy.getAvgTime();
        assertTrue(after == 1500);
    }

    public void testCleaningButton(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        TouchUtils.tapView(this, btnClear);
        updateInfo();
        assertTrue(infoEasy.getSize() == 0);
        assertTrue(infoMedium.getSize() == 0);
        assertTrue(infoHard.getSize() == 0);
    }

    public void testAddRecord() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        dbc.clearDB(appContext);
        dbc.putRecord(1000, "Easy", appContext);
        dbc.putRecord(1000, "Medium", appContext);
        dbc.putRecord(1000, "Hard", appContext);
        updateInfo();
        assertTrue(infoEasy.getSize() == 1);
        assertTrue(infoMedium.getSize() == 1);
        assertTrue(infoHard.getSize() == 1);
    }

    public void testMaxRecordsCount() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        dbc.clearDB(appContext);
        for (int i = 0; i < 6; i++){
            dbc.putRecord(1000, "Easy", appContext);
        }
        for (int i = 0; i < 6; i++){
            dbc.putRecord(1000, "Medium", appContext);
        }
        for (int i = 0; i < 6; i++){
            dbc.putRecord(1000, "Hard", appContext);
        }
        updateInfo();
        assertTrue(infoEasy.getSize() == 5);
        assertTrue(infoMedium.getSize() == 5);
        assertTrue(infoHard.getSize() == 5);
        dbc.clearDB(appContext);
    }

    private void updateInfo(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        infoEasy = dbc.getRecordsInfo("Easy", appContext);
        infoMedium = dbc.getRecordsInfo("Medium", appContext);
        infoHard = dbc.getRecordsInfo("Hard", appContext);
    }
}
