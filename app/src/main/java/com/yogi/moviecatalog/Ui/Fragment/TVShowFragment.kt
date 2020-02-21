package com.yogi.moviecatalog.Ui.Fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yogi.moviecatalog.Adapter.TvShowRowAdapter
import com.yogi.moviecatalog.Api.ApiTheMovieDb
import com.yogi.moviecatalog.BuildConfig
import com.yogi.moviecatalog.Models.ObjectResultMovie
import com.yogi.moviecatalog.Models.ObjectResultTVShow

import com.yogi.moviecatalog.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_tvshow.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 */
class TVShowFragment : Fragment() {

    private val TAG = javaClass.simpleName
    private var adapterTVShow by Delegates.notNull<TvShowRowAdapter>()
    private var isLoading by Delegates.notNull<Boolean>()
    private var page by Delegates.notNull<Int>()
    private var totalPage by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        apiTheMovieDb.getPopularTVShow(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { ObjectResultTVShow: ObjectResultTVShow ->
                    val resultTVShow = ObjectResultTVShow.resultsTVShow as ArrayList
                    if (page == 1) {
                        adapterTVShow = TvShowRowAdapter(
                            this,
                            resultTVShow
                        )
                        rv_tvshow.layoutManager = LinearLayoutManager(activity)
                        rv_tvshow.adapter = adapterTVShow
                    } else {
                        adapterTVShow.refreshAdapter(resultTVShow)
                    }
                    totalPage = ObjectResultTVShow.totalPages
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
        rv_tvshow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        progress_bar.visibility = View.VISIBLE
        rv_tvshow.visibility.let {
            if (isRefresh) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun hideLoading() {
        isLoading = false
        progress_bar.visibility = View.GONE
        rv_tvshow.visibility = View.VISIBLE
    }

}
