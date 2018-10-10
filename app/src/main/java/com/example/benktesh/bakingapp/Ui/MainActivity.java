package com.example.benktesh.bakingapp.Ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.benktesh.bakingapp.Model.Recipe;
import com.example.benktesh.bakingapp.R;
import com.example.benktesh.bakingapp.Ui.MasterListFragment.OnRecipeClickListener;
import com.example.benktesh.bakingapp.Utils.BakingAppWidgetProvider;

public class MainActivity extends AppCompatActivity implements OnRecipeClickListener {

    // private final String TAG = MainActivity.class.getSimpleName();

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

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));
        BakingAppWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetIds, recipe);

        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(CURRENT_RECIPE, recipe);
        startActivity(intent);
    }
}
