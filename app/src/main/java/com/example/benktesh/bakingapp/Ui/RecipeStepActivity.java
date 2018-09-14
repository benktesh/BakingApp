package com.example.benktesh.bakingapp.Ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.benktesh.bakingapp.Model.Step;
import com.example.benktesh.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static com.example.benktesh.bakingapp.Utils.Helper.CURRENT_STEP_RECIPE;
import static com.example.benktesh.bakingapp.Utils.Helper.STEPS;
import static com.example.benktesh.bakingapp.Utils.Helper.STEP_INDEX;

public class RecipeStepActivity extends AppCompatActivity {

    private final String TAG = RecipeStepActivity.class.getSimpleName();

    private SimpleExoPlayer mExoPlayer;
    private ArrayList<Step> mSteps;
    private Step mStep;
    private int mStepIndex;
    private String mTitle;

    private boolean isFullScreen;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyPlayer();
    }

    private void destroyPlayer(){
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        mStepIndex = intent.getIntExtra(STEP_INDEX, 0);
        mTitle = intent.getStringExtra(CURRENT_STEP_RECIPE);
        mSteps = intent.getParcelableArrayListExtra(STEPS);
        mStep = mSteps.get(mStepIndex);

        if(savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            //this is for step details
            recipeStepFragment.setStepsIndex(mSteps, mStepIndex);
            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container, recipeStepFragment)
                    .commit();
        }
    }
}