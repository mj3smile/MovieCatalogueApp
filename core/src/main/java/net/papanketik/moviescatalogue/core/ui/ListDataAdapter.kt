package net.papanketik.moviescatalogue.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.papanketik.moviescatalogue.core.databinding.ItemListBinding
import net.papanketik.moviescatalogue.core.domain.model.MovieAndTvShow

class ListDataAdapter: PagingDataAdapter<MovieAndTvShow, ListDataAdapter.ListViewHolder>(
    DIFF_CALLBACK
) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieAndTvShow>() {
            override fun areItemsTheSame(oldItem: MovieAndTvShow, newItem: MovieAndTvShow): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: MovieAndTvShow, newItem: MovieAndTvShow): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val movieAndTvShow = getItem(position)

        if (movieAndTvShow != null) {
            holder.binding.tvItemTitle.text = movieAndTvShow.title
            holder.binding.tvRatingValue.text = movieAndTvShow.rating.toString()
            holder.binding.tvYear.text = movieAndTvShow.year
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(movieAndTvShow)
            }
            Glide.with(holder.itemView.context)
                .load(movieAndTvShow.poster)
                .into(holder.binding.imgPoster)
        }
    }

    class ListViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: MovieAndTvShow)
    }
}