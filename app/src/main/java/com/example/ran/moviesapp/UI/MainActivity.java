package com.example.ran.moviesapp.UI;

import android.app.LoaderManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ran.moviesapp.Data.MovieModel;
import com.example.ran.moviesapp.R;
import com.example.ran.moviesapp.Settings.SettingsActivity;
import com.example.ran.moviesapp.Utils.JsonUtils;
import com.example.ran.moviesapp.Utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<MovieModel> movies;
    private MoviesAdapter adapter;
    //Create Loader ID
    private final int LOADER_ID = 1;
    MovieViewModel viewModel;
    TextView noInternetMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define the number of columns that GridLayoutManager will have
        final int NUMBER_OF_COLUMNS = 2;

        //Setting the ArrayList and the RecyclerView
        movies = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new MoviesAdapter(this, movies);

        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        recyclerView.setLayoutManager(layoutManager);

        //Define the TextView of no internet connection
        noInternetMsg = findViewById(R.id.no_internet_tv);

        //TODO 3 define viewModel variable
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        // Check if sortByValue has value equal to favorite_movies_pref in the Strings file
        if (getSortValue().equals(getString(R.string.favorite_movies_pref))) {

            //TODO 4:  write the code to get the movies from viewModel when the selected preference is "favorites"

            viewModel.getMovies(true, "").observe(this, new Observer<List<MovieModel>>() {

                @Override
                public void onChanged(@Nullable List<MovieModel> movieModels) {
                    movies.clear();

                    if (movieModels != null) {
                        movies.addAll(movieModels);
                        adapter.notifyDataSetChanged();
                    }
                }
            });

        } else {
            if (isConnected()) {
                //TODO 5: Write the code to get movies from viewModel when the selected preference needs a call to the API
                noInternetMsg.setVisibility(View.GONE);
                viewModel.getMovies(false, buildURL()).observe(this, new Observer<List<MovieModel>>() {
                    @Override
                    public void onChanged(@Nullable List<MovieModel> movieModels) {
                        movies.clear();
                        movies.addAll(movieModels);
                        adapter.notifyDataSetChanged();
                    }
                });

            } else {
                noInternetMsg.setVisibility(View.VISIBLE);
            }

        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Check if sortByValue has value equal to favorite_movies_pref in the Strings file
        if (getSortValue().equals(getString(R.string.favorite_movies_pref))) {

            viewModel.getMovies(true, "").observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(@Nullable List<MovieModel> movieModels) {
                    movies.clear();

                    if (movieModels != null) {
                        movies.addAll(movieModels);
                        adapter.notifyDataSetChanged();
                    }
                }
            });

        } else {
            if (isConnected()) {
                noInternetMsg.setVisibility(View.GONE);
                viewModel.getMovies(false, buildURL()).observe(this, new Observer<List<MovieModel>>() {
                    @Override
                    public void onChanged(@Nullable List<MovieModel> movieModels) {
                        movies.clear();
                        movies.addAll(movieModels);
                        adapter.notifyDataSetChanged();
                    }
                });

            } else {
                //The technique used for showing this msg is taken from: https://stackoverflow.com/questions/28217436/how-to-show-an-empty-view-with-a-recyclerview
                noInternetMsg.setVisibility(View.VISIBLE);
            }

        }
    }


    private String buildURL() {

        /*Note: As per the documentation here: https://www.themoviedb.org/documentation/api/discover
        To get the best movies you should use: http://api.themoviedb.org/3/discover/movie?primary_release_year=the_year&sort_by=vote_average.desc&api_key=your_key
        To get the best drama movies you should use: http://api.themoviedb.org/3/discover/movie?with_genres=18&primary_release_year=2018&api_key=f605efa3d15575c1e01480333cb8a356
        */

        //local variables related to the URL:
        final String BASE_URL = "https://api.themoviedb.org/3/discover/movie";
        final String API_KEY_QUERY_PARAMETER = "api_key";
        final String API_KEY = "f605efa3d15575c1e01480333cb8a356";

        String sortByValue = getSortValue();

        //Build the URI according to the selected value
        Uri uri = Uri.parse(BASE_URL);
        Uri.Builder builder = uri.buildUpon();
        builder.appendQueryParameter("primary_release_year", "2018");

        if (sortByValue.equals(getString(R.string.best_movies_pref))) {
            builder.appendQueryParameter("sort_by", "vote_average.desc");
        } else {
            builder.appendQueryParameter("with_genres", "18");
        }

        builder.appendQueryParameter(API_KEY_QUERY_PARAMETER, API_KEY);
        return builder.toString();
    }

    private Boolean isConnected() {
        //Checking Network state code is taken from here: https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings_menu_item) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private String getSortValue() {
        //Get the selected sort value from SharedPreferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPrefs.getString(getString(R.string.sort_by_key), getString(R.string.best_movies_pref));
    }
}
