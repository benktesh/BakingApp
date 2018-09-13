package com.example.benktesh.bakingapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.benktesh.bakingapp.Model.Recipe;
import com.example.benktesh.bakingapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Benktesh on 5/1/18.
 * Some of the resource for this file were created from Udacity provided content for the students in Google Challenge Scholar's Exercise 2.
 */

//TODO - this is from old code. Needs clearnup perhaps rename

public class NetworkUtilities {
    private static final String TAG = NetworkUtilities.class.getSimpleName();


    /*
    This method returns the list of Recipie from json
     */
    public static ArrayList<Recipe> getRecipes(Context context) {
        Log.d(TAG, "Calling context: " + context);

        String jsonText = null;

        if (!isOnline(context) && false) {
            Log.e(TAG, "There is no network connection. Using local file");
            jsonText = readLocalFile(context);

        }
        else {
            //read from network
            Log.e(TAG, "There is network connection. Getting data from network");

            Uri builtUri = Uri.parse(context.getString(R.string.recipe_URL));
            URL url =  getUrl(builtUri);
            try {
                jsonText =  getResponseFromHttpUrl(getUrl(builtUri), context);

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, Arrays.toString(e.getStackTrace()));
            }
            finally {
                if(jsonText == null) {
                    Log.e(TAG, "Failed get receipe from network. Using local file");
                    jsonText = readLocalFile(context);
                }
            }

        }
        if(jsonText == null) {

            return null;
        }
        Log.d(TAG, jsonText);
        return JsonUtilities.parseRecipeJson(jsonText);

    }

    private static String readLocalFile(Context context) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets()
                    .open(context.getString(R.string.local_recipe_file))));
            String mLine;
            while((mLine = reader.readLine()) != null){
                sb.append(mLine);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                    Log.d(TAG, "readLocalFile: Done reading local file");
                } catch (IOException e) {
                    Log.e(TAG, "IO Error occured while reading local file");
                }
            }
        }
        return sb.toString();

    }

    @Nullable
    private static URL getUrl(Uri builtUri) {
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url, Context context) throws IOException {
        if (!isOnline(context)) {
            Log.e(TAG, "There is no network connection");
            return null;
        }
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(10000); //sets connection timeout to 10 seconds
        urlConnection.setReadTimeout(20000); //sets read time out to 20 seconds
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                scanner.close();
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * This method checks network connection. This code was derived from
     * https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
     **/
    private static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
