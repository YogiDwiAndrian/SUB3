package com.yogi.moviecatalog.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yogi.moviecatalog.BuildConfig
import com.yogi.moviecatalog.Models.ResultTVShow
import com.yogi.moviecatalog.R
import com.yogi.moviecatalog.Ui.DetailTvShowActivity
import com.yogi.moviecatalog.Ui.Fragment.TVShowFragment
import kotlinx.android.synthetic.main.item_row_tvshow.view.*
import java.text.SimpleDateFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TvShowRowAdapter(private val context: TVShowFragment, private var resultTVShowTheMovieDb: ArrayList<ResultTVShow>) : RecyclerView.Adapter<TvShowRowAdapter.TvShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_tvshow, parent, false)
        return TvShowViewHolder(view)
    }

    fun update(data: ArrayList<ResultTVShow>) {
        resultTVShowTheMovieDb = data
        notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {

        val resultItem = resultTVShowTheMovieDb[position]

        Glide.with(holder.itemView.context)
            .load(BuildConfig.BASE_URL_IMAGE + resultItem.posterPath)
            .apply(RequestOptions())
            .into(holder.imgPhoto)
        holder.tvTittle.text = resultItem.name
        holder.tvRateBar.rating = resultItem.voteAverage!!.toFloat() / 2
        holder.tvRateNum.text = resultItem.voteAverage.toString()

        //format date to year
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val convertDate = simpleDateFormat.parse(resultItem.firstAirDate!!)
        simpleDateFormat = SimpleDateFormat("yyyy")
        holder.tvYear.text = simpleDateFormat.format(convertDate)



        //send parcelable data intent
        holder.itemView.setOnClickListener {

            val moveWithObjectIntent = Intent(holder.mContext, DetailTvShowActivity::class.java)

            moveWithObjectIntent.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, resultItem)
            holder.mContext.startActivity(moveWithObjectIntent)
        }

    }

    override fun getItemCount(): Int {
        return resultTVShowTheMovieDb.size
    }

    inner class TvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mContext: Context = itemView.context
        var imgPhoto: ImageView = itemView.img_tvshow
        var tvYear: TextView = itemView.tv_year_tvshow
        var tvTittle: TextView = itemView.tv_tittle_tvshow
        var tvRateBar: RatingBar = itemView.ratingBar_tvshow
        var tvRateNum: TextView = itemView.tv_rate_tvshow
    }
}