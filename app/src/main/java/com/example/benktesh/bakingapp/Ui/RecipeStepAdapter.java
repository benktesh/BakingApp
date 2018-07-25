package com.example.benktesh.bakingapp.Ui;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benktesh.bakingapp.Model.Step;
import com.example.benktesh.bakingapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
        final ImageView imageView;

        RecipeStepHolder(final View view) {
            super(view);

            steptv = view.findViewById(R.id.tv_step_short_description);
            imageView = view.findViewById(R.id.iv_step_thumbnail);
            view.setOnClickListener(this);



        }

        void bind(int listIndex) {

            Step step = mItemList.get(listIndex);
            steptv.setText(step.getStepShortDescription());


            try {
                if(step.thumbnailURL.length() > 0) {
                    Picasso.get()
                            .load(step.thumbnailURL)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });
                }
            }catch(IllegalArgumentException e)
            {
               // holder.stepImage.setImageResource(R.drawable.ic_steps);
            }


        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            // mOnClickListener.OnListItemClick(mItemList.get(clickedPosition));
            Step step = mItemList.get(clickedPosition);
            mOnListItemClick.OnListItemClick(step);
        }
    }

}
