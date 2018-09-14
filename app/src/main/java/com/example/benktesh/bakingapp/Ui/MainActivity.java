package com.example.benktesh.bakingapp.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.benktesh.bakingapp.Model.Recipe;
import com.example.benktesh.bakingapp.R;
import com.example.benktesh.bakingapp.Ui.MasterListFragment.OnRecipeClickListener;

public class MainActivity extends AppCompatActivity implements OnRecipeClickListener {

    private final String TAG = MainActivity.class.getSimpleName();

    public static final String CURRENT_RECIPE = "RECIPE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public void onRecipeSelected(Recipe recipe) {
        //Toast.makeText(getApplicationContext(), "hello clicked " + recipe.name, Toast.LENGTH_LONG).show();

        // Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
        Bundle b = new Bundle();
        // Attach the Bundle to an intent
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(CURRENT_RECIPE, recipe);
        startActivity(intent);
    }


}
