package net.cocooncreations.moviesapp.di.component

import dagger.Component
import net.cocooncreations.moviesapp.di.modules.ApiModule
import net.cocooncreations.moviesapp.models.Services.MoviesService
import net.cocooncreations.moviesapp.ui.modules.main.MainViewModel
import javax.inject.Singleton

//@Component(modules = [ApiModule::class])
//@Singleton
//interface ApiComponent {
//
//    fun inject(service:MoviesService)
//    fun inject(viewModel:MainViewModel)
//
//}