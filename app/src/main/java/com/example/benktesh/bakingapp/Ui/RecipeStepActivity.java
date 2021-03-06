package com.example.benktesh.bakingapp.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.benktesh.bakingapp.Model.Step;
import com.example.benktesh.bakingapp.R;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.ArrayList;

import static com.example.benktesh.bakingapp.Utils.Helper.STEPS;
import static com.example.benktesh.bakingapp.Utils.Helper.STEP_INDEX;

public class RecipeStepActivity extends AppCompatActivity {

    private final String TAG = RecipeStepActivity.class.getSimpleName();

    private SimpleExoPlayer mExoPlayer;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyPlayer();
    }

    private void destroyPlayer() {
        if (mExoPlayer == null) {
            return;
        }
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        int mStepIndex = intent.getIntExtra(STEP_INDEX, 0);
        ArrayList<Step> mSteps = intent.getParcelableArrayListExtra(STEPS);

        if (savedInstanceState == null) {
            Log.e(TAG, "onCreate: savedInstanceState is null");
            try {
                FragmentManager fragmentManager = getSupportFragmentManager();
                RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
                //this is for step details
                recipeStepFragment.setStepsIndex(mSteps, mStepIndex);
                fragmentManager.beginTransaction()
                        .add(R.id.step_detail_container, recipeStepFragment)
                        .commit();
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
        }
    }
}