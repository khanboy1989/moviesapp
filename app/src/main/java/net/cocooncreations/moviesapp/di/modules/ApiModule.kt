package net.cocooncreations.moviesapp.di.modules

import dagger.Module
import dagger.Provides
import net.cocooncreations.moviesapp.models.Movie
import net.cocooncreations.moviesapp.models.Services.MoviesApi
import net.cocooncreations.moviesapp.models.Services.MoviesService
import net.cocooncreations.moviesapp.utils.Feeds
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {


    @Provides
    @Singleton
    fun provideMoviesApi():MoviesApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Feeds.BASE_URL)
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    fun provideMoviesService(): MoviesService {
        return MoviesService()
    }


}