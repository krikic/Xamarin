package com.example.arek.hateapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Arek on 26.07.2017.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivity_SignInTest {
    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void clickSignInButton_opensPostActivity() throws Exception {
        String loginAndPassword = "ssss";

        onView(withId(R.id.logtxtLogin)).perform(typeText(loginAndPassword));
        onView(withId(R.id.logPassword)).perform(typeText(loginAndPassword));

        onView(withId(R.id.logbtnSignIn))
                .perform(click());


        onView(withId(R.id.sendMessage))
                .check(matches(isDisplayed()));
    }
}
