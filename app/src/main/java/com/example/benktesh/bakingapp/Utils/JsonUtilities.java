package com.example.benktesh.bakingapp.Utils;

import android.util.Log;

import com.example.benktesh.bakingapp.Model.Ingredient;
import com.example.benktesh.bakingapp.Model.Recipe;
import com.example.benktesh.bakingapp.Model.Step;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("SpellCheckingInspection")
class JsonUtilities {

    private static final String TAG = JsonUtilities.class.getSimpleName();

    public static ArrayList<Recipe> parseRecipeJson(String json) {

        Log.d(TAG, " entering parseReceipeJson " + json);

        Recipe recipe;
        ArrayList<Recipe> recipies = new ArrayList<>();

        try {

            JSONArray recipeArray = new JSONArray(json);

            int len = recipeArray.length();
            for (int i = 0; i < len; i++) {
                recipe = new Recipe();

                JSONObject recipeObject = recipeArray.getJSONObject(i);

                recipe.id = recipeObject.optInt("id", 0);
                recipe.name = recipeObject.optString("name", "");
                recipe.servings = recipeObject.optInt("servings", 0);
                recipe.image = recipeObject.optString("image", "");

                JSONArray ingredientsArray = new JSONArray(recipeObject.optString("ingredients",
                        "[\"\"]"));
                List<Ingredient> ingredients = new ArrayList<>();
                Ingredient ingredient;
                for (int j = 0; j < ingredientsArray.length(); j++) {
                    ingredient = new Ingredient();

                    JSONObject ingredientObject = ingredientsArray.getJSONObject(j);
                    ingredient.quantity = ingredientObject.optDouble("quantity", 0);
                    ingredient.ingredient = ingredientObject.optString("ingredient", "");
                    ingredient.measure = ingredientObject.optString("measure", "");
                    ingredients.add(ingredient);
                }
                recipe.ingredients = ingredients;


                JSONArray stepsArray = new JSONArray(recipeObject.optString("steps",
                        "[\"\"]"));

                List<Step> steps = new ArrayList<>();
                Step step;
                for (int j = 0; j < stepsArray.length(); j++) {
                    step = new Step();

                    JSONObject object = stepsArray.getJSONObject(j);
                    step.id = object.optInt("id", 0);
                    step.shortDescription = object.optString("shortDescription", "");
                    step.description = object.optString("description", "");
                    step.videoURL = object.optString("videoURL", "");
                    step.thumbnailURL = object.optString("thumbnailURL", "");
                    steps.add(step);
                }
                recipe.steps = steps;

                recipies.add(recipe);

            }

            Log.d(TAG, " exiting parseReceipeJson - results " + recipies.get(0).toString());

            return recipies;

        } catch (Exception ex) {
            Log.e(TAG + " parseRecipeJson", "Could not parse json " + ex.toString());
            return null;
        }
    }

    /*
    public static List<MovieReview> parseMovieReviewJson(String json) {
        try {
            MovieReview review;
            JSONObject object = new JSONObject(json);

            JSONArray resultsArray = new JSONArray(object.optString("results",
                    "[\"\"]"));

            //    {"id":19404,"page":1,"results":[{"author":"MohamedElsharkawy","content":"The Dilwale Dulhania Le Jayenge is a film considered by most to be one of the greatest ever made. From The American Film Institute to as voted by users on the Internet Movie Database (IMDB) it is consider to be one of the best.","id":"59eb3d42925141565100e901","url":"https://www.themoviedb.org/review/59eb3d42925141565100e901"}],"total_pages":1,"total_results":1}
            System.out.println(resultsArray.toString());

            ArrayList<MovieReview> items = new ArrayList<>();
            for (int i = 0; i < resultsArray.length(); i++) {
                String current = resultsArray.optString(i, "");
                JSONObject reviewJson = new JSONObject(current);

                String content = reviewJson.optString("content", "Not Available");
                String author = reviewJson.optString("author", "Not Available");

                review = new MovieReview(author, content);
                items.add(review);
            }
            return items;

        } catch (Exception ex) {
            Log.e(TAG + "parseMovieJson", "Could not parse json " + json);
            return null;
        }

    }

    public static List<MovieVideo> parseMovieVideoJson(String json) {
        try {
            MovieVideo movieVideo;
            JSONObject object = new JSONObject(json);

            JSONArray resultsArray = new JSONArray(object.optString("results",
                    "[\"\"]"));

            //{"id":19404,
            // "results":[
            // {"id":"5581bd68c3a3685df70000c6","iso_639_1":"en","iso_3166_1":"US","key":"c25GKl5VNeY","name":"Dilwale Dulhania Le Jayenge | Official Trailer | Shah Rukh Khan | Kajol","site":"YouTube","size":720,"type":"Trailer"},{"id":"58e9bfb6925141351f02fde0","iso_639_1":"en","iso_3166_1":"US","key":"Y9JvS2TmSvA","name":"Mere Khwabon Mein - Full Song | Dilwale Dulhania Le Jayenge | Shah Rukh Khan | Kajol","site":"YouTube","size":720,"type":"Clip"},{"id":"58e9bf11c3a36872ee070b9a","iso_639_1":"en","iso_3166_1":"US","key":"H74COj0UQ_Q","name":"Zara Sa Jhoom Loon Main - Dilwale Dulhania Le Jayenge (1995) 720p HD","site":"YouTube","size":720,"type":"Clip"},{"id":"58e9c00792514152ac020a34","iso_639_1":"en","iso_3166_1":"US","key":"OkjXMqK1G0o","name":"Ho Gaya Hai Tujhko Toh Pyar Sajna - Full Song - Dilwale Dulhania Le Jayenge","site":"YouTube","size":720,"type":"Clip"},{"id":"58e9c034c3a36872ee070c84","iso_639_1":"en","iso_3166_1":"US","key":"7NhoeyoR_XA","name":"Mehandi Laga Ke Rakhna - Dilwale Dulhaniya Le Jayenge (Full HD 1080p)","site":"YouTube","size":720,"type":"Clip"},{"id":"58e9c07f9251414b2802a16e","iso_639_1":"en","iso_3166_1":"US","key":"Ee-cCwP7VPQ","name":"Tujhe Dekha To (Dilwale Dulhania Le Jaayenge) Piano Cover feat. Aakash Gandhi","site":"YouTube","size":480,"type":"Clip"}]}
            System.out.println(resultsArray.toString());

            ArrayList<MovieVideo> items = new ArrayList<>();
            for (int i = 0; i < resultsArray.length(); i++) {
                String current = resultsArray.optString(i, "");
                JSONObject reviewJson = new JSONObject(current);

                String id = reviewJson.optString("id", "Not Available");
                String key = reviewJson.optString("key", "Not Available");

                movieVideo = new MovieVideo(id, key);
                items.add(movieVideo);
            }
            return items;

        } catch (Exception ex) {
            Log.e(TAG + "parseMovieJson", "Could not parse json " + json);
            return null;
        }

    }
    */
}
