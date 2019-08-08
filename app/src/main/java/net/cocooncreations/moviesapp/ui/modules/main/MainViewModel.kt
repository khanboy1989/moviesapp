package net.cocooncreations.moviesapp.ui.modules.main

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import net.cocooncreations.moviesapp.MoviesApplication
import net.cocooncreations.moviesapp.database.Dao.MoviesDao
import net.cocooncreations.moviesapp.models.Movie
import net.cocooncreations.moviesapp.models.MoviesResult
import net.cocooncreations.moviesapp.models.Services.MoviesService
import net.cocooncreations.moviesapp.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

/**
 * Contains Business logic of MainActivity
 */
class MainViewModel : BaseViewModel() {

    @Inject
    lateinit var moviesService: MoviesService

    @Inject
    lateinit var moviesDao:MoviesDao

    val movies = MutableLiveData<List<Movie>>()
    val moviesLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    /**
     * Refreshes the movies or parses from the remote
     */
    fun refreshMovies(searchText: String) {
        fetchMoviesList(searchText)
    }

    /**
     * fetchMoviesList method
     * helps parsing data from the service
     */
    private fun fetchMoviesList(searchText: String) {
        loading.value = true
        disposable.add(
            moviesService.getMovies(searchText).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MoviesResult>() {
                    @SuppressLint("CheckResult")
                    override fun onSuccess(value: MoviesResult) {
                        movies.value = value.moviesList
                        moviesLoadError.value = false
                        loading.value = false
                        //storeData(value.moviesList)
                    }

                    override fun onError(e: Throwable) {
                        moviesLoadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    override fun onViewCreated() {
        MoviesApplication.appComponent.inject(this)
    }

    override fun onViewDestroyed() {

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


//    private fun storeData(list:List<Movie>){
//        Observable.fromCallable {
//            with(moviesDao){
//                insertMovies(list)
//            }
//            moviesDao.getAllMovies()
//        }.doOnNext {result->
//            result.forEach {
//                Log.d("Long Id====>", it.toString())
//            }
//        }.doOnError {
//            Log.d("Long Error====>", it.toString())
//        }.subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()
//    }
}