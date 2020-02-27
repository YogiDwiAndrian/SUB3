package com.yogi.moviecatalog.Ui.Fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yogi.moviecatalog.Adapter.TvShowRowAdapter
import com.yogi.moviecatalog.Api.ApiTheMovieDb
import com.yogi.moviecatalog.BuildConfig
import com.yogi.moviecatalog.Models.ObjectResultMovie
import com.yogi.moviecatalog.Models.ObjectResultTVShow
import com.yogi.moviecatalog.Models.ResultTVShow

import com.yogi.moviecatalog.R
import com.yogi.moviecatalog.ViewModels.ViewModelTvShow
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

    private lateinit var tvshowViewModel: ViewModelTvShow
    private lateinit var root: View
    private lateinit var adapter : TvShowRowAdapter
    private lateinit var rvTvShows: RecyclerView

    var listItems = ArrayList<ResultTVShow>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tvshowViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ViewModelTvShow::class.java)

        root = inflater.inflate(R.layout.fragment_tvshow, container, false)

        adapter = TvShowRowAdapter(this , listItems)

        tvshowViewModel.setTvShow()
        tvshowViewModel.getTvShow().observe(this, Observer {
            listItems.clear()
            listItems.addAll(it)
            adapter.update(listItems)
        })

        setRecyclerList(root)
        return root
    }

    private fun setRecyclerList(root: View) {
        rvTvShows = root.findViewById(R.id.rv_tvshow)
        rvTvShows.layoutManager = LinearLayoutManager(root.context)
        rvTvShows.adapter = adapter
    }
}
