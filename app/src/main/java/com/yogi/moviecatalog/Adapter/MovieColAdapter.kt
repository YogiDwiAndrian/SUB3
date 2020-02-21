package com.yogi.moviecatalog.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yogi.moviecatalog.Models.ResultMovie
import com.bumptech.glide.request.RequestOptions
import com.yogi.moviecatalog.BuildConfig
import com.yogi.moviecatalog.R
import com.yogi.moviecatalog.Ui.DetailMovieActivity
import java.text.SimpleDateFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MovieColAdapter (private val context: DetailMovieActivity, private var resultMovieTheMovieDb: ArrayList<ResultMovie>) : RecyclerView.Adapter<MovieColAdapter.CardViewViewHolder>() {

    private val TAG = javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_col_movie, parent, false)
        return CardViewViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {

        val resultItem = resultMovieTheMovieDb[position]
        Glide.with(holder.itemView.context)
            .load(BuildConfig.BASE_URL_IMAGE + resultItem.posterPath)
            .apply(RequestOptions())
            .into(holder.imgPhoto)
        holder.tvTittle.text = resultItem.title

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
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_movie_col)
        var tvYear: TextView = itemView.findViewById(R.id.tv_year_col)
        var tvTittle: TextView = itemView.findViewById(R.id.tv_tittle_col)
    }

    fun refreshAdapter(resultMovieTheMovieDb: List<ResultMovie>) {
        this.resultMovieTheMovieDb.addAll(resultMovieTheMovieDb)
        notifyItemRangeChanged(0, this.resultMovieTheMovieDb.size)
    }
}