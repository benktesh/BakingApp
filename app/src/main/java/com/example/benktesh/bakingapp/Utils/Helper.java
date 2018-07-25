package com.example.benktesh.bakingapp.Utils;

//Abstracted from https://stackoverflow.com/questions/1086123/string-conversion-to-title-case
public class Helper {
    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    public static String CURRENT_STEP = "Current Step";
    public static String CURRENT_STEP_RECIPE = "Current RECIPE";

}
