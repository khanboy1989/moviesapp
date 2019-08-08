package net.cocooncreations.moviesapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import net.cocooncreations.moviesapp.database.Dao.MoviesDao
import net.cocooncreations.moviesapp.models.Movie
import net.cocooncreations.moviesapp.models.MoviesResult
import net.cocooncreations.moviesapp.models.Services.MoviesService
import net.cocooncreations.moviesapp.ui.modules.main.MainViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class MainViewModelTest {

    @get:Rule
    var rule  = InstantTaskExecutorRule()

    @Mock
    lateinit var moviesService: MoviesService

    @Mock
    lateinit var moviesDao: MoviesDao

    @InjectMocks
    var mainViewModel = MainViewModel()

    private var testSingle:Single<MoviesResult>? = null

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Before
    fun setUpRxSchedulers(){
        val immidiate = object: Scheduler(){
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }

            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler: Callable<Scheduler> -> immidiate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler: Callable<Scheduler> -> immidiate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler: Callable<Scheduler> -> immidiate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler: Callable<Scheduler> -> immidiate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler> -> immidiate }

    }


    @Test
    fun getMoviesSuccess(){
        val movie1 = Movie(0,"title","mpaa",1,"line","head line","summary short","date","update date")
        val movie2 = Movie(1,"title","mpaa",1,"line","head line","summary short","date","update date")

        val arrayList = arrayListOf<Movie>()

        arrayList.add(movie1)
        arrayList.add(movie2)

        val movieResult = MoviesResult(arrayList)

        testSingle = Single.just(movieResult)

        `when`(moviesService.getMovies("godfather")).thenReturn(testSingle)

        mainViewModel.refreshMovies("godfather")

        Assert.assertEquals(2,mainViewModel.movies.value?.size)
        Assert.assertEquals(false,mainViewModel.moviesLoadError.value)
        Assert.assertEquals(false,mainViewModel.loading.value)

    }

    /**
     * Test if service is returned an error
     */
    @Test
    fun getMoviesFailure(){

        testSingle = Single.error(Throwable())

        `when`(moviesService.getMovies("godfather")).thenReturn(testSingle)

        mainViewModel.refreshMovies("godfather")

        Assert.assertEquals(null,mainViewModel.movies.value?.size)
        Assert.assertEquals(true,mainViewModel.moviesLoadError.value)
        Assert.assertEquals(false,mainViewModel.loading.value)
    }

}