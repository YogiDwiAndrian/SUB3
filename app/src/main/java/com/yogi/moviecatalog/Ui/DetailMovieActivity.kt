package com.yogi.moviecatalog.Ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yogi.moviecatalog.Adapter.MovieColAdapter
import com.yogi.moviecatalog.Api.ApiTheMovieDb
import com.yogi.moviecatalog.BuildConfig
import com.yogi.moviecatalog.Models.ObjectResultMovie
import com.yogi.moviecatalog.Models.ResultMovie

import com.yogi.moviecatalog.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import kotlin.properties.Delegates

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailMovieActivity : AppCompatActivity() {

    //recyclerview horizontal
    private val TAG = javaClass.simpleName
    private var adapterTheMovieDb by Delegates.notNull<MovieColAdapter>()
    private var isLoading by Delegates.notNull<Boolean>()
    private var page by Delegates.notNull<Int>()
    private var totalPage by Delegates.notNull<Int>()

    companion object {
        const val EXTRA_MOVIES = "extra_movies"
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movies = intent.getParcelableExtra(EXTRA_MOVIES) as ResultMovie

        //change poster
        val imgPoster: ImageView = img_detail_movie
        Glide.with(imgPoster)
            .load(BuildConfig.BASE_URL_IMAGE + movies.posterPath)
            .apply(RequestOptions())
            .into(imgPoster)

        //change backdrop
        val imgBackdrop: ImageView = backdrop_detail_movie
        Glide.with(imgPoster)
            .load(BuildConfig.BASE_URL_BACKDROP + movies.backdropPath)
            .apply(RequestOptions())
            .into(imgBackdrop)

        //change tittle
        val tvTittle: TextView = tv_detail_tittle
        val tittle = movies.title
        tvTittle.text = tittle

        //change year
        val tvyear: TextView = tv_detail_year
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val convertDate = simpleDateFormat.parse(movies.releaseDate!!)
        simpleDateFormat = SimpleDateFormat("yyyy")
        val year = simpleDateFormat.format(convertDate)
        tvyear.text = year

        //change ratingBar
        val tvRateBar: RatingBar = ratingBar_detail
        val rateBar = movies.voteAverage!!.toFloat()
        tvRateBar.rating = rateBar / 2

        //change rateNum
        val tvRateNum: TextView = tv_detail_rate
        val rateNum = movies.voteAverage.toString()
        tvRateNum.text = rateNum

        //change overview
        val tvOverview: TextView = tv_detail_overview
        val overview = movies.overview
        tvOverview.text = overview


        page = 1
        totalPage = 0
        doLoadData()
        initListener()

    }

    @SuppressLint("CheckResult")
    private fun doLoadData() {
        Log.d(TAG, "page: $page")
        showLoading(true)
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val apiTheMovieDb = retrofit.create(ApiTheMovieDb::class.java)
        apiTheMovieDb.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { ObjectResultMovie: ObjectResultMovie ->
                    val resultMovie = ObjectResultMovie.resultMovies as ArrayList
                    if (page == 1) {
                        adapterTheMovieDb = MovieColAdapter(
                            this,
                            resultMovie
                        )
                        rv_movie_col.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        rv_movie_col.adapter = adapterTheMovieDb
                    } else {
                        adapterTheMovieDb.refreshAdapter(resultMovie)
                    }
                    totalPage = ObjectResultMovie.totalPages
                },
                { t: Throwable ->
                    t.printStackTrace()
                },
                {
                    hideLoading()
                }
            )
    }

    private fun initListener() {
        rv_movie_col.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager.itemCount
                val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val isLastPosition = countItem.minus(1) == lastVisiblePosition
                Log.d(TAG, "countItem: $countItem")
                Log.d(TAG, "lastVisiblePosition: $lastVisiblePosition")
                Log.d(TAG, "isLastPosition: $isLastPosition")
                if (!isLoading && isLastPosition && page < totalPage) {
                    showLoading(true)
                    page = page.plus(1)
                    doLoadData()
                }
            }
        })
    }

    private fun showLoading(isRefresh: Boolean) {
        isLoading = true
        progress_bar_movie.visibility = View.VISIBLE
        rv_movie_col.visibility.let {
            if (isRefresh) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun hideLoading() {
        isLoading = false
        progress_bar_movie.visibility = View.GONE
        rv_movie_col.visibility = View.VISIBLE
    }


}