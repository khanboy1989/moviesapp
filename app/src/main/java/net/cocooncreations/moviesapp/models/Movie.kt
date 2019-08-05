package net.cocooncreations.moviesapp.models

import com.google.gson.annotations.SerializedName

data class Movie(

    @SerializedName("display_title")
    var title:String,

    @SerializedName("mpaa_rating")
    var mpaaRating:String,

    @SerializedName("critics_pick")
    var criticsPick:Int,

    @SerializedName("byline")
    var byLine:String,

    @SerializedName("headline")
    var headLine:String,

    @SerializedName("summary_short")
    var summaryShort:String,

    @SerializedName("publication_date")
    var publicationDate:String,

    @SerializedName("date_updated")
    var dateUpdate:String

)