package by.zhakov.sudoku.tests;

import android.app.Activity;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import by.zhakov.sudoku.R;
import by.zhakov.sudoku.activities.GameActivity;
import by.zhakov.sudoku.controllers.GameController;

public class FieldTest extends ActivityInstrumentationTestCase2<GameActivity> {
    private Activity activity;
    private View field;
    private View textTime;
    private ArrayList<View> btnNumbers = new ArrayList<View>();

    public FieldTest(){
        super(GameActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        field = activity.findViewById(R.id.game_field);
        textTime = activity.findViewById(R.id.time_text_view);
        btnNumbers.add(activity.findViewById(R.id.button_0));
        btnNumbers.add(activity.findViewById(R.id.button_1));
        btnNumbers.add(activity.findViewById(R.id.button_2));
        btnNumbers.add(activity.findViewById(R.id.button_3));
        btnNumbers.add(activity.findViewById(R.id.button_4));
        btnNumbers.add(activity.findViewById(R.id.button_5));
        btnNumbers.add(activity.findViewById(R.id.button_6));
        btnNumbers.add(activity.findViewById(R.id.button_7));
        btnNumbers.add(activity.findViewById(R.id.button_8));
        btnNumbers.add(activity.findViewById(R.id.button_9));
    }

    public void testControlsCreated() {

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        assertNotNull(activity);
        assertNotNull(field);
        assertNotNull(textTime);
        for (View btn : btnNumbers){
            assertNotNull(btn);
        }
    }

    public void testVisible() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        ViewAsserts.assertOnScreen(field.getRootView(), field);
        ViewAsserts.assertOnScreen(textTime.getRootView(), textTime);
        for (View btn : btnNumbers){
            ViewAsserts.assertOnScreen(btn.getRootView(), btn);
        }
    }

    public void testSize(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        assertEquals(field.getHeight(), field.getWidth());
    }

    public void testTimerChanges() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        String before = ((TextView)textTime).getText().toString();
        TimeUnit.SECONDS.sleep(1);
        String after = ((TextView)textTime).getText().toString();
        assertTrue(!before.equals(after));
    }

    public void testFieldReadableSize(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        assertTrue(field.getHeight() > 100);
        assertTrue(field.getWidth() > 100);
    }

    public void testInitialValues() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        assertNotNull(GameController.getInstance().getInitialNumber());
    }

    public void testBackground(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        assertNotNull(field.getBackground());
    }
}
