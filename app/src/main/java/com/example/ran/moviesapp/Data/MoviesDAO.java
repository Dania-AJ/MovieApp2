package com.example.ran.moviesapp.Data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MoviesDAO {
    @Insert
    public void insertMovie(MovieModel movie);

    @Insert
    public void insertMultipleMovies(List<MovieModel> movies);

     @Query("DELETE FROM MovieModel WHERE imageID = :id")
      void deleteMovie(String id);

    @Update
    public void updateMovie(MovieModel movie);

    @Query("Select * from MovieModel")
    public List<MovieModel> getAllMovies();

    //TODO 6: Write a query to get a movie through its ID
    //Hint: use where imageID = :x, where x is the name of the parameter you'll receive in the java method that corresponds to this query

    public MovieModel getSingleMovie (String id);
}
