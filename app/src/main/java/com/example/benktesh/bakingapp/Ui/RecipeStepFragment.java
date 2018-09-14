package com.example.benktesh.bakingapp.Ui;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class RecipeStepFragment extends Fragment {

    private static  String TAG = RecipeStepFragment.class.getSimpleName();

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
        mRootView =  inflater.inflate(R.layout.fragment_recipe, container, false);

        mPlayerView = (PlayerView) mRootView.findViewById(R.id.playerView);

        Log.d(TAG, "onCreateView is step null?: " + mStep == null ? " YES" : " NO");

        if(mStep != null ) {
            initializePlayer();
        }
        return mRootView;

    }

    public void reloadView() {

        TextView instruction = (TextView) mRootView.findViewById(R.id.tv_step_instruction);
        //setTitle(mTitle);
        if(instruction != null){
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

    public void initializePlayer() {

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            reloadView();
        }
    }

    private void destroyPlayer(){
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    public void setSteps(ArrayList<Step> mSteps) {
        this.mSteps = mSteps;
    }

    public void setStep(Step mStep) {
        this.mStep = mStep;
    }

    public void setStepIndex(int mStepIndex) {
        this.mStepIndex = mStepIndex;
    }

}
