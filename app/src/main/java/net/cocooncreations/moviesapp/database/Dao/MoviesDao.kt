package net.cocooncreations.moviesapp.database.Dao

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.*
import io.reactivex.Completable
import net.cocooncreations.moviesapp.models.Movie


@Dao
interface MoviesDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie:Movie):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies:List<Movie>):List<Long>

    @Query("SELECT * FROM Movie")
    fun getAllMovies():List<Movie>

    @Query("DELETE FROM Movie")
    fun deleteAll()

}