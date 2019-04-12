package com.example.ran.moviesapp.UI;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.ran.moviesapp.Data.Database;
import com.example.ran.moviesapp.Data.MovieModel;
import com.example.ran.moviesapp.Utils.JsonUtils;
import com.example.ran.moviesapp.Utils.NetworkUtils;

import java.util.List;

public class MovieViewModel extends AndroidViewModel{

    Database database;
    MutableLiveData<List <MovieModel>> movieLiveData = new MutableLiveData<>();

    public MovieViewModel (Application application) {
        super (application);
        //Get database instance:
        database = Database.getDatabase(application);

    }

    public LiveData <List<MovieModel>> getMovies (boolean fav, String url) {

        if (fav){
                //load it from room
                new FavoriteAsyncTask().execute();
                return movieLiveData;

        } else {
            //load it from the server through AsyncTask
            //TODO 2: Call MoviesfromServerAsyncTask and send the URL to it, then return movieLiveData

            return movieLiveData;
        }
    }


    public class MoviesfromServerAsyncTask extends AsyncTask <String, Void, Void> {

        @Override
        protected Void doInBackground(String... url) {
            String response = NetworkUtils.fetchResponse(url[0]);
            List <MovieModel> movies =  JsonUtils.extractFeatureFromJson(response);
            movieLiveData.postValue(movies);
            return null;
        }

    }

    public class FavoriteAsyncTask extends AsyncTask <Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO 1: change the value of movieLiveData to hold the movies retrieved from the DB (note that we use postValue not setValue if the value is being changed from a background thread)

            return null;
        }

    }

}
