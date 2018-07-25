package com.example.benktesh.bakingapp.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.benktesh.bakingapp.Model.Step;
import com.example.benktesh.bakingapp.R;

import static com.example.benktesh.bakingapp.Utils.Helper.CURRENT_STEP;
import static com.example.benktesh.bakingapp.Utils.Helper.CURRENT_STEP_RECIPE;

public class StepActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        TextView instruction = (TextView) findViewById(R.id.tv_step_instruction);
        Intent intent = getIntent();
        Step step = intent.getParcelableExtra(CURRENT_STEP);
        String title = intent.getStringExtra(CURRENT_STEP_RECIPE);
        setTitle(title);
        instruction.setText(step.description);
    }
}