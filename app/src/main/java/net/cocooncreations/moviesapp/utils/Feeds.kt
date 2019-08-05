package net.cocooncreations.moviesapp.utils

object Feeds {

    const val  BASE_URL = "https://api.nytimes.com/svc/movies/v2/"
    const val API_KEY = "YsZT5Z3FPj3HQvI6Gt2mvGk53GYXVPGZ"

    fun getReviewUrlBySearchCriteria(searchText:String):String{
        val url:String = "reviews/search.json?query=$searchText&api-key=$API_KEY"
        return url
    }
}