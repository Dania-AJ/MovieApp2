package com.example.ran.moviesapp.Data;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@android.arch.persistence.room.Database(entities = MovieModel.class, version = 1, exportSchema = false)

public abstract class Database extends RoomDatabase {
    //define DAO to be used by the database to perform CRUD
    public abstract  MoviesDAO moviesDAO ();

    private static Database database;

    //Return an object of the database (1 single object should be used throughout the entire app)
    public static Database getDatabase (Context context) {
        if (database == null){
            database = Room.databaseBuilder(context, Database.class, "Favorite_Movie.db").fallbackToDestructiveMigration().build();
        }

        return database;
    }
}
