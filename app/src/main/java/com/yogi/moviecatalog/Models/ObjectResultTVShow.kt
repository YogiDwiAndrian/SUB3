package com.yogi.moviecatalog.Models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ObjectResultTVShow(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultsTVShow: List<ResultTVShow>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)