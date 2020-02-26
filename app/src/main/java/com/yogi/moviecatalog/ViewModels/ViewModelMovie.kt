package com.yogi.moviecatalog.ViewModels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yogi.moviecatalog.Api.ApiTheMovieDb
import com.yogi.moviecatalog.BuildConfig
import com.yogi.moviecatalog.Models.ObjectResultMovie
import com.yogi.moviecatalog.Models.ResultMovie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class ViewModelMovie: ViewModel() {

    private var mutableLiveDataMovies: MutableLiveData<ArrayList<ResultMovie>> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun setMovies() {
        val listItems = ArrayList<ResultMovie>()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val apiTheMovieDb = retrofit.create(ApiTheMovieDb::class.java)
        apiTheMovieDb.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { objectResultMovie: ObjectResultMovie ->
                mutableLiveDataMovies.value = objectResultMovie.resultMovies as ArrayList
            }
        mutableLiveDataMovies.postValue(listItems)
    }

    fun getMovie(): LiveData<ArrayList<ResultMovie>> {
        return mutableLiveDataMovies
    }
















//    private var isFetching = MutableLiveData<Boolean>()
//
//    fun getMovies(): LiveData<ArrayList<ResultMovie>> {
//        return mutableLiveDataMovies
//    }
//
//    fun getIsFetching(): LiveData<Boolean> {
//        return isFetching
//    }
//
//    private fun setIsFetching(isFetching: Boolean) {
//        this.isFetching.postValue(isFetching)
//    }
//    @SuppressLint("CheckResult")
//    fun open() {
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BuildConfig.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//        val apiTheMovieDb = retrofit.create(ApiTheMovieDb::class.java)
//        apiTheMovieDb.getPopularMovies(page = 1)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { objectResultMovie: ObjectResultMovie ->
//                mutableLiveDataMovies.value = objectResultMovie.resultMovies as ArrayList
//            }
//    }
//
//    fun close() {
//        if (mutableLiveDataMovies.value != null) {
//            setIsFetching(false)
//        }
//    }
}
