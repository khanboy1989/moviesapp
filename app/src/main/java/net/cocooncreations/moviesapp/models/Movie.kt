package net.cocooncreations.moviesapp.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(

    @PrimaryKey(autoGenerate = true)
    var id:Int,

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