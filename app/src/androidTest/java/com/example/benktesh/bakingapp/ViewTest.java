package com.example.benktesh.bakingapp;

import android.support.test.rule.ActivityTestRule;

import com.example.benktesh.bakingapp.Ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;


public class ViewTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void openMain() {
        onView(withId(R.id.card_grid_view)).check(matches(hasDescendant(withText("Brownies"))));
    }

    @Test
    public void openMainNotFalsePositive() {
        onView(withId(R.id.card_grid_view)).check(matches(not(hasDescendant(withText("Browniesx")))));
    }

    @Test
    public void openRecipe() {
        onData(anything()).inAdapterView(withId(R.id.card_grid_view)).atPosition(1).perform(click());
        //ensure recipe list exists
        onView(withId(R.id.recipe_linear_layout)).check(matches(hasDescendant(withText("Ingredients"))));
        onView(withId(R.id.recipe_linear_layout)).check(matches(hasDescendant(withText("Steps"))));
        onView(withId(R.id.recipe_linear_layout)).check(matches(hasDescendant(withText("Step 1: Starting prep"))));
    }


}
