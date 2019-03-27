package com.example.ran.moviesapp.UI;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.ran.moviesapp.Data.MovieModel;
import com.example.ran.moviesapp.Utils.JsonUtils;
import com.example.ran.moviesapp.Utils.NetworkUtils;

import java.util.ArrayList;

/**
 * Created by Dania on 4/2/2018.
 */

public class MoviesLoader extends AsyncTaskLoader<ArrayList<MovieModel>> {

    String url;

    public MoviesLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<MovieModel> loadInBackground() {
        String response = NetworkUtils.fetchResponse(url);
        return JsonUtils.extractFeatureFromJson(response);
    }
}
