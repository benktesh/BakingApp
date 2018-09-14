package com.example.benktesh.bakingapp.Ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.benktesh.bakingapp.Model.Recipe;
import com.example.benktesh.bakingapp.R;

import java.util.List;


// Custom adapter class that displays a list of Android-Me images in a GridView
class MasterListAdapter extends BaseAdapter {

    private final String TAG = MasterListAdapter.class.getSimpleName();

    // Keeps track of the context and list of images to display
    private final Context mContext;
    private final List<Recipe> mRecipies;

    /**
     * Constructor method
     *
     * @param recipies The list of recipies to display
     */
    public MasterListAdapter(Context context, List<Recipe> recipies) {
        mContext = context;
        mRecipies = recipies;
    }

    /**
     * Returns the number of items the adapter will display
     */
    @Override
    public int getCount() {
        int count = mRecipies == null ? 0 : mRecipies.size();
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
     * Creates a new TextView for each item referenced by the adapter
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView view;
        View gridView;
        // if (convertView == null) {


        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.recipe_card, null);
        view = viewMyLayout.findViewById(R.id.tv_recipe_card);

        view.setText(mRecipies.get(position).name);

        return view;
    }

}
