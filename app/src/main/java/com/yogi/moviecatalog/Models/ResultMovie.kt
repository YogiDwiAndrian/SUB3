package com.yogi.moviecatalog.Models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class ResultMovie (
        @SerializedName("vote_count") val voteCount: Int? = null,
        @SerializedName("id") val id: Int? = null,
        @SerializedName("video") val video: Boolean? = null,
        @SerializedName("vote_average") val voteAverage: Double? = null,
        @SerializedName("title") val title: String? = null,
        @SerializedName("popularity") val popularity: Double? = null,
        @SerializedName("poster_path") val posterPath: String? = null,
        @SerializedName("original_language") val originalLanguage: String? = null,
        @SerializedName("original_title") val originalTitle: String? = null,
        @SerializedName("genre_ids") val genreIds: List<Int>? = null,
        @SerializedName("backdrop_path") val backdropPath: String? = null,
        @SerializedName("overview") val overview: String? = null,
        @SerializedName("release_date") var releaseDate: String? = null,
        @SerializedName("first_air_date") val releasseDate: String? = null,

        var genreString: String = ""
        ): Parcelable