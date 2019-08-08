package net.cocooncreations.moviesapp.ui.modules.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_movie.view.*
import net.cocooncreations.moviesapp.R
import net.cocooncreations.moviesapp.models.Movie

class MoviesListAdapter(var movies: MutableList<Movie>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateMovies(newCountries: List<Movie>) {
        movies.clear()
        movies.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder = MoviesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MoviesViewHolder).bind(movies[position])
    }

    class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.titleTV
        private val info = view.infoTV

        fun bind(movie: Movie) {
            title.text = movie.title
            info.text = movie.summaryShort
        }
    }

}