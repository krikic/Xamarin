package com.example.arek.hateapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Arek on 26.07.2017.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivity_SignUpTest {

    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void clickSignUpButton_opensSignUp() throws Exception {
        onView(withId(R.id.logbtnSignUp))
                .perform(click());
        onView(withId(R.id.txtLogin))
                .check(matches(isDisplayed()));
    }
}
