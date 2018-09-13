package com.example.benktesh.bakingapp.Ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.benktesh.bakingapp.R;

public class RecipeListFragment extends Fragment {

    public RecipeListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);

        //TextView tv = rootView.findViewById(R.id.ingredient_measure_quantity);
        //tv.setText("HELLO BENKTESH");
        return  rootView;
    }
}
