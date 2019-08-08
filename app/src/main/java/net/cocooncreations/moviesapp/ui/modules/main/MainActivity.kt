package net.cocooncreations.moviesapp.ui.modules.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*
import net.cocooncreations.moviesapp.R
import net.cocooncreations.moviesapp.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>(),SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private var moviesListAdapter: MoviesListAdapter = MoviesListAdapter(mutableListOf())
    private var searchText:String = ""
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

        //load lastly stored data in initial point
        viewModel.getLastlyStoredData()

        //init the RecyclerView
        moviesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = moviesListAdapter
        }

        //set on refresh listener
        swipeRefreshLayout.setOnRefreshListener(this)

        //searchview on text change listener
        searchView.setOnQueryTextListener(this)

        //observe value changes on viewModel
        observeViewModel()
    }

    /**
     * onDestroy
     */
    override fun onDestroy() {
        super.onDestroy()
        viewModel.onViewDestroyed()
    }

    /**
     * observe Value changes from viewModel
     * @property movies is called in order to update the recyclerview
     * @property moviesLoadError is called when there is a internet error or network error
     * @property loading is called when loading is finished or started
     */
    private fun observeViewModel() {
        viewModel.movies.observe(this, Observer {
            it.let { list ->
                if(list?.size == 0){
                    list_error.text = getString(R.string.no_data_to_show)
                }else{
                    list_error.visibility = View.GONE
                    moviesList.visibility = View.VISIBLE
                    moviesListAdapter.updateMovies(list!!)
                }
            }
        })

        viewModel.moviesLoadError.observe(this, Observer { isError ->
            isError?.let { moviesList.visibility = if (it) View.GONE else View.VISIBLE }
        })

        viewModel.loadErrorMessage.observe(this, Observer {
            it.let {
                showAlertDialog(getString(R.string.error_occured), it!!, null)
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

    /**
     * onRefresh triggered, this function is getting called and we can perform swipe operation here
     */
    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = false
        viewModel.refreshMovies(searchText)

    }

    /**
     * onQueryTextSubmitted make request from viewmodel to
     * refresh the list
     */
    override fun onQueryTextSubmit(queryString: String?): Boolean {
        if (!queryString.isNullOrEmpty()){
            searchText = queryString
            viewModel.refreshMovies(queryString)
        }
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }
}
