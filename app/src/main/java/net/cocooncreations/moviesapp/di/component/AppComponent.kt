package net.cocooncreations.moviesapp.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import net.cocooncreations.moviesapp.MoviesApplication
import net.cocooncreations.moviesapp.di.modules.ApiModule
import net.cocooncreations.moviesapp.di.modules.AppModule
import net.cocooncreations.moviesapp.models.Services.MoviesService
import net.cocooncreations.moviesapp.ui.modules.main.MainViewModel
import javax.inject.Singleton


@Component(modules = [AppModule::class,ApiModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application):AppComponent.Builder
        fun build():AppComponent
    }

    fun inject(instance:MoviesApplication)
    fun inject(moviesService: MoviesService)
    fun inject(mainViewModel:MainViewModel)
}