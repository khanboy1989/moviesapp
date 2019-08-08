package net.cocooncreations.moviesapp.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import net.cocooncreations.moviesapp.database.Dao.MoviesDao
import net.cocooncreations.moviesapp.database.MoviesDatabase
import javax.inject.Singleton

@Module
class AppModule {


    @Provides
    @Singleton
    fun provideDatabase(application:Application):MoviesDatabase {
        return Room.databaseBuilder(application.applicationContext, MoviesDatabase::class.java, "movies.db")
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()

    }

    @Provides
    @Singleton
    fun provideMoviesDao(database: MoviesDatabase): MoviesDao {
        return database.moviesDao()
    }
}