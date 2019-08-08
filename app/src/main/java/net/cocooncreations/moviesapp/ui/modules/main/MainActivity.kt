package net.cocooncreations.moviesapp.ui.modules.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import net.cocooncreations.moviesapp.R
import net.cocooncreations.moviesapp.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.onViewCreated()
        viewModel.refreshMovies("godfather")
        observeViewModel()
    }

    /**
     * The first time the ViewModelProviders.of method is called by MainActivity,
     * it creates a new ViewModel instance. When this method is called again,
     * which happens whenever onCreate is called,
     * it will return the pre-existing ViewModel associated with the specific MainActivity.
     */
    override fun instantiateViewModel(): MainViewModel {
        return ViewModelProviders.of(this).get(MainViewModel::class.java)
    }


    private fun observeViewModel(){
        viewModel.movies.observe(this, Observer {
            it?.forEach {
                Log.d("Long Id====>",it.byLine)
            }
        })
    }


}
