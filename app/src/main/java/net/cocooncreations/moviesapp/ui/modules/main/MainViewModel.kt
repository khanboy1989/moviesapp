package net.cocooncreations.moviesapp.ui.modules.main

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import net.cocooncreations.moviesapp.MoviesApplication
import net.cocooncreations.moviesapp.database.Dao.MoviesDao
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

    @Inject
    lateinit var moviesDao: MoviesDao

    val movies = MutableLiveData<List<Movie>>()
    val moviesLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val loadErrorMessage = MutableLiveData<String>()


    /**
     * Refreshes the movies or parses from the remote
     */
    fun refreshMovies(searchText: String) {
        fetchMoviesList(searchText)
    }

    /**
     * Get lastly stored data,
     * when there is no internet get it from local database
     */
    fun getLastlyStoredData() {
        getAllMovies()
    }

    /**
     * fetchMoviesList method
     * helps parsing data from the service
     */
    private fun fetchMoviesList(searchText: String) {
        loading.value = true
        disposable.add(moviesService.getMovies(searchText).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MoviesResult>() {
                    @SuppressLint("CheckResult")
                    override fun onSuccess(value: MoviesResult) {
                        movies.value = value.moviesList
                        moviesLoadError.value = false
                        loading.value = false
                        storeMoviesList(value.moviesList)
                    }
                    override fun onError(e: Throwable) {
                        moviesLoadError.value = true
                        loading.value = false
                        loadErrorMessage.value = e.localizedMessage
                    }
                }))
    }

    /**
     * When view is created the method of onViewCreated is getting called
     * It injects the viewmodel so that class can use the dao and api methods
     */
    override fun onViewCreated() {
        MoviesApplication.appComponent.inject(this)
    }

    /**
     * onViewDestroyed get notified by view and clear the disposable
     * queue is cleared
     */
    override fun onViewDestroyed() {
        disposable.clear()
    }

    /**
     * storeMovieList
     */
    private fun storeMoviesList(list: List<Movie>) {
        disposable.add(Observable.fromCallable {
            with(moviesDao) {
                moviesDao.deleteAll()
                insertMovies(list)
            }
        }.doOnError {
        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }



    /**
     * returns latest stored data in case of there is no internet connection
     * in case of there is no data, no data will be shown
     */
    private fun getAllMovies(){
        disposable.add(moviesDao.getAll().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(object:DisposableSingleObserver<List<Movie>>(){
            override fun onSuccess(result: List<Movie>) {
                moviesLoadError.value = false
                movies.value = result
                loading.value = false
            }

            override fun onError(e: Throwable) {
                loadErrorMessage.value = e.localizedMessage
                moviesLoadError.value = true
                loading.value = false
            }

        }))
    }

    /**
     * viewModel is cleared
     * remove clear the disposable queue
     */
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}