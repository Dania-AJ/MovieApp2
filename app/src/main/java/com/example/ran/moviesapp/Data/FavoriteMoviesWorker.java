package com.example.ran.moviesapp.Data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ran.moviesapp.UI.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMoviesWorker {

    private boolean setAsFavorite = true;
    private Database database;

    public FavoriteMoviesWorker (Context context) {
        database = Database.getDatabase(context);
    }

    public void insertFavMovie (MovieModel movie){
        setAsFavorite = true;
        new FavoriteMoviesAsyncTask ().execute(movie);
    }

    public void deleteFavMovie (MovieModel movie){
        setAsFavorite = false;
        new FavoriteMoviesAsyncTask ().execute(movie);
    }


    private class FavoriteMoviesAsyncTask extends AsyncTask <MovieModel, Void, Void> {

        @Override
        protected Void doInBackground(MovieModel... movieModel) {
            if (setAsFavorite){
                //TODO 7: Insert the received movie to the DB

            } else {
                //TODO 8: Delete the received movie to the DB

            }
            return null;
        }
    }


}
