package com.example.ran.moviesapp.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ran.moviesapp.Data.MovieModel;
import com.example.ran.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dania on 4/2/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    ArrayList<MovieModel> movies;
    Context context;

    public MoviesAdapter(Context context, ArrayList<MovieModel> source) {
        this.context = context;
        this.movies = source;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Picasso.with(context).load(movies.get(position).getMoviePoster()).into(holder.poster);
        Log.i("msg", "link is " + movies.get(position).getMoviePoster());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        //Have the views here so after they are inflated they can be accessed from onBindView:
        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            //Inflate the views here then return the result to onCreateViewHolder
            poster = itemView.findViewById(R.id.poster_thumbnail);

            //Set onClick listener on every grid item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "clicked", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
