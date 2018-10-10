package com.example.benktesh.bakingapp.Utils;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.benktesh.bakingapp.Model.Recipe;
import com.example.benktesh.bakingapp.R;
import com.example.benktesh.bakingapp.Ui.RecipeActivity;
import com.squareup.picasso.Picasso;

import static com.example.benktesh.bakingapp.Ui.MainActivity.CURRENT_RECIPE;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    private static String TAG = BakingAppWidgetProvider.class.getSimpleName();

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int[] appWidgetIds, Recipe recipe) {

        CharSequence ingredients = "";
        if (recipe != null) {
            ingredients = recipe.getIngredientsForWidget();
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        views.setTextViewText(R.id.appwidget_ingredients, ingredients);
        views.setTextViewText(R.id.appwidget_recipe_label, recipe.name);
        // Bitmap image = NetworkUtilities.decodeBase64(recipe.image);

        views.getLayoutId();
        Log.d(TAG, "updateAppWidget: recipe.image = " + recipe.image);
        if (recipe.image != null) {

            if (recipe.image != null && !recipe.image.isEmpty()) {
                Picasso.get()
                        .load(recipe.image)
                        .resize(40,40)
                        .error(R.drawable.bakingimage)
                        .into(views, R.id.widget_image, appWidgetIds);

                //views.setImageViewBitmap(R.id.widget_image, image);
            } else {
                loadDefaultImage(appWidgetIds, views);
            }
        } else {
            // views.setImageViewResource(R.id.widget_image,R.drawable.bakingimage);
            loadDefaultImage(appWidgetIds, views);
        }


        Intent intent = new Intent(context, RecipeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        intent.putExtra(CURRENT_RECIPE, recipe);


        views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);
        // Instruct the widget manager to update the widget

        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private static void loadDefaultImage(int[] appWidgetIds, RemoteViews views) {
        Picasso.get()
                .load(R.drawable.bakingimage)
                .resize(40,40)
                .error(R.drawable.bakingimage)
                .into(views, R.id.widget_image, appWidgetIds);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        updateAppWidget(context, appWidgetManager, appWidgetIds, new Recipe());

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

