package com.example.ran.moviesapp.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.ran.moviesapp.Data.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dania on 4/2/2018.
 * Updated on March 27, 2019
 */

public class JsonUtils {

    private static final String LOG_TAG = "JsonUtils";
    private static final String RESULTS_KEY = "results";
    private static final String POSTER_KEY = "poster_path";


    public static ArrayList<MovieModel> extractFeatureFromJson(String movieJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding movies to
        ArrayList<MovieModel> movies = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(movieJSON);

            //Check if the JSON array associated with the key results exists
            if (baseJsonResponse.has(RESULTS_KEY)) {

                // Extract the JSONArray associated with the key called "results",
                // which represents a list of movies (where every movie is a JSON object inside the array)
                JSONArray moviesArray = baseJsonResponse.getJSONArray(RESULTS_KEY);

                // For each movie in the movies JSON array, create a JSON object
                for (int i = 0; i < moviesArray.length(); i++) {

                    // Get a single movie at position i within the list of movies
                    JSONObject currentmovie = moviesArray.getJSONObject(i);

                    //TODO 13: Get the poster of the movie from the current movie JSON object

                    MovieModel movie = new MovieModel(poster);

                    //TODO 14: Add the new movie object created above to the list of movies.
                }
            } else {
                movies = null;
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the movies JSON results", e);
        }

        // Return the list of movies
        if (movies == null){
            Log.i(LOG_TAG, "movies is null");
        } else {
            Log.i(LOG_TAG, "movies is NOT null");
        }
        return movies;
    }
}
