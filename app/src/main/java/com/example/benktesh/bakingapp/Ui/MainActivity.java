package com.example.benktesh.bakingapp.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.benktesh.bakingapp.Model.Recipe;
import com.example.benktesh.bakingapp.R;
import com.example.benktesh.bakingapp.Ui.MasterListFragment.OnRecipeClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnRecipeClickListener {

    private final String TAG = MainActivity.class.getSimpleName();

    public static String CURRENT_RECIPE = "RECIPE";

    public ArrayList<Recipe> mRecipies = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        if (findViewById(R.id.recipe_linear_layout) != null) {
//            isTwoPane = false;
//            GridView gridView = (GridView) findViewById(R.id.card_grid_view);
//            gridView.setNumColumns(1);
//
//            //Button nextButton = (Button) findViewById(R.id.next_button);
//            //nextButton.setVisibility(View.GONE);
//
//           // if (savedInstanceState == null) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//
//                RecipeCardFragment recipeCardFragment = new RecipeCardFragment();
//
//                // Create the adapter
//                // This adapter takes in the context and an ArrayList
//            mRecipies = NetworkUtilities.getRecipes(getApplicationContext());
//
//               // new NetworkTask().execute();
//
//                recipeCardFragment.setRecipes(mRecipies);
//
//                fragmentManager.beginTransaction()
//                        .add(R.id.head_container, recipeCardFragment)
//                        .commit();
//        //    }
//
//
//        }

    }



    @Override
    public void onRecipeSelected(Recipe recipe) {
//get receipt

        //Toast.makeText(getApplicationContext(), "hello clicked " + recipe.name, Toast.LENGTH_LONG).show();

        // Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
        Bundle b = new Bundle();

        // Attach the Bundle to an intent
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(CURRENT_RECIPE, recipe);
        startActivity(intent);
    }


}
