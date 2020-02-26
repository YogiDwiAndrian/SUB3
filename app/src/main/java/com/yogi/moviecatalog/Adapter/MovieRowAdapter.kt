package com.yogi.moviecatalog.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yogi.moviecatalog.BuildConfig
import com.yogi.moviecatalog.Models.ResultMovie
import com.yogi.moviecatalog.R
import com.yogi.moviecatalog.Ui.DetailMovieActivity
import com.yogi.moviecatalog.Ui.Fragment.MoviesFragment
import java.text.SimpleDateFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MovieRowAdapter(private val context: MoviesFragment, private var resultMovieTheMovieDb: ArrayList<ResultMovie>) : RecyclerView.Adapter<MovieRowAdapter.CardViewViewHolder>() {


    fun update(data: ArrayList<ResultMovie>) {
        resultMovieTheMovieDb = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_movies, parent, false)
        return CardViewViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {

        val resultItem = resultMovieTheMovieDb[position]

        //Poster Image
        Glide.with(holder.itemView.context)
            .load(BuildConfig.BASE_URL_IMAGE + resultItem.posterPath)
            .apply(RequestOptions())
            .into(holder.imgPhoto)
        holder.tvTittle.text = resultItem.title
        holder.tvRateBar.rating = resultItem.voteAverage!!.toFloat() / 2
        holder.tvRateNum.text = resultItem.voteAverage.toString()

        //format date to year
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val convertDate = simpleDateFormat.parse(resultItem.releaseDate!!)
        simpleDateFormat = SimpleDateFormat("yyyy")
        holder.tvYear.text = simpleDateFormat.format(convertDate)

        //send parcelable data intent
        holder.itemView.setOnClickListener {

            val moveWithObjectIntent = Intent(holder.mContext, DetailMovieActivity::class.java)

            moveWithObjectIntent.putExtra(DetailMovieActivity.EXTRA_MOVIES, resultItem)
            holder.mContext.startActivity(moveWithObjectIntent)
        }

    }

    override fun getItemCount(): Int {
        return resultMovieTheMovieDb.size
    }

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mContext: Context = itemView.context
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_movie)
        var tvYear: TextView = itemView.findViewById(R.id.tv_year)
        var tvTittle: TextView = itemView.findViewById(R.id.tv_tittle)
        var tvRateBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        var tvRateNum: TextView = itemView.findViewById(R.id.tv_rate)
    }
}