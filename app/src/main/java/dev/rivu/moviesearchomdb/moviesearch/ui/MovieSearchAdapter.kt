package dev.rivu.moviesearchomdb.moviesearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.rivu.moviesearchomdb.databinding.ItemAdapterMovieBinding
import dev.rivu.moviesearchomdb.moviesearch.data.model.Movie
import dev.rivu.moviesearchomdb.utils.load

class MovieSearchAdapter : ListAdapter<Movie, MovieSearchAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAdapterMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.ivMovie.load(item.poster)
        holder.binding.tvTitle.text = item.title
    }

    class ViewHolder(val binding: ItemAdapterMovieBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title.equals(
                newItem.title,
                false
            ) && oldItem.poster.equals(newItem.poster, false)
        }

    }
}