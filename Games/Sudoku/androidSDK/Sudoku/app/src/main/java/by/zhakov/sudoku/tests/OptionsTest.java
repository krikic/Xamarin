package by.zhakov.sudoku.tests;

import android.app.Activity;
import android.app.Fragment;
import android.preference.ListPreference;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;

import java.util.concurrent.TimeUnit;

import by.zhakov.sudoku.R;
import by.zhakov.sudoku.activities.OptionsActivity;

/**
 * Created by Aleksei on 19.05.14.
 */
public class OptionsTest extends ActivityInstrumentationTestCase2<OptionsActivity> {
    private Activity activity;
    private Fragment fragment;

    public OptionsTest() {
        super(OptionsActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        fragment = activity.getFragmentManager().findFragmentByTag("OptionsFragment");
    }

    public void testControlsCreated() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        assertNotNull(activity);
        assertNotNull(fragment);
    }

    public void testVisible() throws Exception {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }
        ViewAsserts.assertOnScreen(fragment.getView().getRootView(), fragment.getView());
    }
}
