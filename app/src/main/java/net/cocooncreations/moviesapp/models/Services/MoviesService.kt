package net.cocooncreations.moviesapp.models.Services

import io.reactivex.Single
import net.cocooncreations.moviesapp.di.component.DaggerApiComponent
import net.cocooncreations.moviesapp.models.Movie
import net.cocooncreations.moviesapp.models.MoviesResult
import net.cocooncreations.moviesapp.utils.Feeds
import javax.inject.Inject


class MoviesService{


    @Inject
    lateinit var api: MoviesApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getMovies(searchTxt:String):Single<MoviesResult>{
        return api.getMovies(Feeds.getReviewUrlBySearchCriteria(searchTxt))
    }
}