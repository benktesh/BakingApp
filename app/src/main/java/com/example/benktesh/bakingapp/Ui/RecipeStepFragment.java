package com.example.benktesh.bakingapp.Ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benktesh.bakingapp.R;

public class RecipeStepFragment extends Fragment {

    public RecipeStepFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_step, container, false);
    }

}
