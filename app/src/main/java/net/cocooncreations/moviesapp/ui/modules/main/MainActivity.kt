package net.cocooncreations.moviesapp.ui.modules.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import net.cocooncreations.moviesapp.R
import net.cocooncreations.moviesapp.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>() {


    private var moviesListAdapter: MoviesListAdapter = MoviesListAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instantiateViewData()
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

    private fun instantiateViewData() {
        viewModel.onViewCreated()
        viewModel.refreshMovies("godfather")

        moviesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = moviesListAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.movies.observe(this, Observer {
            it.let { list ->
                moviesList.visibility = View.VISIBLE
                moviesListAdapter.updateMovies(list!!)
            }
        })


        viewModel.moviesLoadError.observe(this, Observer { isError ->
            isError?.let { moviesList.visibility = if (it) View.GONE else View.VISIBLE }
        })

        viewModel.loadErrorMessage.observe(this, Observer {
            it.let {
                showAlertDialog(getString(R.string.error_occured),it!!,null)
            }
            viewModel.getLastlyStoredData()
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                if (it) {
                    showProgressDialog("Please wait!")
                } else {
                    hideProgressDialog()
                }
            }
        })

    }


}
