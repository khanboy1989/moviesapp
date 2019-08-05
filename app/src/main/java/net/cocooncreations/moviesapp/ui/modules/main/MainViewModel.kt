package net.cocooncreations.moviesapp.ui.modules.main

import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import net.cocooncreations.moviesapp.di.component.DaggerApiComponent
import net.cocooncreations.moviesapp.models.Movie
import net.cocooncreations.moviesapp.models.MoviesResult
import net.cocooncreations.moviesapp.models.Services.MoviesService
import net.cocooncreations.moviesapp.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * Contains Business logic of MainActivity
 */
class MainViewModel : BaseViewModel() {

    @Inject
    lateinit var moviesService: MoviesService

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
                    override fun onSuccess(value: MoviesResult) {
                        movies.value = value.moviesList
                        moviesLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        moviesLoadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    override fun onViewCreated() {
        DaggerApiComponent.create().inject(this)
    }

    override fun onViewDestroyed() {

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}