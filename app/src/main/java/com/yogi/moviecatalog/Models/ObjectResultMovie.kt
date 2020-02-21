package com.yogi.moviecatalog.Models

import com.google.gson.annotations.SerializedName

data class ObjectResultMovie(
	@SerializedName("results") val resultMovies: List<ResultMovie>,
	@SerializedName("page") val page: Int,
	@SerializedName("total_results") val totalResults: Int,
	@SerializedName("dates") val dates: Dates,
	@SerializedName("total_pages") val totalPages: Int
)


