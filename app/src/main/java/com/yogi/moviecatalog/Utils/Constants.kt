package com.yogi.moviecatalog.Utils

import android.annotation.SuppressLint

class Constants {
    companion object {
        @SuppressLint("UseSparseArrays")
        fun getGenre(id: Int): String {
            val genreMap = HashMap<Int, String>()
            genreMap.put(28, "Action")
            genreMap.put(12, "Adventure")
            genreMap.put(16, "Animation")
            genreMap.put(35, "Comedy")
            genreMap.put(80, "Crime")
            genreMap.put(99, "Documentary")
            genreMap.put(18, "Drama")
            genreMap.put(10751, "Family")
            genreMap.put(14, "Fantasy")
            genreMap.put(36, "History")
            genreMap.put(27, "Horror")
            genreMap.put(10402, "Music")
            genreMap.put(9648, "Mystery")
            genreMap.put(10749, "Romance")
            genreMap.put(878, "Science Fiction")
            genreMap.put(10770, "TV Movie")
            genreMap.put(53, "Thriller")
            genreMap.put(10752, "War")
            genreMap.put(37, "Western")

            return genreMap.get(id)!!
        }

        @SuppressLint("UseSparseArrays")
        fun getGenreTV(id: Int): String {
            val genreMap = HashMap<Int, String>()
            genreMap.put(28, "Action")
            genreMap.put(12, "Adventure")
            genreMap.put(16, "Animation")
            genreMap.put(35, "Comedy")
            genreMap.put(80, "Crime")
            genreMap.put(99, "Documentary")
            genreMap.put(18, "Drama")
            genreMap.put(10751, "Family")
            genreMap.put(14, "Fantasy")
            genreMap.put(36, "History")
            genreMap.put(27, "Horror")
            genreMap.put(10402, "Music")
            genreMap.put(9648, "Mystery")
            genreMap.put(37, "Western")
            genreMap.put(10765, "Sci-Fi & Fantasy")
            genreMap.put(10766, "Soap")
            genreMap.put(10767, "Talk")
            genreMap.put(10768, "War & Politics")
            genreMap.put(10763, "News")
            genreMap.put(10764, "Reality")
            genreMap.put(10762, "Kids")
            genreMap.put(10759, "Action & Adventure")
            genreMap.put(10749, "Romance")
            genreMap.put(878, "Science Fiction")
            genreMap.put(10770, "TV Movie")
            genreMap.put(53, "Thriller")
            genreMap.put(10752, "War")

            return genreMap.get(id)!!
        }
    }
}