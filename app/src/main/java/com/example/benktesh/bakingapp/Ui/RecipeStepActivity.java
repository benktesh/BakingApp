package com.example.benktesh.bakingapp.Ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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

public class RecipeStepActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = RecipeStepActivity.class.getSimpleName();

    private PlayerView mPlayerView;
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

        mPlayerView = (PlayerView) findViewById(R.id.playerView);
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.question_mark));

        Intent intent = getIntent();
        mStepIndex = intent.getIntExtra(STEP_INDEX, 0);
        mTitle = intent.getStringExtra(CURRENT_STEP_RECIPE);
        mSteps = intent.getParcelableArrayListExtra(STEPS);
        mStep = mSteps.get(mStepIndex);



        Button buttonPrevious = (Button) findViewById(R.id.button_previous);
        Button buttonNext = (Button) findViewById(R.id.button_next);

        if(findViewById(R.id.activity_step_instruction_buttons) != null) {
            buttonPrevious.setOnClickListener(this);
            buttonNext.setOnClickListener(this);
        }

        initializePlayer();

    }

    @Override
    public void onClick(View v) {
        v.getId();
        if (v.getId() == R.id.button_previous) {
            if (mStepIndex > 0) {
                mStepIndex = mStepIndex - 1;
                mStep = mSteps.get(mStepIndex);
                reloadView();
            } else {
                Log.d(TAG, " onClick : Previous button clicked. Reached the beginning of the steps"
                        + mStepIndex + " ( " + mSteps.size() + ")");
            }
        } else if (v.getId() == R.id.button_next) {
            //Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
            if (mStepIndex < mSteps.size() - 1) {
                mStepIndex = mStepIndex + 1;
                mStep = mSteps.get(mStepIndex);
                reloadView();
            } else {
                Log.d(TAG, " onClick : Next button clicked. Reached the end of the steps"
                        + mStepIndex + " ( " + mSteps.size() + ")");
            }
        }
    }

    public void reloadView() {

        TextView instruction = (TextView) findViewById(R.id.tv_step_instruction);
        setTitle(mTitle);
        if(instruction != null){
            instruction.setText(mStep.description);
        }


        //mExoPlayer.release();
        mExoPlayer.prepare(getMediaSource());
        mExoPlayer.setPlayWhenReady(true);

    }

    public void initializePlayer() {

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            reloadView();

        }
    }

    private MediaSource getMediaSource() {
        Uri mediaUri = null;
        if (mStep != null) {
            if (mStep.videoURL.length() > 0) {
                mediaUri = Uri.parse(mStep.videoURL);
            } else if (mStep.thumbnailURL.length() > 0) {
                mediaUri = Uri.parse(mStep.thumbnailURL);
            }
        }

        String userAgent = Util.getUserAgent(this, "BakingApp");
        return new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                this, userAgent), new DefaultExtractorsFactory(), null, null);

    }
}