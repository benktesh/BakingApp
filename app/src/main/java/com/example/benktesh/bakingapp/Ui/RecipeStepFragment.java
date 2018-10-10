package com.example.benktesh.bakingapp.Ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class RecipeStepFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = RecipeStepFragment.class.getSimpleName();

    private PlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private ArrayList<Step> mSteps;
    private Step mStep;
    private int mStepIndex;
    private View mRootView;

    public RecipeStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        mPlayerView = mRootView.findViewById(R.id.playerView);

        if (mStep != null) {
            initializePlayer();
        }
        if (mRootView.findViewById(R.id.activity_step_instruction_buttons) != null) {
            Button buttonPrevious = mRootView.findViewById(R.id.button_previous);
            Button buttonNext = mRootView.findViewById(R.id.button_next);

            buttonPrevious.setOnClickListener(this);
            buttonNext.setOnClickListener(this);
        }
        return mRootView;

    }

    private void reloadView() {

        TextView instruction = mRootView.findViewById(R.id.tv_step_instruction);
        //setTitle(mTitle);
        if (instruction != null) {
            instruction.setText(mStep.description);
        }

        //mExoPlayer.release();
        mExoPlayer.prepare(getMediaSource());
        mExoPlayer.setPlayWhenReady(true);

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

        String userAgent = Util.getUserAgent(getContext(), "BakingApp");
        return new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

    }

    private void initializePlayer() {

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            reloadView();
        }
    }

    public void setStep(Step mStep) {
        this.mStep = mStep;
    }

    public void setStepsIndex(ArrayList<Step> steps, int index) {
        this.mSteps = steps;
        this.mStepIndex = index;
        this.mStep = steps.get(index);

    }

    public void onClick(View v) {
        Log.d(TAG, "onClick: " + v.getId());
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
}
