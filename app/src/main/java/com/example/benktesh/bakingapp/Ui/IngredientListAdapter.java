package com.example.benktesh.bakingapp.Ui;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.benktesh.bakingapp.Model.Ingredient;

import java.util.List;

public class IngredientListAdapter extends BaseAdapter {

    private String TAG = IngredientListAdapter.class.getSimpleName();

    // Keeps track of the context and list of images to display
    private Context mContext;
    private List<Ingredient> mItems;


    public IngredientListAdapter(Context context, List<Ingredient> ingredients) {
        mContext = context;
        mItems = ingredients;
    }

    /**
     * Returns the number of items the adapter will display
     */
    @Override
    public int getCount() {
        int count = mItems == null ? 0 : mItems.size();
        Log.d(TAG, "getCount: " + count);
        return count;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Creates a new ImageView for each item referenced by the adapter
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {
            // If the view is not recycled, this creates a new ImageView to hold an image
            view = new TextView(mContext);

        } else {
            view = (TextView) convertView;
        }

        // Set the image resource and return the newly created ImageView
        view.setText(mItems.get(position).ingredient);
        return view;
    }
}
