package net.cocooncreations.moviesapp.models

import com.google.gson.annotations.SerializedName

data class MoviesResult (

    @SerializedName("results")
    var moviesList:ArrayList<Movie>

)