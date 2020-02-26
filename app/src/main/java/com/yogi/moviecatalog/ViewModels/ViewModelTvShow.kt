package com.yogi.moviecatalog.ViewModels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yogi.moviecatalog.Api.ApiTheMovieDb
import com.yogi.moviecatalog.BuildConfig
import com.yogi.moviecatalog.Models.ObjectResultTVShow
import com.yogi.moviecatalog.Models.ResultTVShow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ViewModelTvShow: ViewModel() {

    private var mutableLiveDataTvShow: MutableLiveData<ArrayList<ResultTVShow>> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun setTvShow() {
        val listItems = ArrayList<ResultTVShow>()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val apiTheMovieDb = retrofit.create(ApiTheMovieDb::class.java)
        apiTheMovieDb.getPopularTVShow()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { objectResultTVShow: ObjectResultTVShow ->
                mutableLiveDataTvShow.value = objectResultTVShow.resultsTVShow as ArrayList
            }
        mutableLiveDataTvShow.postValue(listItems)
    }

    fun getTvShow(): LiveData<ArrayList<ResultTVShow>> {
        return mutableLiveDataTvShow
    }
}