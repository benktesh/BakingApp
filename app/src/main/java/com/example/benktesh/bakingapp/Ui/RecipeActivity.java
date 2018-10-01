package com.example.benktesh.bakingapp.Ui;

import android.content.Intent;
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

    private Recipe mRecipe;
    private String mRecipeName;
    private boolean mHasTwoPanes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        //mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);

        Intent intent = getIntent();

        mRecipe = intent.getParcelableExtra("RECIPE");
        mRecipeName = mRecipe == null ? "" : mRecipe.name;

        List<Ingredient> ingredient =mRecipe != null ? mRecipe.ingredients : null;
        List<Step> steps  = mRecipe != null ? mRecipe.steps : null;


        Log.d(TAG, "Before loading step_detail_container");

        View v = findViewById(R.id.step_detail_container); //if the step details is in this view, then we are in two panes

        Log.d(TAG, "After loading step_detail_container ");

        if (v != null) {
            mHasTwoPanes = true;

            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
                //this is for step details
                fragmentManager.beginTransaction()
                        .add(R.id.step_detail_container, recipeStepFragment)
                        .commit();
            }
        }
        Log.d(TAG, "mHasTwoPanes:  " + mHasTwoPanes);

        RecyclerView stepRecyclerView = findViewById(R.id.rv_steps);
        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(this);
        stepRecyclerView.setLayoutManager(layoutManagerReview);
        stepRecyclerView.setHasFixedSize(false);
        RecipeStepAdapter mRecipeStepAdapter = new RecipeStepAdapter(steps, this, this);
        stepRecyclerView.setAdapter(mRecipeStepAdapter);

        try {
            populateUI();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelable(CURRENT_RECIPE, mRecipe);

    }


    private void populateUI() {

        setTitle(mRecipeName);
        TextView measureQantity = findViewById(R.id.ingredient_measure_quantity);
        measureQantity.setText(mRecipe.getIngredient());
        //TODO Make mBinding to Work
        //mBinding.ingredientMeasureQuantity.setText(mRecipe.getIngredient());
        if (mHasTwoPanes) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            //this is for step details

            if (mRecipe != null) {
                Step defaultStep = mRecipe.steps.get(0);
                Log.d(TAG, "step " + defaultStep.description + " " +
                        defaultStep.getStepShortDescription());
                recipeStepFragment.setStep(defaultStep);
            }

            fragmentManager.beginTransaction()
                    .replace(R.id.step_detail_container, recipeStepFragment)
                    .commit();

        }

    }

    @Override
    public void OnListItemClick(Step step) {
        Log.d(TAG, "Current Step is: " + step.toString());

        if (mHasTwoPanes) {
            Log.d(TAG, "OnListItemClick" + step.getStepShortDescription());

            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            //recipeStepFragment.
            //this is for step details
            recipeStepFragment.setStep(step);
            fragmentManager.beginTransaction()
                    .replace(R.id.step_detail_container, recipeStepFragment)
                    .commit();

        } else {
            Intent intent = new Intent(getApplicationContext(), RecipeStepActivity.class);
            //we will pass recipe name, all the steps and current id of the step to intent
            intent.putExtra(Helper.CURRENT_STEP_RECIPE, mRecipeName);
            intent.putParcelableArrayListExtra(Helper.STEPS, (ArrayList<? extends Parcelable>) mRecipe.steps);
            intent.putExtra(Helper.STEP_INDEX, step.id);
            startActivity(intent);

        }

    }
}
