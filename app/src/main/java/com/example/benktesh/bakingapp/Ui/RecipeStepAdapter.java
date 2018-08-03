package com.example.benktesh.bakingapp.Ui;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.benktesh.bakingapp.Model.Step;
import com.example.benktesh.bakingapp.R;

import java.util.List;


public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepHolder> {

    private static final String TAG = RecipeStepAdapter.class.getSimpleName();
    private List<Step> mItemList; //holds the review items
    private final Context mContext;


    final private ListStepClickListener mOnListItemClick;

    public RecipeStepAdapter(List<Step> steps,
                              Context context, ListStepClickListener listener) {
        mItemList = steps;
        mContext = context;
        mOnListItemClick = listener;

    }

    public interface ListStepClickListener {
        void OnListItemClick(Step step);
    }

    @NonNull
    @Override
    public RecipeStepAdapter.RecipeStepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.recipe_step;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new RecipeStepHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepAdapter.RecipeStepHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }


    class RecipeStepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView steptv;

        RecipeStepHolder(final View view) {
            super(view);

            steptv = view.findViewById(R.id.tv_step_short_description);
            //mPlayerView = view.findViewById(R.id.playerView);
            view.setOnClickListener(this);

        }

        void bind(int listIndex) {

            Step step = mItemList.get(listIndex);
            steptv.setText(step.getStepShortDescription());

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Log.d(TAG, "Clicked Position is: " + clickedPosition);
            Step step = mItemList.get(clickedPosition);
            mOnListItemClick.OnListItemClick(step);
        }
    }

}
