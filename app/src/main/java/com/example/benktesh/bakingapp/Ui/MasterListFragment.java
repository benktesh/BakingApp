package com.example.benktesh.bakingapp.Ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.benktesh.bakingapp.Model.Recipe;
import com.example.benktesh.bakingapp.R;
import com.example.benktesh.bakingapp.Utils.NetworkUtilities;

import java.util.ArrayList;

// This fragment displays all of the recipe list in one large list
// The list appears as a grid of images
public class MasterListFragment extends Fragment {

    private static final String TAG = MasterListFragment.class.getSimpleName();
    ArrayList<Recipe> mRecipies = null;
    MasterListAdapter mAdapter;
    GridView gridView;


    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnRecipeClickListener mCallback;

    private Context context = getContext();

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnRecipeClickListener {
        void onRecipeSelected(Recipe recipe);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }


    // Mandatory empty constructor
    public MasterListFragment() {

    }

    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        // Get a reference to the GridView in the fragment_master_list xml layout file
        gridView = rootView.findViewById(R.id.card_grid_view);

        mAdapter = new MasterListAdapter(context, mRecipies);

        new NetworkTaskAsync().execute();


        // Set the adapter on the GridView
        gridView.setAdapter(mAdapter);

        // Set a click listener on the gridView and trigger the callback onImageSelected when an item is clicked
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Trigger the callback method and pass in the position that was clicked
                Recipe recipe = mRecipies.get(position);
                mCallback.onRecipeSelected(recipe);
            }
        });

        // Return the root view
        return rootView;
    }


    class NetworkTaskAsync extends AsyncTask<Void, Void, ArrayList<Recipe>> {


        @Override
        protected ArrayList<Recipe> doInBackground(Void... voids) {
            Log.d(TAG, "NetworkTask: doInBackground");

            ArrayList<Recipe> data = null;
            try {
                data = NetworkUtilities.getRecipes(context);

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> data) {
            if (data != null) {
                mRecipies = data;
                mAdapter = new MasterListAdapter(context, mRecipies);
                mAdapter.notifyDataSetChanged();
                // Set the adapter on the GridView
                //gridView.invalidateViews();
                gridView.setAdapter(mAdapter);


            } else {
                Toast.makeText(context, R.string.Recipe_read_error_msg, Toast.LENGTH_LONG).show();
            }
        }
    }
}