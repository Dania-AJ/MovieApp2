package com.example.ran.moviesapp.UI;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ran.moviesapp.Data.Database;
import com.example.ran.moviesapp.Data.FavoriteMoviesWorker;
import com.example.ran.moviesapp.Data.MovieModel;
import com.example.ran.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    List<MovieModel> retrievedMoviefromDB = new ArrayList<>();
    MovieModel retrievedMoviefromIntent;
    FavoriteMoviesWorker worker = new FavoriteMoviesWorker(this);
      ImageView star;
      Boolean fromClick=false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        ImageView poster = findViewById(R.id.poster);
          star = findViewById(R.id.star);
        retrievedMoviefromIntent = getIntent().getParcelableExtra("movie");

        Picasso.with(this).load(retrievedMoviefromIntent.getMoviePoster()).into(poster);
        new SingleMovieAsyncTask().execute(retrievedMoviefromIntent);


        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SingleMovieAsyncTask().execute(retrievedMoviefromIntent);
                fromClick=true;
            }
        });

    }

    private void markAsFavorite(MovieModel movie) {
        worker.insertFavMovie(movie);
    }

    private void unfavoriteMovie(MovieModel movie) {
        worker.deleteFavMovie(movie);
    }


    private class SingleMovieAsyncTask extends AsyncTask<MovieModel, Void, MovieModel> {

        @Override
        protected MovieModel doInBackground(MovieModel... movie) {

            Database database = Database.getDatabase(DetailsActivity.this);
            MovieModel singleMovie = database.moviesDAO().getSingleMovie(movie[0].getImageID());


            return singleMovie;
        }

        @Override
        protected void onPostExecute(MovieModel list) {
            super.onPostExecute(list);
            if(fromClick){
                if (list != null) {
                    //TODO 9 : Set the movie as unfavorite and set the star to be the unfavorite star
                    unfavoriteMovie(retrievedMoviefromIntent);
                    star.setImageResource(R.drawable.unfav_star);
                } else {
                    markAsFavorite(retrievedMoviefromIntent);
                    star.setImageResource(R.drawable.fav_star);
                }
            }
            else {
                if (list != null) {
                    star.setImageResource(R.drawable.fav_star);
                } else {
                    star.setImageResource(R.drawable.unfav_star);
                }
            }
         }
    }
}
