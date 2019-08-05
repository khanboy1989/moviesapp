package net.cocooncreations.moviesapp.models.Services

import io.reactivex.Single
import net.cocooncreations.moviesapp.models.MoviesResult
import retrofit2.http.GET
import retrofit2.http.Url

interface MoviesApi {


    @GET()
    fun getMovies(@Url() url:String):Single<MoviesResult>

}