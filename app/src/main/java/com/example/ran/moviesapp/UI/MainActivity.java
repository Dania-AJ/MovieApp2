package com.example.ran.moviesapp.UI;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ran.moviesapp.Data.MovieModel;
import com.example.ran.moviesapp.R;
import com.example.ran.moviesapp.Settings.SettingsActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieModel>> {

    private ArrayList<MovieModel> movies;
    private MoviesAdapter adapter;
    //Create Loader ID
    private final int LOADER_ID = 1;

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
        TextView noInternetMsg = findViewById(R.id.no_internet_tv);

        if (isConnected()) {
            noInternetMsg.setVisibility(View.GONE);
            getLoaderManager().initLoader(LOADER_ID, null, this);
        } else {
            //The technique used for showing this msg is taken from: https://stackoverflow.com/questions/28217436/how-to-show-an-empty-view-with-a-recyclerview
            noInternetMsg.setVisibility(View.VISIBLE);
        }
    }

    // TODO 14: Override onRestart to restart the Loader in it, so that when the user is back from the settings Activity, the data can be reloaded accordingly, or whenever the Activity is restarted the data can be updated
    @Override
    protected void onRestart() {
        super.onRestart();
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<MovieModel>> onCreateLoader(int i, Bundle bundle) {
        return new MoviesLoader(this, buildURL());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieModel>> loader, ArrayList<MovieModel> movieModels) {
        //TODO 7: Clear any old data in the movies ArrayList (so in case of a new reload, your app will display only the new data without the old one)
        movies.clear();
        //TODO 8: Add moviesModels ArrayList (returned by the Loader after executing the network request) to movies ArrayList (which is associated with the adapter)
        movies.addAll(movieModels);
        //TODO 9: Notify the adapter that the ArrayList it's associated with (movies) was changed (hint: use notifyDataSetChanged)
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieModel>> loader) {
        //TODO 10: Clear any old data in the ArrayList associated with the adapter
        movies.clear();
        //TODO 11: Notify the adapter that the ArrayList it's associated with (movies) was changed
        adapter.notifyDataSetChanged();
    }

    private String buildURL() {

        /*Note: As per the documentation here: https://www.themoviedb.org/documentation/api/discover
        To get the best movies you should use: http://api.themoviedb.org/3/discover/movie?primary_release_year=the_year&sort_by=vote_average.desc&api_key=your_key
        To get the best drama movies you should use: http://api.themoviedb.org/3/discover/movie?with_genres=18&primary_release_year=2018&api_key=f605efa3d15575c1e01480333cb8a356
        */

        //local variables related to the URL:
        final String BASE_URL = "http://api.themoviedb.org/3/discover/movie";
        final String API_KEY_QUERY_PARAMETER = "api_key";
        final String API_KEY = "f605efa3d15575c1e01480333cb8a356";

        //TODO 3: Get the selected sort value from SharedPreferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sortByValue = sharedPrefs.getString(getString(R.string.sort_by_key), getString(R.string.best_movies_pref));

        //Build the URI according to the selected value
        Uri uri = Uri.parse(BASE_URL);
        Uri.Builder builder = uri.buildUpon();
        builder.appendQueryParameter("primary_release_year", "2018");

        /*TODO 4: Check if sortByValue has value equal to best_movies_pref in the Strings file
        /(because best_movies_pref is the value associated with the Preference that represents the selection of displaying the best movies in the settings screen)*/
        if (sortByValue.equals(getString(R.string.best_movies_pref))){
            builder.appendQueryParameter("sort_by", "vote_average.desc");
        } else {
            //TODO 5: Append a query parameter for the genres (because in this case the user prefers to get best drama movies) and set it's value to 18 (18 is for Drama).
            builder.appendQueryParameter("with_genres", "18");
        }

        //TODO 6: Append a query parameter for your API key
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
}
