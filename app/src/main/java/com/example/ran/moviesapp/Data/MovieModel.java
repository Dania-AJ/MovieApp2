package com.example.ran.moviesapp.Data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Dania on 4/2/2018.
 * Updated on March 27, 2019
 */

public class MovieModel {

    private String moviePoster;

    public MovieModel(String moviePoster) {

        this.moviePoster = moviePoster;
    }

    public String getMoviePoster() {
        return buildFullPosterPath();
    }

    private String buildFullPosterPath() {
        String FIRST_PART_OF_POSTER_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";
        return FIRST_PART_OF_POSTER_URL + moviePoster;
    }
}
