package net.cocooncreations.moviesapp.database.Dao

import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import net.cocooncreations.moviesapp.models.Movie


@Dao
interface MoviesDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie:Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies:List<Movie>)

    @Query("SELECT * FROM Movie")
    fun getAllMovies():List<Movie>

}