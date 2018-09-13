package com.example.benktesh.bakingapp.Ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.benktesh.bakingapp.Model.Ingredient;
import com.example.benktesh.bakingapp.Model.Recipe;
import com.example.benktesh.bakingapp.Model.Step;
import com.example.benktesh.bakingapp.R;
import com.example.benktesh.bakingapp.Utils.Helper;
import com.example.benktesh.bakingapp.databinding.ActivityRecipeBinding;

import java.util.ArrayList;
import java.util.List;

import static com.example.benktesh.bakingapp.Ui.MainActivity.CURRENT_RECIPE;

public class RecipeActivity extends AppCompatActivity implements RecipeStepAdapter.ListStepClickListener {

    private final String TAG = RecipeActivity.class.getSimpleName();

    private ActivityRecipeBinding mBinding;

    public RecipeStepAdapter mRecipeStepAdapter;
    private Recipe mRecipe;
    private String mRecipeName;
    private boolean mHasTwoPanes = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        //mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);

        View v = findViewById(R.id.step_details); //if the step details is in this view, then we are in two panes
        if(v != null)
        {
            mHasTwoPanes = true;

            if(savedInstanceState == null) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                RecipeListFragment recipeListFragment = new RecipeListFragment();

                //this is for lists
                fragmentManager.beginTransaction()
                        .add(R.id.step_list, recipeListFragment)
                        .commit();

                RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
                //this is for step details
                fragmentManager.beginTransaction()
                        .add(R.id.step_details, recipeStepFragment)
                       .commit();


            }


        }
        Log.d(TAG, "mHasTwoPanes:  " + mHasTwoPanes);






        Intent intent = getIntent();

        mRecipe = intent.getParcelableExtra("RECIPE");
        mRecipeName  = mRecipe == null ? "" : mRecipe.name;

        List<Ingredient> ingredient = mRecipe.ingredients;
        List<Step> steps = mRecipe.steps;


        RecyclerView stepRecyclerView = findViewById(R.id.rv_steps);
        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(this);
        stepRecyclerView.setLayoutManager(layoutManagerReview);
        stepRecyclerView.setHasFixedSize(false);
        mRecipeStepAdapter = new RecipeStepAdapter(steps, this, this);
        stepRecyclerView.setAdapter(mRecipeStepAdapter);

        //populateUI();

//        RecyclerView mMovieVideoList = findViewById(R.id.rv_movie_trailers);
//        LinearLayoutManager layoutManagerVideo = new LinearLayoutManager(this);
//        mMovieVideoList.setLayoutManager(layoutManagerVideo);
//        mMovieVideoList.setHasFixedSize(false);
//        movieVideoAdapter = new MovieVideoAdapter(movieVideoItems, this, this);
//        mMovieVideoList.setAdapter(movieVideoAdapter);

        //MovieDbHelper movieDbHelper = new MovieDbHelper(this);
        //LoadAdditionalData();
        try {
            populateUI();
        }
        catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }




        // Get a reference to the TextView in the fragment layout



        //TODO Ignore this bock for now
        /*
        // If a list of image ids exists, set the image resource to the correct item in that list
        // Otherwise, create a Log statement that indicates that the list was not found
        if(mImageIds != null){
            // Set the image resource to the list item at the stored index
            tex.setImageResource(mImageIds.get(mListIndex));

            // Set a click listener on the image view
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Increment position as long as the index remains <= the size of the image ids list
                    if(mListIndex < mImageIds.size()-1) {
                        mListIndex++;
                    } else {
                        // The end of list has been reached, so return to beginning index
                        mListIndex = 0;
                    }
                    // Set the image resource to the new list item
                    imageView.setImageResource(mImageIds.get(mListIndex));
                }
            });

        } else {
            Log.v(TAG, "This fragment has a null list of image id's");
        }
        */

        // Return the rootView
        //return rootView;

        //onSaveInstanceState(savedInstanceState);
    }

    // Setter methods for keeping track of the list images this fragment can display and which image
    // in the list is currently being displayed

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelable(CURRENT_RECIPE, mRecipe);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void populateUI() {

        setTitle(mRecipeName);
        TextView measureQantity = findViewById(R.id.ingredient_measure_quantity);
        measureQantity.setText(mRecipe.getIngredient());
        //TODO Make mBinding to Work
        //mBinding.ingredientMeasureQuantity.setText(mRecipe.getIngredient());

    }

    @Override
    public void OnListItemClick(Step step) {

        if(mHasTwoPanes) {
            Log.d(TAG, "OnListItemClick" + step.getStepShortDescription() );

            //do somethign with fragments

        }
        else {
            Intent intent = new Intent(getApplicationContext(), RecipeStepActivity.class);
            //we will pass recipe name, all the steps and current id of the step to intent
            intent.putExtra(Helper.CURRENT_STEP_RECIPE,mRecipeName);
            intent.putParcelableArrayListExtra(Helper.STEPS, (ArrayList<? extends Parcelable>) mRecipe.steps);
            intent.putExtra(Helper.STEP_INDEX, step.id);
            startActivity(intent);

        }

    }
}
