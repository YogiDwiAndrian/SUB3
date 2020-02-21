package com.yogi.moviecatalog.Ui.Fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yogi.moviecatalog.Adapter.MovieRowAdapter
import com.yogi.moviecatalog.Models.ResultMovie
import com.yogi.moviecatalog.R
import com.yogi.moviecatalog.ViewModels.MainViewModelMovie


/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : Fragment() {

    private lateinit var movieViewModel: MainViewModelMovie
    private lateinit var root: View
    private lateinit var adapter : MovieRowAdapter
    private lateinit var rvMovies: RecyclerView

    var listItems = ArrayList<ResultMovie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModelMovie::class.java)
        root = inflater.inflate(R.layout.fragment_movies, container, false)

        adapter = MovieRowAdapter(this , listItems)
        adapter.notifyDataSetChanged()

        movieViewModel.setMovies()
        movieViewModel.getMovie().observe(this, Observer {
            listItems.addAll(it)
            adapter.update(listItems)
        })

        setRecyclerList(root)
        return root
    }

    private fun setRecyclerList(root: View) {
        rvMovies = root.findViewById(R.id.rv_movie)
        rvMovies.layoutManager = LinearLayoutManager(root.context)
        rvMovies.adapter = adapter
    }
}
