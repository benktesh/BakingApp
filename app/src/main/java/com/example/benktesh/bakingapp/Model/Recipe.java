package com.example.benktesh.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {

    public int id;
    public String name;
    public List<Ingredient> ingredients;
    public List<Step> steps;
    public int servings;
    public String image;

    private Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public Recipe() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getIngredient() {
        String result = "";

        int index = 0;
        if (ingredients != null && ingredients.size() > 0) {


            for (Ingredient ing : ingredients) {
                index++;
                if (result.length() > 0) {
                    result = result + ", ";
                }
                result = result + ing.ingredient + " - " +
                        String.valueOf(ing.quantity) + " " + ing.measure + "";

            }
        }
        return result;
    }

    public String toString() {
        return "name(id): " + name + "(" + id + ")";
    }

    public String getIngredientsForWidget() {

        if (ingredients == null || ingredients.size() <= 0)
            return "";
        String result = "";
        for (Ingredient ing : ingredients) {
            result = result + " " + ing.ingredient + " (" + String.valueOf(ing.quantity) + " " + ing.measure + "); ";
        }
        return result;
    }

}

