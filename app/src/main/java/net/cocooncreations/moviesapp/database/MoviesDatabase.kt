package net.cocooncreations.moviesapp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import net.cocooncreations.moviesapp.database.Dao.MoviesDao
import net.cocooncreations.moviesapp.models.Movie

@Database(entities = [Movie::class],version = 1,exportSchema = false)
abstract class MoviesDatabase:RoomDatabase() {

    abstract fun moviesDao():MoviesDao

}