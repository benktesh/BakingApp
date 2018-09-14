package com.example.benktesh.bakingapp;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import com.example.benktesh.bakingapp.Ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainViewTest {
    @Rule public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    @Test
    public void openMain() {
        onView(withId(R.id.card_grid_view)).check(matches(hasDescendant(withText("Brownies"))));
    }

}
