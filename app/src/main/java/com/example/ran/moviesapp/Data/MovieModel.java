package com.example.ran.moviesapp.Data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Dania on 4/2/2018.
 * Updated on April 9, 2019
 */

@Entity
public class MovieModel implements Parcelable {

    @PrimaryKey (autoGenerate = true)
    public int movieId;
    private String imageID;
    private String moviePoster;

    public MovieModel(String imageID) {
        this.imageID = imageID;
        this.moviePoster = buildFullPosterPath();

    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getImageID() {
        return imageID;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    private String buildFullPosterPath() {
        String FIRST_PART_OF_POSTER_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";
        return FIRST_PART_OF_POSTER_URL + imageID;
    }

    protected MovieModel(Parcel in) {
        imageID = in.readString();
        moviePoster = in.readString();

    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageID);
        parcel.writeString(moviePoster);
    }


}
