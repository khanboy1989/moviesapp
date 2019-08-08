package net.cocooncreations.moviesapp

import android.app.Application
import net.cocooncreations.moviesapp.di.component.AppComponent
import net.cocooncreations.moviesapp.di.component.DaggerAppComponent
import net.cocooncreations.moviesapp.di.modules.AppModule
import javax.inject.Singleton

@Singleton
class MoviesApplication:Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }

}